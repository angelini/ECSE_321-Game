package mcgill.game;

import java.util.UUID;

import com.google.gson.Gson;

public class Client implements Runnable {
	
	private User user;
	
	private Gson gson;
	private String session;
	private Notifications notifications;
	
	public static void main(String[] args) {
    	Client client = new Client(Config.REDIS_HOST, Config.REDIS_PORT);
    	client.run();
    }
	
	public Client(String host, int port) {
		this.gson = new Gson();
		this.session = UUID.randomUUID().toString();
		this.notifications = new Notifications(this.session);
	}
	
	public User getUser() {
		return this.user;
	}
	
	public Boolean register(String username, String password) {
		String[] args = {this.session, username, password};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.REGISTER, args);
		
		if (res.equals("")) {
			return false;
		}
		
		this.user = this.gson.fromJson(res, User.class);
		return true;
	}
	
	public Boolean login(String username, String password) {
		String[] args = {this.session, username, password};
		
		ServerCall server = new ServerCall(this.session);
		String result = server.call(Config.LOGIN, args);
		
		if (result.equals("")) {
			return false;
		}
		
		this.user = this.gson.fromJson(result, User.class);
		return true;
	}
	
	public User[] getFriends(String username) {
		String[] args = {this.session, username};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.GET_FRIENDS, args);
		
		return this.gson.fromJson(res, User[].class);
	}
	
	public Boolean addFriend(String username, String friend) {
		String[] args = {this.session, username, friend};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.ADD_FRIEND, args);
		
		return this.gson.fromJson(res, Boolean.class);	
	}
	
	public Chat[] getChats(String username) {
		String[] args = {this.session, username};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.GET_CHATS, args);
		
		return this.gson.fromJson(res, Chat[].class);
	}
	
	public Chat createChat(String username, String friend) {
		String[] args = {this.session, username, friend};
			
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.CREATE_CHAT, args);
		
		return this.gson.fromJson(res, Chat.class);
	}
	
	public Chat sendMessage(String username, String message, String chat_id) {
		String[] args = {this.session, username, message, chat_id};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.MESSAGE, args);
		
		return this.gson.fromJson(res, Chat.class);
	}
	
	public User addCredits(String username) {			
		String[] args = {this.session, username};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.ADD_CREDITS, args);
		
		return this.gson.fromJson(res, User.class);	
	}
	
	public Table[] getTables() {
		String[] args = {this.session};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.GET_TABLES, args);
		
		return this.gson.fromJson(res, Table[].class);
	}
	
	public Table createTable(String username, String name) {
		String[] args = {this.session, username, name};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.CREATE_TABLE, args);
		
		return this.gson.fromJson(res, Table.class);
	}
	
	public Table joinTable(String username, String table_id) {
		String[] args = {this.session, username, table_id};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.JOIN_TABLE, args);
		
		return this.gson.fromJson(res, Table.class);
	}

	public void startRound(String table_id) {
		String[] args = {this.session, table_id};
		
		ServerCall server = new ServerCall(this.session);
		server.call(Config.START_ROUND, args);
	}
	
	public void logout() {
		String[] args = {this.session};
		
		ServerCall server = new ServerCall(this.session);
		server.call(Config.LOGOUT, args);
	}
	
	public void run() {
		this.notifications.run();
	}
	
}
