package mcgill.game;

import java.util.EventListener;

public interface ClientEventListener extends EventListener {
	public void eventOccured(ClientEvent e);
}
