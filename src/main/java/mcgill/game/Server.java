package mcgill.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;

public class Server {
	
	public Jedis emit;
	public Jedis subscribe;
	
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
    }
    
    public void start() {
    	this.subscribe.psubscribe(this.listener, "server::*");
    }
    
    public void login(String c_key, String[] args) {
    	String session = args[0];
    	String username = args[1];
    	String password = args[2];
    	
    	User user = User.verifyUser(username, password, this.db);
    	this.session.put(session, user);
    	
    	if (user == null) {
    		this.emit.publish(c_key, "");
    	} else {
    		this.emit.publish(c_key, this.gson.toJson(user));
    	}
    }
    
    public void register(String c_key, String[] args) {
    	String session = args[0];
    	String username = args[1];
    	String password = args[2];
    	
    	User user = User.createUser(username, password, this.db);
    	this.session.put(session, user);
    	
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
    	
    	this.emit.publish(c_key, this.gson.toJson(user));
    }
    
}
