package com.example.check.util;

import com.example.check.pojo.DepSecend;
import com.example.check.pojo.Department;
import com.example.check.pojo.ResultScore;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {

    /**
     *
     * @param listData 获取的数据
     * @param sheetName
     * @param fileName
     * @return
     */
    public static File writeExcel(List<ResultScore> listData, String sheetName, String fileName) {
//       声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
//        生成一个表格，设置表格名称
        HSSFSheet sheet = workbook.createSheet(sheetName);
//        设置表格列宽
//        sheet.setDefaultColumnWidth(10);
        sheet.setDefaultRowHeight((short) 550);

//      第一行
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 5);//合并单元格
        sheet.addMergedRegion(region);
        CellStyle style1 = workbook.createCellStyle();
//        水平居中
        style1.setAlignment(HorizontalAlignment.CENTER);
//        垂直居中
        style1.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFRow row1 = sheet.createRow(0);
        row1.setHeight((short) 1000);

        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("7S改善每月評核表");

        Font font1 = workbook.createFont();
        font1.setBold(true);
        font1.setFontHeight((short) 400);

//      将字体对象赋值给单元格样式对象
        style1.setFont(font1);
//      将单元格样式应用于单元格
        cell1.setCellStyle(style1);

//      第二行
        HSSFRow row2 = sheet.createRow(1);
        row2.setHeight((short) 550);

        Font font2 = workbook.createFont();
        CellStyle style2 = workbook.createCellStyle();

        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);

        String[] strs = new String[]{"部门", "小組", "改善并維持的項目", "分数", "责任人", "原因", "备注"};
        HSSFCell cell2 = null;
        for (int i = 0; i < strs.length; i++) {
            cell2 = row2.createCell(i);
            cell2.setCellValue(strs[i]);
            sheet.setColumnWidth(i, 4500);
            if (i == 1) {
                font2.setFontHeight((short) 300);
                font2.setBold(true);
                style2.setFont(font2);
                sheet.setColumnWidth(i, 3000);
            }
            if (i == 2) {
                font2.setFontHeight((short) 300);
                font2.setBold(true);
                style2.setFont(font2);
                sheet.setColumnWidth(i, 10000);
            }
            if (i == 3) {
                font2.setFontHeight((short) 300);
                font2.setBold(true);
                style2.setFont(font2);
                sheet.setColumnWidth(i, 2500);
            }

            cell2.setCellStyle(style2);
        }
        CellStyle style = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        Font font = null;
        CellRangeAddress region2 = new CellRangeAddress(2, listData.size() + 1, 0, 0);//合并单元格
        sheet.addMergedRegion(region2);
        workbook.createCellStyle();


        double total = 0;
        int tempDepSecendId = 1;
        int column = 6; //扣分图片位置
        for (int j = 0; j < listData.size(); j++) {



            row = sheet.createRow(j + 2);
            row.setHeight((short) 550);
            style = workbook.createCellStyle();
            font = workbook.createFont();
            font.setFontHeight((short) 180);
            style.setFont(font);
            int col = 0;
//            整理后的结果将空的数据行放合计
            if (listData.get(j).getCheckId()==null){
                CellStyle styleCount1 =  workbook.createCellStyle();
                col=2;
                cell = row.createCell(col);
                cell.setCellValue("合计:");
                Font fontCount1 = workbook.createFont();
                fontCount1.setBold(true);
                fontCount1.setColor(Font.COLOR_NORMAL);
                fontCount1.setFontHeight((short) 200);

                styleCount1.setVerticalAlignment(VerticalAlignment.CENTER);
                styleCount1.setFont(fontCount1);

                cell.setCellStyle(styleCount1);

                CellStyle styleCount2 =  workbook.createCellStyle();
                cell = row.createCell(col+1);
                cell.setCellValue(total);
                Font fontCount2 = workbook.createFont();
                fontCount2.setBold(true);
                fontCount2.setColor(Font.COLOR_RED);
                fontCount2.setFontHeight((short) 200);

                styleCount2.setAlignment(HorizontalAlignment.CENTER);
                styleCount2.setVerticalAlignment(VerticalAlignment.CENTER);
                styleCount2.setFont(fontCount2);

                cell.setCellStyle(styleCount2);
                total=0;
                continue;
            }
//给第一列设置样式
//            部门
            CellStyle styleDep =  workbook.createCellStyle();
            cell = row.createCell(col);
            cell.setCellValue(listData.get(j).getDepartment().getDepName());
            Font fontDep = workbook.createFont();
            fontDep.setBold(true);
            fontDep.setFontHeight((short) 200);

            styleDep.setAlignment(HorizontalAlignment.CENTER);
            styleDep.setVerticalAlignment(VerticalAlignment.CENTER);
            styleDep.setFont(fontDep);

            cell.setCellStyle(styleDep);


//小组
            col = ++col;
            cell = row.createCell(col);
            if (listData.get(j).getDepSecend() != null) {
                cell.setCellValue(listData.get(j).getDepSecend().getDepSecendName());
            } else {
                cell.setCellValue("");
            }
//项目
            col = ++col;
            cell = row.createCell(col);
            cell.setCellValue(listData.get(j).getItem());
//分数
            col = ++col;
            cell = row.createCell(col);
            cell.setCellValue(listData.get(j).getScore());
            total = (double) listData.get(j).getScore() + total;

//            给分数列设置样式
            CellStyle styleScore = workbook.createCellStyle();
            Font fontScore = workbook.createFont();
            styleScore.setAlignment(HorizontalAlignment.CENTER);
            styleScore.setVerticalAlignment(VerticalAlignment.CENTER);
            styleScore.setFont(fontScore);

            cell.setCellStyle(styleScore);


// 责任人
            col = ++col;
            cell = row.createCell(col);
            cell.setCellValue(listData.get(j).getCheckitems().getResponsibility());
//            cell.setCellValue(listData.get(j).getDepSecend().getResponsibility());


            if (listData.get(j).getDeduct() != null) {
                if (listData.get(j).getDeduct().size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    col = ++col;
                    cell = row.createCell(col);
                    for (int k = 0; k < listData.get(j).getDeduct().size(); k++) {
                        if (listData.get(j).getDeduct().get(k).getMinusScore() != 0) {
                            sb.append((k + 1));
                            sb.append(".");
                            sb.append(listData.get(j).getDeduct().get(k).getReason());
                            sb.append("。扣除的分数-->");
                            sb.append(listData.get(j).getDeduct().get(k).getMinusScore());
                            sb.append("\n");
                        }else{
                            continue;
                        }

//                        插入图片

                        System.err.println(listData.get(j).getDeduct().get(k).getImagelists().size());
                        int num = listData.get(j).getDeduct().get(k).getImagelists().size();
                        HSSFClientAnchor anchor = null;
                        BufferedImage bufferImg = null;
                        ByteArrayOutputStream byteArrayOut = null;
                        for (int x = 0; x < num; x++) {
                            String imgName = listData.get(j).getDeduct().get(k).getImagelists().get(x).getImgName();
                            String imgPath = listData.get(j).getDeduct().get(k).getImagelists().get(x).getImgPath();
                            String suffix = imgName.substring(imgName.lastIndexOf("."));
                            String suffixName = suffix.substring(1);


                            byteArrayOut = new ByteArrayOutputStream();
                            try {

                                bufferImg = ImageIO.read(new File(imgPath));
                                ImageIO.write(bufferImg, "jpg", byteArrayOut);
                                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                                //anchor主要用于设置图片的属性
                                anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) column, j + 2, (short) column, j + 2);
                                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                                //插入图片
                                patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
//                                column++;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    cell.setCellValue(sb.toString());
                }

            }


            cell.setCellStyle(style);





        }
        //      最后一行
        CellStyle styleLast1 = workbook.createCellStyle();
//        垂直居中
        styleLast1.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFRow rowLast = sheet.createRow(listData.size() + 2);
        rowLast.setHeight((short) 550);

        HSSFCell cellLast1 = rowLast.createCell(2);
        cellLast1.setCellValue("合计:");
        Font fontLast1 = workbook.createFont();
        fontLast1.setBold(true);
        fontLast1.setColor((short) 200);
        fontLast1.setFontHeight((short) 200);

        styleLast1.setFont(fontLast1);
        cellLast1.setCellStyle(styleLast1);


//        第二个单元格样式
        CellStyle styleLast2 = workbook.createCellStyle();
        styleLast2.setAlignment(HorizontalAlignment.CENTER);
        styleLast2.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFCell cellLast2 = rowLast.createCell(3);
        cellLast2.setCellValue(total);
        Font fontLast2 = workbook.createFont();
        fontLast2.setBold(true);
        fontLast2.setColor(Font.COLOR_RED);
        fontLast2.setFontHeight((short) 200);

        styleLast2.setFont(fontLast2);
        cellLast2.setCellStyle(styleLast2);



//      保存到本地
        File dest = new File("D:\\7Sdata\\7Sexcel\\" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fout = new FileOutputStream(dest.getAbsolutePath());
            workbook.write(fout);
            fout.close();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
