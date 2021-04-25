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
 * 扣分类
 */
public class Deduct {

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 点检项目id
     */
    private Integer itemId;
    /**
     * 扣分原因
     */
    private String reason;
    /**
     * 扣除的分数
     */
    private Integer minusScore;
    /**
     * 点检人员
     */
    private String name;
    /**
     * 点检时间
     */
    private String time;
    /**
     * 该扣分项所对应的图片
     */
    private List<Imagelist> imagelists;

}
