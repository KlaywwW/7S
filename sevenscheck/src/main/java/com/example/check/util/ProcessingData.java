package com.example.check.util;

import com.example.check.pojo.Checkitems;
import com.example.check.pojo.Deduct;
import com.example.check.pojo.Imagelist;
import com.example.check.pojo.ResultScore;
import com.example.check.service.CheckService;
import com.example.check.service.CheckServiceImpl;
import com.example.check.service.DepartmentServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProcessingData {


    @Resource
    private  CheckServiceImpl checkService;
    @Resource
    private DepartmentServiceImpl departmentService;

    public List<ResultScore> getData(String startTime,String endTime,Integer depId,Integer depSecendId){
        List<Checkitems> items = checkService.getAllItems(depId, depSecendId);

        List<ResultScore> resultList = new ArrayList<>();
        ResultScore resultScore=null;
        ArrayGroupUtil arrayGroupUtil=new ArrayGroupUtil();

//        中间变量解决在向结果类中保存扣分数据时，无法将扣分数据保存到结果类中
        int tempCount=0;

//        保存点检项目信息
        for (Checkitems check : items) {
            resultScore=new ResultScore();
//            将所有的项目放到结果类里
            resultScore.setItem(check.getItem());
            resultScore.setCheckId(check.getId());
            resultScore.setCheckitems(check);
//            保存部门信息
            resultScore.setDepartment(departmentService.getDep(check.getDepId()));
            resultScore.setDepSecend(departmentService.getSecend(check.getDepId(),check.getDepSecendId()));

//            获取扣分项的ItemId
            List<Checkitems> checkitemsList = checkService.getDeductItem(startTime, endTime, depId, depSecendId);
            double count = 0;
//            此循环保存扣分信息
            for (Checkitems checkitems : checkitemsList) {
                if(!checkitems.getId().equals(check.getId())){
                    continue;
                }


//                System.out.println("checkItem----" + checkitems.getId());
//                根据itemId找到对应的分数
                List<Deduct> deductsList = checkService.getDeduct(checkitems.getId());
                List<String> hay = new ArrayList<String>();
//                保存图片信息
                for (Deduct deduct : deductsList) {

//                    获取对应扣分项的图片
                    List<Imagelist> imgs=checkService.getDeductImgs(deduct.getId());


                    deduct.setImagelists(imgs);
//                    System.err.println("deduct----" + deduct.getMinusScore());

                    /*
                    将时间戳进行转换，再将对应用数组分组来获取一共点检了多少天;
                     */
                    Long time=new Long(deduct.getTime());
                    SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                    hay.add(sf.format(new Date(Long.parseLong(String.valueOf(time)))));
                    System.err.println(sf.format(new Date(Long.parseLong(String.valueOf(time)))));
                    count += deduct.getMinusScore();
                }
                System.err.println(arrayGroupUtil.merge(hay));
                int groupTotal=arrayGroupUtil.merge(hay);

                System.err.println("groupTotal===================");

                resultScore.setDeduct(deductsList);

//            扣去的总分
//                System.out.println("count=----" + count);
//              将项目的分数减去扣去的平均分数 的 结果放到结果类里
                resultScore.setScore(((check.getScore()*groupTotal)-count)/groupTotal);
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
