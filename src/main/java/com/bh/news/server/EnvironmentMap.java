package com.bh.news.server;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class EnvironmentMap {

	private static EnvironmentMap instance;
	private Map<String, String> envMap;

	public EnvironmentMap() {
		envMap = new HashMap<String, String>();
		Properties properties = new Properties();
		try {
			InputStream inputStream = EnvironmentMap.class.getResourceAsStream("/application.properties");
			properties.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Entry<Object, Object> entry : properties.entrySet()) {
			envMap.put((String)entry.getKey(), (String)entry.getValue());
		}
		
		Map<String, String> sysEnv = System.getenv();
		for (Entry<String, String> entry :sysEnv.entrySet()) {
			envMap.put(entry.getKey(), entry.getValue());
		}
	}

	public static synchronized EnvironmentMap getInstance() {
		if (instance == null) {
			instance = new EnvironmentMap();
		}
		return instance;
	}

	public Map<String, String> getEnvMap() {
		return envMap;
	}
}
