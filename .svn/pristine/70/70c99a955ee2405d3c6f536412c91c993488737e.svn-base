package com.pqsoft.leeds.utils;

import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.pqsoft.skyeye.VM;

public class VelocityUtil {
//	public static final String CONFIG_PATH = "config/velocity.properties";
//	static {//通过static块，初始化velocity引擎
////		InputStream is = VelocityUtil.class.getClassLoader()
////				.getResourceAsStream(CONFIG_PATH);
//		Properties p = new Properties();
////		try {
////			p.load(new InputStreamReader(is, "utf-8"));
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//		p.setProperty("input.encoding", "UTF-8");
//		p.setProperty("output.encoding", "UTF-8");
//		p.setProperty("resource.loader", "class");
//		p.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
//		p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//		Velocity.init(p);
//	}
	/**
	 * @desc
	 * 1、将vm解析，并打印到console
	 * @param vmPath 
	 * 		必须是符合maven标准资源目录src/man/resources下的content目录下的vm模板文件的路径
	 *
	 * @author leeds
	 * @time 2015年1月14日-下午11:54:28
	 */
	public static void print(String vmPath, VelocityContext context) {
		Template template = null;
		try {
			template = Velocity.getTemplate("content/"+vmPath);
		} catch (ResourceNotFoundException rnfe) {
			// couldn't find the template
			rnfe.printStackTrace();
		} catch (ParseErrorException pee) {
			// syntax error: problem parsing the template
			pee.printStackTrace();
		} catch (MethodInvocationException mie) {
			// something invoked in the template
			// threw an exception
			mie.printStackTrace();
		}

		StringWriter sw = new StringWriter();
		template.merge(context, sw);
		System.out.println(sw.toString());
	}
	public static String printString(String vmString, VelocityContext context) {
//		StringWriter writer = new StringWriter();
//		Velocity.evaluate(context, writer, "", vmString);
//		System.out.println(writer.toString());
		return VM.strTemp(vmString, context);
	}
	public static String printString(String vmString, Map<String, Object> params) {
		return VM.strTemp(vmString, new VelocityContext(params));
	}
	
	public static void main(String[] args) {
		VelocityContext context = new VelocityContext();
		context.put("name", new String("Velocity"));
		context.put("date", (new Date()).toString());
		
		// 取得velocity的模版内容, 模板内容来自字符传
		  String content = "";
		  content += "Welcome  $name  to Javayou.com! ";
		  content += " today is  $date.";
		printString(content, context);
	}

}
