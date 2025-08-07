package org.jjmirowski.exception;

/**
 * Thrown when a score is set to a negative value.
 */
public class NegativeScoreException extends RuntimeException {
    public NegativeScoreException(int homeScore, int awayScore) {
        super("Scores must not be negative. Provided: home= " + homeScore + " : away= " + awayScore);
    }
}
