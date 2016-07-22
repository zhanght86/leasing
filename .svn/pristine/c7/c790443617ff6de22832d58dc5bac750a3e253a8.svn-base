/**
 * TODO 
 * @author LUYANFENG @ 2015年6月4日
 */
package test.comm;

import java.util.Locale;

import jsp.weixin.encryption.util.AesException;
import jsp.weixin.encryption.util.WXBizMsgCrypt;

import org.junit.Test;

/**
 * TODO 
 * @author LUYANFENG @ 2015年6月4日
 *
 */
public class TestWXBizMsgCrypt {
	
	@Test
	public void test1(){
		try {
			WXBizMsgCrypt  wxcpt = new WXBizMsgCrypt("jdjdjd", "yyJ5XzLK1sbJJsPI5GygrRbJL9TRGKMPjXEwAtGGme3", "wx50aff605c05cf5b4");
			String verifyMsgSig = "a7ccd759eef88d4764550fe34d4e55d5cb671907";
			String timeStamp = "1433394843";
			String nonce = "1915271680";
			String echoStr = "6217851678137245691";
			wxcpt.VerifyURL(verifyMsgSig, timeStamp, nonce, echoStr);
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2(){
		System.out.println(Locale.getDefault());
	}
	
	@Test
	public void test3(){
		System.out.println(B.a);
	}
}
class B{
	
	private static String b = "b";
	static {
		b = "B";
	}
	public static final String a = String.format("http://abc?name=%1$s", b);
}
