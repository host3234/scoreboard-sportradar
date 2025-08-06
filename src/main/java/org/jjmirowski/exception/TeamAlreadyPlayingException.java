package org.jjmirowski.exception;

/**
 * Thrown when trying to start match with a team that is already playing.
 */
public class TeamAlreadyPlayingException extends RuntimeException {
    public TeamAlreadyPlayingException() {
        super("One of the teams is already playing in an active match.");
    }
}
