package mcgill.game;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class Server {
	
	Jedis jedis;
	Database db;
	ServerListener listener;
	Map<String, User> session;
	
    public static void main(String[] args) {
    	Server server = new Server(Config.REDIS_HOST, Config.REDIS_PORT);
    	System.out.println("Server Ready");
    	server.start();
    }
    
    public Server(String host, int port) {
    	this.db = new Database(Config.REDIS_HOST, Config.REDIS_PORT);
    	this.jedis = new Jedis(host, port);
    	this.listener = new ServerListener(this);
    	this.session = new HashMap<String, User>();
    }
    
    public void start() {
    	this.jedis.psubscribe(this.listener, "server::*");
    }
    
    public User login(Object args) {
    	String[] typed_args = (String[]) args;
    	String session = typed_args[0];
    	String username = typed_args[1];
    	String password = typed_args[2];
    	
    	User user = User.verifyUser(username, password, this.db);
    	this.session.put(session, user);
    	
    	return user;
    }
    
    public User register(Object args) {
    	String[] typed_args = (String[]) args;
    	String session = typed_args[0];
    	String username = typed_args[1];
    	String password = typed_args[2];
    	
    	User user = User.verifyUser(username, password, this.db);
    	this.session.put(session, user);
    	
    	return user;
    }
    
}
