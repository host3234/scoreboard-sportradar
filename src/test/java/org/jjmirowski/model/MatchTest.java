package org.jjmirowski.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Match}.
 */
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
        assertEquals(homeTeam, match.homeTeam());
        assertEquals(awayTeam, match.awayTeam());
        assertEquals(0, match.homeScore());
        assertEquals(0, match.awayScore());
    }

    @Test
    void shouldReturnNewMatchWithUpdatedScore() {
        // given
        Match match = new Match(homeTeam, awayTeam);


        // when
        Match updatedMatch = match.withUpdatedScore(2, 1);

        //then
        assertEquals(homeTeam, updatedMatch.homeTeam());
        assertEquals(awayTeam, updatedMatch.awayTeam());
        assertEquals(2, updatedMatch.homeScore());
        assertEquals(1, updatedMatch.awayScore());

        assertEquals(0, match.homeScore());
        assertEquals(0, match.awayScore());
    }
}