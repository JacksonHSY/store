package com.ymkj.store.server.conf;

import org.springframework.beans.factory.annotation.Value;

public class UCloudParam {

	public static String UCloudPublicKey;
	
	public static String UCloudPrivateKey;
	
	public static String ProxySuffix;
	
	public static String DownloadProxySuffix;

	@Value("${UCloudPublicKey}")
	public void setUCloudPublicKey(String uCloudPublicKey) {
		UCloudPublicKey = uCloudPublicKey;
	}

	@Value("${UCloudPrivateKey}")
	public void setUCloudPrivateKey(String uCloudPrivateKey) {
		UCloudPrivateKey = uCloudPrivateKey;
	}

	@Value("${ProxySuffix}")
	public void setProxySuffix(String proxySuffix) {
		ProxySuffix = proxySuffix;
	}

	@Value("${DownloadProxySuffix}")
	public void setDownloadProxySuffix(String downloadProxySuffix) {
		DownloadProxySuffix = downloadProxySuffix;
	}
	
	
}
