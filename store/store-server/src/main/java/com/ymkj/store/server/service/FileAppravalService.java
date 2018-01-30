package com.ymkj.store.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.StrUtils;
import com.ymkj.store.server.entity.FileApplicationRecord;
import com.ymkj.store.server.entity.FileUploadRecord;
import com.ymkj.store.server.mapper.FileApplicationRecordMapper;
import com.ymkj.store.server.utils.PageUtils;

/**
 * @Description：文件申请记录service
 * @ClassName: FileAppravalService.java
 * @Author：huang
 * @Date：2017年7月17日
 * -----------------变更历史-----------------
 * 如：who  2017年7月13日  修改xx功能
 */
@Service
public class FileAppravalService {
	
	@Autowired
	private FileApplicationRecordMapper fileApplicationRecordMapper;
	
	/**
	 * 文件管理分页查询
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<FileApplicationRecord> getFileManagementPage(PageInfo<FileApplicationRecord> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		FileApplicationRecord far = (FileApplicationRecord) pageInfo.getQueryParam();
		Page<FileApplicationRecord> page = (Page)getFilemanagementList(far);
        return PageUtils.convertPage(page);
	}
	/**
	 * 更新状态
	 * */
	public boolean updateRecord(String flowstatus,String id,String reason) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flowstatus", flowstatus);
		map.put("idList", "("+id+")");
		map.put("reason", reason);
		map.put("auditTime", new Date());
		return fileApplicationRecordMapper.updateByCondition(map);
	}
	public boolean updateRecordByCurrent(String flowstatus,String id,String reason,String name,String auditorNo) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("name", name);
		map.put("flowstatus", flowstatus);
		map.put("auditorNo", auditorNo);
		map.put("idList", "("+id+")");
		map.put("reason", reason);
		map.put("auditTime", new Date());
		return fileApplicationRecordMapper.updateByCondition(map);
	}
	/**
	 * 根据条件查询申请记录表
	 * 
	 */
	public List<FileApplicationRecord> getFilemanagementList(FileApplicationRecord far){
		List<FileApplicationRecord> fileList = new ArrayList<FileApplicationRecord>();
		Map<String, Object> map = new HashMap<String,Object>();
		if(StrUtils.isNotBlank(far.getFileKey())){
			map.put("fileKey", far.getFileKey());
		}
		if(StrUtils.isNotBlank(far.getCustomerName())){
			map.put("customerName", far.getCustomerName());
		}
		if(StrUtils.isNotBlank(far.getApplicationCaseNo())){
			map.put("applicationCaseNo", far.getApplicationCaseNo());
		}
		if(StrUtils.isNotBlank(far.getFlowStatus())){
			map.put("flowStatus", far.getFlowStatus());
		}
		if(StrUtils.isNotBlank(far.getFileType())){
			map.put("fileType", far.getFileType());
		}
		if(StrUtils.isNotBlank(far.getCertifyBusinessDepart())){
			map.put("certifyBusinessDepart", far.getCertifyBusinessDepart());
		}
		if(StrUtils.isNotBlank(far.getApplyDateBegin())){
			map.put("applyDateBegin", far.getApplyDateBegin());
		}
		if(StrUtils.isNotBlank(far.getApplyDateEnd())){
			map.put("applyDateEnd", far.getApplyDateEnd());
		}
		if(StrUtils.isNotBlank(far.getUploadDateBegin())){
			map.put("uploadDateBegin", far.getUploadDateBegin());
		}
		if(StrUtils.isNotBlank(far.getUploadDateEnd())){
			map.put("uploadDateEnd", far.getUploadDateEnd());
		}
		if(StrUtils.isNotBlank(far.getDealDateBegin())){
			map.put("dealDateBegin", far.getDealDateBegin());
		}
		if(StrUtils.isNotBlank(far.getDealDateEnd())){
			map.put("dealDateEnd", far.getDealDateEnd());
		}
		
		 fileList = fileApplicationRecordMapper.getFileApplyList(map);
		
		return fileList;
	}

}