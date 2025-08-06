package org.jjmirowski.core;

import org.jjmirowski.exception.MatchNotFoundException;
import org.jjmirowski.exception.TeamAlreadyPlayingException;
import org.jjmirowski.model.Match;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ScoreBoard {

    private final List<Match> matches = new LinkedList<>();

    public void startMatch(String homeTeam, String awayTeam) {
        if (isTeamPlaying(homeTeam) || isTeamPlaying(awayTeam)) {
            throw new TeamAlreadyPlayingException();
        }
        matches.add(new Match(homeTeam, awayTeam));
    }

    public List<Match> getMatches() {
        return Collections.unmodifiableList(matches);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new MatchNotFoundException(homeTeam, awayTeam));

        matches.remove(match);
    }

    private boolean isTeamPlaying(String team) {
        return matches
                .stream()
                .anyMatch(m -> m.getHomeTeam().equals(team) || m.getAwayTeam().equals(team));
    }
}
