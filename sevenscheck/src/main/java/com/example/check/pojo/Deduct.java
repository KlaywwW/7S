package com.example.check.pojo;


public class Deduct {

  private Integer id;
  private Integer itemId;
  private String reason;
  private Integer minusScore;
  private String name;
  private java.sql.Timestamp time;

  @Override
  public String toString() {
    return "Deduct{" +
            "id=" + id +
            ", itemId=" + itemId +
            ", reason='" + reason + '\'' +
            ", minusScore=" + minusScore +
            ", name='" + name + '\'' +
            ", time=" + time +
            '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }


  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }


  public Integer getMinusScore() {
    return minusScore;
  }

  public void setMinusScore(Integer minusScore) {
    this.minusScore = minusScore;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }

}
