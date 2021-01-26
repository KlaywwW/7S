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

    /**
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param depId 部门id
     * @param depSecendId 班别id
     * @return
     */
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
            double count = 0;//所有次数点检的总分
//            此循环保存扣分信息
            for (Checkitems checkitems : checkitemsList) {
                if(!checkitems.getId().equals(check.getId())){
                    continue;
                }


//                根据itemId找到对应的分数

//                后续加上时间段，不然会查询所有的时间的扣分项
                List<Deduct> deductsList = checkService.getDeduct(checkitems.getId(),startTime,endTime);
                List<String> hay = new ArrayList<String>();
//                保存图片和分数信息

                int minusCount=0;//记录扣分次数
                for (Deduct deduct : deductsList) {

//                    获取对应扣分项的图片
                    List<Imagelist> imgs=checkService.getDeductImgs(deduct.getId());


                    deduct.setImagelists(imgs);

                    /*
                    将时间戳进行转换，再将对应用数组分组来获取一共点检了多少天;
                     */
                    Long time=new Long(deduct.getTime());
                    SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                    hay.add(sf.format(new Date(Long.parseLong(String.valueOf(time)))));
                    if(deduct.getMinusScore()>0){
                        minusCount++;
                    }
                    count += deduct.getMinusScore();

                }
                System.err.println(arrayGroupUtil.merge(hay));

                int groupTotal=arrayGroupUtil.merge(hay);

                System.err.println("groupTotal==================="+groupTotal);

                resultScore.setDeduct(deductsList);

//            扣去的总分
//                System.out.println("count=----" + count);
//              将项目的分数减去扣去的平均分数 的 结果放到结果类里
//                有三次扣分就可以零分

                if (minusCount > 3&&(checkitems.getDepId()==7||checkitems.getDepId()==8)){
                    resultScore.setScore(0);
                }else if(minusCount>0&&(checkitems.getDepId()!=7&&checkitems.getDepId()!=8)){
                        resultScore.setScore(0);
                }else{
                    resultScore.setScore(((check.getScore()*groupTotal)-count)/groupTotal);
                }
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
