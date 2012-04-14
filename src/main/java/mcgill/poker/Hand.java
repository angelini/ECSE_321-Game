package mcgill.poker;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents the Cards in the player's hands
 */
public class Hand implements Iterable<Card> {

	/**
	 * Maximum number of Cards the hand can have
	 */
	public static final int MAX_SIZE = 5;
	
	/**
	 * The hand is represented by an Arraylist of Cards
	 */
	private ArrayList<Card> hand;
	
	/**
	 * Hand constructor. Creates a new Hand with an Arraylist of Cards
	 */
	public Hand() {
		this.hand = new ArrayList<Card>();
	}
	
	/**
	 * Iterates through the Cards in the hand
	 */
	public Iterator<Card> iterator() {
		return this.hand.iterator();
	}
	
	/**
	 * Adds a Card to the hand
	 * @param card
	 * @throws TooManyCardsException
	 */
	public void addCard(Card card) throws TooManyCardsException {
		if(this.hand.size() >= MAX_SIZE) {
			throw new TooManyCardsException();
		}
		
		this.hand.add(card);
	}
	
	/**
	 * Returns the HandRank of the hand
	 * @return HandRank
	 * @throws TooFewCardsException
	 */
	public HandRank getRank() throws TooFewCardsException {
		return new HandRank(this);
	}

	/**
	 * Returns the number of Card in the hand
	 * @return int
	 */
	public int getSize() {
		return this.hand.size();
	}
	
	/**
	 * Returns the highest Card in the hand
	 * @return Card
	 */
	public Card getHighest() {
		Card max = new Card(0, 0);
		
		for(Card card : this.hand) {
			if(card.getValue() > max.getValue()) {
				max = card;
			}
		}
		
		return max;
	}
	
	/**
	 * Returns the Card of the specific position in the hand
	 * @param index
	 * @return Card
	 */
	public Card cardAt(int index) {
		return this.hand.get(index);
	}
	
}
