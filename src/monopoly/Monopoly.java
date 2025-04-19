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
 * @author Valor Goff so far
 */
public class Monopoly {
	// tools
	private Dice dice;
	private ChanceCards chanceCards;
	private CommunityChest communityChest;
	// data
	private LandingLedger ledger;
	private TempPlayer[] players;
	private int turns;

	// TODO: replace with actual player class
	// Temporary
	private class TempPlayer {
		char strategy;

		public TempPlayer(char strat) {
			this.strategy = strat;
		}
	}

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
			players[slot] = new TempPlayer(strategy);
			System.out.println("A created");
		}
		case 'B' -> {
			int slot = nextSlot();
			if (slot == -1) {
				System.out.println("Players full! Didn't add player.");
				return;
			}
			players[slot] = new TempPlayer(strategy);
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
			ledger.landOn(0);

		}
		System.out.println("Finised playing!");
	}
}
