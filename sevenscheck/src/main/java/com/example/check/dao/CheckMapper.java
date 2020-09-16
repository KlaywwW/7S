package com.example.check.dao;

import com.example.check.pojo.Checkitems;
import com.example.check.pojo.Deduct;
import com.example.check.pojo.Imagelist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckMapper {

    /**
     * 获取对应部门的点检项目
     * @param depId 部门id
     * @param depSecendId 二级id
     * @return
     */
    List<Checkitems> getAllItems(@Param("depId") Integer depId,@Param("depSecendId") Integer depSecendId);

    /**
     * 保存扣分项
     * @param deduct 扣分对象 itemId 对应的是 CheckItems中的Id
     * @return
     */
    int addDeduct(Deduct deduct);

    /**
     * 添加扣分项对应图片
     * @return
     */
    int addImages(Imagelist imagelist);

    /**
     * 获取最新的保存扣分的id
     * @return
     */
    Integer getNewId();


}
