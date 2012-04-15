package mcgill.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Chat class implements the chat function in the game. Keep track of the list
 * of messages and set of users in the chat.
 */
public class Chat {

	/**
	 * Id of the message
	 */
	private String id;

	/**
	 * List of messages
	 */
	private List<Message> messages;

	/**
	 * Set of users
	 */
	private Set<User> users;

	/**
	 * Chat constructor
	 * 
	 * @param id
	 * @param messages
	 * @param users
	 */
	public Chat(String id, List<Message> messages, Set<User> users) {
		this.id = id;
		this.messages = messages;
		this.users = users;
	}

	/**
	 * Chat constructor
	 * 
	 * @param users
	 */
	public Chat(Set<User> users) {
		this.messages = new ArrayList<Message>();
		this.users = users;
		this.id = UUID.randomUUID().toString();
	}

	/**
	 * Adds a message to the list of messages
	 * 
	 * @param message
	 */
	public void addMessage(Message message) {
		this.messages.add(message);
	}

	/**
	 * Adds a User to the set of users
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		this.users.add(user);
	}

	/**
	 * REmoves a user
	 * 
	 * @param user
	 * @return boolean
	 */
	public boolean removeUser(User user) {
		return this.users.remove(user);
	}

	/**
	 * Returns the id of the message
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the list of messages
	 * 
	 * @return List<Messsage>
	 */
	public List<Message> getMessages() {
		return messages;
	}

	/**
	 * Returns users
	 * 
	 * @return Set<User>
	 */
	public Set<User> getUsers() {
		return users;
	}

}
