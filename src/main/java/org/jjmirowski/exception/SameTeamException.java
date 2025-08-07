package org.jjmirowski.exception;

/**
 * Thrown when a match is attempted to be started between the same team.
 */
public class SameTeamException extends RuntimeException {
    public SameTeamException(String teamName) {
        super("A match cannot be started between the same team: " + teamName);
    }
}
