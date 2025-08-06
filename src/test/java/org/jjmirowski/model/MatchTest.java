package org.jjmirowski.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @Test
    void shouldCreateMatchWithInitialScoreZero() {
        Match match = new Match("Poland", "Italy");
        assertEquals("Poland", match.getHomeTeam());
        assertEquals("Italy", match.getAwayTeam());
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void shouldUpdateScore() {
        Match match = new Match("Poland", "Italy");
        match.updateScore(4, 0);
        assertEquals(4, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }
}