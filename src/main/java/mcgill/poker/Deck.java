package mcgill.poker;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Deck class represents a deck of cards
 */
public class Deck {

	/**
	 * The number of cards in a deck
	 */
	public static final int SIZE = 52;
	
	/**
	 * Array of the card suits
	 */
	private int[] suits = {
			Card.HEART, 
			Card.DIAMOND,
			Card.CLUB,
			Card.SPADE
	};
	
	/**
	 * An Arraylist of Cards
	 */
	private ArrayList<Card> deck;
	
	/**
	 * Deck constructor. The Deck is an Arraylist of Cards.
	 */
	public Deck() {
		this.deck = new ArrayList<Card>();
		
		for(int suit : this.suits) {
			for(int value = Card.LOW; value <= Card.HIGH; value++) {
				this.deck.add(new Card(value, suit));
			}
		}
		
		Collections.shuffle(this.deck);
	}
	
	/**
	 * Randomly permutes the Cards in the deck
	 */
	public void shuffle() {
		Collections.shuffle(this.deck);
	}
	
	/**
	 * Returns the first Card in the deck
	 * @return Card
	 */
	public Card getTop() {
		return this.deck.remove(0);
	}
	
	/**
	 * Returns the number of cards in the deck
	 * @return int
	 */
	public int getSize() {
		return this.deck.size();
	}
}
