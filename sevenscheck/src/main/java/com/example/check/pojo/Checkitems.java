package com.example.check.pojo;


public class Checkitems {

  private Integer id;
  private String item;
  private double score;
  private Integer depId;
  private Integer depSecendId;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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


  public Integer getDepId() {
    return depId;
  }

  public void setDepId(Integer depId) {
    this.depId = depId;
  }


  public Integer getDepSecendId() {
    return depSecendId;
  }

  public void setDepSecendId(Integer depSecendId) {
    this.depSecendId = depSecendId;
  }

}
