package com.example.scoreboard.impl;

import com.example.scoreboard.Match;
import com.example.scoreboard.MatchService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchServiceImplTest {

    @Test
    void startMatch_shouldAddNewMatchToTheList() {
        //given
        MatchService matchService = new MatchServiceImpl();
        assertEquals(0, matchService.getMatches().size());

        //when
        matchService.startMatch("Mexico", "Canada");

        //then
        List<Match> result = matchService.getMatches();
        assertEquals(1, result.size());
        assertEquals(0, result.getFirst().getHomeScore());
        assertEquals(0, result.getFirst().getAwayScore());
    }

    @Test
    void startMatch_shouldThrowAnIllegalArgumentException_missingTeamName() {
        //given
        MatchService matchService = new MatchServiceImpl();
        assertEquals(0, matchService.getMatches().size());

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> matchService.startMatch("Mexico", ""));
    }

    @Test
    void startMatch_shouldThrowAnIllegalArgumentException_teamNamesAreTheSame() {
        //given
        MatchService matchService = new MatchServiceImpl();
        assertEquals(0, matchService.getMatches().size());

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> matchService.startMatch("Mexico", "Mexico"));
    }

    @Test
    void updateScore_shouldUpdateScoreWhenMatchIsInProgressAndArgumentsAreCorrect() {
        //given
        MatchService matchService = new MatchServiceImpl();
        matchService.startMatch("Mexico", "Canada");
        List<Match> matches = matchService.getMatches();
        assertEquals(1, matches.size());
        assertEquals(0, matches.getFirst().getHomeScore());
        assertEquals(0, matches.getFirst().getAwayScore());

        //when
        matchService.updateScore("Mexico", 2, "Canada", 6);

        //then
        matches = matchService.getMatches();
        assertEquals(1, matches.size());
        assertEquals(2, matches.getFirst().getHomeScore());
        assertEquals(6, matches.getFirst().getAwayScore());
    }

    @Test
    void updateScore_shouldThrowInvalidArgumentException_negativeNumber() {
        //given
        MatchService matchService = new MatchServiceImpl();
        matchService.startMatch("Mexico", "Canada");
        List<Match> matches = matchService.getMatches();
        assertEquals(1, matches.size());
        assertEquals(0, matches.getFirst().getHomeScore());
        assertEquals(0, matches.getFirst().getAwayScore());

        //when


        //then
        assertThrows(IllegalArgumentException.class, () -> matchService.updateScore("Mexico", -1, "Canada", 6));
    }

    @Test
    void updateScore_shouldThrowInvalidArgumentException_blankTeamName() {
        //given
        MatchService matchService = new MatchServiceImpl();
        matchService.startMatch("Mexico", "Canada");
        List<Match> matches = matchService.getMatches();
        assertEquals(1, matches.size());
        assertEquals(0, matches.getFirst().getHomeScore());
        assertEquals(0, matches.getFirst().getAwayScore());

        //when


        //then
        assertThrows(IllegalArgumentException.class, () -> matchService.updateScore("", 1, "Canada", 6));
    }

    @Test
    void updateScore_shouldThrowInvalidArgumentException_teamNamesAreTheSame() {
        //given
        MatchService matchService = new MatchServiceImpl();

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> matchService.updateScore("Canada", 1, "Canada", 6));
    }

    @Test
    void updateScore_shouldNotUpdateScoreWhenMatchDoesNotExist() {
        //given
        MatchService matchService = new MatchServiceImpl();

        //when
        matchService.updateScore("Mexico", 1, "Germany", 2);

        //then
        assertTrue(matchService.getMatches().isEmpty(), "Matches list should remain empty");
    }

    @Test
    void finishMatch_shouldRemoveMatchFromTheList() {
        //given
        MatchService matchService = new MatchServiceImpl();
        matchService.startMatch("Mexico", "Canada");
        assertEquals(1, matchService.getMatches().size());

        //when
        matchService.finishMatch("Mexico", "Canada");

        //then
        assertEquals(0, matchService.getMatches().size());
    }

    @Test
    void finishMatch_shouldThrowAnIllegalArgumentException_missingTeamName() {
        //given
        MatchService matchService = new MatchServiceImpl();

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> matchService.finishMatch("Mexico", ""));
    }

    @Test
    void finishMatch_shouldThrowAnIllegalArgumentException_teamNamesAreTheSame() {
        //given
        MatchService matchService = new MatchServiceImpl();

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> matchService.finishMatch("Mexico", "Mexico"));
    }

    @Test
    void finishMatch_shouldNotRemoveMatchFromTheListWhenMatchDoesNotExist() {
        //given
        MatchService matchService = new MatchServiceImpl();
        matchService.startMatch("Mexico", "Canada");
        List<Match> matchesBefore = matchService.getMatches();
        assertEquals(1, matchesBefore.size());

        //when
        matchService.finishMatch("Mexico", "Germany");

        //then
        List<Match> matchesAfter = matchService.getMatches();
        assertEquals(1, matchesAfter.size());
        assertEquals(matchesBefore, matchesAfter);
    }

    @Test
    void getSummaries() {
        //given
        Match match1 = new Match("Mexico", 3, "Canada", 5);
        Match match2 = new Match("Spain", 1, "Brazil", 2);
        MatchService matchService = new MatchServiceImpl();

        //when
        List<String> result = matchService.getSummaries(Arrays.asList(match1, match2));

        //then
        assertEquals(2, result.size());
        assertEquals("Mexico 3 - Canada 5", result.get(0));
        assertEquals("Spain 1 - Brazil 2", result.get(1));
    }

    @Test
    void shouldReturnListOfMatchesCurrentlyInProgressOrderedByTheirTotalScoreAndStartTimeAscending() {
        //given
        MatchService service = new MatchServiceImpl();

        Match match1 = new Match("Mexico", 0, "Canada", 5);
        Match match2 = new Match("Spain", 10, "Brazil", 2);
        Match match3 = new Match("Germany", 2, "France", 2);
        Match match4 = new Match("Uruguay", 6, "Italy", 6);
        Match match5 = new Match("Argentina", 3, "Australia", 1);
        List<Match> matches = Arrays.asList(match1, match2, match3, match4, match5);

        //when
        List<String> result = service.getSummaries(matches);

        //then
        List<String> expected = List.of(
                "Uruguay 6 - Italy 6",
                "Spain 10 - Brazil 2",
                "Mexico 0 - Canada 5",
                "Argentina 3 - Australia 1",
                "Germany 2 - France 2"
        );
        assertEquals(expected, result);
    }
}