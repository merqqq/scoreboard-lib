package com.example.scoreboard;

import java.time.LocalDateTime;

public class Match {

    private final String homeTeam;
    private int homeScore = 0;
    private final String awayTeam;
    private int awayScore = 0;
    private final LocalDateTime startTime;

    public Match(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        this.homeTeam = homeTeam;
        this.homeScore = homeScore;
        this.awayTeam = awayTeam;
        this.awayScore = awayScore;
        this.startTime = LocalDateTime.now();
    }

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = LocalDateTime.now();
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
