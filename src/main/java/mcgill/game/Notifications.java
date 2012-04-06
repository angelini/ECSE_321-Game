package mcgill.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Notifications implements Runnable {
 
	private Jedis jedis;
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
	
	public Notifications(String session) {
		this.session = session;
		this.jedis = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
	}
	
	public void run() {
		Listener listener = new Listener(this);
		this.jedis.psubscribe(listener, Database.cat(Config.NOTIFICATIONS, "*", this.session));
	}
	
	public void getCommand(String c_key, String call_amount) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.println("\n\nAmount to call is " + call_amount);
			System.out.print("0 is check, -1 is fold, any positive integer is a raise or call: ");
			String command = br.readLine();
			
			System.out.println("C_Key " + c_key);
			System.out.println("Command " + command);
			
			this.jedis.publish(c_key, command);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
