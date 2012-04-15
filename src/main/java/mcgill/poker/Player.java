package mcgill.poker;

/**
 * The Player class represents the players in a game. Keeps track of each player
 * contribution to the pot and their status.
 */
public class Player implements Comparable<Player> {

	/**
	 * The player folds
	 */
	public static final int FOLDED = -1;

	/**
	 * The player bets
	 */
	public static final int BETTING = 0;

	/**
	 * The player puts all chips in the pot
	 */
	public static final int ALL_IN = 1;

	/**
	 * The player's username
	 */
	private String username;

	/**
	 * The amount of chips the player has
	 */
	private int money;

	/**
	 * Amount of chips the player has put in the pot
	 */
	private int amountInPots;

	/**
	 * The player's hand
	 */
	private Hand hand;

	/**
	 * The player's status
	 */
	private int status;

	/**
	 * The player is the winner of the game
	 */
	private boolean winner;

	/**
	 * Player constructor
	 * 
	 * @param username
	 * @param money
	 */
	public Player(String username, int money) {
		this.username = username;
		this.money = money;
		this.amountInPots = 0;
		this.hand = new Hand();
		this.status = BETTING;
		this.winner = false;
	}

	/**
	 * Resets amount of chips in the player's pot to 0 and creates a new hand
	 * for the player.
	 */
	public void reset() {
		this.amountInPots = 0;
		this.hand = new Hand();
		this.status = BETTING;
	}

	/**
	 * Adds a card to the player's hand
	 * 
	 * @param card
	 * @throws TooManyCardsException
	 */
	// gives null pointer exception
	public void addCard(Card card) throws TooManyCardsException {
		this.hand.addCard(card);
	}

	/**
	 * Returns the number of cards in the player's hand
	 * 
	 * @return int
	 */
	public int getHandSize() {
		return this.hand.getSize();
	}

	/**
	 * Get the player's hand rank
	 * 
	 * @return HandRank
	 * @throws TooFewCardsException
	 */
	public HandRank getHandRank() throws TooFewCardsException {
		return new HandRank(this.hand);
	}

	/**
	 * Returns the player's hand
	 * 
	 * @return Hand
	 */
	public Hand getHand() {
		return this.hand;
	}

	/**
	 * Add chips to the player and returns the amount of chips the player has.
	 * 
	 * @param amount
	 * @return int
	 */
	public int addMoney(int amount) {
		this.money += amount;
		return this.money;
	}

	/**
	 * Player places a bet
	 * 
	 * @param amount
	 * @throws OutOfMoneyException
	 */
	public void bet(int amount) throws OutOfMoneyException {
		if (amount > this.money) {
			throw new OutOfMoneyException();
		}

		this.money -= amount;
		this.amountInPots += amount;
	}

	/**
	 * Returns the amount of chips the player places in the pot.
	 * 
	 * @return int
	 */
	public int getAmountInPots() {
		return this.amountInPots;
	}

	/**
	 * Returns the player's total amount of chips
	 * 
	 * @return int
	 */
	public int getTotalMoney() {
		return this.money;
	}

	/**
	 * Sets the player's status
	 * 
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Determines if the player folded.
	 * 
	 * @return boolean
	 */
	public boolean isFolded() {
		if (this.status == FOLDED) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determines if the player's status is betting
	 * 
	 * @return boolean
	 */
	public boolean isBetting() {
		if (this.status == BETTING) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determines if the player's status is all in
	 * 
	 * @return boolean
	 */
	public boolean isAllIn() {
		if (this.status == ALL_IN) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Compares if the player has more chips in the pot than another player.
	 * Returns -1 (player has lesser chips in the pot than the other player), 1
	 * (player has more chips in pot than the other player), 0 (player has same
	 * amount of chips in the pot as the other player).
	 * 
	 * @return int
	 */
	public int compareTo(Player player) {
		if (this.amountInPots < player.getAmountInPots()) {
			return -1;
		} else if (this.amountInPots > player.getAmountInPots()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Returns the player's username
	 * 
	 * @return String
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Sets the winning player as the winner
	 * 
	 * @param winner
	 */
	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	/**
	 * Determines if the player won
	 * 
	 * @return boolean
	 */
	public boolean isWinner() {
		return this.winner;
	}

	/**
	 * Returns the player's status
	 * 
	 * @return int
	 */
	public int getStatus() {
		return this.status;
	}
}
