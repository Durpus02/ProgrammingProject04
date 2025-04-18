package decks;

public class Card{
	private final String NAME;
	private final int ID;
	
	Card (String name, int ID){
		this.NAME = name;
		this.ID = ID;
	}

	
	/**
	 * Private methods for getting the Card name.
	 * Permissions may need to be modified
	 * @return String name
	 */
	private String getName() {
		return NAME;
	}

	/**
	 * Private method for getting Card ID number.
	 * Permissions may need to be modified 
	 * @return int ID 
	 */
	private int getID() {
		return ID;
	}


	@Override
	public String toString() {
		return "Card [name=" + NAME + ", ID=" + ID + "]";
	}
	
	
	
}