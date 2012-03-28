package mcgill.game;

import com.google.gson.Gson;

import redis.clients.jedis.JedisPubSub;

public class ServerListener extends JedisPubSub {

	Gson gson;
	Server server;
	
	public ServerListener(Server server) {
		this.gson = new Gson();
		this.server = server;
	}
	
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		String[] keys = Database.split(channel);
		String method = keys[1];
		
		if (method.equals(Config.LOGIN)) {
			this.server.login(this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.REGISTER)) {
			this.server.register(this.gson.fromJson(message, String[].class));
		}
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {}
	
	@Override
	public void onMessage(String arg0, String arg1) {}

	@Override
	public void onSubscribe(String arg0, int arg1) {}

	@Override
	public void onUnsubscribe(String arg0, int arg1) {}

}
