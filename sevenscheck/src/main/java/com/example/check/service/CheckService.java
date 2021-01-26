package com.example.check.service;

import com.example.check.pojo.Checkitems;
import com.example.check.pojo.Deduct;
import com.example.check.pojo.Imagelist;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckService {
    /**
     * 获取对应部门的点检项目
     * @param depId 部门id
     * @param depSecendId 二级id
     * @return
     */
    List<Checkitems> getAllItems(Integer depId, Integer depSecendId);

    /**
     * 保存扣分项
     * @param deduct 扣分对象 itemId 对应的是 CheckItems中的Id
     * @return
     */
    int addDeduct(Deduct deduct);

    int addImages(Imagelist imagelist);
    Integer getNewId();
    List<Deduct> getDeduct(Integer itemId,String startTime,String endTime);

    /**
     * 查询某个时间段下的扣分项目
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param depId 部门Id
     * @param depSecendId 二级部门
     * @return
     */
    List<Checkitems> getDeductItem(String startTime,String endTime,Integer depId,Integer depSecendId);
    /**
     * 根据扣分记录id获取图片
     * @param dudctId
     * @return
     */
    List<Imagelist> getDeductImgs(Integer dudctId);

    int delDeduct(@Param("deductId") Integer deductId);
}
