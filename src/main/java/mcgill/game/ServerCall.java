package mcgill.game;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class ServerCall {

	private Gson gson;
	private Jedis jedis;
	private String session;
	private String response;
	
	private class ClientListener extends JedisPubSub {

		private ServerCall serverCall;
		
		public ClientListener(ServerCall serverCall) {
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
		this.jedis = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
	}
	
	public String call(String method, String[] args) {
		String s_key = Database.cat(Config.SERVER, method, this.session);
		String c_key = Database.cat(Config.CLIENT, this.session);
		
		ClientListener listener = new ClientListener(this);
		
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
