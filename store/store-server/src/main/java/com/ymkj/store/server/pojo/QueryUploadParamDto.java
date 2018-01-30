package com.ymkj.store.server.pojo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description：文件上传入参对象
 * @ClassName: FileUploadParamDto.java
 * @Author：tianx
 * @Date：2017年7月14日
 * -----------------变更历史-----------------
 * 如：who  2017年7月14日  修改xx功能
 */
@Getter
@Setter
public class QueryUploadParamDto extends ParamDto{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -9163619132639610724L;

    private Long id;

    /**
    * 客户姓名
    */
    private String customerName;

    /**
    * 客户编号
    */
    private String customerNo;

    /**
    * 申请件编号
    */
    private String applicationCaseNo;
    
    /**
     * 文件名称
     */
    private String fileName;

	/**
	 * 文件key
	 */
	private String fileKey;

	/**
	 * 文件类型（1、视频;2、音频）
	*/
	private String fileType;

	/**
	 * 文件大小(单位Byte)
	 */
	private BigDecimal fileSize;
    
    /**
    * 上传人姓名
    */
    private String userName;

    /**
    * 上传人工号
    */
    private String userNum;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 0、有效;1、失效
    */
    private String status;

    /**
    * 上传时间
    */
    private Date uploadTime;

}