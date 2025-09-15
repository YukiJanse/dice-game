package se.jensen.yuki.model;

/**
 * Det behåller resultatet av tärning matchen.
 * @param winnerName är vinnares namn.
 * @param winnerScore är vinnares score.
 * @param loserScore är förlorares score.
 */
public record Result(String winnerName, int winnerScore, int loserScore){ }
