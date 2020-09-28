package com.example.check.pojo;

import java.util.List;

public class ResultScore {

    private String item;
    private Integer checkId;

    private double score;

    private List<Deduct> deduct;
    private Department department;
    private DepSecend depSecend;
    private Checkitems checkitems;

    public Checkitems getCheckitems() {
        return checkitems;
    }

    public void setCheckitems(Checkitems checkitems) {
        this.checkitems = checkitems;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public DepSecend getDepSecend() {
        return depSecend;
    }

    public void setDepSecend(DepSecend depSecend) {
        this.depSecend = depSecend;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

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
