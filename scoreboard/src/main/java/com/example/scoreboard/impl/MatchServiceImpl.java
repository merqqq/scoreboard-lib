package com.example.scoreboard.impl;

import com.example.scoreboard.Match;
import com.example.scoreboard.MatchService;

import java.util.List;

public class MatchServiceImpl implements MatchService {


    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        
    }

    @Override
    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {

    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {

    }

    @Override
    public List<Match> getMatches() {
        return List.of();
    }

    @Override
    public int getTotalScore(Match match) {
        return 0;
    }

    @Override
    public String getSummary(Match match) {
        return "";
    }

    @Override
    public List<String> getSummaries(List<Match> matches) {
        return List.of();
    }
}
