package com.example.scoreboard;

import java.util.List;

public interface MatchService {

    void startMatch(String homeTeam, String awayTeam);

    void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore);

    void finishMatch(String homeTeam, String awayTeam);

    List<Match> getMatches();

    List<String> getSummaries(List<Match> matches);
}
