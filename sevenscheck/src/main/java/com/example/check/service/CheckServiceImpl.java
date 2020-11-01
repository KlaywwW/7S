package com.example.check.service;

import com.example.check.dao.CheckMapper;
import com.example.check.pojo.Checkitems;
import com.example.check.pojo.Deduct;
import com.example.check.pojo.Imagelist;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckServiceImpl implements CheckService {

    @Resource
    private CheckMapper checkMapper;

    @Override
    public List<Checkitems> getAllItems(Integer depId, Integer depSecendId) {
        return checkMapper.getAllItems(depId,depSecendId);
    }

    @Override
    public int addDeduct(Deduct deduct) {
        return checkMapper.addDeduct(deduct);
    }

    @Override
    public int addImages(Imagelist imagelist) {
        return checkMapper.addImages(imagelist);
    }

    @Override
    public Integer getNewId() {
        return checkMapper.getNewId();
    }

    @Override
    public List<Deduct> getDeduct(Integer itemId,String startTime,String endTime) {
        return checkMapper.getDeduct(itemId,startTime,endTime);
    }

    @Override
    public List<Checkitems> getDeductItem(String startTime, String endTime, Integer depId, Integer depSecendId) {
        return checkMapper.getDeductItem(startTime, endTime, depId, depSecendId);
    }

    @Override
    public List<Imagelist> getDeductImgs(Integer dudctId) {
        return checkMapper.getDeductImgs(dudctId);
    }
}
