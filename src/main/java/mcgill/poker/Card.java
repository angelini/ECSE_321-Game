package mcgill.poker;

public class Card {

	public static final int CLUB = 0;
	public static final int DIAMOND = 1;
	public static final int HEART = 2;
	public static final int SPADE = 3;
	
	public static final int LOW = 2;
	public static final int HIGH = 14;
	
	private int value;
	private int suit;
	
	public Card(int value, int suit) {
		this.value = value;
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public int getSuit() {
		return suit;
	}
	
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
