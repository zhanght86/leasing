/**
 * TODO 
 * @author LUYANFENG @ 2015年7月9日
 */
package test.comm;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月9日
 *
 */
public class IteratorMapTest {
	
	@Test
	public void test1(){
//		HashMap<String,Object> a = new HashMap<String,Object> ();
		LinkedHashMap<String,Object> a = new LinkedHashMap<String,Object> ();
		a.put("1", "1");
		a.put("2", "2");
		a.put("3", "3");
		a.put("0", "0");
		a.put("9", "9");
		Map<String,Object> b = new TreeMap<String,Object>(a);
		for(Map.Entry<String, Object> m : b.entrySet()){
			System.out.println(m.getKey() + " : " + m.getValue());
		}
	}
	
	@Test
	public void test2(){
//		Integer a = 1;
//		Integer b = 1;
		String a = "1";
		String b = "1";
		System.out.println(a == b);
		System.out.println(a.equals(b));
		
		System.out.println(this.getme());
	}
	
	private String getme(){
		String s = "";
		try{
			 s= "是我";
			 System.out.println(s);
			 Integer.valueOf(s);
		}catch(Exception e){
			 s = "是我哈";
			 System.out.println(s);
		}finally{
			s = "是我啊";
			 System.out.println(s);
		}
		return s;
	}

}
