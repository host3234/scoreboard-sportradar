package org.jjmirowski.core;

import org.jjmirowski.model.Match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public void startMatch(String homeTeam, String awayTeam) {
        matches.add(new Match(homeTeam, awayTeam));
    }

    public List<Match> getMatches() {
        return Collections.unmodifiableList(matches);
    }

    public void finishMatch(Match match) {
        matches.remove(match);
    }
}
