package mcgill.game;

import java.util.EventObject;
import java.util.Map;

import mcgill.fiveCardStud.EndOfRound;
import mcgill.poker.Hand;

/**
 * Implements client events
 */
public class ClientEvent extends EventObject {

	private static final long serialVersionUID = -6303128939724816459L;
	
	public static int HAND = 0;
	public static int ACTION_GET = 1;
	public static int ACTION_REC = 2;
	public static int USER = 3;
	public static int MESSAGE = 4;
	public static int POT_STATUS = 5;
	public static int END_OF_ROUND = 6;
	
	private int type;
	private int action;
	private int[] limits;
	private Map<String, Hand> hands;
	private User[] users;
	private String chatId;
	private int[] potStatus;
	private EndOfRound endOfRound;
	
	public ClientEvent(Object source) {
		super(source);
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int[] getLimits() {
		return limits;
	}

	public void setLimits(int[] limits) {
		this.limits = limits;
	}
	
	public Map<String, Hand> getHands() {
		return this.hands;
	}
	
	public void setHands(Map<String, Hand> hands) {
		this.hands = hands;
	}

	public User[] getUsers() {
		return users;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}

	public String getChatId() {
		return this.chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public int[] getPotStatus() {
		return potStatus;
	}

	public void setPotStatus(int[] potStatus) {
		this.potStatus = potStatus;
	}

	public EndOfRound getEndOfRound() {
		return endOfRound;
	}

	public void setEndOfRound(EndOfRound endOfRound) {
		this.endOfRound = endOfRound;
	}

}
