package com.ymkj.store.server.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.web.multipart.MultipartFile;

import com.ymkj.store.server.utils.PropertiesUtil;

import cn.ucloud.ufile.UFileClient;
import cn.ucloud.ufile.UFileConfig;
import cn.ucloud.ufile.UFileRequest;

public class UCloudConfig {
	
	private static UFileConfig config = null;

	public static void loadUFileConfig(){
		if(null == config){
		  config = UFileConfig.getInstance();
	      config.setUcloudPublicKey(UCloudParam.UCloudPublicKey);
	      config.setUcloudPrivateKey(UCloudParam.UCloudPrivateKey);
	      config.setProxySuffix(UCloudParam.ProxySuffix);
	      config.setDownloadProxySuffix(UCloudParam.DownloadProxySuffix);
		}
	}
	
	public static UFileRequest create(String buckName, String key, String filePath){
		UFileRequest request = new UFileRequest();
		request.setBucketName(buckName);
		request.setKey(key);
		request.setFilePath(filePath);
		try {
			request.setInputStream(new FileInputStream(filePath));
			request.setContentLength(new File(filePath).length());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return request;
	}
	
	public static UFileRequest createForMultipartFile(String buckName, String key, MultipartFile file){
		UFileRequest request = new UFileRequest();
		request.setBucketName(buckName);
		request.setKey(key);
		try {
			request.setInputStream(file.getInputStream());
			request.setContentLength(file.getSize());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return request;
	}
	
	public static void shutdownClient(UFileClient client){
		if(null != client){
			client.shutdown();
		}
	}
}
