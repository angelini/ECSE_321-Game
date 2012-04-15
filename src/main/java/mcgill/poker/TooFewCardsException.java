package mcgill.poker;

/**
 * Exception: player has too few cards in the hand
 */
public class TooFewCardsException extends Exception {

	private static final long serialVersionUID = -3805189325624145873L;
	
	public String getMessage() {
		return "Too few cards in the Hand";
	}
	
}
