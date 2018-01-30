package com.ymkj.store.server.service;

import cn.ucloud.ufile.DownloadUrl;
import cn.ucloud.ufile.UFileClient;
import cn.ucloud.ufile.UFileRequest;
import cn.ucloud.ufile.UFileResponse;
import cn.ucloud.ufile.sender.DeleteSender;
import cn.ucloud.ufile.sender.GetSender;
import cn.ucloud.ufile.sender.PutSender;
import com.ymkj.store.server.PrintLog;
import com.ymkj.store.server.common.Constants;
import com.ymkj.store.server.conf.UCloudConfig;
import com.ymkj.store.server.entity.FileUploadRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * UFile操作
 * 
 * @author longjw@yuminsoft.com
 *
 */
@Service
@Slf4j
public class UFileService {
	
	@Autowired
	private FileUploadRecordService uploadRecordService;

	@Value("${bucketName}")
	private String bucketName;
	/**
	 * 上传文件
	 * 
	 * @param bucketName 需要上传至的存储空间
	 * @param key 上传至存储空间中的文件名
	 * @param filePath 需要上传的本地文件路径
	 */
	public boolean putFile(String key, String filePath){
		UFileClient clinet = null;
		PutSender sender = null;
		try {
			UCloudConfig.loadUFileConfig();
			clinet = new UFileClient();
			UFileRequest request = UCloudConfig.create(bucketName, key, filePath);
			request.addHeader("Content-Type", "image/jpg"); 
			
			sender = new PutSender();
			sender.makeAuth(clinet, request);
			
			UFileResponse response = sender.send(clinet, request);
			
			System.out.println("status line: " + response.getStatusLine());
			if (200 != response.getStatusLine().getStatusCode()) {
				//打印返回异常信息
				PrintLog.printErrMsg("putFile", response.getContent());
				throw new RuntimeException("文件上传失败");
			}
			
			Header[] headers = response.getHeaders();
			for (int i = 0; i < headers.length; i++) {
				System.out.println("header " + headers[i].getName() + " : " + headers[i].getValue());
			}

			//文件大小
			long contentLength = response.getContentLength();
			log.info("body length: " + contentLength);
			//TODO 保存文件上传记录
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			UCloudConfig.shutdownClient(clinet);
		}
		return false;
		
	}
	
	/**
	 * 功能描述：上传文件(直接传输文件)
	 * 输入参数：
	 * @param bucketName 需要上传至的存储空间
	 * @param key 上传至存储空间中的文件名
	 * @param file 需要上传的本地文件路径
	 * @return
	 * 返回类型：boolean
	 * 创建人：tianx
	 * 日期：2017年7月17日
	 */
	public boolean putMultipartFile(String key, MultipartFile file){
		UFileClient clinet = null;
		PutSender sender = null;
		try {
//			UFileConfig.getInstance().loadConfig(System.getenv().get("global.config.path") + "/config.properties");
			UCloudConfig.loadUFileConfig();
			clinet = new UFileClient();
			UFileRequest request = UCloudConfig.createForMultipartFile(bucketName, key, file);
			request.addHeader("Content-Type", file.getContentType()); 
			
			sender = new PutSender();
			sender.makeAuth(clinet, request);
			
			UFileResponse response = sender.send(clinet, request);
			
			if (response==null || 200 != response.getStatusLine().getStatusCode()) {
				//打印返回异常信息
				PrintLog.printErrMsg("putFile", response.getContent());
				throw new RuntimeException("文件上传失败");
			}
			
			Header[] headers = response.getHeaders();
			for (int i = 0; i < headers.length; i++) {
				log.info("header " + headers[i].getName() + " : " + headers[i].getValue());
			}

			//文件大小
			long contentLength = response.getContentLength();
			log.info("body length: " + contentLength);
			//TODO 保存文件上传记录
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			UCloudConfig.shutdownClient(clinet);
		}
		return false;
		
	}
	
	
	/**
	   * 删除文件
	   * 
	   * @param bucketName
	   * @param key
	   * 
	   * */
	  public boolean deleteFile(String key){
	    
	    UFileClient client = null;
	    DeleteSender sender = null;
	    try {
	      UCloudConfig.loadUFileConfig();
	      client = new UFileClient();
	      
	    
	      UFileRequest request = new UFileRequest();
	      request.setBucketName(bucketName);
	      request.setKey(key);
	      
	      sender = new DeleteSender();
	      sender.makeAuth(client, request);
	      
	      UFileResponse response = sender.send(client, request);
	      
	      if(response==null || 204 != response.getStatusLine().getStatusCode()){
	        PrintLog.printErrMsg("deleteFile", response.getContent());
	        throw new RuntimeException("文件删除失败");
	      }
	      
	      Header[] headers = response.getHeaders();
	      for (int i = 0; i < headers.length; i++) {
	        log.info("header " + headers[i].getName() + " : " + headers[i].getValue());
	      }
	      //文件大小
	      long contentLength = response.getContentLength();
	      log.info("body length: " + contentLength);
	      return true;
	    } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }finally{
	      UCloudConfig.shutdownClient(client);
	    }    
	    return false;
	    
	  }
	
	/**
	 * 下载文件
	 * @TODO
	 * @param ufileClient
	 * @param request
	 * @param saveAsPath
	 * @return
	 * boolean
	 * @author liangj@yuminsoft.com
	 * @date2017年7月12日
	 */
	@SuppressWarnings({ "null", "resource" })
	public void getFile(String key, HttpServletResponse resp) {
		UFileClient clinet = null;
		GetSender sender = null;
		InputStream inputStream = null;
		ServletOutputStream outputStream = null;
		try {
			UCloudConfig.loadUFileConfig();
			//UFileConfig.getInstance().loadConfig(System.getenv().get("global.config.path") + "/config.properties");
			UFileRequest request = new UFileRequest();
			request.setBucketName(bucketName);
			request.setKey(key);
			clinet = new UFileClient();
			sender = new GetSender();
			sender.makeAuth(clinet, request);
			UFileResponse response = sender.send(clinet, request);
			if (response==null && 200 != response.getStatusLine().getStatusCode()) {
				 PrintLog.printErrMsg("getFile", response.getContent());
			     throw new RuntimeException("文件下载失败");
			}else{
				Header[] headers = response.getHeaders();
				for (int i = 0; i < headers.length; i++) {
					resp.setHeader(headers[i].getName(), headers[i].getValue());
					log.info("header " + headers[i].getName() + " : " + headers[i].getValue());
				}
				log.info("body length: " + response.getContentLength());
				inputStream = response.getContent();
				outputStream = resp.getOutputStream();				
			    int bufSize = 1024 * 1024 * 40;
			    byte[] buffer = new byte[bufSize];
			    int bytesRead;
			    while ((bytesRead = inputStream.read(buffer)) > 0) {			    	
			        outputStream.write(buffer, 0, bytesRead);
				} 
			    if(null == inputStream){
			    	 inputStream.close();  
			    }
			    if(null == outputStream){
			    	 outputStream.close();  
			    }
			    outputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			 UCloudConfig.shutdownClient(clinet);
		}
	}
	
	/**
	 * 功能描述：获取下载链接
	 * 输入参数：
	 * @param bucketName
	 * @param key
	 * @return
	 * 返回类型：String
	 * 创建人：tianx
	 * 日期：2017年7月25日
	 */
	@SuppressWarnings("unused")
	public String downloadUrl(String key) {
		UFileRequest request = new UFileRequest();
		request.setBucketName(bucketName);
		request.setKey(key);
		DownloadUrl downloadUrl = new DownloadUrl();
		UFileClient ufileClient = new UFileClient();
		return downloadUrl.getUrl(ufileClient, request, 0, Boolean.FALSE);
	}
	
	/**
	 * 功能描述：获取文件流
	 * 输入参数：
	 * @param bucketName
	 * @param key
	 * @param saveAsPath
	 * @return
	 * 返回类型：boolean
	 * 创建人：tianxR
	 * 日期：2017年7月26日
	 */
	public void getFileStream(String key,String fileName,HttpServletResponse resp) throws Exception {
		UFileClient clinet = null;
		GetSender sender = null;
		try {
			UCloudConfig.loadUFileConfig();
//			UFileConfig.getInstance().loadConfig(System.getenv().get("global.config.path") + "/config.properties");
			UFileRequest request = new UFileRequest();
			request.setBucketName(bucketName);
			request.setKey(key);
			clinet = new UFileClient();
			sender = new GetSender();
			sender.makeAuth(clinet, request);
			UFileResponse response = sender.send(clinet, request);
			if (response==null || 200 != response.getStatusLine().getStatusCode()) {
				 PrintLog.printErrMsg("getFile", response.getContent());
			     throw new RuntimeException("文件下载失败");
			}else{
				Header[] headers = response.getHeaders();
				for (int i = 0; i < headers.length; i++) {
					resp.setHeader(headers[i].getName(), headers[i].getValue());
					log.info("header " + headers[i].getName() + " : " + headers[i].getValue());
				}
				resp.setHeader("Content-Disposition", "attachment;fileName="+fileName);
				log.info("body length: " + response.getContentLength());
				InputStream inputStream = null;
				ServletOutputStream outputStream = resp.getOutputStream();				
				inputStream = response.getContent();
			    int bufSize = 1024 * 4;
			    byte[] buffer = new byte[bufSize];
			    int bytesRead;
			    while ((bytesRead = inputStream.read(buffer)) > 0) {			    	
			        outputStream.write(buffer, 0, bytesRead);
				} 
			    inputStream.close();  
			    outputStream.close();  
			    outputStream.flush();  
			}
		} catch (Exception e) {
			log.error("文件下载失败",e);
			throw e;
		}finally{
			 UCloudConfig.shutdownClient(clinet);
		}
	}

	/**
	 * 功能描述：获取文件流
	 * 输入参数：
	 * @param bucketName
	 * @param key
	 * @param saveAsPath
	 * @return
	 * 返回类型：boolean
	 * 创建人：tianxR
	 * 日期：2017年7月26日
	 */
	public void getFileStreams(Long[] ids,String zipFileName,HttpServletResponse resp) throws Exception {
		UFileClient clinet = null;
		GetSender sender = null;
		resp.setHeader("Content-Disposition", "attachment;fileName="+zipFileName);
		ZipOutputStream outputStream = new ZipOutputStream(resp.getOutputStream());	
		try {
			for(Long id:ids){
				FileUploadRecord queryUpload = new FileUploadRecord();
				queryUpload.setId(id);
				queryUpload.setStatus(Constants.DATA_VALID);
				FileUploadRecord upload = uploadRecordService.selectOne(queryUpload);
				if(null == upload){
					throw new RuntimeException("文件下载失败,文件不存在或已删除!");
				}
				if(StringUtils.isBlank(upload.getFileKey())){
					throw new RuntimeException("文件下载失败,文件KEY为空!");
				}
				UCloudConfig.loadUFileConfig();
				UFileRequest request = new UFileRequest();
				request.setBucketName(bucketName);
				request.setKey(upload.getFileKey());
				clinet = new UFileClient();
				sender = new GetSender();
				sender.makeAuth(clinet, request);
				UFileResponse response = sender.send(clinet, request);
				if (response==null || 200 != response.getStatusLine().getStatusCode()) {
					 PrintLog.printErrMsg("getFile", response.getContent());
				     throw new RuntimeException("文件下载失败");
				}else{
					Header[] headers = response.getHeaders();
					StringBuffer fileName = new StringBuffer(upload.getCustomerName());
					fileName.append("_");
					fileName.append(upload.getFileName());
//					String fileNameNew = new String(fileName.toString().getBytes("UTF-8"), "ISO8859-1");
					log.info("body length: " + response.getContentLength());
					InputStream inputStream = null;
					inputStream = response.getContent();
					outputStream.putNextEntry(new ZipEntry(fileName.toString())); 
	                //设置压缩文件内的字符编码，不然会变成乱码  
					outputStream.setEncoding("GBK");
				    int bufSize = 1024 * 4;
				    byte[] buffer = new byte[bufSize];
				    int bytesRead;
				    while ((bytesRead = inputStream.read(buffer)) > 0) {			    	
				        outputStream.write(buffer, 0, bytesRead);
					} 
				    inputStream.close();  
				}
			}
			outputStream.close();  
			outputStream.flush();  
			
		} catch (Exception e) {
			log.error("文件下载失败",e);
			throw e;
		}finally{
			 UCloudConfig.shutdownClient(clinet);
		}
	}
	
}
