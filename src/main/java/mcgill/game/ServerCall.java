package mcgill.game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class ServerCall {

	private Gson gson;
	private Jedis jedis;
	private String session;
	private String response;
	private String message;
	
	private class Listener extends JedisPubSub {

		private ServerCall serverCall;
		
		public Listener(ServerCall serverCall) {
			this.serverCall = serverCall;
		}
		
		public void onMessage(String channel, String message) {
			this.serverCall.setResponse(message);
			this.unsubscribe();
		}

		public void onPMessage(String arg0, String arg1, String arg2) {}

		public void onPSubscribe(String arg0, int arg1) {}

		public void onPUnsubscribe(String arg0, int arg1) {}

		public void onSubscribe(String arg0, int arg1) {}

		public void onUnsubscribe(String arg0, int arg1) {}
		
	}
	
	public ServerCall(String session) {
		this.session = session;
		this.gson = new Gson();
		this.jedis = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT, 0);
	}
	
	public void publish(final String key, final String message) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(new Runnable() {
			public void run() {
				try {
					Thread.sleep(100);
					Jedis jedis = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
					jedis.publish(key, message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public String call(String method, String[] args) {
		String s_key = Database.cat(Config.SERVER, method, this.session);
		String c_key = Database.cat(Config.CLIENT, this.session);
		
		Listener listener = new Listener(this);
		
		this.jedis.publish(s_key, this.gson.toJson(args));
		this.jedis.subscribe(listener, c_key);
		
		return this.response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
}
