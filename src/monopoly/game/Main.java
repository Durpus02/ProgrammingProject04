package monopoly.game;

import monopoly.model.Dice;

public class Main {

	/* * * * * * * * Test Client * * * * * * */
	
	public static void main(String[] args) {

		Dice dice = new Dice();
		dice.roll();

		System.out.println("Rolled: " + dice.getDie1() + " + " + dice.getDie2());
		System.out.println("Total: " + dice.getTotal());

		if (dice.isDouble()) {
		    System.out.println("You rolled a double!");
		}        
    }
}
