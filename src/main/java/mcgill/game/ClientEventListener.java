package mcgill.game;

import java.util.EventListener;

/**
 * Listens for client events
 */
public interface ClientEventListener extends EventListener {
	public void eventOccured(ClientEvent e);
}
