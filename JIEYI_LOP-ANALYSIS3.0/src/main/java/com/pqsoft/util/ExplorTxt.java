package com.pqsoft.util;

import java.io.BufferedWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.SkyEye;

public class ExplorTxt {

	public void expTxt( String fileName, String[] titlesName, int[] titlesIndex,
			List<Map<String, Object>> dataList) {
		OutputStream os;
		try {
			os = SkyEye.getResponse().getOutputStream();
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os));
			
			// 写入标题
			StringBuilder sb=new StringBuilder();
			//获得列数
			int col=titlesName.length; 
			for (int i = 0; i < col; i++) {
				sb.append(titlesName[i]);
				if(i<col-1){
					sb.append(",");
				}
			}
			sb.append("\r");
			bw.write(sb.toString());
			System.out.println(sb.toString());
			//获得行数
			int row_count = dataList.size();
			for (int i = 0; i < row_count; i++) {
				Map<String, Object> rowMap = dataList.get(i);
				StringBuilder sbBuilder=new StringBuilder();
				for (int j = 0; j < col; j++) {
					Object value = rowMap.get("COLUMN" + (titlesIndex[j])) == null ? ""
							: rowMap.get("COLUMN" + (titlesIndex[j]));
					sbBuilder.append(value.toString());
					if(i<col-1){
						sbBuilder.append(",");
					}
					
				}
				sbBuilder.append("\r");
				bw.write(sbBuilder.toString());	
//				System.out.println(sbBuilder.toString());
			}
			
			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String(fileName.getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType("text/plain");
			bw.flush();			
			bw.close();
			os.close();
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
}
