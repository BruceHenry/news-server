package com.bh.news.server.controller;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bh.news.server.EnvironmentMap;

// Temporarily static file server, should be move to a separate static file server(eventually CDN) 
@Configuration
public class ArticleStaticController implements WebMvcConfigurer {
	private final static  String URL_PREFIX = "file:///";
	private final static String FILE_PATH = "file.path";
	
	private final static String SAVE_PATH = EnvironmentMap.getInstance().getEnvMap().get(FILE_PATH);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String path = URL_PREFIX + new File(SAVE_PATH).getAbsolutePath();
		registry.addResourceHandler("/browse/**").addResourceLocations(path);
	}
}
