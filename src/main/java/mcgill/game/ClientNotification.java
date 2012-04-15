package mcgill.game;

import java.util.Map;

import com.google.gson.Gson;

import mcgill.fiveCardStud.EndOfRound;
import mcgill.poker.Hand;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Notifies client
 */
public class ClientNotification {

	private Gson gson;
	private Jedis emit;
	private Jedis subscribe;
	private String session;
	private String response;
	
	private class Listener extends JedisPubSub {

		private ClientNotification clientNotify;
		private String key;
		private String message;
		
		public Listener(ClientNotification clientNotify, String key, String message) {
			this.clientNotify = clientNotify;
			this.key = key;
			this.message = message;
		}
		
		public void onMessage(String channel, String message) {
			this.clientNotify.setResponse(message);
			this.unsubscribe();
		}

		public void onPMessage(String arg0, String arg1, String arg2) {}

		public void onPSubscribe(String arg0, int arg1) {}

		public void onPUnsubscribe(String arg0, int arg1) {}

		public void onSubscribe(String arg0, int arg1) {
			this.clientNotify.emit.publish(this.key, this.message);
		}

		public void onUnsubscribe(String arg0, int arg1) {}
		
	}
	
	public ClientNotification(String session) {
		this.session = session;
		this.gson = new Gson();
		
		this.emit = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
		this.subscribe = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT, 0);
	}
	
	public String getCommand(int[] limits) {
		String n_key = Database.cat(Config.NOTIFICATIONS, Config.GET_COMMAND, this.session);
		String c_key = Database.cat(Config.COMMAND, this.session);
		
		Listener listener = new Listener(this, n_key, this.gson.toJson(limits));
		
		this.subscribe.subscribe(listener, c_key);
		
		return this.response;
	}

	public void sendHand(Map<String, Hand> hands) {
		String n_key = Database.cat(Config.NOTIFICATIONS, Config.EMIT_HANDS, this.session);
		HandsSerializable serializable = new HandsSerializable(hands);
		this.emit.publish(n_key, this.gson.toJson(serializable));
	}
	
	public void sendUsers(User[] users) {
		String n_key = Database.cat(Config.NOTIFICATIONS, Config.TABLE_USERS, this.session);
		this.emit.publish(n_key, this.gson.toJson(users));
	}
	
	public void sendMessage(String chat_id) {
		String n_key = Database.cat(Config.NOTIFICATIONS, Config.MESSAGE_REC, this.session);
		this.emit.publish(n_key, chat_id);
	}
	
	public void potAndStatus(int[] current) {
		String n_key = Database.cat(Config.NOTIFICATIONS, Config.POT_STATUS, this.session); 
		this.emit.publish(n_key, this.gson.toJson(current));
	}
	
	public void sendEndOfRound(EndOfRound end) {
		String n_key = Database.cat(Config.NOTIFICATIONS, Config.END_OF_ROUND, this.session); 
		this.emit.publish(n_key, this.gson.toJson(end));
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
