package se.jensen.yuki.controller;

import se.jensen.yuki.model.Dice;
import se.jensen.yuki.model.Player;
import se.jensen.yuki.util.GameUi;

import java.awt.*;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;

/**
 * Controller för applikationen
 */
public class Game {
    /** Antalet runda av spelet */
    private static final int NUMBER_OF_DICE_ROLL = 2;
    /** Dialogens titeln för Player1. */
    private static final String DIALOG_TITLE_PLAYER1 = "Player1";
    /** Dialogens titeln för Player2. */
    private static final String DIALOG_TITLE_PLAYER2 = "Player2";
    /** Meddelande texten till förnamn inmatningen. */
    private static final String MESSAGE_TO_ASK_FIRSTNAME = "Ange ditt förnamn.";
    /** Meddelande texten till efternamn inmatningen. */
    private static final String MESSAGE_TO_ASK_LASTNAME = "Ange ditt efternamn.";
    /** Player instansen till player1. */
    private final Player player1;
    /** Player instansen till player1. */
    private final Player player2;
    /** GameUi instansen för att inmatningarna. */
    private final GameUi gameUi = new GameUi();
    /** Dice instansen för spelet */
    private final Dice dice = new Dice();

    /**
     * Det är konstruktorn som tilldelar fälten.
     * @throws HeadlessException om applikationen körs på CUI-miljö.
     * @throws CancellationException om användare avslutade namn inmatningen och valde att avsluta spelet.
     */
    public Game() throws HeadlessException, CancellationException {
        // Visa introduktionen
        gameUi.showStartDialog();
        // Fråga spelares namn och tilldela dem med konstruktor
        // Player1
        player1 = makePlayer(DIALOG_TITLE_PLAYER1);
        // Player2
        player2 = makePlayer(DIALOG_TITLE_PLAYER2);
    }

    /**
     * Styr hela applikationen.
     * @throws HeadlessException om applikationen körs på CUI-miljö.
     */
    public void run() throws HeadlessException {
        // Spelare
        boolean isGamePlaying = true;

        // Loopar spelet
        while(isGamePlaying) {
            // Starta matchen två gånger
            for (int i = 0; i < NUMBER_OF_DICE_ROLL; i++) {
                int numberOfTurns = i + 1;
                // Player1 kasta
                startTurn(player1, numberOfTurns);
                // Player2 kasta
                startTurn(player2, numberOfTurns);
            }

            // Visa vinnare
            String winnerName = null;
            int winnerScore = 0;
            int loserScore = 0;
            if (player1.getScore() > player2.getScore()) {
                winnerName = player1.getFullName();
                winnerScore = player1.getScore();
                loserScore = player2.getScore();
            } else if (player2.getScore() > player1.getScore()) {
                winnerName = player2.getFullName();
                winnerScore = player2.getScore();
                loserScore = player1.getScore();
            } // winnerName ska vara null om det är oavgjort
            gameUi.showWinner(winnerName, winnerScore, loserScore);

            // Fråga om att spela igen
            isGamePlaying = gameUi.askPlayAgain();
            // nollställa poäng
            player1.clearScore();
            player2.clearScore();
        }
    }

    /**
     * Får String från askPlayerName och skapa en Player instans.
     * @param dialogTitle är titeln av dialogen.
     * @return Player instance med namn fälten
     * @throws HeadlessException om applikationen körs på CUI-miljö.
     * @throws CancellationException om applikationen körs på CUI-miljö.
     */
    private Player makePlayer(String dialogTitle) throws HeadlessException, CancellationException {
        String firstName = askPlayerName(dialogTitle, MESSAGE_TO_ASK_FIRSTNAME);
        String lastName = askPlayerName(dialogTitle, MESSAGE_TO_ASK_LASTNAME);

        return new Player(firstName, lastName);
    }

    /**
     * Visar meddelande vems runda är och kasta tärningen. Visar resultatet och tillägga poängen till Player instansen.
     * @param player är den som spelar den här rundan.
     * @param numberOfTurns är rundans nummer.
     * @throws HeadlessException om applikationen körs på CUI-miljö.
     */
    private void startTurn(Player player, int numberOfTurns) throws HeadlessException {
        gameUi.startTurn(player.getFullName(), numberOfTurns);
        int result = dice.roll();
        gameUi.showDiceNumber(player.getFullName(), result);
        player.addToScore(result);
    }

    /**
     * Fråga användare namn via GUI.
     * @param title är titeln till dialogen.
     * @param message är message till dialogen.
     * @return playerName är det som användare skickar.
     * @throws HeadlessException om applikation körs på CUI-miljö.
     * @throws CancellationException om användare avslutar inmatningen.
     */
    private String askPlayerName(String title, String message) throws HeadlessException, CancellationException {
        String playerName = "";
        boolean isAsking = true;
        while(isAsking) {
            try {
                playerName = gameUi.askPlayerName(title, message);
                isAsking = false;
            } catch (NoSuchElementException e) {
                // Om spelare vill avsluta
                if (gameUi.askQuitGame(e.getMessage())) {
                    throw new CancellationException();
                }

            }
        }
        return playerName;
    }
}
