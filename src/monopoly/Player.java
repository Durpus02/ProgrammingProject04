package monopoly;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Monopoly game. Tracks the player's current
 * position on the board, jail status, and held cards.
 */
public class Player {

	private boolean isJailed;
	private List<Card> hand;
	private Card getoutOfJailCC;
	private Card getoutOfJailChance;
	private int space; // Player's current board position (0–39)
	private final char strategy;

	/**
	 * Constructs a new player with default values. - Starts at position 0 (GO) -
	 * Not in jail - Empty hand
	 */
	public Player(char strat) {
		this.isJailed = false;
		this.hand = new ArrayList<>();
		this.space = 0;
		this.strategy = strat;
	}

	/**
	 * Puts the player in jail
	 * or takes the player out of jail
	 * Set true to jail the player
	 * Set false to free the player
	 */
	public void setJail(boolean b) {
		this.isJailed = b;
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
	 * @param card the card to add
	 */
	public void addGOJCC(Card card) {
		this.getoutOfJailCC = card;
	}

	/**
	 * Adds a "Get Out of Jail Free" Chance card to the player's hand
	 * @param card the card to add
	 */
	public void addGOJChance(Card card) {
		this.getoutOfJailChance = card;
	}

	/**
	 * Returns boolean indicating if the player has 
	 * a Get Out of Jail Free card from  either 
	 * Community Chest or Chance decks
	 */
	public boolean hasGetOutOfJailCard() {
		return (getoutOfJailCC != null || getoutOfJailChance != null);
	}

	/**
	 * Sets the player's position on the board.
	 *
	 * @param space the board space index (0–39)
	 */
	public void setSpace(int space) {
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
}
