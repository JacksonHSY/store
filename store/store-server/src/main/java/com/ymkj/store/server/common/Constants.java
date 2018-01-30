package com.ymkj.store.server.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description：常量类
 * @ClassName: Constants.java
 * @Author：tianx
 * @Date：2017年7月13日
 * -----------------变更历史-----------------
 * 如：who  2017年7月13日  修改xx功能
 */
public class Constants {
	
	/**
	 * 文件类型
	 */
	/**视频*/
	public static final String FILE_TYPE_VIDEO = "1";
	/**音频*/
	public static final String FILE_TYPE_AUDIO = "2";
	
	/**
	 * 数据有效性
	 */
	/**有效*/
	public static final String DATA_VALID = "0";
	/**失效*/
	public static final String DATA_UNVALID = "1";
	
	/**
	 * 允许上传文件格式
	 */
	/**视频*/
	public static final String[] ALLOW_UPLOAD_FILE_MIME_VIDEO = new String[]{"video/mp4","application/octet-stream","video/x-flv"};
	/**音频*/
	public static final String[] ALLOW_UPLOAD_FILE_MIME_AUDIO = new String[]{"audio/mp3","audio/mpeg"};
	
	/**
	 * 上传来源
	 */
	/**PC*/
	public static final String FILE_SOURCE_PC = "1";
	/**APP*/
	public static final String FILE_SOURCE_APP = "2";
	/**其他*/
	public static final String FILE_SOURCE_OTHER = "3";
	
	/**
	 * 申请流转状态（1、申请中;2、拒绝;3、同意;4、已下载;5、已删除）
	 */
	/**申请中*/
	public static final String FLOW_STATUS_APPLYING = "1";
	/**拒绝*/
	public static final String FLOW_STATUS_REFUSE = "2";
	/**同意*/
	public static final String FLOW_STATUS_AGREE = "3";
	/**已下载*/
	public static final String FLOW_STATUS_DOWNLOAD = "4";
	/**已删除*/
	public static final String FLOW_STATUS_DELETE = "5";
	
	/**存储空间
	public static final String BUCKET_NAME = "zdmoney";*/
	
	/**项目编码(平台)*/
	public static final String STORE_SYS_CODE = "strs";
	
	/**平台接口成功返回码*/
	public static final String PMS_SUCCESS_CODE = "000000";
	
	/**
	 * 文件类型
	 */
	public final static Map FILE_TYPE = new HashMap<String, String>(){{    
	    put("1","视频文件");    //
	    put("2", "联系人核实");	//
	    put("3","银行流水核实");		
	    put("4","保单贷核实");		
	    put("5","随车贷核实");
	    put("6","水电煤单据核实");
	    put("7","其他核实");
	}};
	
	public final static Map FILE_CLASS = new HashMap<String, String>(){{    
	    put("1","VIDEO");    //视频
	    put("2", "AUDIO");	//音频
	}}; 
}
