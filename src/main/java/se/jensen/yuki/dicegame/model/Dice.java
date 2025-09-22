package se.jensen.yuki.dicegame.model;

import java.util.Random;

/**
 * Tärning
 */
public class Dice {
    /**
     * Slår tärningen och returnerar resultatet.
     * @return Resultatet (1-6)
     */
    public int roll(){
        return new Random().nextInt(1, 7);
    }
}
