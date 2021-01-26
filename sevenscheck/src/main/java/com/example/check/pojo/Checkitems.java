package com.example.check.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Checkitems {

  private Integer id;
  private String item;
  private double score;
  private Integer depId;
  private Integer depSecendId;
  private String responsibility;

}
