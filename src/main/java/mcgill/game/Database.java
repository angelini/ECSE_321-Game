package mcgill.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class Database {

	static final String SEPERATOR = "::";
	
	static final String USERS = "users";
	static final String FRIENDS = "friends";
	static final String CHATS = "chats";
	static final String MESSAGES = "messages";
	static final String TABLES = "tables";
	
	private Jedis jedis;
	
	public static String cat(String ... keys) {
		String result = "";
		
		for (int i = 0; i < keys.length; i++) {
			result += keys[i];
			if (i != keys.length - 1) result += SEPERATOR;
		}
		
		return result;
	}
	
	public static String[] split(String key) {
		return key.split(SEPERATOR);
	}
	
	public Database(String host, int port) {
		this.jedis = new Jedis(host, port);
	}
	
	// USER DB Adapter
	
	public User getUser(String username, boolean include_friends) {
		Map<String, String> info = this.jedis.hgetAll(cat(USERS, username));
		
		if (info.get("username") == null) return null;
		
		if (include_friends) {
			Set<String> friend_names = this.jedis.smembers(cat(FRIENDS, username));
			List<User> friends = new ArrayList<User>();
			
			for (String friend : friend_names) {
				friends.add(getUser(friend, false));
			}
			
			return new User(info, friends);
		}
		
		return new User(info);
	}
	
	public void setUser(User user) {
		this.jedis.hmset(cat(USERS, user.getUsername()), user.getMap());
	}
	
	public void addFriend(String username, String friend) {
		this.jedis.sadd(cat(FRIENDS, username), friend);
	}
	
	public void delUser(String username) {
		this.jedis.del(cat(USERS, username));
		this.jedis.del(cat(FRIENDS, username));
	}
	
	// MESSAGE DB Adapter
	
	public Message getMessage(String id) {
		Map<String, String> info = this.jedis.hgetAll(cat(MESSAGES, id));
		return new Message(info);
	}
	
	public void setMessage(Message message) {
		this.jedis.hmset(cat(MESSAGES, message.getId()), message.getMap());
	}
	
	public void delMessage(String id) {
		this.jedis.del(cat(MESSAGES, id));
	}
	
	// CHAT DB Adapter
	
	public Chat getChat(String id) {
		Set<User> users = new HashSet<User>();
		List<Message> messages = new ArrayList<Message>();
		
		Set<String> usernames = this.jedis.smembers(cat(CHATS, id, USERS));
		List<String> message_ids = this.jedis.lrange(cat(CHATS, id, MESSAGES), 0 , -1);
		
		for (String message_id : message_ids) {
			messages.add(getMessage(message_id));
		}
		
		for (String username : usernames) {
			users.add(getUser(username, false));
		}
		
		return new Chat(id, messages, users);
	}
	
	public void setChat(Chat chat) {
		for (User user : chat.getUsers()) {
			this.jedis.sadd(cat(CHATS, chat.getId(), USERS), user.getUsername());
		}
		
		for (Message message : chat.getMessages()) {
			this.jedis.rpush(cat(CHATS, chat.getId(), MESSAGES), message.getId());
		}
		
		this.jedis.sadd(CHATS, cat(CHATS, chat.getId()));
	}
	
	public void delChat(String id) {
		this.jedis.del(cat(CHATS, id, USERS));
		this.jedis.del(cat(CHATS, id, MESSAGES));
		this.jedis.srem(CHATS, cat(CHATS, id));
	}
	
	public List<Chat> getUserChats(String username) {
		List<Chat> chats = new ArrayList<Chat>();
		Set<String> keys = this.jedis.keys(CHATS);
		
		for (String key : keys) {
			String id = split(key)[1];
			if (this.jedis.sismember(cat(CHATS, id, USERS), username)) {
				chats.add(getChat(id));
			}
		}
		
		return chats;
	}
	
	// TABLE DB Adapter
	
	public Table getTable(String id) {
		Set<User> users = new HashSet<User>();
		
		Set<String> usernames = this.jedis.smembers(cat(TABLES, id, USERS));
		
		for (String username : usernames) {
			users.add(getUser(username, false));
		}
		
		String name = this.jedis.get(cat(TABLES, id));
		
		return new Table(id, name, users);
	}
	
	public void setTable(Table table) {
		for (User user : table.getUsers()) {
			this.jedis.sadd(cat(TABLES, table.getId(), USERS), user.getUsername());
		}
		
		this.jedis.set(cat(TABLES, table.getId()), table.getName());
		this.jedis.sadd(TABLES, cat(TABLES, table.getId()));
	}
	
	public void delTable(String id) {
		this.jedis.del(cat(TABLES, id, USERS));
		this.jedis.del(cat(TABLES, id));
		this.jedis.srem(TABLES, cat(TABLES, id));
	}
	
	public List<Table> getTables() {
		List<Table> tables = new ArrayList<Table>();
		Set<String> table_keys = this.jedis.smembers(TABLES);
		
		for (String table_key : table_keys) {
			String id = split(table_key)[1];
			tables.add(getTable(id));
		}
		
		return tables;
	}
	
}
