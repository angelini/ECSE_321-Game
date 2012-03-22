package mcgill.game;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Message {

	private String id;
	private String message;
	private Date time;
	
	public Message(String message, Database db) {
		this.id = UUID.randomUUID().toString();
		this.message = message;
		this.time = new Date();
		
		db.setMessage(this);
	}
	
	public Message(Map<String, String> info) {
		this.id = info.get("id");
		this.message = info.get("message");
		this.time = new Date(Long.parseLong(info.get("time")));
	}

	public Map<String, String> getMap() {
		Map<String, String> info = new HashMap<String, String>();
		info.put("id", this.id);
		info.put("message", this.message);
		info.put("time", "" + this.time.getTime());
		
		return info;
	}
	
	public String getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public Date getTime() {
		return time;
	}
	
}
