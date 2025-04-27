package monopoly;

import data.LandingLedger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
 * @author Valor Goff so far
 */
public class Monopoly {
	Monopoly instance;
	// tools
	private Dice dice;
	private ChanceCards chanceCards;
	private CommunityChest communityChest;
	private final List<Card> getOutOfJailChance;
	private final List<Card> getOutOfJailCC;
	// data
	private LandingLedger ledger;
	private TempPlayer[] players;
	private int turns;

	/**
	 * Sets up the initial rules and tools of the game. Ledger implementation is
	 * unique to the simulation project.
	 * 
	 * This is the public "constructor" using a factory design pattern so the
	 * objects created can have its own pointer.
	 * 
	 * @param ledger     The LandingLedger that the game will write to.
	 * @param numPlayers The number of players that will be playing.
	 */
	public static Monopoly create(LandingLedger ledger, int numPlayers) {
		Monopoly game = new Monopoly(ledger, numPlayers);
		game.instance = game;
		return game;
	}

	// the actual constructor
	private Monopoly(LandingLedger ledger, int numPlayers) {
		// tools
		dice = new Dice();
		chanceCards = new ChanceCards();
		communityChest = new CommunityChest();
		this.getOutOfJailChance = new ArrayList<>();
		this.getOutOfJailCC = new ArrayList<>();
		// data
		this.ledger = ledger;
		if (numPlayers < 1) {
			System.out.println("Can't have less than one player dummy! numPlayers = 1.");
			numPlayers = 1;
		}
		this.players = new TempPlayer[numPlayers];
		turns = -1; // default
	}

	// TODO: implement with actual player class
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
			players[slot] = new TempPlayer(instance, strategy);
			System.out.println("A created");
		}
		case 'B' -> {
			int slot = nextSlot();
			if (slot == -1) {
				System.out.println("Players full! Didn't add player.");
				return;
			}
			players[slot] = new TempPlayer(instance, strategy);
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
		for (TempPlayer player : players) {
			if (player == null) {
				System.out.println("Not all players are added! Use addPlayer. Game not started.");
				return;
			}
		}

		System.out.println("Starting game!");
		// Unnecessarily complicated, but it *can* run multiple players
		// runs 1-turns many times, incrementing through player order as it goes
		for (int turn = 1, currPlayer = 0; turn <= turns; ++turn, currPlayer = ++currPlayer % players.length) {
			System.out.println("Playing Turn " + turn + ", player " + (currPlayer + 1));

			// TODO: Add playTurn()
			players[currPlayer].playTurn();
			// testing credentials change

		}
		System.out.println("Finised playing!");
	}

	// example implementation
	public void move() {
		// TODO: remove this random junk later.
		dice.roll();
		// check for doubles
		// = if third doubles, jailed
		// move to space
		// = player space + dice
		int randomSpace = new Random().nextInt(40) + 1; // 1-40
		// process move
		// 1. land on it
		ledger.landOn(randomSpace);
		// 2. draw card (if applicable)
		// 2a. process card (more private functions)
		storeCard(randomSpace);
	}

	// TODO: replace with actual player class
	// example implementation
	private class TempPlayer {
		char strategy;
		Monopoly game;

		public TempPlayer(Monopoly refrence, char strat) {
			this.game = refrence;
			this.strategy = strat;
		}

		public void playTurn() {
			this.game.move();
		}
	}

	/**
	 * Adds a card to the getOutOfJailCC or getOutOfJailChance fields if the player 
	 * lands on a chance or community chest space, and the card ID is 15.
	 * 
	 * @param space The current space number the player is located
	 */
	private void storeCard(int space) {
		Card card;
		
		switch (space) {
			case 2, 17, 33:
				card = communityChest.drawCC();
				if (card.getID() == 15) {
					getOutOfJailCC.add(card);
				}
				break;
			case 7, 22, 36:
				card = chanceCards.drawChance();
				if (card.getID() == 15) {
					getOutOfJailChance.add(card);
				}
				break;
		}
	}
}
