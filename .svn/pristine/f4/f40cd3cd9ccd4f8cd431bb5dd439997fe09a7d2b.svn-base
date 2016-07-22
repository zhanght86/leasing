package com.pqsoft.init;

import java.io.IOException;

import org.apache.velocity.app.Velocity;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.pqsoft.skyeye.dependent.Init;
import com.pqsoft.skyeye.exception.ActionException;

public class InitVelocity extends Init {

	@Override
	public void exec() throws ActionException {
		ResourceLoader  rloader = new DefaultResourceLoader();
		try {
			Resource resource = rloader.getResource("classpath:velocity.properties");
			Velocity.init(resource.getFile().getPath());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ActionException("### 加载velocity配置文件出错！", e);
		}
	}

}
