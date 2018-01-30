package com.ymkj.store.server.mapper;

import java.util.List;
import java.util.Map;

import com.ymkj.springside.modules.orm.mybatis.JdMapper;
import com.ymkj.store.server.entity.FileUploadRecord;

/**
 * @Description：文件上传记录MAPPER
 * @ClassName: FileUploadRecordMapper.java
 * @Author：tianx
 * @Date：2017年7月13日
 * -----------------变更历史-----------------
 * 如：who  2017年7月13日  修改xx功能
 */
public interface FileUploadRecordMapper extends JdMapper<FileUploadRecord, Long> {
	public List<FileUploadRecord> selectFileUploadRecordByUserAndApplicationCaseNo(FileUploadRecord fr);
	public int updateByCondition(Map<String, Object>map);
}