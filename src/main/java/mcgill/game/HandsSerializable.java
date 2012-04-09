package mcgill.game;

import java.util.Map;

import mcgill.poker.Hand;

public class HandsSerializable {

	private Map<String, Hand> hands;
	
	public HandsSerializable(Map<String, Hand> hands) {
		this.hands = hands;
	}
	
	public Map<String, Hand> getHands() {
		return this.hands;
	}
}
