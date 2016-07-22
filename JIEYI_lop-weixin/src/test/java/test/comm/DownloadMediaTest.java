package test.comm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class DownloadMediaTest {
	
	@Test
	public void test1(){
		try {
			URL url = new URL("https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=lY0GxJNVxWMP-Y1RpaqEQm6qk9LN-C2AXIp_7_hc_sz15rksJlrtrQ4SgPtGeEK-&media_id=Ne0T1vHdGpyX6nILEqS1G8dLXCgmw-UjUMUJioJiqOwKi1LxnHj2YYA5hgIx3a9I");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			System.out.println(conn.getContentType());
			BufferedInputStream bis = new BufferedInputStream( conn.getInputStream());
			File file = new File("D:/testweixin/");
			if(!file.exists()){
				file.mkdirs();
			}
			File file2 = new File(file, "ddj.jpg");
			file2.createNewFile();
			FileOutputStream fos = new FileOutputStream(file2);
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();

			conn.disconnect();
			
		} catch ( Exception e) {
			e.printStackTrace();
		} 
	}
}

