package com.ymkj.store.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.StrUtils;
import com.ymkj.store.server.common.Constants;
import com.ymkj.store.server.entity.FileApplicationRecord;
import com.ymkj.store.server.mapper.FileApplicationRecordMapper;
import com.ymkj.store.server.utils.PageUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Description：文件申请记录service
 * @ClassName: FileApplicationRecordService.java
 * @Author：tianx
 * @Date：2017年7月13日
 * -----------------变更历史-----------------
 * 如：who  2017年7月13日  修改xx功能
 */
@Service
public class FileApplicationRecordService {
	
	@Autowired
	private FileApplicationRecordMapper fileApplicationRecordMapper;
	
	/**
	 * 文件管理分页查询
	 * @param pageInfo
	 * @return
	 * PageInfo<FileApplicationRecord>
	 * @author liangj@yuminsoft.com
	 * @date2017年7月14日
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<FileApplicationRecord> getFileManagementPage(PageInfo<FileApplicationRecord> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		FileApplicationRecord far = (FileApplicationRecord) pageInfo.getQueryParam();
		Page<FileApplicationRecord> page = (Page)getFilemanagementList(far);
        return PageUtils.convertPage(page);
	}
	
	/**
	 * 根据条件查询申请记录表
	 * @param far
	 * @return
	 * List<FileApplicationRecord>
	 * @author liangj@yuminsoft.com
	 * @date2017年7月14日
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
		if(StrUtils.isNotBlank(far.getUserNum())){
			map.put("userNum", far.getUserNum());
		}
		
		 fileList = fileApplicationRecordMapper.getFileManagementList(map);
		
		return fileList;
	}
	
	/**
	 * 功能描述：新增申请查看记录
	 * 输入参数：
	 * @param record
	 * 返回类型：void
	 * 创建人：tianx
	 * 日期：2017年7月17日
	 */
	public void insert(FileApplicationRecord record){
		record.setFlowStatus(Constants.FLOW_STATUS_APPLYING);
		fileApplicationRecordMapper.insert(record);
	}
	
	/**
	 * 功能描述：查询申请查看记录
	 * 输入参数：
	 * @param fileUploadId
	 * @return
	 * 返回类型：List<FileApplicationRecord>
	 * 创建人：tianx
	 * 日期：2017年7月27日
	 */
	public List<FileApplicationRecord> selectApplyRecords(Long fileUploadId){
		Example example = new Example(FileApplicationRecord.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("fileUploadId", fileUploadId);
		criteria.andNotEqualTo("flowStatus", Constants.FLOW_STATUS_REFUSE);
		return fileApplicationRecordMapper.selectByExample(example);
	}
}