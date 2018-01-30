package com.ymkj.store.server.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesUtil {
	
	public static final String FILE_PATH = "file:"+System.getProperty("global.config.path") + "/config.properties";
//	public static final String FILE_PATH = PropertiesLoader.loadProperties(GLOBAL_CONFIG_PATH+"/config.properties");
	
	private static Properties properties;
	
	private static void init() throws IOException { 
		properties = readResourceProperties(PropertiesUtil.class, FILE_PATH);
	}
	
	public static String getValue(String key){
		try {
			if(null == properties){
				init();
			}
			return properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("读取资源文件异常", e);
		}
		return null;
	}
	/**
	 * @author      ym10093 
	 * @createTime  2016年11月9日 下午8:32:04
	 * @description 读项目内的文件
	 */
	public static Properties readResourceProperties(Class clazz, String filePath) throws IOException {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = clazz.getResourceAsStream(filePath);
			prop.load(in);
		} finally {
			PropertiesUtil.close(in);
		}
		return prop;
	}
	
	/**
	 * @author      ym10093 
	 * @createTime  2016年11月9日 下午8:32:04
	 * @description 读硬盘上的文件
	 */
	public static Properties readFileProperties(String filePath) throws IOException {
		Properties prop = new Properties();
		FileInputStream in =null;
		try {
			in = new FileInputStream(filePath);
			prop.load(in);
		} finally {
			PropertiesUtil.close(in);
		}
		return prop;
	}
	
	public static void close (InputStream in) {
		if (in!=null) {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
