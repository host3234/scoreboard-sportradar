package org.jjmirowski.exception;

/**
 * Thrown when trying to finish a match that is not active.
 */
public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(String homeTeam, String awayTeam) {
        super("No active match found between " + homeTeam + " and " + awayTeam);
    }
}
