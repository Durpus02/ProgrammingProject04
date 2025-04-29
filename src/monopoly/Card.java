package monopoly;

/**
 * Card object for Monopoly project. Stores a name and ID; has a toString.
 * 
 * @author Corbin
 */
public class Card {
	private final String NAME;
	private final int ID;

	/**
	 * Creates card.
	 * 
	 * @param name Name of/on Card.
	 * @param ID   Integer ID.
	 */
	Card(String name, int ID) {
		this.NAME = name;
		this.ID = ID;
	}

	/**
	 * Public methods for getting the Card name. Permissions may need to be
	 * modified
	 * 
	 * @return String name
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Public method for getting Card ID number. Permissions may need to be
	 * modified
	 * 
	 * @return int ID
	 */
	public int getID() {
		return ID;
	}

	@Override
	public String toString() {
		return "Card [name=" + NAME + ", ID=" + ID + "]";
	}

}
