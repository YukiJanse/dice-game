package se.jensen.yuki.controller;

import se.jensen.yuki.model.Dice;
import se.jensen.yuki.model.Player;
import se.jensen.yuki.model.Result;
import se.jensen.yuki.sound.GameSound;
import se.jensen.yuki.ui.GameUi;

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
    private final Player player1 = new Player();
    /** Player instansen till player1. */
    private final Player player2 = new Player();
    /** GameUi instansen för att inmatningarna. */
    private final GameUi gameUi = new GameUi();
    /** GameSound instansen för att spela ljud */
    private final GameSound gameSound = new GameSound();
    /** Dice instansen för spelet */
    private final Dice dice = new Dice();

    /**
     * Styr hela applikationen.
     * @throws HeadlessException om applikationen körs på CUI-miljö.
     */
    public void run() throws HeadlessException, CancellationException {

        // Sätta true till värdet för loop villkoren
        boolean isGamePlaying = true;
        // Spela BGM
        gameSound.playBgm();

        // Loopar spelet
        while(isGamePlaying) {
            // Visa introduktionen
            gameUi.showStartDialog();
            // Sätta players namn
            setPlayerNames(player1, DIALOG_TITLE_PLAYER1);
            setPlayerNames(player2, DIALOG_TITLE_PLAYER2);

            // Starta matchen två gånger
            for (int i = 1; i <= NUMBER_OF_DICE_ROLL; i++) {
                // Player1 kasta
                startTurn(player1, i);
                // Player2 kasta
                startTurn(player2, i);
            }

            // Kontrollera vinnare och visa resultat
            Result result = checkWinner(player1, player2);
            gameSound.playApplauseSound();
            gameUi.showWinner(result.winnerName(), result.winnerScore(), result.loserScore());

            // Fråga om att spela igen
            isGamePlaying = gameUi.askPlayAgain();

            // Nollställa poäng och radera namn
            player1.cleanUp();
            player2.cleanUp();
        }
    }

    /**
     * Sätta förnamnet och efternamnet till en Player instans.
     * @param player är player
     * @param dialogTitle är titeln av dialogen.
     * @throws HeadlessException om applikationen körs på CUI-miljö.
     * @throws CancellationException om applikationen körs på CUI-miljö.
     */
    private void setPlayerNames(Player player, String dialogTitle) throws HeadlessException, CancellationException {
        player.setFirstName(askPlayerName(dialogTitle, MESSAGE_TO_ASK_FIRSTNAME));
        player.setLastName(askPlayerName(dialogTitle, MESSAGE_TO_ASK_LASTNAME));
    }

    /**
     * Visar meddelande vems runda är och kasta tärningen. Visar resultatet och tillägga poängen till Player instansen.
     * @param player är den som spelar den här rundan.
     * @param numberOfTurns är rundans nummer.
     * @throws HeadlessException om applikationen körs på CUI-miljö.
     */
    private void startTurn(Player player, int numberOfTurns) throws HeadlessException {
        gameUi.startTurn(player.getFullName(), numberOfTurns);
        gameSound.playThrowDiceSound();
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
        while(isAsking) { // Loopen är för om spelare ångrar sig att avsluta
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

    /**
     * Kontrollerar vinnare
     * @param player1 är instansen av player1
     * @param player2 är instansen av player2
     * @return Result record (winnerName, winnerScore, loserScore)
     */
    private Result checkWinner(Player player1, Player player2) {
        // Visa vinnare
        String winnerName = null;
        int winnerScore = 0;
        int loserScore = 0;
        if (player1.getScore() > player2.getScore()) { // Om player1 vinner
            winnerName = player1.getFullName();
            winnerScore = player1.getScore();
            loserScore = player2.getScore();
        } else if (player2.getScore() > player1.getScore()) { // Om player2 vinner
            winnerName = player2.getFullName();
            winnerScore = player2.getScore();
            loserScore = player1.getScore();
        } // winnerName ska vara null om det är oavgjort
        return new Result(winnerName, winnerScore, loserScore);
    }
}
