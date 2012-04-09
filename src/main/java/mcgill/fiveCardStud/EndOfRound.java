package mcgill.fiveCardStud;

import java.util.Map;

public class EndOfRound {

	private Map<String, Integer> creditMap;
	private String winner;
	
	public EndOfRound(String winner, Map<String, Integer> creditMap) {
		this.creditMap = creditMap;
		this.winner = winner;
	}

	public Map<String, Integer> getCreditMap() {
		return creditMap;
	}

	public String getWinner() {
		return winner;
	}
	
}
