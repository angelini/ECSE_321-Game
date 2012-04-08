package mcgill.game;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class ClientNotification {

	private Jedis jedis;
	private String session;
	private String response;
	
	private class Listener extends JedisPubSub {

		private ClientNotification clientNotify;
		
		public Listener(ClientNotification clientNotify) {
			this.clientNotify = clientNotify;
		}
		
		public void onMessage(String channel, String message) {
			this.clientNotify.setResponse(message);
			this.unsubscribe();
		}

		public void onPMessage(String arg0, String arg1, String arg2) {}

		public void onPSubscribe(String arg0, int arg1) {}

		public void onPUnsubscribe(String arg0, int arg1) {}

		public void onSubscribe(String arg0, int arg1) {}

		public void onUnsubscribe(String arg0, int arg1) {}
		
	}
	
	public ClientNotification(String session) {
		this.session = session;
		this.jedis = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT, 0);
	}
	
	public String getCommand(int call_amount) {
		String n_key = Database.cat(Config.NOTIFICATIONS, Config.GET_COMMAND, this.session);
		String c_key = Database.cat(Config.COMMAND, this.session);
		
		Listener listener = new Listener(this);
		
		this.jedis.publish(n_key, Integer.toString(call_amount));
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
