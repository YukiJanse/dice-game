package se.jensen.yuki.app;

import se.jensen.yuki.controller.Game;

import java.awt.*;
import java.util.concurrent.CancellationException;

/**
 * Kör main metoden.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Skapa Controller instance
            Game game = new Game();
            // Starta spelet
            game.run();
        } catch (HeadlessException e) { // Om applikation körs på CUI+miljö.
            System.out.println("Det är en GUI app.");
        } catch (CancellationException e) { // Om användare avslutar spelet.
            System.out.println("Spelet avslutas...");
        }
    }
}
