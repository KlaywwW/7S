package com.example.check.service;

import com.example.check.pojo.Checkitems;
import com.example.check.pojo.Deduct;
import com.example.check.pojo.Imagelist;

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
}
