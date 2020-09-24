package com.example.check.controller;

import com.example.check.pojo.*;
import com.example.check.service.CheckServiceImpl;
import com.example.check.service.DepartmentServiceImpl;
import com.example.check.util.ProcessingData;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class DepController {

    @Resource
    private CheckServiceImpl checkService;
    @Resource
    private DepartmentServiceImpl departmentService;
    @Resource
    private ProcessingData processingData;


    private String fileRootPath = "D:\\7Sdata\\7Simages\\";

    //    , @RequestParam("file") MultipartFile[] files
    @PostMapping("/addDeduct")
    public String addDeduct(Deduct deduct, MultipartRequest request, Integer num) {
        System.err.println(num);
        List<MultipartFile> files = new ArrayList<MultipartFile>();
        for (int i = 0; i < num; i++) {
            System.err.println(request.getFile("file" + i).getOriginalFilename() + "=============================");
            files.add(request.getFile("file" + i));
        }

        System.out.println(String.valueOf(new Date().getTime()));
        deduct.setTime(String.valueOf(new Date().getTime()));
        System.out.println(deduct.toString());
        int res = checkService.addDeduct(deduct);
//
        int imgres = 0;
        String filePath = "";
        // 多文件上传
        if (files.size() > 0) {
            for (MultipartFile file : files) {
                // 上传简单文件名
                Calendar cal=Calendar.getInstance();
                int mi=cal.get(Calendar.MINUTE);

                String filename = file.getOriginalFilename();
                System.out.println(filename);
                String suffixName = null;
                System.out.println(filename.lastIndexOf("."));
                if (filename.lastIndexOf(".") > 0) {
                    suffixName = filename.substring(filename.lastIndexOf("."));
                    System.out.println(suffixName);
                }
                if (".jpg".equals(suffixName) || ".png".equals(suffixName) || ".gif".equals(suffixName) || ".jpeg".equals(suffixName)) {

                    // 存储路径
                    filePath = new StringBuilder(fileRootPath)
                            .append(mi).append("-")
                            .append(filename)
                            .toString();
                    try {
//                 保存文件


                        file.transferTo(new File(filePath));

                        Imagelist imagelist = new Imagelist();
                        imagelist.setImgName(mi+"-"+filename);
                        imagelist.setImgPath(filePath);
                        imagelist.setDeductId(checkService.getNewId());

                        int result = checkService.addImages(imagelist);
                        if (result == 1) {
                            imgres++;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("文件格式不正确");
                }
            }
        } else {
            System.out.println("无图片上传");
        }
        if (res == 1) {
            return "success";
        }


        return "failed";
    }

    @GetMapping("/getCheckItems")
    public List<Checkitems> getAllitems(@RequestParam Integer depId, @RequestParam Integer depSecendId) {
        List<Checkitems> items = checkService.getAllItems(depId, depSecendId);
        return items;
    }

    @GetMapping("/getDep")
    public List<Department> getDep() {

        return departmentService.getAllDep();
    }

    @GetMapping("/getSecend")
    public List<DepSecend> getSecend(@RequestParam Integer depId) {
        return departmentService.getDepSecend(depId);
    }


    @GetMapping("/getDeduct")
    public List<ResultScore> getDeduct(@RequestParam String dates, @RequestParam Integer depId, @RequestParam Integer depSecendId) {
//        List<Deduct> list=checkService.
        String[] temp = dates.split(",");
        String startTime = temp[0];
        String endTime = temp[1];
        System.err.println(startTime + "  +  " + endTime + "  +  " + depId + "  +  " + depSecendId);
        return processingData.getData(startTime,endTime,depId,depSecendId);

    }

}
