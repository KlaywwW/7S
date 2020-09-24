package com.example.check.util;

import com.example.check.pojo.Checkitems;
import com.example.check.pojo.Deduct;
import com.example.check.pojo.Imagelist;
import com.example.check.pojo.ResultScore;
import com.example.check.service.CheckService;
import com.example.check.service.CheckServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessingData {


    @Resource
    private  CheckServiceImpl checkService;

    public List<ResultScore> getData(String startTime,String endTime,Integer depId,Integer depSecendId){
        List<Checkitems> items = checkService.getAllItems(depId, depSecendId);

        List<ResultScore> resultList = new ArrayList<>();
        ResultScore resultScore=null;

//        中间变量解决在向结果类中保存扣分数据时，无法将扣分数据保存到结果类中
        int tempCount=0;

//        保存点检项目信息
        for (Checkitems check : items) {
            resultScore=new ResultScore();
//            将所有的项目放到结果类里
            resultScore.setItem(check.getItem());
            resultScore.setCheckId(check.getId());

//            获取扣分项的ItemId
            List<Checkitems> checkitemsList = checkService.getDeductItem(startTime, endTime, depId, depSecendId);
            double count = 0;
//            此循环保存扣分信息
            for (Checkitems checkitems : checkitemsList) {


                if(checkitems.getId()!=check.getId()){
                    continue;
                }

//                System.out.println("checkItem----" + checkitems.getId());
//                根据itemId找到对应的分数
                List<Deduct> deductsList = checkService.getDeduct(checkitems.getId());

//                保存图片信息
                for (Deduct deduct : deductsList) {
//                    获取对应扣分项的图片
                    List<Imagelist> imgs=checkService.getDeductImgs(deduct.getId());


                    deduct.setImagelists(imgs);
//                    System.err.println("deduct----" + deduct.getMinusScore());
                    count += deduct.getMinusScore();
                }
                resultScore.setDeduct(deductsList);

//            扣去的总分
//                System.out.println("count=----" + count);
//              将项目的分数减去扣去的分数 的 结果放到结果类里
                resultScore.setScore(check.getScore()-count);
                count = 0;

//            添加到集合里
                resultList.add(resultScore);
                tempCount=1;
            }
//            添加到集合里
            if (tempCount==0) {
                resultScore.setScore(check.getScore());
                resultList.add(resultScore);
            }
            tempCount=0;

        }
        return  resultList;
    }
}
