import java.util.Random;

/**
 * Generates a random number, so that Doom Project can incorporate randonum numbers when we need to implement it.
 * Used a Singleton design pattern.
 * 
 * Hyunggil Woo
 * Version: 1.1
 */

public class RandomNumberGenerator {
    
    private static RandomNumberGenerator instance;
    private Random rand;

    /**
     * Private constructor prevents other classes from instantiating it outside of class.
     * Prints number from 0 upto exclusive to the maximum number.
     * @param theMaxNum
     * @spec.modifies: this
     * @effect: Generates random number up to the maximum number.
     */
    private RandomNumberGenerator() {
        rand = new Random();
    }

    /**
     * Returns an instance of random integer generator
     * @return
     */
    public static synchronized RandomNumberGenerator getInstance() {
        if (instance == null) {
            instance = new RandomNumberGenerator();
        }
        return instance;
    }

    /**
     * Returns a random double between 0.0 to 1
     * @param theNum
     * @spec.effect: random double between 0.0 upto 1 is generated.
     * @return random double.
     */
    public double nextDouble() {
        return rand.nextDouble();
    }
}
