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
	
	private static Map<String, User> session = new HashMap<String, User>();
	
	public static String getUserSession(String username) {
		for (Map.Entry<String, User> entry : session.entrySet()) {
			if (entry.getValue().getUsername().equals(username)) {
				return entry.getKey();
			}
		}
		
		return null;
	}
	
    public static void main(String[] args) {
    	Server server = new Server(Config.REDIS_HOST, Config.REDIS_PORT);
    	System.out.println("Server Ready");
    	server.start();
    }
    
    public Server(String host, int port) {
    	this.gson = new Gson();
    	this.db = new Database(Config.REDIS_HOST, Config.REDIS_PORT);
    	this.listener = new ServerListener(this);
    	
    	this.emit = new Jedis(host, port);
    	this.subscribe = new Jedis(host, port, 0);
    	
    	this.executor = Executors.newFixedThreadPool(Config.THREADS);
    }
    
    public void start() {
    	this.subscribe.psubscribe(this.listener, "server::*");
    }
    
    public void login(String c_key, String[] args) {
    	String session_str = args[0];
    	String username = args[1];
    	String password = args[2];
    	
    	User user = User.verifyUser(username, password, this.db);
    	session.put(session_str, user);
    	
    	if (user == null) {
    		this.emit.publish(c_key, "");
    	} else {
    		this.emit.publish(c_key, this.gson.toJson(user));
    	}
    }
    
    public void logout(String c_key, String[] args) {
    	String session_str = args[0];
    	
    	session.remove(session_str);
    	this.emit.publish(c_key, "");
    }
    
    public void register(String c_key, String[] args) {
    	String session_str = args[0];
    	String username = args[1];
    	String password = args[2];
    	
    	User user = User.createUser(username, password, this.db);
    	session.put(session_str, user);
    	
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
    		this.emit.publish(c_key, "false");
    		return;
    	}
    	
    	this.db.addFriend(username, friend_name);
    	this.emit.publish(c_key, "true");
    }
    
    public void getChats(String c_key, String[] args) {
    	String  username = args[1];
    	
    	List<Chat> chats = this.db.getUserChats(username);
    	this.emit.publish(c_key, this.gson.toJson(chats));
    }
    
    public void getChat(String c_key, String[] args) {
    	String chat_id = args[1];
		
    	Chat chat = this.db.getChat(chat_id);
    	this.emit.publish(c_key, this.gson.toJson(chat));
	}
    
    public void createChat(String c_key, String[] args) {
    	String username = args[1];
    	String friend_name = args[2];
    	
    	User user = this.db.getUser(username, true);
    	User friend = this.db.getUser(friend_name, false);
    	
    	if (user == null || friend == null) {
    		this.emit.publish(c_key, "null");
    		return;
    	}
    	
    	Set<User> users = new HashSet<User>();
    	users.add(user);
    	users.add(friend);
    	
    	Chat exists = this.db.getChatWithUsers(users);
    	
    	if (exists != null) {
    		this.emit.publish(c_key, this.gson.toJson(exists));
    	
    	} else {
    		Chat chat = new Chat(users);
        	this.db.setChat(chat);
        	this.emit.publish(c_key, this.gson.toJson(chat));
    	}
    }
    
    public void message(String c_key, String[] args) {
    	String username = args[1];
    	String message_text = args[2];
    	String chat_id = args[3];
    	
    	User user = this.db.getUser(username, false);
    	if (user == null) {
    		this.emit.publish(c_key, "null");
    		return;
    	}
    	
    	Message message = new Message(message_text, user.getUsername(), this.db);
    	
    	Chat chat = this.db.getChat(chat_id);
    	chat.addMessage(message);
    	this.db.setChat(chat);
    	
    	for (User c_user : chat.getUsers()) {
    		String session_str = Server.getUserSession(c_user.getUsername());
			ClientNotification notification = new ClientNotification(session_str);
			notification.sendMessage(chat.getId());
    	}
    	
    	this.emit.publish(c_key, this.gson.toJson(chat));
    }
    
    public void addCredits(String c_key, String[] args) {
    	String username = args[1];
    	
    	User user = this.db.getUser(username, false);
    	if (user == null) {
    		this.emit.publish(c_key, "null");
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
    	String name = args[1];
    	
    	Table table = new Table(name);
    	this.db.setTable(table);
    	this.emit.publish(c_key, this.gson.toJson(table));
    }

	public void joinTable(String c_key, String[] args) {
		String username = args[1];
		String table_id = args[2];
		
		User user = this.db.getUser(username, false);
		Table table = this.db.getTable(table_id);
		
		if (user == null || table == null) {
			this.emit.publish(c_key, "null");
    		return;
		}
		
		Boolean result = table.addUser(user);
		this.db.setTable(table);
		
		if (result) {
			this.emit.publish(c_key, this.gson.toJson(table));
		} else {
			this.emit.publish(c_key, this.gson.toJson(null));
		}
	}

	public void startRound(String c_key, String[] args) {
		String table_id = args[1];
		
		Table table = this.db.getTable(table_id);
		table.startRound(this);
		
		this.emit.publish(c_key, "");
	}
    
}
