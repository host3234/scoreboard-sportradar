package org.jjmirowski.exception;

/**
 * Thrown when a match is attempted to be started between the same team.
 */
public class EmptyNameException extends RuntimeException {
    public EmptyNameException( ) {
        super("Team names must not be null or blank");
    }
}