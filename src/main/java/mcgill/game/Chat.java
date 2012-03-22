package mcgill.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Chat {

	private String id;
	private List<Message> messages;
	private Set<User> users;
	
	public Chat(String id, List<Message> messages, Set<User> users) {
		this.id = id;
		this.messages = messages;
		this.users = users;
	}
	
	public Chat(User ...users) {
		this.users = new HashSet<User>();
		this.messages = new ArrayList<Message>();
		
		for (int i = 0; i < users.length; i++) {
			this.users.add(users[i]);
		}
		
		this.id = UUID.randomUUID().toString();
	}
	
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public boolean removeUser(User user) {
		return this.users.remove(user);
	}

	public String getId() {
		return id;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public Set<User> getUsers() {
		return users;
	}
	
}
