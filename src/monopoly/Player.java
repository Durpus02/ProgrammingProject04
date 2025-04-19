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
	private int space; // Player's current board position (0–39)

	/**
	 * Constructs a new player with default values. - Starts at position 0 (GO) -
	 * Not in jail - Empty hand
	 */
	public Player() {
		this.isJailed = false;
		this.hand = new ArrayList<>();
		this.space = 0;
	}

	/**
	 * Puts the player in jail.
	 */
	public void setJailed() {
		this.isJailed = true;
	}

	/**
	 * Frees the player from jail.
	 */
	public void setFreed() {
		this.isJailed = false;
	}

	/**
	 * Checks if the player is currently in jail.
	 *
	 * @return true if the player is jailed, false otherwise
	 */
	public boolean isJailed() {
		return this.isJailed;
	}

	/**
	 * Adds a card to the player's hand (e.g., a Get Out of Jail Free card).
	 *
	 * @param card the card to add
	 */
	public void addCard(Card card) {
		this.hand.add(card);
	}

	/**
	 * Returns a list of the cards currently held by the player.
	 *
	 * @return the player's hand as a List of Cards
	 */
	public List<Card> getHand() {
		return this.hand;
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
