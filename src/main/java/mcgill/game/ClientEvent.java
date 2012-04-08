package mcgill.game;

import java.util.EventObject;

public class ClientEvent extends EventObject {

	private static final long serialVersionUID = -6303128939724816459L;	
	
	public static int HAND = 0;
	public static int ACTION = 1;
	public static int ACTION_REC = 2;
	
	private int type;
	private int action;
	
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

}
