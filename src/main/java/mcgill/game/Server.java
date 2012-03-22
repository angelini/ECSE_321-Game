package mcgill.game;

import redis.clients.jedis.Jedis;

public class Server {
	
	Jedis jedis;
	ServerListener listener;
	
    public static void main(String[] args) {
    	Server server = new Server(Config.REDIS_HOST, Config.REDIS_PORT);
    	server.start();
    }
    
    public Server(String host, int port) {
    	this.jedis = new Jedis(host, port);
    	this.listener = new ServerListener();
    }
    
    public void start() {
    	this.jedis.psubscribe(this.listener, "server::*");
    	System.out.println("Server ready");
    }
    
}
