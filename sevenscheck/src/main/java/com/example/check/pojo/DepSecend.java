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
 * 班别
 */
public class DepSecend {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 班别id
     */
    private Integer depSecendId;
    /**
     * 班别名称
     */
    private String depSecendName;
    /**
     * 所属部门id
     */
    private Integer depId;

}
