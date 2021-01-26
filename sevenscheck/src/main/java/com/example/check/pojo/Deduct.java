package com.example.check.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Deduct {

  private Integer id;
  private Integer itemId;
  private String reason;
  private Integer minusScore;
  private String name;
  private String time;
  private List<Imagelist> imagelists;


}
