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
		String session = keys[2];
		
		String c_key = Database.cat(Config.CLIENT, session);
		
		if (method.equals(Config.LOGIN)) {
			this.server.login(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.REGISTER)) {
			this.server.register(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.GET_FRIENDS)) {
			this.server.getFriends(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.ADD_FRIEND)) {
			this.server.addFriend(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.GET_CHATS)) {
			this.server.getChats(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.GET_CHATS)) {
			this.server.getChat(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.CREATE_CHAT)) {
			this.server.createChat(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.MESSAGE)) {
			this.server.message(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.ADD_CREDITS)) {
			this.server.addCredits(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.GET_TABLES)) {
			this.server.getTables(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.CREATE_TABLE)) {
			this.server.createTable(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.JOIN_TABLE)) {
			this.server.joinTable(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.START_ROUND)) {
			this.server.startRound(c_key, this.gson.fromJson(message, String[].class));
		}
		
		if (method.equals(Config.LOGOUT)) {
			this.server.logout(c_key, this.gson.fromJson(message, String[].class));
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
