package mcgill.game;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.event.EventListenerList;

import com.google.gson.Gson;

/**
 * Implements client
 */
public class Client implements Runnable {
	
	private User user;
	private Table table;
	
	private Gson gson;
	private String session;
	private Notifications notifications;
	
	private EventListenerList eventlist;
	
	public static void main(String[] args) {
    	Client client = new Client(Config.REDIS_HOST, Config.REDIS_PORT);
    	client.run();
    }
	
	public Client(String host, int port) {
		this.gson = new Gson();
		this.session = UUID.randomUUID().toString();
		this.notifications = new Notifications(this);
		this.eventlist = new EventListenerList();
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Table getTable() {
		return this.table;
	}
	
	public void addEventListener(ClientEventListener elistener) {
		this.eventlist.add(ClientEventListener.class, elistener);
	}
	
	public void removeEventListener(ClientEventListener elistener) {
		this.eventlist.remove(ClientEventListener.class, elistener);
	}
	
	public void fireEvent(ClientEvent e) {
		Object[] elisteners = this.eventlist.getListenerList();
		for (int i = 0; i < elisteners.length; i = i+2) {
			if (elisteners[i] == ClientEventListener.class) {
				((ClientEventListener) elisteners[i + 1]).eventOccured(e);
			}
		}
	}
	
	public Boolean register(String username, String password) {
		String[] args = {this.session, username, password};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.REGISTER, args);
		server.close();
		
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
		server.close();
		
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
		server.close();
		
		return this.gson.fromJson(res, User[].class);
	}
	
	public Boolean addFriend(String username, String friend) {
		String[] args = {this.session, username, friend};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.ADD_FRIEND, args);
		server.close();
		
		return this.gson.fromJson(res, Boolean.class);	
	}
	
	public Chat[] getChats(String username) {
		String[] args = {this.session, username};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.GET_CHATS, args);
		server.close();
		
		return this.gson.fromJson(res, Chat[].class);
	}
	
	public Chat getChat(String chat_id) {
		String[] args = {this.session, chat_id};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.GET_CHAT, args);
		server.close();
		
		return this.gson.fromJson(res, Chat.class);
	}
	
	public Chat createChat(String username, String friend) {
		String[] args = {this.session, username, friend};
			
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.CREATE_CHAT, args);
		server.close();
		
		return this.gson.fromJson(res, Chat.class);
	}
	
	public Chat sendMessage(String username, String message, String chat_id) {
		String[] args = {this.session, username, message, chat_id};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.MESSAGE, args);
		server.close();
		
		return this.gson.fromJson(res, Chat.class);
	}
	
	public User addCredits(String username) {			
		String[] args = {this.session, username};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.ADD_CREDITS, args);
		server.close();
		
		return this.gson.fromJson(res, User.class);	
	}
	
	public Table[] getTables() {
		String[] args = {this.session};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.GET_TABLES, args);
		server.close();
		
		return this.gson.fromJson(res, Table[].class);
	}
	
	public Table createTable(String name) {
		String[] args = {this.session, name};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.CREATE_TABLE, args);
		server.close();
		
		return this.gson.fromJson(res, Table.class);
	}
	
	public Boolean joinTable(String username, String table_id) {
		String[] args = {this.session, username, table_id};
		
		ServerCall server = new ServerCall(this.session);
		String res = server.call(Config.JOIN_TABLE, args);
		server.close();
		
		Table table = this.gson.fromJson(res, Table.class);
		this.table = table;
		
		if (table == null) {
			return false;
		} else {
			return true;
		}
	}

	public void startRound(String table_id) {
		String[] args = {this.session, table_id};
		
		ServerCall server = new ServerCall(this.session);
		server.call(Config.START_ROUND, args);
		server.close();
	}
	
	public void logout() {
		String[] args = {this.session};
		
		ServerCall server = new ServerCall(this.session);
		server.call(Config.LOGOUT, args);
		server.close();
	}
	
	public String getSession() {
		return this.session;
	}
	
	public void run() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(this.notifications);
	}
	
}
