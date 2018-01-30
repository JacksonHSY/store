package com.ymkj.store.server.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import com.ymkj.springside.modules.orm.AbstractEntity;

/**
 * @Description：文件申请记录
 * @ClassName: FileApplicationRecord.java
 * @Author：tianx
 * @Date：2017年7月13日
 * -----------------变更历史-----------------
 * 如：who  2017年7月13日  修改xx功能
 */
@Table(name = "file_application_record")
@Getter
@Setter
public class FileApplicationRecord extends AbstractEntity<Long> {
	

    @Id
    private Long id;

    /**
    * 文件上传记录ID
    */
    private Long fileUploadId;

    /**
    * 流转状态（1、申请中;2、拒绝;3、同意;4、已下载;5、已删除）
    */
    private String flowStatus;

    /**
    * 申请人姓名
    */
    private String userName;

    /**
    * 申请人工号
    */
    private String userNum;

    /**
    * 申请描述
    */
    private String applicationDesc;

    /**
    * 备注(拒绝原因)
    */
    private String memo;

    /**
    * 审核人姓名
    */
    private String auditorName;

    /**
    * 审核人工号
    */
    private String auditorNo;

    /**
    * 申请时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
    * 审核时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    
    @Transient
    private String customerName;
    
    @Transient
    private String applicationCaseNo;
    
    @Transient
    private String fileName;
    
    @Transient
    private String fileSize;
    
    @Transient
    private String fileType;
    
    @Transient
    private String fileKey;
    
    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;
    
    @Transient
    private String applyDateBegin;
    
    @Transient
    private String applyDateEnd;
    
    @Transient
    private String uploadDateBegin;
    
    @Transient
    private String uploadDateEnd;
    
    @Transient
    private String dealDateBegin;
    
    @Transient
    private String dealDateEnd;
    
    @Transient
    private String certifyBusinessDepart;
    
    @Transient
    private String refusalReasons;
    
    @Transient
    private String status;

}