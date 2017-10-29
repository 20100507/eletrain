package cn.edu.qqhru.train.utils;

import java.awt.Color;

import org.apache.poi.hssf.usermodel.EscherGraphics;
import org.apache.poi.hssf.usermodel.EscherGraphics2d;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShapeGroup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShapeGroup;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class POIUtils {
	
	   public static void drawLine(HSSFSheet sheet, HSSFRow row, int i, int j, int width, int height,
	            int[] xys) {
	        int cellWidth = (int) (50 * 0.75f * width);
	        short cellHeight = (short) ( 30* 0.75f * height);
	        sheet.setColumnWidth(j, cellWidth);
	        row.setHeight(cellHeight);
	 
	        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
	        HSSFClientAnchor a = new HSSFClientAnchor(0, 0, 1023, 255, (short) j, i, (short) (j), i);
	        HSSFShapeGroup group = patriarch.createGroup(a);
	        float verticalPointsPerPixel = a.getAnchorHeightInPoints(sheet);
	        EscherGraphics g = new EscherGraphics(group, sheet.getWorkbook(), Color.black,
	                verticalPointsPerPixel);
	        EscherGraphics2d g2d = new EscherGraphics2d(g);
	 
	        for (int l = 0; l < xys.length; l += 2) {
	            int x = (int) ((50 * 0.75 * xys[l] / cellWidth) * 1023);
	            int y = (int) ((20 * 0.75 * xys[l + 1] / cellHeight) * 255);
	            g2d.drawLine(0, y, x, 0);
	        }
	    }


}
