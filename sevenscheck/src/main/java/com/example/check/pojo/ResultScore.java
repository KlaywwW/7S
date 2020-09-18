package com.example.check.pojo;

import java.util.List;

public class ResultScore {

    private String item;
    private double score;
    private List<Deduct> deduct;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<Deduct> getDeduct() {
        return deduct;
    }

    public void setDeduct(List<Deduct> deduct) {
        this.deduct = deduct;
    }
}
