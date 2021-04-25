package com.example.check.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
/**
 * 图片
 */
public class Imagelist {

  /**
   * 主键
   */
  private Integer id;
  /**
   * 图片名
   */
  private String imgName;
  /**
   * 图片路径
   */
  private String imgPath;
  /**
   * 对应扣分项id
   */
  private Integer deductId;


}
