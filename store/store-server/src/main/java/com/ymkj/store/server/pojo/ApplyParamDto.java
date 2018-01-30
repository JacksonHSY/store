package com.ymkj.store.server.pojo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * @Description：文件申请查看入参
 * @ClassName: ApplyParamDto.java
 * @Author：tianx
 * @Date：2017年7月18日
 * -----------------变更历史-----------------
 * 如：who  2017年7月18日  修改xx功能
 */
@Getter
@Setter
public class ApplyParamDto extends ParamDto {
	
	private static final long serialVersionUID = 5786841570163433839L;

	private Long id;

    /**
    * 文件上传记录ID
    */
    private Long fileUploadId;

    /**
    * 申请描述
    */
    private String applicationDesc;

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
    private Date applyTime;

    /**
    * 审核时间
    */
    private Date auditTime;
    
}