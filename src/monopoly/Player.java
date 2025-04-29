package monopoly;

/**
 * Represents a player in the Monopoly game. Tracks the player's current
 * position on the board, jail status, and held cards.
 * 
 * @author Alex, Edwin
 * 
 */
public class Player {

	private boolean isJailed;
	private Card getoutOfJailCC; // holds GOoJF-Card from Community Chest
	private Card getoutOfJailChance; // holds GOoJF-Card from Chance Deck
	private int space; // player's current board position [0–39].
	private final char strategy; // 'A' or 'B', AI options
	private int attemptsToRollOutOfJail = 0;

	/**
	 * Constructs a new player with default values. - Starts at position 0 (GO) -
	 * Not in jail - Empty hand
	 * 
	 * @param strat Strategy, "AI," player will use
	 */
	public Player(char strat) {
		this.isJailed = false;
		// this.hand = new ArrayList<>();
		this.space = 0;
		this.strategy = strat;
	}

	/**
	 * Puts the player in jail or takes the player out of jail Set true to jail the
	 * player Set false to free the player
	 * 
	 * @param bool True if player is in jail
	 */
	public void setJailedTo(boolean bool) {
		if (!bool) // free/not in jail
			attemptsToRollOutOfJail = 0;
		this.isJailed = bool;
	}

	/**
	 * Checks if the player is currently in jail.
	 * 
	 * @return true if the player is jailed, false otherwise
	 */
	public boolean jailStatus() {
		return this.isJailed;
	}

	/**
	 * Adds a "Get Out of Jail Free" Community Chest card to the player's hand
	 * 
	 * @param card the card to add
	 */
	public void addGOJCC(Card card) {
		this.getoutOfJailCC = card;
	}

	/**
	 * Adds a "Get Out of Jail Free" Chance card to the player's hand
	 * 
	 * @param card the card to add
	 */
	public void addGOJChance(Card card) {
		this.getoutOfJailChance = card;
	}

	/**
	 * Returns boolean indicating if the player has a Get Out of Jail Free card from
	 * Community Chest.
	 * 
	 * @return Returns true if this player has GOoJF-Card from Community Chest
	 */
	public boolean hasGOJFCC() {
		return (getoutOfJailCC != null);
	}

	/**
	 * Returns boolean indicating if the player has a Get Out of Jail Free card from
	 * Chance deck.
	 * 
	 * @return Returns true if this play has GOoJF-Card from Chance deck
	 */
	public boolean hasGOJFChance() {
		return (getoutOfJailChance != null);
	}

	/**
	 * Gives up GOoJF-card, returning it to caller.
	 * 
	 * @return Returns GOoJF-Card object
	 */
	public Card useGOJFCC() {
		assert getoutOfJailCC != null : "Player does not have GOoJF-card from Community chest!!";
		return getoutOfJailCC;
	}

	/**
	 * Gives up GOoJF-card, returning it to caller.
	 * 
	 * @return Returns GOoJF-Card object
	 */
	public Card useGOJFChance() {
		assert getoutOfJailChance != null : "Player does not have GOoJF-card from Chance!!";
		return getoutOfJailChance;
	}

	/**
	 * Sets the player's position on the board.
	 *
	 * @param space the board space index (0–39)
	 * @throws IllegalArgumentException if space is outside of valid range
	 */
	public void setSpace(int space) {
		if(space < 0 || space > 39){
			throw new IllegalArgumentException("Space must be between 0 and 39.");
		}
		this.space = space;
	}

	/**
	 * Gets the player's current board position.
	 *
	 * @return the board space index (0–39)
	 */
	public int getSpace() {
		return this.space;
	}

	// This is going to be space dedicated to player strategies for getting out of
	// jail

	/**
	 * Getter for returning the player's strategy.
	 * 
	 * @return char representing the player's strategy
	 */
	public char getStrategy() {
		return strategy;
	}

	/**
	 * This gettier is used to determine the player's eligibility for attempting to
	 * roll out of jail. If the player is not jailed this field should be 0. If the
	 * player is jailed they have the option to attempt to get out of jail for free
	 * by rolling doubles. If they fail to roll doubles 3 times in a row, they must
	 * pay $50 and move to the space indicated by the dice roll.
	 * 
	 * @return Returns how many times player has tried to roll out of jail
	 */
	public int getAttemptsToRollOutOfJail() {
		return attemptsToRollOutOfJail;
	}

	/**
	 * Sets the number of attempts the player has made to roll out of jail. This is
	 * used to track how many times the player has rolled while in jail.
	 * 
	 * @param attempts Sets the number of times Player has tried to roll out of jail
	 * @throws IlllegalArgumentException if attempts is negative or greater than 3
	 */
	public void setAttemptsToRollOutOfJail(int attempts) {
		if (attempts < 0 || attempts > 3){
			throw new IllegalArgumentException("Jail attempts must be between 0 and 3");
		}
		this.attemptsToRollOutOfJail = attempts;
	
	}
}
