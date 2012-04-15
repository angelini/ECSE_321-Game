package mcgill.fiveCardStud;

import java.util.Map;
/**
 * Keep track of winner and credit map at the end of a round
 */
public class EndOfRound {

	private Map<String, Integer> creditMap;
	private String winner;
	
	/**
	 * EndOfRound constructor
	 * @param winner
	 * @param creditMap
	 */
	public EndOfRound(String winner, Map<String, Integer> creditMap) {
		this.creditMap = creditMap;
		this.winner = winner;
	}

	/**
	 * Returns the map
	 * @return Map<String, Integer>
	 */
	public Map<String, Integer> getCreditMap() {
		return creditMap;
	}

	/**
	 * Returns the winner
	 * @return String
	 */
	public String getWinner() {
		return winner;
	}
	
}
