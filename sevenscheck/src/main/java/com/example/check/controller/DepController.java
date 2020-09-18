package com.example.check.controller;

import com.example.check.pojo.*;
import com.example.check.service.CheckServiceImpl;
import com.example.check.service.DepartmentServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class DepController {

    @Resource
    private CheckServiceImpl checkService;
    @Resource
    private DepartmentServiceImpl departmentService;

    private String fileRootPath = "D:\\7Simages\\";

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
                            .append(System.currentTimeMillis()).append("_")
                            .append(filename)
                            .toString();
                    try {
//                 保存文件
                        file.transferTo(new File(filePath));

                        Imagelist imagelist = new Imagelist();
                        imagelist.setImgName(filename);
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

        List<Checkitems> items = checkService.getAllItems(depId, depSecendId);

        List<ResultScore> resultList = new ArrayList<>();
        ResultScore resultScore=null;

        int tempCount=0;

        for (Checkitems check : items) {
            resultScore=new ResultScore();
//            将所有的项目放到结果类里
            resultScore.setItem(check.getItem());

//            获取扣分项的ItemId
            List<Checkitems> checkitemsList = checkService.getDeductItem(startTime, endTime, depId, depSecendId);
            double count = 0;
            for (Checkitems checkitems : checkitemsList) {


                if(checkitems.getId()!=check.getId()){
                    continue;
                }

//                System.out.println("checkItem----" + checkitems.getId());
//                根据itemId找到对应的分数
                List<Deduct> deductsList = checkService.getDeduct(checkitems.getId());

                for (Deduct deduct : deductsList) {
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


        return resultList;

    }

}
