package com.ymkj.store.server.mapper;

import java.util.List;
import java.util.Map;

import com.ymkj.springside.modules.orm.mybatis.JdMapper;
import com.ymkj.store.server.entity.FileApplicationRecord;

/**
 * 
 * @Description：文件申请记录mapper
 * @ClassName: FileApplicationRecordMapper.java
 * @Author：tianx
 * @Date：2017年7月13日
 * -----------------变更历史-----------------
 * 如：who  2017年7月13日  修改xx功能
 */
public interface FileApplicationRecordMapper extends JdMapper<FileApplicationRecord, Long> {

	public List<FileApplicationRecord> getFileManagementList(Map<String, Object> map);
	public boolean updateByCondition(Map<String, Object> map);
	
	public boolean updateByConditionForUpload(Map<String, Object> map);
	public List<FileApplicationRecord> getFileApplyList(Map<String, Object> map);
}