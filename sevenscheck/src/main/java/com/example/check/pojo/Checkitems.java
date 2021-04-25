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
 * 点检项目类
 */
public class Checkitems {

  /**
   * 主键
   */
  private Integer id;
  /**
   * 点检项
   */
  private String item;
  /**
   * 分数
   */
  private double score;
  /**
   * 部门id
   */
  private Integer depId;
  /**
   * 班别id
   */
  private Integer depSecendId;
  /**
   * 该点检项目负责人
   */
  private String responsibility;

}
