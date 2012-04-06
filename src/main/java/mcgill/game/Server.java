package mcgill.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;

public class Server {
	
	public Jedis emit;
	public Jedis subscribe;
	public ExecutorService executor;
	
	private Gson gson;
	private Database db;
	private ServerListener listener;
	private Map<String, User> session;
	
    public static void main(String[] args) {
    	Server server = new Server(Config.REDIS_HOST, Config.REDIS_PORT);
    	System.out.println("Server Ready");
    	server.start();
    }
    
    public Server(String host, int port) {
    	this.gson = new Gson();
    	this.db = new Database(Config.REDIS_HOST, Config.REDIS_PORT);
    	this.listener = new ServerListener(this);
    	this.session = new HashMap<String, User>();
    	
    	this.emit = new Jedis(host, port);
    	this.subscribe = new Jedis(host, port, 0);
    	
    	this.executor = Executors.newFixedThreadPool(Config.THREADS);
    }
    
    public void start() {
    	this.subscribe.psubscribe(this.listener, "server::*");
    }
    
    public String getUserSession(String username) {
		for (Map.Entry<String, User> entry : this.session.entrySet()) {
			if (entry.getValue().getUsername().equals(username)) {
				return entry.getKey();
			}
		}
		
		return null;
	}
    
    public void login(String c_key, String[] args) {
    	String session_str = args[0];
    	String username = args[1];
    	String password = args[2];
    	
    	User user = User.verifyUser(username, password, this.db);
    	this.session.put(session_str, user);
    	
    	System.out.println("Session is: " + this.gson.toJson(this.session));
    	
    	if (user == null) {
    		this.emit.publish(c_key, "");
    	} else {
    		this.emit.publish(c_key, this.gson.toJson(user));
    	}
    }
    
    public void register(String c_key, String[] args) {
    	String session_str = args[0];
    	String username = args[1];
    	String password = args[2];
    	
    	User user = User.createUser(username, password, this.db);
    	this.session.put(session_str, user);
    	
    	if (user == null) {
    		this.emit.publish(c_key, "");
    	} else {
    		this.emit.publish(c_key, this.gson.toJson(user));
    	}
    }
    
    public void getFriends(String c_key, String[] args) {
    	String username = args[1];
    	
    	User user = this.db.getUser(username, true);
    	if (user == null) {
    		this.emit.publish(c_key, "");
    		return;
    	}
    	
    	this.emit.publish(c_key, this.gson.toJson(user.getFriends()));
    }
    
    public void addFriend(String c_key, String[] args) {
    	String username = args[1];
    	String friend_name = args[2];
    	
    	User user = this.db.getUser(username, true);
    	User friend = this.db.getUser(friend_name, false);
    	
    	if (user == null || friend == null) {
    		this.emit.publish(c_key, "");
    		return;
    	}
    	
    	this.db.addFriend(username, friend_name);
    	this.emit.publish(c_key, "");
    }
    
    public void getChats(String c_key, String[] args) {
    	String  username = args[1];
    	
    	List<Chat> chats = this.db.getUserChats(username);
    	this.emit.publish(c_key, this.gson.toJson(chats));
    }
    
    public void createChat(String c_key, String[] args) {
    	String username = args[1];
    	String friend_name = args[2];
    	
    	User user = this.db.getUser(username, true);
    	User friend = this.db.getUser(friend_name, false);
    	
    	if (user == null || friend == null) {
    		this.emit.publish(c_key, "");
    		return;
    	}
    	
    	Set<User> users = new HashSet<User>();
    	users.add(user);
    	users.add(friend);
    	Chat chat = new Chat(users);
    	this.db.setChat(chat);
    	this.emit.publish(c_key, this.gson.toJson(chat));
    }
    
    public void message(String c_key, String[] args) {
    	String username = args[1];
    	String message_text = args[2];
    	String chat_id = args[3];
    	
    	User user = this.db.getUser(username, false);
    	if (user == null) {
    		this.emit.publish(c_key, "");
    		return;
    	}
    	
    	Message message = new Message(message_text, user.getUsername(), this.db);
    	
    	Chat chat = this.db.getChat(chat_id);
    	chat.addMessage(message);
    	this.db.setChat(chat);
    	
    	this.emit.publish(c_key, this.gson.toJson(chat));
    }
    
    public void addCredits(String c_key, String[] args) {
    	String username = args[1];
    	
    	User user = this.db.getUser(username, false);
    	if (user == null) {
    		this.emit.publish(c_key, "");
    		return;
    	}
    	
    	if (user.getCredits() < Config.REFILL_CREDITS) {
    		user.setCredits(Config.REFILL_CREDITS);
    	}
    	
    	this.db.setUser(user);
    	
    	this.emit.publish(c_key, this.gson.toJson(user));
    }
    
    public void getTables(String c_key, String[] args) {
    	this.emit.publish(c_key, this.gson.toJson(this.db.getTables()));
    }
    
    public void createTable(String c_key, String[] args) {
    	String username = args[1];
    	String name = args[2];
    	
    	User user = this.db.getUser(username, false);
    	if (user == null) {
    		this.emit.publish(c_key, "");
    		return;
    	}
    	
    	Table table = new Table(name);
    	table.addUser(user);
    	this.db.setTable(table);
    	this.emit.publish(c_key, this.gson.toJson(table));
    }

	public void joinTable(String c_key, String[] args) {
		String username = args[1];
		String table_id = args[2];
		
		User user = this.db.getUser(username, false);
		Table table = this.db.getTable(table_id);
		
		if (user == null || table == null) {
			this.emit.publish(c_key, "");
    		return;
		}
		
		Boolean result = table.addUser(user);
		this.db.setTable(table);
		this.emit.publish(c_key, this.gson.toJson(result));
	}

	public void startRound(String c_key, String[] args) {
		String table_id = args[1];
		
		Table table = this.db.getTable(table_id);
		table.startRound(this);
		
		this.emit.publish(c_key, "");
	}
	
	public int getAction(String username, int call_amount) {
		System.out.println("Get Action for " + username);
		
		String session_str = this.getUserSession(username);
		ClientNotification notification = new ClientNotification(session_str);
		
		String command = notification.getCommand(call_amount);
		
		return Integer.parseInt(command);
	}
    
}
