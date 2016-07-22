package com.pqsoft.util;

import java.util.HashMap;

public class CurrencyConverter 
{
	static HashMap<Integer, String> hm = new HashMap<Integer, String>();

	public static String toUpper(String num) {
		// 小数如果没有，自动补个0
		// logger.debug("sa1:" + snum.indexOf("\\."));
		if (num.indexOf("\\.") == -1) {
			num = num + ".00";
		}

		hm.put(0, "零");
		hm.put(1, "壹");
		hm.put(2, "贰");
		hm.put(3, "叁");
		hm.put(4, "肆");
		hm.put(5, "伍");
		hm.put(6, "陆");
		hm.put(7, "柒");
		hm.put(8, "捌");
		hm.put(9, "玖");
		hm.put(10, "拾");
		hm.put(100, "佰");
		hm.put(1000, "仟");
		hm.put(10000, "万");
		String snum = num;
		String intpart = null;
		String decpart = null;
		String dec0 = null;
		String dec1 = null;
		String hasdec = null;

		String[] sa = new String[2];
		sa = snum.split("\\.");

		intpart = sa[0];
		decpart = sa[1];
		String[] sint = intpart.split(""); // 整数部分
		
		switch (sint.length) {
		case 2:
			snum = hm.get(Integer.parseInt(sint[1]));
			break;
		case 3:
			snum = hm.get(Integer.parseInt(sint[1])) + hm.get(10) + hm.get(Integer.parseInt(sint[2]));
			break;
		case 4:
			snum = hm.get(Integer.parseInt(sint[1])) + hm.get(100) + hm.get(Integer.parseInt(sint[2]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[3]));
			break;
		case 5:
			snum = hm.get(Integer.parseInt(sint[1])) + hm.get(1000) + hm.get(Integer.parseInt(sint[2]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[3])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[4]));
			break;
		case 6:
			snum = hm.get(Integer.parseInt(sint[1])) + hm.get(10000) + hm.get(Integer.parseInt(sint[2]))
					+ hm.get(1000) + hm.get(Integer.parseInt(sint[3])) + hm.get(100)
					+ hm.get(Integer.parseInt(sint[4])) + hm.get(10) + hm.get(Integer.parseInt(sint[5]));
			break;
		case 7:
			snum = hm.get(Integer.parseInt(sint[1])) + hm.get(10) + hm.get(Integer.parseInt(sint[2]))
					+ hm.get(10000) + hm.get(Integer.parseInt(sint[3])) + hm.get(1000)
					+ hm.get(Integer.parseInt(sint[4])) + hm.get(100) + hm.get(Integer.parseInt(sint[5]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[6]));
			break;
		case 8:
			snum = hm.get(Integer.parseInt(sint[1])) + hm.get(100) + hm.get(Integer.parseInt(sint[2]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[3])) + hm.get(10000)
					+ hm.get(Integer.parseInt(sint[4])) + hm.get(1000) + hm.get(Integer.parseInt(sint[5]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[6])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[7]));
			break;
		case 9:
			snum = hm.get(Integer.parseInt(sint[1])) + hm.get(1000) + hm.get(Integer.parseInt(sint[2]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[3])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[4])) + hm.get(10000) + hm.get(Integer.parseInt(sint[5]))
					+ hm.get(1000) + hm.get(Integer.parseInt(sint[6])) + hm.get(100)
					+ hm.get(Integer.parseInt(sint[7])) + hm.get(10) + hm.get(Integer.parseInt(sint[8]));
			break;
		case 10:
			snum = hm.get(Integer.parseInt(sint[1])) + "亿" + hm.get(Integer.parseInt(sint[2])) + hm.get(1000)
					+ hm.get(Integer.parseInt(sint[3])) + hm.get(100) + hm.get(Integer.parseInt(sint[4]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[5])) + hm.get(10000)
					+ hm.get(Integer.parseInt(sint[6])) + hm.get(1000) + hm.get(Integer.parseInt(sint[7]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[8])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[9]));
			break;
		case 11:
			snum = hm.get(Integer.parseInt(sint[1])) + hm.get(10) + hm.get(Integer.parseInt(sint[2])) + "亿"
					+ hm.get(Integer.parseInt(sint[3])) + hm.get(1000) + hm.get(Integer.parseInt(sint[4]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[5])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[6])) + hm.get(10000) + hm.get(Integer.parseInt(sint[7]))
					+ hm.get(1000) + hm.get(Integer.parseInt(sint[8])) + hm.get(100)
					+ hm.get(Integer.parseInt(sint[9])) + hm.get(10) + hm.get(Integer.parseInt(sint[10]));
			break;
		case 12:
			snum = hm.get(Integer.parseInt(sint[1])) + hm.get(100) + hm.get(Integer.parseInt(sint[2]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[3])) + "亿"
					+ hm.get(Integer.parseInt(sint[4])) + hm.get(1000) + hm.get(Integer.parseInt(sint[5]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[6])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[7])) + hm.get(10000) + hm.get(Integer.parseInt(sint[8]))
					+ hm.get(1000) + hm.get(Integer.parseInt(sint[9])) + hm.get(100)
					+ hm.get(Integer.parseInt(sint[10])) + hm.get(10) + hm.get(Integer.parseInt(sint[11]));
			break;
		case 13:
			snum = hm.get(Integer.parseInt(sint[1])) + hm.get(1000) + hm.get(Integer.parseInt(sint[2]))
					+ hm.get(100) + hm.get(Integer.parseInt(sint[3])) + hm.get(10)
					+ hm.get(Integer.parseInt(sint[4])) + "亿" + hm.get(Integer.parseInt(sint[5]))
					+ hm.get(1000) + hm.get(Integer.parseInt(sint[6])) + hm.get(100)
					+ hm.get(Integer.parseInt(sint[7])) + hm.get(10) + hm.get(Integer.parseInt(sint[8]))
					+ hm.get(10000) + hm.get(Integer.parseInt(sint[9])) + hm.get(1000)
					+ hm.get(Integer.parseInt(sint[10])) + hm.get(100) + hm.get(Integer.parseInt(sint[11]))
					+ hm.get(10) + hm.get(Integer.parseInt(sint[12]));
			break;
		}

		snum += "元";

		snum = snum.replaceAll("零仟", "");
		snum = snum.replaceAll("零佰", "零");
		snum = snum.replaceAll("零拾", "");
		snum = snum.replaceAll("零零亿", "亿");
		snum = snum.replaceAll("零亿", "亿");
		snum = snum.replaceAll("零零万", "万");
		snum = snum.replaceAll("零万", "万");
		snum = snum.replaceAll("亿万", "亿");
		snum = snum.replaceAll("零零元", "元");
		snum = snum.replaceAll("零元", "元");
		if (snum.startsWith("元"))
			snum = "零" + snum;
		
		String[] sdec = decpart.split(""); // 小数部分

		if(sdec.length>=3){
			if (sdec[1].equals("0") && sdec[2].equals("0")) {
				hasdec = "整";
				snum += hasdec;
			} else {
				if (sdec[1].equals("0"))
					dec0 = "零";
				else
					dec0 = hm.get(Integer.parseInt(sdec[1])) + "角";

				if (sdec[2].equals("0"))
					dec1 = "";
				else
					dec1 = hm.get(Integer.parseInt(sdec[2])) + "分";

				snum += dec0 + dec1;
			}			
		}


		return snum;

	}
}
