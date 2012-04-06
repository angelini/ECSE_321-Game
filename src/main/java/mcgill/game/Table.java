package mcgill.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import mcgill.fiveCardStud.FiveCardStud;
import mcgill.poker.Player;

public class Table {

	String id;
	String name;
	Set<User> users;
	
	public Table(String id, String name, Set<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
	}
	
	public Table(String name) {
		this.name = name;
		this.id = UUID.randomUUID().toString();
		this.users = new HashSet<User>();
	}
	
	public Boolean addUser(User user) {
		if (this.users.size() >= Config.MAX_PLAYERS) {
			return false;
		}
		
		this.users.add(user);
		return true;
	}
	
	public Boolean removeUser(User user) {
		return users.remove(user);
	}
	
	public void startRound(Server server) {
		List<Player> players = new ArrayList<Player>();
		
		for (User user : this.users) {
			players.add(new Player(user.getUsername(), user.getCredits()));
		}
		
		Callable<List<Player>> round = new FiveCardStud(server, players, Config.LOW_BET, Config.MAX_RAISES, Config.BRING_IN);
		try {
			players = server.executor.submit(round).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		for (Player player : players) {
			for (User user : this.users) {
				if (player.getUsername().equals(user.getUsername())) {
					user.setCredits(player.getTotalMoney());
					break;
				}
			}
		}
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<User> getUsers() {
		return users;
	}
	
}
