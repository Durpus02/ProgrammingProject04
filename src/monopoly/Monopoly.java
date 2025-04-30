package monopoly;

import data.LandingLedger;

/**
 * The rule-book and game-master of the Monopoly simulation. The utility classes
 * of Dice, ChanceCards, and CommunityChest are composed together giving
 * Monopoly the tools to run the game. Ledger is connected to store what spaces
 * players land on. Monopoly edits player data and requests what they wish to do
 * with their turn.
 * 
 * This version of monopoly will only play n number of turns, where each turn is
 * one player's full turn. One turn, one player.
 * 
 * Defining "landing" as any space you move to after the instant start of your
 * turn. If you land on a chance card you also land anywhere it moves you. If
 * you get sent to jail you also land on jail. Failure to escape from jail is
 * not landing on jail again.
 * 
 * @author Valor (The Body), Edwin + Corbin (Player, Card, Jail Behavior)
 */
public class Monopoly {
	// tools
	private Dice dice;
	private ChanceCards chanceCards;
	private CommunityChest communityChest;
	// data
	private LandingLedger ledger;
	private Player[] players;
	private int turns;
	// trackers
	private int doublesCount = 0; // number of doubles rolled in a row

	/**
	 * Sets up the initial rules and tools of the game. Ledger implementation is
	 * unique to the simulation project.
	 * 
	 * @param ledger     The LandingLedger that the game will write to.
	 * @param numPlayers The number of players that will be playing.
	 */
	public Monopoly(LandingLedger ledger, int numPlayers) {
		// tools
		dice = new Dice();
		chanceCards = new ChanceCards();
		communityChest = new CommunityChest();
		// data
		this.ledger = ledger;
		if (numPlayers < 1) {
			System.out.println("Can't have less than one player! \n" + "Players will be set to 1");
			numPlayers = 1;
		}
		this.players = new Player[numPlayers];
		turns = -1; // default
	}

	/**
	 * Adds player as long as game isn't full and strategy is valid.
	 * 
	 * Think of strategy as which NonPlayerCharacter AI will be in use.
	 * 
	 * @param strategy {'A','B'}, Which strategy will be used.
	 */
	public void addPlayer(char strategy) {
		switch (strategy) {
		case 'A' -> {
			int slot = nextSlot();
			if (slot == -1) {
				System.out.println("Players full! Didn't add player.");
				return;
			}
			players[slot] = new Player(strategy);
			System.out.println("A created");
		}
		case 'B' -> {
			int slot = nextSlot();
			if (slot == -1) {
				System.out.println("Players full! Didn't add player.");
				return;
			}
			players[slot] = new Player(strategy);
			System.out.println("B created");
		}
		default -> {
			System.out.println(strategy + " is not a valid strategy {'A','B'}, player not created.");
		}
		}
	}

	// finds next available player slot
	private int nextSlot() {
		for (int i = 0; i < players.length; ++i) {
			if (players[i] == null) {
				return i;
			}
		}
		return -1; // full on players already
	}

	/**
	 * Sets the number of turns the game will run.
	 * 
	 * @param turnsToPlay Number of turns that will be played.
	 */
	public void setTurns(int turnsToPlay) {
		// check for valid input
		if (turnsToPlay < 1) {
			System.out.println("Can't play without turns, dummy! Turns set to 1.");
			turnsToPlay = 1;
		}

		this.turns = turnsToPlay;
	}

	/**
	 * Starts the game as long as turns have been determined and players have been
	 * added. After each turn, the player is rotated to the next one in queue. Each
	 * player is then asked what they want to do on that turn. Movement is recorded
	 * as it happens.
	 * 
	 * Player order was determined by what player was added. (First in, first
	 * player).
	 */
	public void startGame() {
		// check for valid state (can I start?)
		if (turns == -1) {
			System.out.println("Turns not set! Use setTurns(). Game not started.");
			return;
		}

		for (Player player : players) {
			if (player == null) {
				System.out.println("Not all players are added! Use addPlayer. Game not started.");
				return;
			}
		}

		// = = = = THE GAMEPLAY LOOP = = = =

		System.out.println("Starting game!");
		// Unnecessarily complicated, but it *can* run multiple players
		// runs 1-turns many times, incrementing through player order as it goes
		for (int turn = 1, currPlayer = 0; turn <= turns; ++turn, currPlayer = ++currPlayer % players.length) {

			System.out.println("Playing Turn " + turn + ", player " + (currPlayer + 1));
			playTurn(players[currPlayer]);

		}
		System.out.println("Finised playing!\n");
	}

	/**
	 * Method to execute the current player's turn. This method will take the
	 * current Dice roll and move the player accordingly. It will also check for any
	 * special conditions such as landing on a Chance or Community Chest space, or
	 * going to jail.
	 * 
	 * @param p
	 */
	public void playTurn(Player p) {
		dice.roll();
		int roll = dice.getTotal();
		if (p.jailStatus()) {
			// doublesCount = 0; // reset doubles count - players in jail should not have a
			// doubles count.
			jailHandling(p); // If player is able to get escape jail they can finish their turn.
			if (p.jailStatus()) { // check if player was able to get out of jail
				return; // immediately ends players turn if they were not able to get out of jail.
			}

		}
		// check for 3 doubles in a row.
		doublesCount = (dice.isDouble()) ? doublesCount + 1 : 0;
		if (doublesCount > 2) {
			gotoJail(p);
			System.out.println("Doubles rolled 3 times in a row! Go to jail!");
			return;
		} else // player has a normal turn
			p.setSpace((p.getSpace() + roll) % 40); // If roll + p.getSpace() >= 40, this would pay $200 for passing GO.

		// Check for Community Chest
		if (p.getSpace() == 2 || p.getSpace() == 17 || p.getSpace() == 33) {
			Card cc = communityChest.drawCC();
			if (getCc(p, cc))
				communityChest.discardPileCC(cc);
		}

		// Check for Chance
		if (p.getSpace() == 7 || p.getSpace() == 22 || p.getSpace() == 36) {
			Card chance = chanceCards.drawChance();
			if (getChance(p, chance))
				chanceCards.discardPileChance(chance);
		}

		// Check for landing on Go to jail
		if (p.getSpace() == 30) {
			ledger.landOn(p.getSpace());
			gotoJail(p);
			return; // immediately end player's turn
		}

		// This print line is just for feedback for testing.
		System.out.println("Rolled " + roll + " " + dice.getDie1() + " " + dice.getDie2() + " "
				+ ledger.getName(p.getSpace()) + " space: " + p.getSpace() + " doubles: " + doublesCount);

		// This is line is used for player tracking for the assignment.
		ledger.landOn(p.getSpace());

		// If player rolled doubles they get to roll again.
		if (dice.isDouble()) {
			playTurn(p);
		}
	}

	/**
	 * Method to send a player to jail. This method only sets the player's jail
	 * status to true and moves them to the Jail location. Client needs to handle
	 * ending the player's turn.
	 * 
	 * @param p
	 */
	private void gotoJail(Player p) {
		p.setJailedTo(true);
		p.setSpace(10); // Go to jail
		ledger.landOn(p.getSpace());
		doublesCount = 0; // reset doubles count
	}

	/**
	 * Method for handing player strategies for getting out of jail. This method
	 * will use a Get Out of Jail Free card if the player has one. Next, if the
	 * player is using strategy 'A', they will pay to get out of jail immediately.
	 * If the player is using strategy 'B', they will roll the dice to try to get
	 * out of jail. If they roll doubles, they will get out of jail. If sttrategy
	 * 'B' cannot roll doubles after 3 attempts, they will pay to get out of jail.
	 * 
	 * @param p
	 */
	private void jailHandling(Player p) {
		// check if player has a Get Out of Jail Free card
		if (p.hasGOJFCC()) {
			p.setJailedTo(false); // free the player
			communityChest.discardPileCC(p.useGOJFCC());
			return;
		} else if (p.hasGOJFChance()) {
			p.setJailedTo(false); // free the player
			chanceCards.discardPileChance(p.useGOJFChance());
			return;
		}

		// Strategy 'A' is to pay immediately to get out of jail if
		// they don't have a Get Out of Jail Free card
		if (p.getStrategy() == 'A') {
			// we assume the player has to pay
			p.setJailedTo(false); // free the player
			return;
		}
		// Strategy 'B' is to roll doubles to get out of jail
		else if (p.getStrategy() == 'B') {
			if (p.getAttemptsToRollOutOfJail() < 3) {
				// check if the dice roll is a doubles
				if (dice.isDouble()) {
					p.setJailedTo(false); // free the player
					p.setAttemptsToRollOutOfJail(0);
					return;
				} else {
					p.setAttemptsToRollOutOfJail(p.getAttemptsToRollOutOfJail() + 1);
					return; // player is still in jail
				}
			} else if (p.getAttemptsToRollOutOfJail() >= 3) {
				// player has rolled 3 times and failed to roll doubles
				p.setJailedTo(false); // free the player assuming they finally paid the $50 fine
				p.setAttemptsToRollOutOfJail(0);
				return;
			}
		}
	}

	/**
	 * Method to handle the actions associated with Chance cards. This method is
	 * currently only used to check for the "Go to Jail" "Advance to Go" "Get Out of
	 * Jail Free" "Advance to Illinois Ave", "Advance to Boardwalk", "Advance to
	 * Reading Railroad", "Advance to St. Charles Place"
	 * 
	 * @param p The player who drew the card.
	 * @param c The Chance card drawn.
	 */
	private boolean getChance(Player p, Card c) {
		switch (c.getID()) {
		case 0: {
			ledger.landOn(p.getSpace());
			gotoJail(p);
			return true;
		}
		case 1:
			ledger.landOn(p.getSpace());
			p.setSpace(0); // Move to Go
			return true;
		case 2:
			ledger.landOn(p.getSpace());
			p.setSpace(24); // Move to Boardwalk
			return true;
		case 3:
			ledger.landOn(p.getSpace());
			p.setSpace(39); // Move to St. Charles Place
			return true;
		case 4:
			ledger.landOn(p.getSpace());
			p.setSpace(5); // Move to Illinois Ave
			return true;
		case 5:
			ledger.landOn(p.getSpace());
			p.setSpace(moveToNearestRR(p.getSpace())); // Move to Nearest RR
			return true;
		case 6:
			ledger.landOn(p.getSpace());
			p.setSpace(moveToNearestUtil(p.getSpace())); // Move to Nearest Utility
			return true;
		case 7:
			ledger.landOn(p.getSpace());
			p.setSpace(35); // Move Nearest RR
			return true;
		case 8:
			ledger.landOn(p.getSpace());
			p.setSpace(5); // Move to Reading RR
			return true;
		case 9:
			ledger.landOn(p.getSpace());
			p.setSpace((p.getSpace() - 3) % 40); // Move back 3 spaces (Chance locations should all be greater than
													// 7)
			return true;
		case 15: {
			p.addGOJChance(c); // Get Out of Jail Free
			return false; // card is not returned to the discard pile
		}
		default:
			return true; // return the card to the discard pile
		}
	}

	/**
	 * Method to handle the actions associated with Community Chest cards. This
	 * method is currently only used to check for the "Go to Jail" "Advance to Go"
	 * "Get Out of Jail Free".
	 * 
	 * Boolean return value is used to determine if the card should moved to the
	 * discard pile after being evaluated. "Get Out of Jail Free" card returns
	 * false, since it is assumed the player will always keep it.
	 * 
	 * @param p The player who drew the card.
	 * @param c The Community Chest card drawn.
	 * @return boolean indicating if the card should go to the discard pile.
	 */
	private boolean getCc(Player p, Card c) {
		switch (c.getID()) {
		case 0: {
			ledger.landOn(p.getSpace());
			gotoJail(p);
			return true;
		}
		case 1:
			ledger.landOn(p.getSpace());
			p.setSpace(0); // Move to Go
			return true;
		case 15: { // Get Out of Jail Free
			p.addGOJCC(c);
			return false;
		}
		default:
			return true; // return the card to the discard pile
		}
	}

	/**
	 * Helper method to find the nearest railroad space from the current space. The
	 * RR are on spaces 5, 15, 25, 35.
	 * 
	 * @param space The current space of the player.
	 * @return The nearest railroad space.
	 */
	private int moveToNearestRR(int space) {
		while (space % 5 != 0) {
			++space; // should count up to 5, 10, 15, 20, 25, 30, 35, 40
		}
		if (space % 10 == 0) {
			space += 5;
		}
		return space % 40; // mod 40 to make sure we are not out of bounds
	}

	/**
	 * Helper method to find the nearest utility space from the current space. The
	 * utilities are on spaces 12 and 28. Since Chance cards are only picked up from
	 * 3 locations, water works is only selected if the player is on space 22.
	 * 
	 * @param space The current space of the player.
	 * @return The nearest utility space.
	 */
	private int moveToNearestUtil(int space) {
		if (space == 22)
			return 28;
		return 12;

	}
}
