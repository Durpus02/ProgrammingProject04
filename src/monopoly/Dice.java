package monopoly;

import java.util.Random;

/**
 * Represents a pair of six-sided dice used in the Monopoly game.
 * Handles rolling, checking for doubles, and tracking consecutive doubles.
 */
public class Dice {

    private int die1;
    private int die2;
    private int doublesCount;
    private Random random;

    /**
     * Constructs a new Dice object with an internal random number generator.
     */
    public Dice() {
        this.random = new Random();
        this.doublesCount = 0;
    }

    /**
     * Rolls both dice, stores their values, and updates doubles count.
     */
    public void roll() {
        this.die1 = random.nextInt(6) + 1; // 1 to 6
        this.die2 = random.nextInt(6) + 1;

        if (die1 == die2) {
            doublesCount++;
        } else {
            doublesCount = 0;
        }
    }

    /**
     * Returns the value of the first die.
     *
     * @return value of die1 (1-6)
     */
    public int getDie1() {
        return die1;
    }

    /**
     * Returns the value of the second die.
     *
     * @return value of die2 (1-6)
     */
    public int getDie2() {
        return die2;
    }

    /**
     * Returns the total sum of both dice.
     *
     * @return total of die1 and die2
     */
    public int getTotal() {
        return die1 + die2;
    }

    /**
     * Checks whether the last roll was a double.
     *
     * @return true if both dice show the same number
     */
    public boolean isDouble() {
        return die1 == die2;
    }

    /**
     * Returns the number of consecutive doubles rolled.
     *
     * @return the current doubles count
     */
    public int getDoublesCount() {
        return doublesCount;
    }

    /**
     * Resets the doubles count (e.g. after going to jail or not rolling a double).
     */
    public void resetDoublesCount() {
        this.doublesCount = 0;
    }
}
