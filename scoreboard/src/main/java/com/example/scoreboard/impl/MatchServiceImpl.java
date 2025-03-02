package com.example.scoreboard.impl;

import com.example.scoreboard.Match;
import com.example.scoreboard.MatchService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MatchServiceImpl implements MatchService {

    private final Logger LOG = Logger.getLogger(MatchServiceImpl.class.getName());

    private final List<Match> matches = new ArrayList<>();

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        if (homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Invalid arguments:\nhomeTeam and awayTeam are required");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Invalid arguments:\nhomeTeam and awayTeam cannot be the same");
        }

        matches.add(new Match(homeTeam, awayTeam));

        LOG.info("Match " + homeTeam + " vs " + awayTeam + " started");
    }

    @Override
    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        if (homeTeam.isBlank() || homeScore < 0 || awayTeam.isBlank() || awayScore < 0) {
            throw new IllegalArgumentException("Invalid arguments:\nHome Team: " + homeTeam + "\nHome Score: " + homeScore + "\nAway Team: " + awayTeam + "\nAway Score: " + awayScore);
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Invalid arguments:\nhomeTeam and awayTeam cannot be the same");
        }

        findMatch(homeTeam, awayTeam).ifPresentOrElse(
                match -> {
                    match.setHomeScore(homeScore);
                    match.setAwayScore(awayScore);
                    LOG.info("Match updated with scores: " + homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore);
                },
                () -> LOG.info("There is no ongoing match between " + homeTeam + " and " + awayTeam)
        );
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        if (homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Invalid arguments:\nhomeTeam and awayTeam are required");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Invalid arguments:\nhomeTeam and awayTeam cannot be the same");
        }

        findMatch(homeTeam, awayTeam).ifPresentOrElse(
                match -> {
                    LOG.info("Removing match " + match.getHomeTeam() + " vs " + match.getAwayTeam());
                    matches.remove(match);
                    LOG.info("Match " + homeTeam + " vs " + awayTeam + " finished");
                },
                () -> LOG.info("No ongoing match found between " + homeTeam + " and " + awayTeam)
        );
    }

    @Override
    public List<Match> getMatches() {
        return matches;
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

    private Optional<Match> findMatch(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst();
    }

    private int getTotalScore(Match match) {
        return match.getHomeScore() + match.getAwayScore();
    }

    private String getSummary(Match match) {
        return match.getHomeTeam() + " " + match.getHomeScore() + " - " + match.getAwayTeam() + " " + match.getAwayScore();
    }
}
