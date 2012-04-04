package mcgill.game;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class Server {
	
	public Jedis subscribe;
	public Jedis emitJedis;
	private Database db;
	private ServerListener listener;
	private Map<String, User> session;
	
    public static void main(String[] args) {
    	Server server = new Server(Config.REDIS_HOST, Config.REDIS_PORT);
    	System.out.println("Server Ready");
    	server.start();
    }
    
    public Server(String host, int port) {
    	this.db = new Database(Config.REDIS_HOST, Config.REDIS_PORT);
    	this.listener = new ServerListener(this);
    	this.session = new HashMap<String, User>();
    	
    	this.subscribe = new Jedis(host, port, 0);
    	this.emitJedis = new Jedis(host, port);
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
    		this.emitJedis.publish(c_key, "");
    	} else {
    		this.emitJedis.publish(c_key, user.getUsername());
    	}
    }
    
    public void register(String c_key, String[] args) {
    	String session = args[0];
    	String username = args[1];
    	String password = args[2];
    	
    	User user = User.createUser(username, password, this.db);
    	this.session.put(session, user);
    	
    	if (user == null) {
    		this.emitJedis.publish(c_key, "");
    	} else {
    		this.emitJedis.publish(c_key, user.getUsername());
    	}
    }
    
}
