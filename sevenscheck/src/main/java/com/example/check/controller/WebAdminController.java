package com.example.check.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.check.pojo.Deduct;
import com.example.check.pojo.ResultScore;
import com.example.check.service.CheckServiceImpl;
import com.example.check.service.DepartmentServiceImpl;
import com.example.check.util.DownloadFileUtil;
import com.example.check.util.ExcelUtil;
import com.example.check.util.ProcessingData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WebAdminController {

    @Resource
    private ProcessingData processingData;
    @Resource
    private DepartmentServiceImpl departmentService;
    @Resource
    private CheckServiceImpl checkService;

    /**
     * 导出excel表格
     * @param s
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
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


//        System.err.println(depId + "-" + depSecendId + "-" + startTime + "-" + endTime);

        List<ResultScore> lists = processingData.getData(startTime, endTime, depId, depSecendId);

        //        对查询到的数据进行进一步整理
        /**
         * 因为需要对每个班进行汇总，
         * 要判断前一个和后一个的班次是否不一样，
         * 不一样就需要在对象数组里放入一个空的对象。
         * 方便在写Excel中空出一行位置编写数据
         */
        List<ResultScore> res = new ArrayList<>();
        for (ResultScore resultScore : lists) {
            res.add(resultScore);
        }
        int count = 0;
        List<ResultScore> res2 = new ArrayList<>();
        for (int i = 0; i < res.size(); i++) {
            if ((i + 1) < res.size()) {
                if (res.get(i).getDepSecend() != null) {
                    if (!(res.get(i).getDepSecend().getDepSecendId()).equals(res.get(i + 1).getDepSecend().getDepSecendId())) {
                        count++;
                        res2.add(res.get(i));
                        continue;
                    }
                }

            }
            if (count > 0) {
                res2.add(new ResultScore());
                count = 0;
            }
            res2.add(res.get(i));
        }

        //
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = time.format(dtf);

        File file = null;
        if (depSecendId == null || depSecendId == 0) {
            file = ExcelUtil.writeExcel(res2,
                    "7S稽查项目",
                    date + departmentService.getDep(depId).getDepName() + "点检.xls");
        } else {


            file = ExcelUtil.writeExcel(res2,
                    "7S稽查项目",
                    date + departmentService.getDep(depId).getDepName() + departmentService.getSecend(depId, depSecendId).getDepSecendName() + "点检.xls");
        }

        //      下载文件
        DownloadFileUtil.downloadFile(file.getName(), new File(file.getAbsolutePath()), response, request);

        return "success";
    }


    /**
     * 删除点检项
     * @param deductId
     * @return
     */
    @RequestMapping("/delDeduct")
    public String delDeduct(@RequestParam("deductId") Integer deductId) {
        int res = checkService.delDeduct(deductId);
        if (res == 1) {
            return "删除成功";
        }
        return "删除失败";
    }

    /**
     * 修改点检项
     * @param strs
     * @return
     */
    @RequestMapping("/updateDeduct")
    public String updateDeduct(@RequestBody String strs) {

        JSONObject jsonObject = JSON.parseObject(strs);
        Integer id = jsonObject.getInteger("id");
        String reason = jsonObject.getString("reason");
        Integer minusScore = jsonObject.getInteger("minusScore");

        Deduct deduct = new Deduct();
        deduct.setId(id);
        deduct.setReason(reason);
        deduct.setMinusScore(minusScore);

        int res = checkService.updateDeduct(deduct);
        if (res == 1) {
            return "修改成功";
        }
        return "修改失败";
    }

}
