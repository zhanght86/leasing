package com.pqsoft.entity;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.pqsoft.util.DateUtil;
import com.pqsoft.util.StringUtils;

public class ExcelMoreSheet {
	

	private String name = "";//excel文件名
	private int sheetNum = 0;//sheet数目
	private List<String> sheetNames = new ArrayList<String>();//sheet名称
	private List<LinkedHashMap<String,String>> titles = new ArrayList<LinkedHashMap<String,String>>();//表头
	private List<List<Map<String,Object>>> data = new ArrayList<List<Map<String,Object>>>();//数据
	
	private boolean hasTitle = true;//是否需要列title
	private WritableWorkbook book = null;//exclebook
	private String filePath = "";
	
	//财务报表用到以下两个字段
	private List<String> headTitles = new ArrayList<String>();// sheet 里面大标题名称																// yuq
	private boolean isHeadDate = false;// 是否插入 日期 yuq
	
	private boolean isHeadMoreColumn=false;
	private List<List<String>> headMoreColumnTitles = new ArrayList<List<String>>();// sheet 里面大标题名称


	private boolean hasRownum = false;//是否需要序列号
	private String rownumName = "序号";//是否需要序列号
	private boolean autoColumn = true;// 列宽是否jxl默认
	private int columnLengh = 10;//不是jxl默认列宽时的默认宽度
	private boolean neadDateFormat = true;//是否需要格式化日期
	private boolean defaultFormat = true;//默认格式化
	private Map<String,String> formatParams = new HashMap<String,String>();//格式化匹配对象
	

	
	/*
	 * 预定义格式
	 */
	//文件头
	public static String HEADERFORMAT = "1";
	//插入标题式导入日期样式
	public static String HEADDATEFORMAT = "2";
	//标题样式
	public static String TITLEFORMAT = "3";
	//普通文本样式
	public static String DETFORMAT = "4";
	//number样式0.######
	public static String PRICEFORMAT = "5"; 
	//

	public static String PRICEFORMAT1 = "6"; 
	//整数
	public static String ZSFORMAT = "7"; 

	//日期yyyy/MM/dd
	public static String DATEFORMAT = "8"; 

	//日期yyyy-MM-dd
	public static String DATEFORMAT1 = "9"; 
	//￥
	public static String RMBFORMAT = "10"; 
	
	public void putFormateParam(String i,String format){
		formatParams.put(i, format);
	}
	
	public void defaultFormat(boolean flag){
		this.defaultFormat = flag;
	}
	
	public void neadDateFormat(boolean neadDateFormat){
		this.neadDateFormat = neadDateFormat;
	}
	public void setAutoColumn(int columnLengh){
		this.columnLengh = columnLengh;
		this.autoColumn = false;
	}
	public void setAutoColumn(boolean autoColumn){
		this.autoColumn = autoColumn;
	}
	public boolean isHasRownum() {
		return hasRownum;
	}
	public void hasRownum(boolean hasRownum) {
		this.hasRownum = hasRownum;
	}
	public void hasRownum() {
		this.hasRownum = true;
	}
	public void hasRownum(String rowNumTitleName) {
		this.hasRownum = true;
		this.rownumName = StringUtils.nullToOther(rowNumTitleName,this.rownumName);
	}
	public List<String> getHeadTitles() {
		return headTitles;
	}
	public void setHeadTitles(String headTitle) {
		this.headTitles.add(headTitle);
	}
	public boolean isHeadDate() {
		return isHeadDate;
	}
	public void setHeadDate(boolean isDate) {
		this.isHeadDate = isDate;
	}
	
	public boolean isHeadMoreColumn() {
		return isHeadMoreColumn;
	}

	public void setHeadMoreColumn(boolean isHeadMoreColumn) {
		this.isHeadMoreColumn = isHeadMoreColumn;
	}
	
	public ExcelMoreSheet(String filePath){
		this.filePath = filePath;
	}
	public  static Map<Integer,List<List<String>>> read2Map(String fileName){
		return read2Map(new File(fileName));
	}
	public  static Map<Integer,List<List<String>>> read2Map(File file){
		Map<Integer,List<List<String>>> map = new HashMap<Integer,List<List<String>>>();//每个excel
		List<List<String>> lists = null;//每个sheet
		List<String> list = null;//每行
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet[] sheets = book.getSheets();
			Sheet sheet = null;
			Cell cell = null;
			for(int i = 0; i < sheets.length ; i++){
				lists = new ArrayList<List<String>>();//excel每个sheet
				sheet = sheets[i];
				for(int j = 0 ; j < sheet.getRows() ; j++){//行
					list = new ArrayList<String>();//sheet中每行
					for(int k = 0 ; k < sheet.getColumns() ; k++){//列
						cell = sheet.getCell(k, j);//k列j行
						if(cell.getType() == CellType.DATE){
							list.add(DateUtil.format(((DateCell)cell).getDate(),"yyyy-MM-dd"));
						}else{
							list.add(cell.getContents().trim());
						}
						
					}
					lists.add(list);
				}
				map.put(i, lists);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void write2Stream(OutputStream out) throws Exception{
		this.book = Workbook.createWorkbook(out);
		write();
		book.write();
	}
	
	public void write2File(File file) throws Exception{
		this.book = Workbook.createWorkbook(file);
		write();
		book.write();
	}
	
	@SuppressWarnings("unchecked")
	private void write() throws WriteException{
		String currSheetName = "";
		if(name == null || name.trim().equals(""))
			this.name = (new Date()).getTime()+".xls";
		this.name = this.name.indexOf(".xls") == -1 ? this.name + ".xls" : this.name;
		WritableFont headerFont = new WritableFont(WritableFont.createFont("宋体"), 15, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
		WritableCellFormat headerFormat = new WritableCellFormat (headerFont); 
		headerFormat.setAlignment(Alignment.CENTRE);//居中
		headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		headerFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		headerFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		headerFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		headerFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);

		//插入标题式导入日期样式
		WritableFont headDateFont = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.TEAL); 
		WritableCellFormat headDateFormat = new WritableCellFormat (headDateFont); 
		headDateFormat.setAlignment(Alignment.CENTRE);//居中
		headDateFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		headDateFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		headDateFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		headDateFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		headDateFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);

		//标题样式
		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
		WritableCellFormat titleFormat = new WritableCellFormat (titleFont); 
		titleFormat.setAlignment(Alignment.CENTRE);
		titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		titleFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		titleFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		titleFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		titleFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);

		//普通文本样式
		WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
		WritableCellFormat detFormat = new WritableCellFormat (detFont);
		detFormat.setAlignment(Alignment.CENTRE);
		detFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		detFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		detFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		detFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
		
		//number样式
		jxl.write.NumberFormat nf=new jxl.write.NumberFormat("0.######");  //用于Number的格式
		WritableCellFormat priceFormat = new WritableCellFormat (detFont, nf); 
		priceFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		priceFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		priceFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		priceFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
		//￥样式
		jxl.write.NumberFormat rmb=new jxl.write.NumberFormat("￥##,##0");  //用于Number的格式
		WritableCellFormat rmbFormat = new WritableCellFormat (detFont, rmb); 
		rmbFormat.setAlignment(Alignment.CENTRE);
		rmbFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		rmbFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		rmbFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		rmbFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
		
		//0.00
		WritableCellFormat priceFormat1 = 
			new WritableCellFormat (
					new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK), 
						new jxl.write.NumberFormat("0.00")); 
		priceFormat1.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		priceFormat1.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		priceFormat1.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		priceFormat1.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);

		// 日期
		WritableCellFormat dateFormat1 = 
			new WritableCellFormat (
					new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK), 
						new jxl.write.DateFormat("yyyy-MM-dd")
					); 
		dateFormat1.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		dateFormat1.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		dateFormat1.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		dateFormat1.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
		
		//整形
		WritableCellFormat zsFormat = 
			new WritableCellFormat (
					new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK), 
						new jxl.write.NumberFormat("0")); 
		zsFormat.setAlignment(Alignment.CENTRE);
		zsFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		zsFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		zsFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		zsFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
		
        //日期
		jxl.write.DateFormat df=new jxl.write.DateFormat("yyyy/MM/dd");//用于日期的
		WritableCellFormat dateFormat = new WritableCellFormat (detFont, df); 
		dateFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		dateFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		dateFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		dateFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);



		for(int sheetIndex = 0; sheetIndex < this.sheetNum; sheetIndex++){
			WritableSheet sheet = null;
			Cell label = null;
			LinkedHashMap<String,String> title = null;
			List<Map<String,Object>> singleData = null;
			Map<String,Object> singleRow = null;
			
	
			//sheetName
			try{
				currSheetName = sheetNames.get(sheetIndex);
				if(StringUtils.isBlank(currSheetName)){
					throw new Exception();
				}
			}catch(Exception e){
				currSheetName = "sheet"+sheetIndex+1;
			}
			sheet = book.createSheet(currSheetName, 0);
			
			singleData = data.get(sheetIndex);

			int rowSeq = 0; // 行序列

			// head 标题
			if (isHeadDate) {
				int numberOfColumns = titles.get(0).size() - 1; // 总列数
				if(this.hasRownum){
					++numberOfColumns;
				}
				// 添加合并区域 标题行
				if (headTitles.size() > 0) {
					sheet.mergeCells(0, rowSeq, numberOfColumns, rowSeq);// 合并单元格
					label = new Label(0, rowSeq, headTitles.get(sheetIndex),
							headerFormat);
					sheet.addCell((WritableCell) label);
					rowSeq++;
				}
				if (isHeadDate) {
					sheet.mergeCells(0, rowSeq, numberOfColumns, rowSeq);
					label = new Label(0, rowSeq, "导出日期："
							+ DateUtil.getSysDate("yyyy-MM-dd"), headDateFormat);
					sheet.addCell((WritableCell) label);
					rowSeq++;
				}

			}
			
			//标题有多列，不会和isHeadDate共存
			if(isHeadMoreColumn && !isHeadDate){
				if(headMoreColumnTitles.size()>0){
					List<String> titleList=headMoreColumnTitles.get(sheetIndex);
					for(int titleMoreNum=0;titleMoreNum<titleList.size();titleMoreNum++){
						sheet.mergeCells(titleMoreNum, rowSeq, titleMoreNum, rowSeq);// 合并单元格
						label = new Label(titleMoreNum, rowSeq, titleList.get(titleMoreNum),detFormat);
						sheet.addCell((WritableCell) label);
					}
					rowSeq++;
				}
			}

			// title 列
			if (titles.size() > sheetIndex) {
				title = titles.get(sheetIndex);
				Iterator<String> ite = title.keySet().iterator(); 
				int temp = 0;
				if(this.hasRownum){
					//+
					label = new Label(temp++,rowSeq,StringUtils.nullToString(rownumName), titleFormat);
					sheet.addCell((WritableCell)label);
					this.headTitles.add("1");
					//+++
				}
				
				while(ite.hasNext()){
					label = new Label(temp++,rowSeq,StringUtils.nullToString(title.get(ite.next())), titleFormat);
					sheet.addCell((WritableCell)label);
				}
				rowSeq++;
			}
			if(title == null){
				title = new LinkedHashMap(singleData.get(0));
				Iterator<String> ite = title.keySet().iterator(); 
				int temp = 0;
				if(this.hasRownum){
					//+
					label = new Label(temp++,rowSeq,StringUtils.nullToString("number"), titleFormat);
					sheet.addCell((WritableCell)label);
					//+++
				}
				
				while(ite.hasNext()){
					label = new Label(temp++,rowSeq,StringUtils.nullToString(ite.next()), titleFormat);
					sheet.addCell((WritableCell)label);
				}
				rowSeq++;
			}
			//body
			for(int rowNum = 0; rowNum < singleData.size(); rowNum++){
				singleRow = singleData.get(rowNum);
				Iterator<String> it = title.keySet().iterator(); 
				int col = 0;
				if(this.hasRownum){
					//+
					label = new jxl.write.Label(col++,rowNum+rowSeq,(rowNum+1)+"", detFormat);	
					sheet.addCell((WritableCell)label);
					//+++
				}

				if(defaultFormat||this.formatParams.size()<1){
					while(it.hasNext()){
						Object tem =singleRow.get(it.next().toUpperCase());
						if(tem instanceof java.math.BigDecimal){
							if((tem == null) || (tem.equals(""))){
								tem = new BigDecimal("0.0000");
							}
							label = new jxl.write.Number(col++,rowNum+rowSeq,((BigDecimal)tem).doubleValue(), priceFormat);	
						}else if(tem instanceof java.sql.Timestamp && neadDateFormat){
							label = new jxl.write.DateTime(col++,rowNum+rowSeq,(java.util.Date)tem, dateFormat);
						}else if(StringUtils.nullToString(tem).matches("[1-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") && neadDateFormat){
							SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd");
							try {
								label = new jxl.write.DateTime(col++,rowNum+rowSeq,sdf.parse(tem.toString()), dateFormat);
							} catch (ParseException e) {
								label = new jxl.write.Label(col++,rowNum+rowSeq,StringUtils.nullToString(tem), detFormat);
							}
							
						}else{
						
							label = new jxl.write.Label(col++,rowNum+rowSeq,StringUtils.nullToString(tem), detFormat);
						}
						//label = new Label(col++,rowNum+1,StringUtils.nullToString(tem), DETFORMAT);
						//System.out.println(tem.getClass());
						if(!autoColumn){
							sheet.setColumnView(col,this.columnLengh);
						}
						sheet.addCell((WritableCell)label);
					}
					//singleData.set(rowNum, null);//防止内存溢出
				}else{
					while(it.hasNext()){
						String temp = it.next().toUpperCase(); 
						Object tem = singleRow.get(temp);
						String formater = formatParams.get(temp);
						if(formater != null){
							if(RMBFORMAT.equals(formater)){
								label = new jxl.write.Number(col++,rowNum+rowSeq,Double.valueOf(tem.toString()), rmbFormat);	
							}else if(PRICEFORMAT.equals(formater)){//0.########
								if((tem == null) || (tem.equals(""))){
									tem = new BigDecimal("0.0000");
								}
								label = new jxl.write.Number(col++,rowNum+rowSeq,Double.valueOf(tem.toString()), priceFormat);	

							}else if (DATEFORMAT.equals(formater)){//yyyy/MM/dd
								label = new jxl.write.DateTime(col++,rowNum+rowSeq,this.parse(tem.toString()), dateFormat);

							}else if(PRICEFORMAT1.equals(formater)){
								if((tem == null) || (tem.equals(""))){
									tem = new BigDecimal("0.00");
								}
								label = new jxl.write.Number(col++,rowNum+rowSeq,Double.valueOf(tem.toString()), priceFormat1);	
							}else if(ZSFORMAT.equals(formater)){
								if((tem == null) || (tem.equals(""))){
									tem = new BigDecimal("0");
								}
								label = new jxl.write.Number(col++,rowNum+rowSeq,Double.valueOf(tem.toString()), zsFormat);	
							}else if(DATEFORMAT1.equals(formater)){
								label = new jxl.write.DateTime(col++,rowNum+rowSeq,this.parse(tem.toString()), dateFormat1);
							}else{
								label = new jxl.write.Label(col++,rowNum+rowSeq,StringUtils.nullToString(tem), detFormat);
							}
						}else{
							label = new jxl.write.Label(col++,rowNum+rowSeq,StringUtils.nullToString(tem), detFormat);
						}
						if(!autoColumn){
							sheet.setColumnView(col,this.columnLengh);
						}
						sheet.addCell((WritableCell)label);
					}
					//singleData.set(rowNum, null);//防止内存溢出
				}
			}
			
			//判断是否需要列title  不需要删除第一行
			if(!hasTitle){
				sheet.removeRow(0);
			}
			
		}
	}
	
	
	public ExcelMoreSheet() {
		
	}
	public String getName() {
		return name;
	}
	public ExcelMoreSheet(String name, List<String> sheetName,
			List<LinkedHashMap<String, String>> titles,
			List<List<Map<String, Object>>> data) {
		super();
		this.name = name;
		this.sheetNames = sheetName;
		this.titles = titles;
		this.data = data;
		this.sheetNum = data.size();
	}
	
	public int getSheetNum() {
		return sheetNum;
	}
	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getSheetName() {
		return sheetNames;
	}
	public void setSheetName(List<String> sheetName) {
		this.sheetNames = sheetName;
	}
	public List<LinkedHashMap<String, String>> getTitles() {
		return titles;
	}
	public void setTitles(List<LinkedHashMap<String, String>> titles) {
		this.titles = titles;
	}
	public List<List<Map<String, Object>>> getData() {
		return data;
	}
	public void setData(List<List<Map<String, Object>>> data) {
		this.data = data;
		this.sheetNum = data.size();
	}

	public List<String> getSheetNames() {
		return sheetNames;
	}

	public void setSheetNames(List<String> sheetNames) {
		this.sheetNames = sheetNames;
	}
	
	public List<List<String>> getHeadMoreColumnTitles() {
		return headMoreColumnTitles;
	}

	public void setHeadMoreColumnTitles(List<List<String>> headMoreColumnTitles) {
		this.headMoreColumnTitles = headMoreColumnTitles;
	}

	public WritableWorkbook getBook() {
		return book;
	}

	public void setBook(WritableWorkbook book) {
		this.book = book;
	}

	public void close() throws Exception {
		if(book != null){
			book.close();
		}
	}
	
	/**
	 * 是否需要列title
	 * @param hasTitle 默认true(有title)
	 * @author:  吴剑东
	 * @date:    2013-9-27 上午09:39:14
	 */
	public void setHasTitle(boolean hasTitle) {
		this.hasTitle = hasTitle;
	}
	
	public Date parse(String s){
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
