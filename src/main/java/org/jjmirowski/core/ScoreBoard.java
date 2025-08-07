package org.jjmirowski.core;

import org.jjmirowski.exception.EmptyNameException;
import org.jjmirowski.exception.MatchNotFoundException;
import org.jjmirowski.exception.SameTeamException;
import org.jjmirowski.exception.TeamAlreadyPlayingException;
import org.jjmirowski.model.Match;

import java.util.*;

/**
 * Handles live football matches: starting, updating scores, retrieving list of all active and finishing matches.
 * Ensures no team plays more than one match at a time.
 */
public class ScoreBoard {

    /**
     * Internal list of currently ongoing matches.
     * Matches are stored in insertion order and replaced immutably when scores are updated.
     */
    private final List<Match> matches = new LinkedList<>();

    /**
     * Starts a new match between the specified home and away teams.
     *
     * @param homeTeam  name of the home team
     * @param awayTeam  name of the away team
     * @throws TeamAlreadyPlayingException if either team is already participating in another match
     */
    public void startMatch(String homeTeam, String awayTeam) {
        validate(homeTeam, awayTeam);
        matches.add(new Match(homeTeam, awayTeam));
    }

    /**
     * Returns a list of all ongoing matches sorted by total score (descending),
     * and then by recency (most recently added first).
     *
     * @return sorted list of ongoing matches
     */
    public List<Match> getMatches() {
        List<Match> result = new ArrayList<>(matches);

        // Reverse to prioritize most recent matches
        Collections.reverse(result);
        // Sort by total score descending
        result.sort(Comparator.comparingInt((Match m) -> m.homeScore() + m.awayScore())
                .reversed());

        return result;
    }

    /**
     * Updates the score for a specific match identified by home and away team names.
     *
     * @param homeTeam   name of the home team
     * @param awayTeam   name of the away team
     * @param homeScore  new home team score
     * @param awayScore  new away team score
     * @throws MatchNotFoundException if no match is found with the specified teams
     */
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        ListIterator<Match> iterator = matches.listIterator();
        while (iterator.hasNext()) {
            Match match = iterator.next();
            if (match.homeTeam().equals(homeTeam) && match.awayTeam().equals(awayTeam)) {
                iterator.set(match.withUpdatedScore(homeScore, awayScore));
                return;
            }
        }
        throw new MatchNotFoundException(homeTeam, awayTeam);
    }

    /**
     * Finishes the match between the specified home and away teams, by removing it from matches list.
     *
     * @param homeTeam name of the home team
     * @param awayTeam name of the away team
     * @throws MatchNotFoundException if no such match is found
     */
    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = matches.stream()
                .filter(m -> m.homeTeam().equals(homeTeam) && m.awayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new MatchNotFoundException(homeTeam, awayTeam));

        matches.remove(match);
    }

    private void validate(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null || homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new EmptyNameException();
        }
        if (homeTeam.equals(awayTeam)) {
            throw new SameTeamException(homeTeam);
        }
        if (isTeamPlaying(homeTeam) || isTeamPlaying(awayTeam)) {
            throw new TeamAlreadyPlayingException();
        }
    }

    private boolean isTeamPlaying(String team) {
        return matches
                .stream()
                .anyMatch(m -> m.homeTeam().equals(team) || m.awayTeam().equals(team));
    }
}
