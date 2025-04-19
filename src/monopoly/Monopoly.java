package monopoly;

import data.LandingLedger;

public class Monopoly {
	private LandingLedger ledger;
	private TempPlayer[] players;
	private int turns;

	// Temporary
	private class TempPlayer {
		char strategy;

		public TempPlayer(char strat) {
			this.strategy = strat;
		}
	}

	public Monopoly(LandingLedger ledger, int numPlayers) {
		if (numPlayers < 1) {
			System.out.println("Can't have less than one player dummy! numPlayers = 1.");
			numPlayers = 1;
		}

		this.ledger = ledger;
		this.players = new TempPlayer[numPlayers];
		turns = -1; // default
	}

	// TODO: implement with actual player class
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

	private int nextSlot() {
		for (int i = 0; i < players.length; ++i) {
			if (players[i] == null) {
				return i;
			}
		}
		return -1;
	}

	public void setTurns(int turnsToPlay) {
		this.turns = turnsToPlay;
	}

	public void startGame() {
		// check if can start
		if (turns == -1) {
			System.out.println("Turns not set! Use setTurns().");
			return;
		}
		for (TempPlayer player : players) {
			if (player == null) {
				System.out.println("Not all players are added! Use addPlayer.");
				return;
			}
		}
		// start
		System.out.println("Starting game!");
		while(--turns >= 0) { // plays n turns, [n-1,0] inclusive
			System.out.println("Playing Turn " + turns);
			// TODO: Add playTurn()
			ledger.landOn(0);
		}
		System.out.println("Finised playing!");
	}
}
