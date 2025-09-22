package se.jensen.yuki.dicegame.model;

/**
 * Player model.
 */
public class Player {
    /** Players förnamn */
    private String firstName;
    /** Players efternamn */
    private String lastName;
    /** Players poäng */
    private int score = 0;

    /**
     * Getter till firstName.
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter till lastName.
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter för firstName
     * @param firstName är Players förnamn.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter för lastName.
     * @param lastName är Players förnamn.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter för score.
     * @return är Players poäng.
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter för score. Tillägger score.
     * @param score är det som Player fick poäng.
     */
    public void addToScore(int score) {
        this.score += score;
    }

    /**
     * Nollställar score efter matchen.
     */
    private void clearScore() {
        this.score = 0;
    }

    /**
     * Getter för full name.
     * @return full name.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Raderar players namn.
     */
    private void clearName() {
        setFirstName("");
        setLastName("");
    }

    /**
     * Initialization
     */
    public void cleanUp() {
        clearScore();
        clearName();
    }
}
