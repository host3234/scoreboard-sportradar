package org.jjmirowski.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    private String homeTeam;
    private String awayTeam;

    @BeforeEach
    void setUp() {
        homeTeam = "Poland";
        awayTeam = "Italy";
    }

    @Test
    void shouldCreateMatchWithInitialScoreZero() {
        // when
        Match match = new Match(homeTeam, awayTeam);

        //then
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(awayTeam, match.getAwayTeam());
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void shouldUpdateScore() {
        // given
        Match match = new Match(homeTeam, awayTeam);

        // when
        match.updateScore(4, 0);

        match.updateScore(4, 0);
        assertEquals(4, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }
}