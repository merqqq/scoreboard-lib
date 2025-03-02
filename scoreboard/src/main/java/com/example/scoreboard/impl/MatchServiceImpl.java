package com.example.scoreboard.impl;

import com.example.scoreboard.Match;
import com.example.scoreboard.MatchService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MatchServiceImpl implements MatchService {

    private final List<Match> matches = new ArrayList<>();

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        if (homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Invalid arguments:\nhomeTeam and awayTeam are required");
        }
        matches.add(new Match(homeTeam, awayTeam));
    }

    @Override
    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        if (homeTeam.isBlank() || homeScore < 0 || awayTeam.isBlank() || awayScore < 0) {
            throw new IllegalArgumentException("Invalid arguments:\nHome Team: " + homeTeam + "\nHome Score: " + homeScore + "\nAway Team: " + awayTeam + "\nAway Score: " + awayScore);
        }
        matches.stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst()
                .ifPresent(match -> {
                    match.setHomeScore(homeScore);
                    match.setAwayScore(awayScore);
                });
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        matches.removeIf(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam));
    }

    @Override
    public List<Match> getMatches() {
        return matches;
    }

    @Override
    public int getTotalScore(Match match) {
        return match.getHomeScore() + match.getAwayScore();
    }

    @Override
    public List<String> getSummaries(List<Match> matches) {
        return matches.stream()
                .sorted(
                        Comparator
                                .comparingInt(this::getTotalScore)
                                .thenComparing(Match::getStartTime))
                .map(this::getSummary)
                .collect(Collectors.toList())
                .reversed();
    }

    private String getSummary(Match match) {
        return match.getHomeTeam() + " " + match.getHomeScore() + " - " + match.getAwayTeam() + " " + match.getAwayScore();
    }
}
