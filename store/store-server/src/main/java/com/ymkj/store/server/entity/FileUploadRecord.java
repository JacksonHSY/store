package com.ymkj.store.server.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import com.ymkj.springside.modules.orm.AbstractEntity;

/**
 * @Description：文件上传记录
 * @ClassName: FileUploadRecord.java
 * @Author：tianx
 * @Date：2017年7月13日
 * -----------------变更历史-----------------
 * 如：who  2017年7月13日  修改xx功能
 */
@Table(name = "file_upload_record")
@Getter
@Setter
public class FileUploadRecord extends AbstractEntity<Long> {
	

    @Id
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
    * 下载路径
    */
    private String downloadUrl;

    /**
    * 上传人姓名
    */
    private String userName;

    /**
    * 上传人工号
    */
    private String userNum;
    
    /**
     * 证件营业部
     */
    private String certifyBusinessDepart;
    
    /**
     * 上传来源(1、PC;2、APP;3、其他)
     */
    private String uploadSource;

    /**
    * 创建时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
    * 更新时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
    * 0、有效;1、失效
    */
    private String status;

    /**
    * 备注
    */
    private String memo;

    /**
    * 上传时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;

}