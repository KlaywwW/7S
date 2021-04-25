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

    /**
     * 添加扣分信息，有图片上传图片
     * @param deduct 扣分信息
     * @param request 请求
     * @param num 图片个数
     * @return
     */
    @PostMapping("/addDeduct")
    public String addDeduct(Deduct deduct, MultipartRequest request, Integer num) {


        List<MultipartFile> files = new ArrayList<MultipartFile>();
        for (int i = 0; i < num; i++) {
            System.err.println("文件名："+request.getFile("file" + i).getOriginalFilename() + "=============================");
            files.add(request.getFile("file" + i));
        }

        System.out.println(String.valueOf((new Date().getTime())));
        deduct.setTime(String.valueOf((new Date().getTime())));
//        保存扣分信息
        int res = checkService.addDeduct(deduct);
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
                    // 保存文件


                        file.transferTo(new File(filePath));

                        Imagelist imagelist = new Imagelist();
                        imagelist.setImgName(mi+"-"+filename);
                        imagelist.setImgPath(filePath);
                        imagelist.setDeductId(checkService.getNewId());
                    //  保存图片路径
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

    /**
     * 部门id+班别id查询点检项目
     * @param depId 部门id
     * @param depSecendId 班别id
     * @return
     */
    @GetMapping("/getCheckItems")
    public List<Checkitems> getAllitems(@RequestParam Integer depId, @RequestParam Integer depSecendId) {
        List<Checkitems> items = checkService.getAllItems(depId, depSecendId);
        return items;
    }

    /**
     * 查询所有部门
     * @return
     */
    @GetMapping("/getDep")
    public List<Department> getDep() {

        return departmentService.getAllDep();
    }

    /**
     * 根据部门id查询该部门下对应的班别
     * @param depId 部门id
     * @return
     */
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
    @PostMapping("/addDeductList")
    public String addDeductList(@RequestBody List<Deduct> deducts){
        System.out.println(deducts.size());
        int res=0;
        for (Deduct deduct: deducts) {
            System.out.println(deduct.toString());
            deduct.setTime(String.valueOf((new Date().getTime())));
            res=checkService.addDeduct(deduct)+res;
        }
        if (res==deducts.size()) {
            return "success";
        }
        return "error";
    }

}
