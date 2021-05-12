package com.codesquad.baseball.team14.dto;

public class CurrentPlayerRecord {
    private int atBat;
    private int hits;

    public CurrentPlayerRecord(int atBat, int hits) {
        this.atBat = atBat;
        this.hits = hits;
    }

    public int getAtBat() {
        return atBat;
    }

    public int getHits() {
        return hits;
    }

}
