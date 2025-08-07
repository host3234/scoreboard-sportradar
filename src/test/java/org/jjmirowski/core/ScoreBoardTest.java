package org.jjmirowski.core;

import static org.junit.jupiter.api.Assertions.*;

import org.jjmirowski.exception.*;
import org.jjmirowski.model.Match;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Test class for {@link ScoreBoard}.
 */
public class ScoreBoardTest {

    private static final String TEAM_ALREADY_PLAYING_MSG = "One of the teams is already playing in an active match.";
    private static final String EMPTY_NAME_MSG = "Team names must not be null or blank";

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
        assertEquals("Poland", match.homeTeam());
        assertEquals("Italy", match.awayTeam());
    }

    @Test
    void shouldThrowExceptionWhenAwayTeamIsAlreadyPlaying() {
        ScoreBoard scoreBoard = new ScoreBoard();

        scoreBoard.startMatch("Poland", "Italy");

        Exception exception = assertThrows(TeamAlreadyPlayingException.class, () ->
                scoreBoard.startMatch("France", "Italy")
        );
        assertEquals(TEAM_ALREADY_PLAYING_MSG, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamIsAlreadyPlaying() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        // when
        scoreBoard.startMatch("Poland", "Italy");
        Exception exception = assertThrows(TeamAlreadyPlayingException.class, () ->
                scoreBoard.startMatch("Poland", "Germany")
        );
        // then
        assertEquals(TEAM_ALREADY_PLAYING_MSG, exception.getMessage());
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
                .anyMatch(m -> m.homeTeam().equals("Poland") && m.awayTeam().equals("Italy")));
        assertTrue(scoreBoard.getMatches().stream()
                .anyMatch(m -> m.homeTeam().equals("Germany") && m.awayTeam().equals("France")));
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
        assertEquals("Poland", match.homeTeam());
        assertEquals("Italy", match.awayTeam());
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
        assertEquals("France", matches.get(0).homeTeam()); //check that France's match with 4 goals was placed first
        assertEquals("Poland", matches.get(1).homeTeam());
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
        assertEquals("France", matches.get(0).homeTeam()); //check that recently added match is placed first
        assertEquals("Poland", matches.get(1).homeTeam());
    }

    @Test
    void shouldNotAllowMatchWithSameTeam() {
        ScoreBoard board = new ScoreBoard();
        Exception ex = assertThrows(SameTeamException.class, () ->
                board.startMatch("Poland", "Poland")
        );
        assertEquals("A match cannot be started between the same team: Poland", ex.getMessage());
    }

    @Test
    void shouldThrownExceptionIfMatchToUpdateNotFound() {
        ScoreBoard board = new ScoreBoard();
        MatchNotFoundException ex = assertThrows(MatchNotFoundException.class, () ->
                board.updateScore("Poland", "Italy", 1, 1)
        );
        assertEquals("No active match found between Poland and Italy", ex.getMessage());
    }

    @Test
    void shouldHandleMultipleMatchesStartAndFinishCorrectly() {
        ScoreBoard board = new ScoreBoard();
        board.startMatch("Poland", "Italy");
        board.startMatch("France", "Germany");
        board.startMatch("Brazil", "Argentina");

        board.finishMatch("France", "Germany");
        List<Match> matches = board.getMatches();

        assertEquals(2, matches.size());
        assertFalse(matches.stream().anyMatch(m -> m.homeTeam().equals("France")));
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamNameIsNull() {
        ScoreBoard board = new ScoreBoard();

        EmptyNameException exception = assertThrows(EmptyNameException.class, () ->
                board.startMatch(null, "Italy")
        );

        assertEquals(EMPTY_NAME_MSG, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamNameIsBlank() {
        ScoreBoard board = new ScoreBoard();

        EmptyNameException exception = assertThrows(EmptyNameException.class, () ->
                board.startMatch("   ", "Italy")
        );

        assertEquals(EMPTY_NAME_MSG, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAwayTeamNameIsNull() {
        ScoreBoard board = new ScoreBoard();

        EmptyNameException exception = assertThrows(EmptyNameException.class, () ->
                board.startMatch("Poland", null)
        );

        assertEquals(EMPTY_NAME_MSG, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAwayTeamNameIsBlank() {
        ScoreBoard board = new ScoreBoard();

        EmptyNameException exception = assertThrows(EmptyNameException.class, () ->
                board.startMatch("Poland", "   ")
        );

        assertEquals(EMPTY_NAME_MSG, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenScoreIsNegative() {
        ScoreBoard board = new ScoreBoard();
        board.startMatch("Poland", "Italy");

        NegativeScoreException ex = assertThrows(NegativeScoreException.class, () ->
            board.updateScore("Poland", "Italy", -1, 2)
        );

        assertEquals("Scores must not be negative. Provided: home= -1 : away= 2", ex.getMessage());
    }

}
