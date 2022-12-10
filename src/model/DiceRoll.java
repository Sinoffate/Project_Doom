package model;

import java.util.Random;

/**
 * This class is used to generate random numbers.
 * @author James Deal and Jered Wiegel
 * @version 1.0
 */
public class DiceRoll {

    /** Random value source. */
    private static final Random DICE_ROLL = new Random();


    /**
     * Private constructor to prevent instantiation.
     */
    private DiceRoll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Rolls a random number between 0 and 1.
     * @return random number.
     */
    public static float nextFloat() {
        return DICE_ROLL.nextFloat();
    }

    /**
     * Rolls a random number with user defined upper bound.
     * @return random number.
     */
    public static float nextFloat(final float theBound) {
        return DICE_ROLL.nextFloat(theBound);
    }

    /**
     * Rolls a random number with user defined upper bound.
     * @return random number.
     */
    public static int nextInt(final int theBound) {
        return DICE_ROLL.nextInt(theBound);
    }
}
