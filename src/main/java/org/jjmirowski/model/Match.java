package org.jjmirowski.model;

/**
 * Represents a football match between two teams with current scores.
 *
 * @param homeTeam   name of the home team
 * @param awayTeam   name of the away team
 * @param homeScore  current score of the home team
 * @param awayScore  current score of the away team
 */
public record Match(String homeTeam, String awayTeam, int homeScore, int awayScore) {

    /**
     * Creates a new match with initial score 0-0.
     *
     * @param homeTeam name of the home team
     * @param awayTeam name of the away team
     */
    public Match(String homeTeam, String awayTeam) {
        this(homeTeam, awayTeam, 0, 0);
    }

    /**
     * Returns a new Match with updated scores.
     *
     * @param homeScore new home team score
     * @param awayScore new away team score
     * @return a new Match instance with updated scores
     */
    public Match withUpdatedScore(int homeScore, int awayScore) {
        return new Match(this.homeTeam, this.awayTeam, homeScore, awayScore);
    }
}
