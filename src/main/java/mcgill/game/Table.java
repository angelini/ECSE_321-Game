package mcgill.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mcgill.fiveCardStud.FiveCardStud;
import mcgill.poker.Player;

/**
 * Keeps track of players in a table
 */
public class Table {

	String id;
	String name;
	List<User> users;
	
	public Table(String id, String name, List<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
	}
	
	public Table(String name) {
		this.name = name;
		this.id = UUID.randomUUID().toString();
		this.users = new ArrayList<User>();
	}
	
	private void emitUsers() {
		for (User t_user : this.users) {
			String session_str = Server.getUserSession(t_user.getUsername());
			ClientNotification notification = new ClientNotification(session_str);
			notification.sendUsers(this.users.toArray(new User[0]));
			notification.close();
		}
	}
	
	public Boolean addUser(User user) {
		if (this.users.size() >= Config.MAX_PLAYERS) {
			return false;
		}
		
		for (User t_user : this.users) {
			if (t_user.getUsername().equals(user.getUsername())) {
				return true;
			}
		}
		
		this.users.add(user);
		emitUsers();
		
		return true;
	}
	
	public Boolean removeUser(User user) {
		for (int i = 0; i < this.users.size(); i++) {
			if (this.users.get(i).getUsername().equals(user.getUsername())) {
				this.users.remove(i);
				emitUsers();
				return true;
			}
		}
		
		return false;
	}
	
	public void startRound(Server server) {
		List<Player> players = new ArrayList<Player>();
		
		for (User user : this.users) {
			players.add(new Player(user.getUsername(), user.getCredits()));
		}
		
		FiveCardStud round = new FiveCardStud(players, Config.LOW_BET, Config.MAX_RAISES, Config.BRING_IN);
		
		server.executor.execute(round);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<User> getUsers() {
		return users;
	}
	
}
