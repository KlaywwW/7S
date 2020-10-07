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
            sheet.setColumnWidth(i, 4000);
            if (i == 2) {
//                sheet.setDefaultColumnWidth(30);
                font2.setFontHeight((short) 300);
                font2.setBold(true);
                style2.setFont(font2);
                sheet.setColumnWidth(i, 20000);
//                sheet.setColumnWidth(i, 20);
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
        for (int j = 0; j < listData.size(); j++) {

            row = sheet.createRow(j + 2);
            row.setHeight((short) 550);
            style = workbook.createCellStyle();
            font = workbook.createFont();
            font.setFontHeight((short) 180);
            style.setFont(font);


//            if (listData.get(j).getDepSecend() != null) {
//                if (listData.get(j).getDepSecend().getDepSecendId() > tempDepSecendId) {
//
//                    cell = row.createCell(2);
//                    cell.setCellValue("总分:");
//                    row.createCell(3).setCellValue(total);
//
//
//                    tempDepSecendId=listData.get(j).getDepSecend().getDepSecendId();
////                    j=j-1;
//                    continue;
//                }
//            }


            int col = 0;
            cell = row.createCell(col);
            cell.setCellValue(listData.get(j).getDepartment().getDepName());


            col = ++col;
            cell = row.createCell(col);
            if (listData.get(j).getDepSecend() != null) {
                cell.setCellValue(listData.get(j).getDepSecend().getDepSecendName());

//                tempDepSecendId = listData.get(j).getDepSecend().getDepSecendId();
            } else {
                cell.setCellValue("");
            }

            col = ++col;
            cell = row.createCell(col);
            cell.setCellValue(listData.get(j).getItem());

            col = ++col;
            cell = row.createCell(col);
            System.err.println(col);
            cell.setCellValue(listData.get(j).getScore());
            total = (double) listData.get(j).getScore() + total;

            col = ++col;
            cell = row.createCell(col);
            cell.setCellValue(listData.get(j).getCheckitems().getResponsibility());

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
                            sb.append("\n");
                        }else{
                            continue;
                        }

//                        插入图片
                        int column = 6; //备注图片位置
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
                                column++;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    System.err.println(sb.toString());

                    cell.setCellValue(sb.toString());
                }

            }


            cell.setCellStyle(style);

        }
        //      最后一行
        CellStyle styleLast = workbook.createCellStyle();
//        水平居中
        styleLast.setAlignment(HorizontalAlignment.CENTER);
//        垂直居中
        styleLast.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFRow rowLast = sheet.createRow(listData.size() + 2);
        rowLast.setHeight((short) 550);

        HSSFCell cellLast = rowLast.createCell(2);
        cellLast.setCellValue("总分:");
        rowLast.createCell(3).setCellValue(total);
        Font fontLast = workbook.createFont();
        fontLast.setBold(true);
        fontLast.setColor((short) 200);
        fontLast.setFontHeight((short) 200);

//      将字体对象赋值给单元格样式对象
        styleLast.setFont(fontLast);
//      将单元格样式应用于单元格
        cellLast.setCellStyle(styleLast);


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
