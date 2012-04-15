package mcgill.poker;

/**
 * Exception: player runs out of chips
 */
public class OutOfMoneyException extends Exception {

	private static final long serialVersionUID = -7343871194292515333L;

	public String getMessage() {
		return "Player is out of money";
	}
	
}
