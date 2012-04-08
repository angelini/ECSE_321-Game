package mcgill.game;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Notifications implements Runnable {
 
	private Client client;
	private Jedis emit;
	private Jedis subscribe;
	private String session;
	
	private class Listener extends JedisPubSub {

		private Notifications notifications;
		
		public Listener(Notifications notifications) {
			this.notifications = notifications;
		}

		public void onPMessage(String pattern, String channel, String message) {
			String[] keys = Database.split(channel);
			String method = keys[1];
			String session = keys[2];
			
			String c_key = Database.cat(Config.COMMAND, session);
			
			if (method.equals(Config.GET_COMMAND)) {
				this.notifications.getCommand(c_key, message);
			}
		}

		public void onMessage(String channel, String message) {}
		
		public void onPSubscribe(String arg0, int arg1) {}

		public void onPUnsubscribe(String arg0, int arg1) {}

		public void onSubscribe(String arg0, int arg1) {}

		public void onUnsubscribe(String arg0, int arg1) {}
		
	}
	
	public Notifications(Client client) {
		this.client = client;
		this.session = client.getSession();
		
		this.subscribe = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT, 0);
		this.emit = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
	}
	
	public void run() {
		Listener listener = new Listener(this);
		this.subscribe.psubscribe(listener, Database.cat(Config.NOTIFICATIONS, "*", this.session));
	}
	
	public void getCommand(final String c_key, String call_amount) {
		System.out.println("Notifications.getCommand");
		
		ClientEvent event = new ClientEvent(new Object());
		event.setType(ClientEvent.ACTION);
		
		this.client.addEventListener(new ClientEventListener() {
			
			public void eventOccured(ClientEvent e) {
				if (e.getType() == ClientEvent.ACTION_REC) {
					emit.publish(c_key, Integer.toString(e.getAction()));
				}
			}
		});
		
		this.client.fireEvent(event);
	}
	
}
