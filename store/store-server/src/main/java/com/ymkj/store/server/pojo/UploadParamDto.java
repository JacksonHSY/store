package com.ymkj.store.server.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

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
public class UploadParamDto extends ParamDto{
	
	private static final long serialVersionUID = -9163619132639610724L;

    private Long id;

    /**
    * 客户姓名
    */
    @NotNull(message="{客户姓名不能为空!}")  
    private String customerName;

    /**
    * 客户编号
    */
    @NotNull(message="{客户编号不能为空!}")  
    private String customerNo;

    /**
    * 申请件编号
    */
    @NotNull(message="{申请件编号不能为空!}")  
    private String applicationCaseNo;
    
    /**
     * 放款日期
     */
    private String loanDateStr;
    
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
    * 下载路径
    */
    private String downloadUrl;

    /**
     * 上传时间
     */
    @NotNull(message="{上传日期不能为空!}") 
    private String uploadTime;
    
    /**
     * 证件营业部
     */
    private String certifyBusinessDepart;
    
    private MultipartFile[] files;
}