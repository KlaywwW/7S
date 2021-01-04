package com.example.check;

import com.example.check.pojo.Checkitems;
import com.example.check.pojo.Deduct;
import com.example.check.pojo.Imagelist;
import com.example.check.pojo.ResultScore;
import com.example.check.service.CheckServiceImpl;
import com.example.check.service.DepartmentServiceImpl;
import com.example.check.util.ArrayGroupUtil;
import com.example.check.util.ProcessingData;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
class SevenscheckApplicationTests {

//    @Resource
//    private CheckServiceImpl checkService;
//    @Resource
//    private DepartmentServiceImpl departmentService;
//
//    public List<ResultScore> getData(String startTime, String endTime, Integer depId, Integer depSecendId) {
//        List<Checkitems> items = checkService.getAllItems(depId, depSecendId);
//
//        List<ResultScore> resultList = new ArrayList<>();
//        ResultScore resultScore = null;
//        ArrayGroupUtil arrayGroupUtil = new ArrayGroupUtil();
//
////        中间变量解决在向结果类中保存扣分数据时，无法将扣分数据保存到结果类中
//        int tempCount = 0;
//
////        保存点检项目信息
////		for (Checkitems check : items) {
//        for (int i = 0; i < items.size(); i++) {
//            resultScore = new ResultScore();
////            将所有的项目放到结果类里
//            resultScore.setItem(items.get(i).getItem());
//            resultScore.setCheckId(items.get(i).getId());
//            resultScore.setCheckitems(items.get(i));
////            保存部门信息
//            resultScore.setDepartment(departmentService.getDep(items.get(i).getDepId()));
//            resultScore.setDepSecend(departmentService.getSecend(items.get(i).getDepId(), items.get(i).getDepSecendId()));
//
////            获取扣分项的ItemId
//            List<Checkitems> checkitemsList = checkService.getDeductItem(startTime, endTime, depId, depSecendId);
//            double count = 0;
////            此循环保存扣分信息
//            for (Checkitems checkitems : checkitemsList) {
//                if (!checkitems.getId().equals(items.get(i).getId())) {
//                    continue;
//                }
//
//
////                System.out.println("checkItem----" + checkitems.getId());
////                根据itemId找到对应的分数
////                后续加上时间段，不然会查询所有的时间的扣分项
//                List<Deduct> deductsList = checkService.getDeduct(checkitems.getId(), startTime, endTime);
//                List<String> hay = new ArrayList<>();
////                保存图片信息
//                for (Deduct deduct : deductsList) {
//
////                    获取对应扣分项的图片
//                    List<Imagelist> imgs = checkService.getDeductImgs(deduct.getId());
//
//
//                    deduct.setImagelists(imgs);
//
//                    /*
//                    将时间戳进行转换，再将对应用数组分组来获取一共点检了多少天;
//                     */
//                    Long time = new Long(deduct.getTime());
//                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//                    hay.add(sf.format(new Date(Long.parseLong(String.valueOf(time)))));
//                    count += deduct.getMinusScore();
//                }
//                System.err.println(arrayGroupUtil.merge(hay));
//                int groupTotal = arrayGroupUtil.merge(hay);
//
//                System.err.println("groupTotal===================" + groupTotal);
//
//                resultScore.setDeduct(deductsList);
//
////            扣去的总分
////                System.out.println("count=----" + count);
////              将项目的分数减去扣去的平均分数 的 结果放到结果类里
//                resultScore.setScore(((items.get(i).getScore() * groupTotal) - count) / groupTotal);
//                count = 0;
//
////            添加到集合里
//                resultList.add(resultScore);
//                tempCount = 1;
//            }
////            添加到集合里
//            if (tempCount == 0) {
//                resultScore.setScore(items.get(i).getScore());
//                resultList.add(resultScore);
//            }
//            tempCount = 0;
//
//        }
//        return resultList;
//    }

    @Test
    void test1() {

        LocalDateTime time=LocalDateTime.now();
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date=time.format(dtf);
        System.err.println(date);


    }


}
