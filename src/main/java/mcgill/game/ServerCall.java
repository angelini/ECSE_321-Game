package mcgill.game;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class ServerCall {

	private Gson gson;
	private Jedis subscribe;
	private Jedis emit;
	private String session;
	private String response;
	
	private class Listener extends JedisPubSub {

		private ServerCall serverCall;
		private String key;
		private String message;
		
		public Listener(ServerCall serverCall, String key, String message) {
			this.serverCall = serverCall;
			this.key = key;
			this.message = message;
		}
		
		public void onMessage(String channel, String message) {
			this.serverCall.setResponse(message);
			this.unsubscribe();
		}

		public void onPMessage(String arg0, String arg1, String arg2) {}

		public void onPSubscribe(String arg0, int arg1) {}

		public void onPUnsubscribe(String arg0, int arg1) {}

		public void onSubscribe(String arg0, int arg1) {
			this.serverCall.emit.publish(this.key, this.message);
		}

		public void onUnsubscribe(String arg0, int arg1) {}
		
	}
	
	public ServerCall(String session) {
		this.session = session;
		this.gson = new Gson();
		
		this.subscribe = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT, 0);
		this.emit = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
	}
	
	public String call(String method, String[] args) {
		String s_key = Database.cat(Config.SERVER, method, this.session);
		String c_key = Database.cat(Config.CLIENT, this.session);
		
		Listener listener = new Listener(this, s_key, this.gson.toJson(args));
		
		this.subscribe.subscribe(listener, c_key);
		
		return this.response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	public void close() {
		this.emit.quit();
		this.subscribe.quit();
	}
	
}
