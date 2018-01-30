package com.ymkj.store.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ymkj.store.server.common.Constants;

/**
 * @Description：对类进行描述
 * @ClassName: NumberUtil.java
 * @Author：tianx
 * @Date：2017年7月14日
 * -----------------变更历史-----------------
 * 如：who  2017年7月14日  修改xx功能
 */
public class NumberUtil {
	
	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();  
    private static int seq = 0;  
    private static final int ROTATION = 99999;  
	
	/**
	 * 功能描述：生成唯一文件名
	 * 输入参数：
	 * @param caseNo
	 * @param fileType
	 * @return
	 * 返回类型：String
	 * 创建人：tianx
	 * 日期：2017年7月14日
	 */
    public static String generateFileKey(String caseNo,String fileType){  
    	StringBuffer key = new StringBuffer(caseNo);
    	if(Constants.FILE_TYPE_VIDEO.equals(fileType)){
    		key.append(Constants.FILE_CLASS.get(Constants.FILE_TYPE_VIDEO));
    	}else{
    		key.append(Constants.FILE_CLASS.get(Constants.FILE_TYPE_AUDIO));
    	}
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");  
        String temp = sf.format(new Date());
        key.append(temp);
        int random=(int) (Math.random()*10000); 
        key.append(String.valueOf(random));
        return key.toString();  
    }  
    
	/**
	 * 返回类型+日期流水号
	 * @return
	 */
    public static String getNumberForPK(String type){  
        StringBuffer batchNo = new StringBuffer();  
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");  
        String temp = sf.format(new Date());  
        int random=(int) (Math.random()*10000);  
        return batchNo.append(type).append("_").append(temp).append(random).toString();
    } 
    
    /**
	 * 返回日期流水号
	 * @return
	 */
    public static synchronized String getNumberForPK(){  
        if (seq > ROTATION) seq = 0;  
        buf.delete(0, buf.length());  
        date.setTime(System.currentTimeMillis());  
        return String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);  
      } 
    
    public static void main(String args[]){
    	System.out.println(generateFileKey("1111111","AUDIO"));
//    	System.out.println(getNumberForPK(CommConstants.BUSIRECORD_TYPE_MATCH_FINANCE));
    }
}	
