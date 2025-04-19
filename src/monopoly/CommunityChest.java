package decks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommunityChest {
	// TODO
	// test commitment.
	List<Card> cardsCC = new ArrayList<>();
	List<Card> discardCC = new ArrayList<>(); 
	List<Card> handCC = new ArrayList<>();
	int currentCard;

	public CommunityChest() {
		cardsCC.add( new Card("Go to Jail – Go directly to jail. Do not pass Go. Do not collect $200", 0));
		cardsCC.add( new Card("Advance to Go", 1));
		cardsCC.add( new Card("Doctor's fees – Pay $50", 2));
		cardsCC.add( new Card("From sale of stock you get $50 - Collect $50", 3));
		cardsCC.add( new Card("Bank error in your favor – Collect $200", 4));
		cardsCC.add( new Card("Holiday Fund matures - Collect $100", 5));
		cardsCC.add( new Card("Pay hospital fees - Pay $100", 6));
		cardsCC.add( new Card("Pay school fees - Pay $50", 7));
		cardsCC.add( new Card("Receive $25 consultancy fee - Collect $25", 8));
		cardsCC.add( new Card("You have won second prize in a beauty contest – Collect $10", 9));
		cardsCC.add( new Card("Income tax refund – Collect $20", 10));
		cardsCC.add( new Card("It is your birthday – Collect $10 from every player", 11));
		cardsCC.add( new Card("Life insurance matures – Collect $100", 12));
		cardsCC.add( new Card("You are assessed for street repair - Pay $40/House, $115/hotel", 13));
		cardsCC.add( new Card("You inherit $100 - Collect $100", 14));
		cardsCC.add( new Card("Get Out of Jail Free – This card may be kept until needed", 15));
		
		currentCard = cardsCC.size()-1;
		shuffleCC();
	}
	
	public void shuffleCC() {
		Collections.shuffle(cardsCC);
	}
	
	public Card drawCC() {
		if (cardsCC.size() == 0) {
			cardsCC.addAll(discardCC);
			shuffleCC();
			discardCC.clear();
		}

		Card draw = cardsCC.remove(currentCard--);

		if (draw.getID() == 15) {
			addToHandCC(draw);
		}
		
		return draw;
	}
	
	public void discardPileCC(Card card){
		if (card != null) {
			discardCC.add(card);
		}
	}
	
	public void addToHandCC(Card card){
		if (card != null) {
			handCC.add(card);
		}
	}

	public void removeFromHandCC(Card card){
		handCC.remove(card);
	}
	
	public List<Card> getHandCC(){
		return new ArrayList<>(handCC);
	}
}
