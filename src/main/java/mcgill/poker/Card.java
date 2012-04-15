package mcgill.poker;

/**
 * The Card class is used to represent cards in a deck.
 */
public class Card {

	/**
	 * Represents a card with a club suit
	 */
	public static final int CLUB = 0;
	
	/**
	 * Represents a card with a diamond suit
	 */
	public static final int DIAMOND = 1;
	
	/**
	 * Represents a card with a heart suit
	 */
	public static final int HEART = 2;
	
	/**
	 * Represents a card with a spade suit
	 */
	public static final int SPADE = 3;
	
	/**
	 * Represents the lowest value of a card
	 */
	public static final int LOW = 2;
	
	/**
	 * Represents the highest value of a card
	 */
	public static final int HIGH = 14;
	
	/**
	 * Represents the value of the card
	 */
	private int value;
	
	/**
	 * Represents the card's suit
	 */
	private int suit;
	
	/**
	 * Card constructor. Each card has a value (2-14) and a suit (0-3)
	 * @param value
	 * @param suit
	 */
	public Card(int value, int suit) {
		this.value = value;
		this.suit = suit;
	}

	/**
	 * Returns the value of the card
	 * @return int
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns the suit of the card
	 * @return int
	 */
	public int getSuit() {
		return suit;
	}
	
	/**
	 * Return the string representation of the card (value and suit)
	 * @return String
	 */
	public String toString() {
		String value;
		String suit = null;
		
		switch (this.value) {
			case 11:
				value = "J";
				break;
			case 12:
				value = "Q";
				break;
			case 13:
				value = "K";
				break;
			case 14: 
				value = "A";
				break;
			default:
				value = String.valueOf(this.value);
				break;
		}
		
		switch (this.suit) {
			case 0:
				suit = "♣";
				break;
			case 1:
				suit = "♦";
				break;
			case 2: 
				suit = "♥";
				break;
			case 3: 
				suit = "♠";
				break;
		}
		
		return value.concat(suit);
	}
	
}
