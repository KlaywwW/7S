package com.example.check.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Imagelist {

  private Integer id;
  private String imgName;
  private String imgPath;
  private Integer deductId;


}
