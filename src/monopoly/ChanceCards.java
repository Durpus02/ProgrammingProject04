package monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck of Chance cards. You can draw, discard, and shuffle the discard back
 * into deck.
 * 
 * @author Corbin, Edwin
 */
public class ChanceCards {
	List<Card> cardsChance = new ArrayList<>();
	List<Card> discardChance = new ArrayList<>();

	/**
	 * Creates Chance Deck and its cards.
	 */
	public ChanceCards() {
		cardsChance.add(new Card("Go to Jail. Go directly to jail. Do not pass Go. Do not collect $200", 0));
		cardsChance.add(new Card("Advance to Go", 1));
		cardsChance.add(new Card("Advance to Boardwalk", 2));
		cardsChance.add(new Card("Advance to St Charles Place", 3));
		cardsChance.add(new Card("Advance to Illinois Avenue", 4));
		cardsChance.add(new Card("Advance to nearest Railroad - If owned, pay owner 2x rental fee", 5));
		cardsChance.add(new Card("Advance to nearest Utility - If owned, pay owner 2x rental fee", 6));
		cardsChance.add(new Card("Adnvace to nearest Railroad - If owned, pay owner 2x rental fee", 7));
		cardsChance.add(new Card("Advance to Reading Railroad", 8));
		cardsChance.add(new Card("Move Back 3 Spaces", 9));
		cardsChance.add(new Card("Speeding fine - pay $15", 10));
		cardsChance.add(new Card("Make general repairs on all properties - Pay $25/House, $100/Hotel", 11));
		cardsChance.add(new Card("You have been elected Chairman - Collect $50/Player", 12));
		cardsChance.add(new Card("Your building loan matures - Collect $50", 13));
		cardsChance.add(new Card("Bank pays you dividend - Collect $50", 14));
		cardsChance.add(new Card("Get Out of Jail Free – This card may be kept until needed", 15));

		shuffleChance();
	}

	/**
	 * Shuffles the Chance Deck.
	 */
	public void shuffleChance() {
		Collections.shuffle(cardsChance);
	}

	/**
	 * Draws card objects, "popping" it out.
	 * 
	 * @return Chance Deck, Card Object.
	 */
	public Card drawChance() {
		if (cardsChance.isEmpty()) {
			cardsChance.addAll(discardChance);
			// currentCard = cardsChance.size() - 1;
			shuffleChance();
			discardChance.clear();
		}
		Card draw = cardsChance.remove(0);
		return draw;
	}

	/**
	 * Takes and adds card to discard pile.
	 * 
	 * (!! Make sure you're discard the card to the right deck !!)
	 * 
	 * @param card Card Object to discard.
	 */
	public void discardPileChance(Card card) {
		assert card != null : "Can't discard null to Chance deck!!";
		if (card != null) {
			discardChance.add(card);
		}
	}

}
