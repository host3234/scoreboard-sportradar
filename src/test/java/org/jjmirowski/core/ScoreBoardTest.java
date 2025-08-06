package org.jjmirowski.core;

import static org.junit.jupiter.api.Assertions.*;

import org.jjmirowski.exception.MatchNotFoundException;
import org.jjmirowski.exception.TeamAlreadyPlayingException;
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

    @Test
    void shouldThrowExceptionWhenAwayTeamIsAlreadyPlaying() {
        ScoreBoard scoreBoard = new ScoreBoard();

        scoreBoard.startMatch("Poland", "Italy");

        Exception exception = assertThrows(TeamAlreadyPlayingException.class, () -> {
            scoreBoard.startMatch("France", "Italy");
        });

        assertEquals("One of the teams is already playing in an active match.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamIsAlreadyPlaying() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        // when
        scoreBoard.startMatch("Poland", "Italy");
        Exception exception = assertThrows(TeamAlreadyPlayingException.class, () -> {
            scoreBoard.startMatch("Poland", "Germany");
        });

        // then
        assertEquals("One of the teams is already playing in an active match.", exception.getMessage());
    }

    @Test
    void shouldFinishExistingMatch() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Poland", "Italy");
        scoreBoard.startMatch("Germany", "France");

        // when
        scoreBoard.finishMatch("Poland", "Italy");

        // then
        assertEquals(1, scoreBoard.getMatches().size());
        assertFalse(scoreBoard.getMatches().stream()
                .anyMatch(m -> m.getHomeTeam().equals("Poland") && m.getAwayTeam().equals("Italy")));
        assertTrue(scoreBoard.getMatches().stream()
                .anyMatch(m -> m.getHomeTeam().equals("Germany") && m.getAwayTeam().equals("France")));
    }

    @Test
    void shouldThrownExceptionIfMatchToFinishNotFound() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        // when
        MatchNotFoundException exception = assertThrows(MatchNotFoundException.class,
                () -> scoreBoard.finishMatch("Poland", "Italy"));

        // then
        assertEquals("No active match found between Poland and Italy", exception.getMessage());
    }

    @Test
    void shouldReturnEmptyListWhenNoMatches() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        // when
        List<Match> matches = scoreBoard.getMatches();

        // then
        assertTrue(matches.isEmpty());
    }

    @Test
    void shouldReturnSingleMatch() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Poland", "Italy");

        // when
        List<Match> matches = scoreBoard.getMatches();

        // then
        assertEquals(1, matches.size());
        Match match = matches.get(0);
        assertEquals("Poland", match.getHomeTeam());
        assertEquals("Italy", match.getAwayTeam());
    }

    @Test
    void shouldReturnAllMatchesSortedByTotalScoreDescending() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Poland", "Italy");
        scoreBoard.startMatch("France", "Germany");
        scoreBoard.updateScore("Poland", "Italy", 1, 0);  // total 1 goal
        scoreBoard.updateScore("France", "Germany", 3, 1); // total 4 goals


        // when
        List<Match> matches = scoreBoard.getMatches();

        // then
        assertEquals("France", matches.get(0).getHomeTeam()); //check that France's match with 4 goals was placed first
        assertEquals("Poland", matches.get(1).getHomeTeam());
    }

    @Test
    void shouldSortByMostRecentlyAddedWhenScoresEqual() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Poland", "Italy");
        scoreBoard.startMatch("France", "Germany");
        scoreBoard.updateScore("Poland", "Italy", 1, 1);  // total 2 goal
        scoreBoard.updateScore("France", "Germany", 2, 0); // total 2 goals

        // when
        List<Match> matches = scoreBoard.getMatches();

        // then
        assertEquals("France", matches.get(0).getHomeTeam()); //check that recently added match is placed first
        assertEquals("Poland", matches.get(1).getHomeTeam());
    }

}
