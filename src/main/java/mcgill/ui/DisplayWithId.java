package mcgill.ui;

/**
 * Keeps track of message string and id
 */
public class DisplayWithId {

	private String display;
	private String id;
	
	/**
	 * DisplayWithId constructor
	 * @param display
	 * @param id
	 */
	public DisplayWithId(String display, String id) {
		this.display = display;
		this.id = id;
	}
	
	/**
	 * Returns the string of object DisplayWithIsd
	 * @return String
	 */
	public String toString() {
		return this.display;
	}
	
	/**
	 * Returns the id
	 * @return String
	 */
	public String getId() {
		return this.id;
	}
	
}
