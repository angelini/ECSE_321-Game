package mcgill.game;

import java.lang.reflect.Method;

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
		NetworkCommand nc = gson.fromJson(message, NetworkCommand.class);
		
		try {
			Method method = Class.forName("mcgill.game.Server")
								 .getMethod(nc.getCommand(), new Class[] {Object.class});
			method.invoke(this.server, (Object) nc.getArgs());
			
		} catch (Exception e) {
			e.printStackTrace();
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
