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
/**
 * 结果类
 */
public class ResultScore {

    /**
     * 点检项
     */
    private String item;
    /**
     * 点检项id
     */
    private Integer checkId;
    /**
     * 点检项分数
     */
    private double score;
    /**
     * 点检项对应的扣分集合
     */
    private List<Deduct> deduct;
    /**
     * 部门
     */
    private Department department;
    /**
     * 二级部门
     */
    private DepSecend depSecend;
    /**
     * 点检类
     */
    private Checkitems checkitems;
}
