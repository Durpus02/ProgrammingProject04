package data;

import monopoly.Monopoly;

/**
 * Runs Project 4 for Group 2.
 * 
 * @author Valor Goff
 */
public class Main {

	// hello
	// TODO: TEMPORARY
	private static void play(int n) {
		System.out.println("n: " + n);
	}

	// feel free to edit these
	private static final String NAME_ENDER = "simTEST";
	private static final int STARTING_N = 1;

	/**
	 * Creates data storage, runs 4 simulations for A, 4 for B, prints files, and
	 * saves them to CSV.
	 * 
	 * Largely pulled over from my code, Fall24, Project4. Lightly edited to fit
	 * this project.
	 * 
	 * @param args Unused this project.
	 */
	public static void main(String[] args) {
		// prep data storage
		LandingLedger ledgerA = new LandingLedger(); // for strategy A
		LandingLedger ledgerB = new LandingLedger(); // for strategy B
		// prep variables
		LandingLedger ledger; // current ledger in use
		char strat; // should be {'A','B'}
		int n_turns;
		int numPlayers;

		// = = = = Run Simulation A = = = =

		ledger = ledgerA; // selecting which ledger to store in
		numPlayers = 1;
		strat = 'A';
		// run 4 tests of increasing magnitude of n
		n_turns = STARTING_N;
		for (int i = 1; i <= 4; ++i, n_turns *= 10) {
			ledger.swap(i); // swap what 'CD' to store
			// setup and run game
			Monopoly game = new Monopoly(ledger, numPlayers);
			game.addPlayer(strat);
			game.setTurns(n_turns);
			game.startGame();
		}

		// = = = = Run Simulation B = = = =

		ledger = ledgerB; // selecting which ledger to store in
		numPlayers = 1;
		strat = 'B';
		// run 4 tests of increasing magnitude of n
		n_turns = STARTING_N;
		for (int i = 1; i <= 4; ++i, n_turns *= 10) {
			ledger.swap(i); // swap what 'CD' to store
			// setup and run game
			Monopoly game = new Monopoly(ledger, numPlayers);
			game.addPlayer(strat);
			game.setTurns(n_turns);
			game.startGame();
		}

		// = = = = prints = = = = (for testing)

		int sum = 0;
		System.out.println("\nA stats:");
		for (int i = 1; i <= 40; i++) {
			System.out.println(i + ": " + ledgerA.get(i));
			sum += ledgerA.get(i);
		}
		System.out.println("sum = " + sum);

		sum = 0;
		System.out.println("\nB stats:");
		for (int i = 1; i <= 40; i++) {
			System.out.println(i + ": " + ledgerB.get(i));
			sum += ledgerB.get(i);
		}
		System.out.println("sum = " + sum);

		// = = = = save data = = = =

		ledgerA.createCSV("stratA" + NAME_ENDER);
		ledgerB.createCSV("stratB" + NAME_ENDER);
	}
}
