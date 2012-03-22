package mcgill.game;

import com.google.gson.Gson;

import redis.clients.jedis.JedisPubSub;

public class ServerListener extends JedisPubSub {

	Gson gson;
	
	public ServerListener() {
		this.gson = new Gson();
	}
	
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		System.out.println("PMessage");
		System.out.println("Channel: " + channel);
		System.out.println("Message: " + message);
		
		NetworkCommand nc = gson.fromJson(message, NetworkCommand.class);
		System.out.println("Command: " + nc.getCommand());
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		
	}
	
	@Override
	public void onMessage(String arg0, String arg1) {}

	@Override
	public void onSubscribe(String arg0, int arg1) {}

	@Override
	public void onUnsubscribe(String arg0, int arg1) {}

}
