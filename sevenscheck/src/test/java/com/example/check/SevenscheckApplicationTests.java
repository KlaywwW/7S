package com.example.check;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class SevenscheckApplicationTests {

	@Test
	void contextLoads() {
		FileOutputStream fileOut = null;
		BufferedImage bufferImg =null;
		BufferedImage bufferImg1 = null;
		try{

			//先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();
			bufferImg = ImageIO.read(new File("D:\\7Sdata\\7Simages\\46-1600825608307.jpg"));
			bufferImg1 = ImageIO.read(new File("D:\\7Sdata\\7Simages\\12-1599953804985.jpeg"));
			ImageIO.write(bufferImg,"jpg",byteArrayOut);
			ImageIO.write(bufferImg1,"jpg",byteArrayOut1);

			//创建一个工作薄
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("new sheet");
			//HSSFRow row = sheet1.createRow(2);
			HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 5, 2, (short) 5, 2);
			HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, 2, (short) 6, 2);
			anchor1.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
			//插入图片
			patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
			patriarch.createPicture(anchor1 , wb.addPicture(byteArrayOut1.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));

			fileOut = new FileOutputStream("d:/workbook.xls");
			//写入excel文件
			wb.write(fileOut);
			fileOut.close();

		}catch(IOException io){
			io.printStackTrace();
			System.out.println("io erorr : "+ io.getMessage());
		} finally
		{
			if (fileOut != null)
			{

				try {
					fileOut.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	@Test
	void testTime(){
//		String s=String.valueOf("1601251882");
//		System.out.println(s*1000);
//		String t=String.toString(s*1000);
//		System.out.println(t);
		Long time=new Long("1601252362736");
		Long time2=new Long("1601252667462");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf2=new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sf.format(new Date(Long.parseLong(String.valueOf(time)))));

	}

	@Test
	void testCount(){
		List<String> hay = new ArrayList<String>();
		hay.add("2020-09-27");
		hay.add("2020-09-28");
//		Match m = new Match();
		merge(hay);
	}
	class Group {
		private List<String> ins = new ArrayList<String>();

		public List<String> getIns() {
			return ins;
		}

		public void setIns(List<String> ins) {
			this.ins = ins;
		}

	}
	public void merge(List<String> hay) {
		Set<String> keys = new HashSet<String>();
		Map<String, Group> groups = new HashMap<String, Group>();
		for (String each : hay) {
			keys.add(each);
		}
		for (String key : keys) {
			groups.put(key, new Group());
		}
		for (String each : hay) {
			groups.get(each).getIns().add(each);
		}
		display(groups);
	}

	public void display(Map<String, Group> groups) {
		int count=0;
		for (Map.Entry<String, Group> each : groups.entrySet()) {
			System.err.println("++++++++++++++++++==");
			for (String g : each.getValue().getIns()) {
				System.err.println(g);
			}
			System.err.println("+++++++++++++++++++++");
			count++;
		}
		System.out.println(count);
	}

}
