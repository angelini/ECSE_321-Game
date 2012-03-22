package mcgill.game;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

public class User {
	
	private String username;
	private String passwordHash;
	private int credits;
	private User[] friends;
	
	public static User createUser(String username, String password, Database db) throws Exception {
		User exists = db.getUser(username, false);
		
		if (exists != null) {
			throw new Exception("User already exists");
		}
		
		String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
		User user = new User(username, passwordHash, 0); 
		
		db.setUser(user);
		return user;
	}
	
	public static User verifyUser(String username, String password, Database db) {
		User user = db.getUser(username, false);
		
		if (user == null) return null;
		
		if (BCrypt.checkpw(password, user.getPasswordHash())) {
			return user;
		}
		
		return null;
	}
	
	public User(String username, String passwordHash, int credits) throws Exception {
		this.username = username;
		this.passwordHash = passwordHash;
		this.credits = 0;
	}
	
	public User(Map<String, String> info) {
		this.init(info);
	}
	
	public User(Map<String, String> info, User[] friends) {
		this.init(info);
		this.friends = friends;
	}
	
	private void init(Map<String, String> info) {
		this.username = info.get("username");
		this.passwordHash = info.get("passwordHash");
		this.credits = Integer.parseInt(info.get("credits"));
	}
	
	public Map<String, String> getMap() {
		Map<String, String> info = new HashMap<String, String>();
		info.put("username", this.username);
		info.put("passwordHash", this.passwordHash);
		info.put("credits", "" + this.credits);
		
		return info;
	}

	public String getUsername() {
		return username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public int getCredits() {
		return credits;
	}

	public User[] getFriends() {
		return friends;
	}

}
