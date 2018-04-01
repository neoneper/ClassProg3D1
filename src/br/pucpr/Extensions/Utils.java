package br.pucpr.Extensions;
import java.util.Random;

/**
 * Created by devsecond on 31/03/2018.
 */
public class Utils {


    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max)
    {


        Random rand = new Random();


        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
