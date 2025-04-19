package monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChanceCards {
	// TODO
	// test commit
	List<Card> cardsChance = new ArrayList<>();
	List<Card> discardChance = new ArrayList<>();
	List<Card> handChance = new ArrayList<>();
	int currentCard;

	ChanceCards() {
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

		currentCard = cardsChance.size() - 1;
		shuffleChance();
	}

	public void shuffleChance() {
		Collections.shuffle(cardsChance);
	}

	public Card drawChance() {
		if (cardsChance.size() == 0) {
			cardsChance.addAll(discardChance);
			shuffleChance();
			discardChance.clear();
		}

		Card draw = cardsChance.remove(currentCard--);

		if (draw.getID() == 15) {
			addToHandChance(draw);
		}

		return draw;
	}

	public void discardPileChance(Card card) {
		if (card != null) {
			discardChance.add(card);
		}
	}

	public void addToHandChance(Card card) {
		if (card != null) {
			handChance.add(card);
		}
	}

	public void removeFromHandChance(Card card) {
		handChance.remove(card);
	}

	public List<Card> getHandChance() {
		return new ArrayList<>(handChance);
	}
}
