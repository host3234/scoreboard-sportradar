package org.jjmirowski.core;

import static org.junit.jupiter.api.Assertions.*;

import org.jjmirowski.model.Match;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Test class for {@link ScoreBoard}.
 */
public class ScoreBoardTest {

    @Test
    void shouldStartMatch() {
        //given
        ScoreBoard board = new ScoreBoard();

        //when
        board.startMatch("Poland", "Italy");

        //then
        List<Match> matches = board.getMatches();

        assertEquals(1, matches.size());
        Match match = matches.get(0);
        assertEquals("Poland", match.getHomeTeam());
        assertEquals("Italy", match.getAwayTeam());
    }

}
