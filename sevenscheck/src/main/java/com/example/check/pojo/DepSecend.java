package com.example.check.pojo;


public class DepSecend {

  private Integer id;
  private Integer depSecendId;
  private String depSecendName;
  private Integer depId;

  @Override
  public String toString() {
    return "DepSecend{" +
            "id=" + id +
            ", depSecendId=" + depSecendId +
            ", depSecendName='" + depSecendName + '\'' +
            ", depId=" + depId +
            '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getDepSecendId() {
    return depSecendId;
  }

  public void setDepSecendId(Integer depSecendId) {
    this.depSecendId = depSecendId;
  }


  public String getDepSecendName() {
    return depSecendName;
  }

  public void setDepSecendName(String depSecendName) {
    this.depSecendName = depSecendName;
  }


  public Integer getDepId() {
    return depId;
  }

  public void setDepId(Integer depId) {
    this.depId = depId;
  }

}
