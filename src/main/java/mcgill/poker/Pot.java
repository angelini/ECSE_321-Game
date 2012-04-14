package mcgill.poker;

import java.util.ArrayList;

/**
 * The Pot class keeps track of the game's pot.
 */
public class Pot {

	/**
	 * Amount of chips in the pot
	 */
	private int amount;
	
	/**
	 * Arraylist of the players in a game
	 */
	private ArrayList<Player> players;
	
	/**
	 * The betting limit
	 */
	private int limit;
	
	/**
	 * Pot constructor
	 */
	public Pot(){
		this.amount = 0;
		this.players = new ArrayList<Player>();
		this.limit = -1;
	}
	
	/**
	 * Adds a player to the game and adds ante to the pot
	 * @param player
	 * @param amount
	 */
	public void addPlayer(Player player, int amount) {
		players.add(player);
		this.amount += amount;
	}
	
	/**
	 * Removes a player from the game
	 * @param player
	 */
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	/**
	 * Returns the total amount of chips
	 * @return int
	 */
	public int getTotalAmount() {
		return amount;
	}
	
	/**
	 * Adds an amount to the pot
	 * @param amount
	 */
	public void addToPot (int amount) {
		this.amount += amount;
	}
	
	/**
	 * Checks if the player is in the game
	 * @param player
	 * @return boolean
	 */
	public boolean containsPlayer(Player player) {
		if (players.contains(player)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the Arraylist of players
	 * @return ArrayList<Player>
	 */
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	
	/**
	 * Sets the limit
	 * @param limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	/**
	 * Returns the limit
	 * @return int
	 */
	public int getLimit() {
		return this.limit;
	}
	
	/**
	 * Check if there is a limit
	 * @return boolean
	 */
	public boolean hasLimit() {
		if (this.limit == -1) {
			return false;
		} else {
			return true;
		}
	}
}
