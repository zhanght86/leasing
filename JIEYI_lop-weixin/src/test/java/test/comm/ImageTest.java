package test.comm;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.pqsoft.util.ImageUtils;

public class ImageTest {
	
	@Test
	public void test1(){
			File file = new File("D:/testweixin/ddj.jpg");
//			String processImage = ImageUtils.scaleImage(file, 0.1f);
			String processImage = ImageUtils.reSizeImage(file, 24, 24 ,true);
			System.out.println(processImage);
	}
	
	@Test
	public void test2(){
		String str = "admin";
		this.changeMe(str);
		Assert.assertSame("admin", str);
	}
	
	private void changeMe(String str){
		str+="1";
		System.out.println(str);
	}

}
