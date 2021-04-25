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
 * 部门类
 */
public class Department {

  /**
   * 主键
   */
  private Integer id;
  /**
   * 部门名称
   */
  private String depName;

}
