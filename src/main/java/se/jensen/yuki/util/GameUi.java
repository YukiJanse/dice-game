package se.jensen.yuki.util;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

/**
 * UI klass med JOptionPane.
 */
public class GameUi {
    /** String array för continue menu button. */
    private static final String[] CONTINUE_MENU_OPTIONS = {"play", "quit"};
    /** Start-Dialogens titeln. */
    private static final String START_DIALOG_TITLE = "Tärning Spel";
    /** Start-Dialogens texten. */
    private static final String START_DIALOG_MESSAGE = "Varje spelare slår tärningen två gånger, och den som får högst summa vinner!";
    /** Error message för namn inmatningen. */
    private static final String INPUT_ERROR_MESSAGE = "Fel inmatning! Ange minst 1 och högst 10 tecken";
    /** Quit-Dialogens texten. */
    private static final String QUIT_MESSAGE = "Vill du avsluta spelet?";
    /** Fil vägen för icon bild. */
    private static final String ICON_PATH = "./src/main/resources/dice.png";
    /** Icon bild */
    private final ImageIcon icon;

    /**
     * Konstruktionen som Skapa icon i.
     */
    public GameUi() {
        icon = makeIcon();
        // Om fil vägen är felaktig
        if (icon == null) {
            System.out.println("Felaktig map till icon bild.");
        }
    }

    /**
     * Visar meddelande för att börja spelet.
     * @throws HeadlessException om applikationen körs på CUI-miljö.
     */
    public void showStartDialog() throws HeadlessException {
        JOptionPane.showMessageDialog(null, START_DIALOG_MESSAGE, START_DIALOG_TITLE, PLAIN_MESSAGE, icon);
    }

    /**
     * Visar dialogen och får användarinmatning. Returnerar ett namn. Tomt text låtas inte.
     * @param dialogTitle är titeln för dialogen.
     * @param dialogMessage är meddelande texten för dialogen.
     * @return name är namnet som användare skickar.
     * @throws HeadlessException om applikation körs på CUI-miljä.
     * @throws NoSuchElementException om användare klickar cancel eller stäng-korset
     */
    public String askPlayerName(String dialogTitle, String dialogMessage) throws HeadlessException, NoSuchElementException {
        // THe variabel array för return
        String name = "";
        boolean isAsking = true;
        while(isAsking) {
            try {
                name = JOptionPane.showInputDialog(null, dialogMessage, dialogTitle, PLAIN_MESSAGE);
                if (name == null) { // Användare klickar cancel eller stäng-korset
                    throw new NoSuchElementException(QUIT_MESSAGE);
                } else if (name.trim().isEmpty() || name.length() > 10) { // Inmatningen är tomm
                    throw new IllegalArgumentException(INPUT_ERROR_MESSAGE);
                }
                isAsking = false;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }

        }

        return name;
    }

    /**
     * Visar vems runda är.
     * @param fullName är spelaren som kör rundan.
     * @param numberOfTurns är rundans nummer.
     */
    public void startTurn(String fullName, int numberOfTurns) throws HeadlessException {
        JOptionPane.showMessageDialog(null, (fullName + "s tur. Kasta tärningen!"), numberOfTurns + " rundan", PLAIN_MESSAGE);
    }

    /**
     * Visar tärningsvärde.
     * @param playerName är spelares full namn
     * @param result är poängen som spelaren fick
     * @throws HeadlessException om applikation körs på CUI-miljä.
     */
    public void showDiceNumber(String playerName, int result) throws HeadlessException {
        JOptionPane.showMessageDialog(null, playerName + " fick " + result + " poäng", "Resultat", PLAIN_MESSAGE);
    }

    /**
     * Frågar spelare om hen spelar spelet igen.
     * @return ett boolean värde om spelare spelar spelet igen. True: Hen spelar det igen.
     * @throws HeadlessException om applikation körs på CUI-miljä.
     */
    public boolean askPlayAgain() throws HeadlessException {
        int choice = JOptionPane.showOptionDialog(null, "Vill du spela igen?", "", YES_NO_OPTION, PLAIN_MESSAGE, null, CONTINUE_MENU_OPTIONS, CONTINUE_MENU_OPTIONS[1]);

        return choice == 0;
    }

    /**
     * Fråga spelare om hen avslutar spelet.
     * @param message är meddelande texten för dialogen.
     * @return ett boolean värde om spelare avslutar spelet. True: Hen avslutar det.
     * @throws HeadlessException om applikation körs på CUI-miljä.
     */
    public boolean askQuitGame(String message) throws HeadlessException {
        int choice = JOptionPane.showOptionDialog(null, message, "", YES_NO_OPTION, PLAIN_MESSAGE, null, CONTINUE_MENU_OPTIONS, CONTINUE_MENU_OPTIONS[1]);

        return choice == 1;
    }

    /**
     * Visar vinnares namn och deras poäng
     * @param winnerFullName är vinnares namn.
     * @param winnerScore är vinnares poäng.
     * @param loserScore är förlorares poäng.
     * @throws HeadlessException om applikation körs på CUI-miljä.
     */
    public void showWinner(String winnerFullName, int winnerScore, int loserScore) throws HeadlessException {
        if(winnerFullName == null) {
            // Det är oavgjort om winner är null
            JOptionPane.showMessageDialog(null, "Det var oavgjort!");

        } else {
            // Visa vinnare
            JOptionPane.showMessageDialog(null, winnerFullName + " vann!\nScores: " + winnerScore + " - " + loserScore, "Resultat", PLAIN_MESSAGE);
        }
    }

    /**
     * Läser en bild fil och skalar om för icon
     * @return ImageIcon för icon av dialogerna. När fil vägen är felaktig returnerar null.
     */
    public ImageIcon makeIcon() {
        ImageIcon unScaledIcon = new ImageIcon(ICON_PATH);
        if (unScaledIcon.getIconWidth() == -1) {
            return null;
        }
        Image img = unScaledIcon.getImage();
        img = img.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
