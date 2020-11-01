package com.example.check.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.check.pojo.ResultScore;
import com.example.check.service.DepartmentServiceImpl;
import com.example.check.util.DownloadFileUtil;
import com.example.check.util.ExcelUtil;
import com.example.check.util.ProcessingData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WebAdminController {

    @Resource
    private ProcessingData processingData;
    @Resource
    private DepartmentServiceImpl departmentService;

    @RequestMapping("/exportExcel")
    public String exportExcel(@RequestBody String s, HttpServletResponse response, HttpServletRequest request) throws Exception {
        System.err.println(s);

//        获取前端传来的参数；
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray jsonArray = jsonObject.getJSONArray("dates");
        Integer depId = jsonObject.getInteger("depId");
        Integer depSecendId = jsonObject.getInteger("depSecendId");
        String startTime = jsonArray.get(0).toString();
        String endTime = jsonArray.get(1).toString();


        System.err.println(depId + "-" + depSecendId + "-" + startTime + "-" + endTime);

        List<ResultScore> lists = processingData.getData(startTime, endTime, depId, depSecendId);

        //        对查询到的数据进行进一步整理
        List<ResultScore> res = new ArrayList<>();
        for (ResultScore resultScore : lists) {
            res.add(resultScore);
        }
        int count=0;
        List<ResultScore> res2 = new ArrayList<>();
        for (int i = 0; i < res.size(); i++) {
            if ((i + 1) < res.size()) {
                if (res.get(i).getDepSecend().getDepSecendId() != res.get(i + 1).getDepSecend().getDepSecendId()) {
                    count++;
                    res2.add(res.get(i));
                    continue;
                }

            }
            if (count>0){
                res2.add(new ResultScore());
                count=0;
            }
            res2.add(res.get(i));
        }



        File file = null;
        if (depSecendId==null || depSecendId == 0) {
             file = ExcelUtil.writeExcel(res2,
                     "7S稽查项目",
                     (System.currentTimeMillis() / 1000) + departmentService.getDep(depId).getDepName() + "点检.xls");
        } else {


             file = ExcelUtil.writeExcel(res2,
                     "7S稽查项目",
                     (System.currentTimeMillis() / 1000) + departmentService.getDep(depId).getDepName() + departmentService.getSecend(depId, depSecendId).getDepSecendName() + "点检.xls");
        }

        System.err.println(file.getAbsolutePath());
        System.err.println(file.getName());

//        DownloadFileUtil.downloadFile(file.getName(), new File(file.getAbsolutePath()), response, request);

        return "success";
    }

}
