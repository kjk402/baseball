package com.codesquad.baseball.team14.domain;

import java.text.DecimalFormat;

public class Record {
    private static final DecimalFormat decimalForm = new DecimalFormat("0.000");

    private int atBat;
    private int hits;
    private int outs;
    private double average;

    public Record(int atBat, int hits, int outs, double average) {
        this.atBat = atBat;
        this.hits = hits;
        this.outs = outs;
        this.average = (double)hits/atBat;
    }

    public void update(int hits, int outs) {
        this.atBat = atBat +1;
        this.hits += hits;
        this.outs += outs;
    }

    public int getAtBat() {
        return atBat;
    }

    public int getHits() {
        return hits;
    }

    public int getOuts() {
        return outs;
    }

    public String getAverage() {
        return decimalForm.format(average);
    }

//    public String getAverage() {
//        return decimalForm.format(average);
//    }

}
