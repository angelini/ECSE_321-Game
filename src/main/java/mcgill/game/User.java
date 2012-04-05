package mcgill.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

public class User implements Serializable {
	
	private static final long serialVersionUID = 2000312905803278839L;
	
	private String username;
	private String passwordHash;
	private int credits;
	private List<User> friends;
	
	public static User createUser(String username, String password, Database db) {
		User exists = db.getUser(username, false);
		
		if (exists != null) {
			return null;
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
	
	public User(String username, String passwordHash, int credits) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.credits = 0;
	}
	
	public User(Map<String, String> info) {
		this.init(info);
	}
	
	public User(Map<String, String> info, List<User> friends) {
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

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public List<User> getFriends() {
		return friends;
	}

}
