package mcgill.game;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Message class keeps track of the messages in the database.
 */
public class Message {

	/**
	 * id of the message
	 */
	private String id;
	
	/**
	 * String representation of the message
	 */
	private String message;
	
	/**
	 * String representation of the name of the user who created the message
	 */
	private String username;
	
	/**
	 * The time the message was created
	 */
	private Date time;
	
	/**
	 * Message constructor
	 * @param message
	 * @param username
	 * @param db
	 */
	public Message(String message, String username, Database db) {
		this.id = UUID.randomUUID().toString();
		this.message = message;
		this.username = username;
		this.time = new Date();
		
		db.setMessage(this);
	}
	
	/**
	 * Message constructor
	 * @param info
	 */
	public Message(Map<String, String> info) {
		this.id = info.get("id");
		this.message = info.get("message");
		this.username = info.get("username");
		this.time = new Date(Long.parseLong(info.get("time")));
	}

	/**
	 * Associates each key with its value
	 * @return Map<String, String>
	 */
	public Map<String, String> getMap() {
		Map<String, String> info = new HashMap<String, String>();
		info.put("id", this.id);
		info.put("message", this.message);
		info.put("username", this.username);
		info.put("time", "" + this.time.getTime());
		
		return info;
	}
	
	/**
	 * Return id
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the message
	 * @return String
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Returns the username
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the time
	 * @return Date
	 */
	public Date getTime() {
		return time;
	}
	
}
