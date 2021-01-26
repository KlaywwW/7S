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

    @Test
    void test(){}

}
