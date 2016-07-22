package com.pqsoft.util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.pqsoft.skyeye.api.SkyEye;

public class JfreeChartUtil {
	/**
	* 饼状图
	*/
	public  void generatePieChart(HttpSession session,OutputStream pw,PieDataset dataset,int w, int h){
		JFreeChart chart = ChartFactory.createPieChart3D(
			"授信占用统计图", // 图表标题
			dataset, // 数据集
			true, // 是否显示图例
			false, // 是否生成工具
			false // 是否生成URL链接
		);
	
		chart.setBackgroundPaint(Color.pink);
		chart.setTitle(new TextTitle("授信占用统计图",new Font("宋体",Font.BOLD,20)));
		LegendTitle legendtitle=chart.getLegend(0); 
		legendtitle.setItemFont(new Font("宋体",Font.ITALIC,20));
		PiePlot3D plot=(PiePlot3D)chart.getPlot();
		plot.setLabelFont(new Font("宋体",Font.BOLD,20));
		
		try {
			/*------使用printWriter将文件写出----*/
			ChartUtilities.writeChartAsJPEG(pw, chart, w, h);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String showGeneratePieChart(HttpSession session,PieDataset dataset,int w, int h){
		   JFreeChart chart = ChartFactory.createPieChart3D(
				"授信占用统计图", // 图表标题
				dataset, // 数据集
				true, // 是否显示图例
				false, // 是否生成工具
				false // 是否生成URL链接
			);
			chart.setBackgroundPaint(Color.pink);
			chart.setTitle(new TextTitle("授信占用统计图",new Font("宋体",Font.BOLD,20)));
			LegendTitle legendtitle=chart.getLegend(0); 
			legendtitle.setItemFont(new Font("宋体",Font.ITALIC,20));
			PiePlot3D plot=(PiePlot3D)chart.getPlot();
			plot.setLabelFont(new Font("宋体",Font.BOLD,20));
			String filePath ="";
			String fileName = "";
			try {
				/*------使用printWriter将文件写出----*/
				String path = SkyEye.getConfig("file.path").toString()+File.separator+"CreditReport"+ File.separator+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())+File.separator;
				BaseUtil.createDirectory(path);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
				filePath = path+"creditReportMap"+sdf.format(new Date())+".jpg" ;
				ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
				fileName = ServletUtilities.saveChartAsJPEG(chart, w, h,info,session);
				ChartUtilities.saveChartAsJPEG(new File(filePath), chart, w, h);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return fileName;
	}
}
