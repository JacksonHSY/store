package com.ymkj.store.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.store.server.common.Constants;
import com.ymkj.store.server.entity.FileUploadRecord;
import com.ymkj.store.server.mapper.FileApplicationRecordMapper;
import com.ymkj.store.server.mapper.FileUploadRecordMapper;
import com.ymkj.store.server.utils.PageUtils;
import com.ymkj.store.server.utils.ResultVo;

/**
 * @Description：文件上传记录service
 * @ClassName: FileUploadRecordService.java
 * @Author：tianx
 * @Date：2017年7月13日
 * -----------------变更历史-----------------
 * 如：who  2017年7月13日  修改xx功能
 */
@Service
public class FileUploadRecordService {
	@Autowired
	private FileUploadRecordMapper fileUploadRecordmapper;
	@Autowired
	private FileApplicationRecordMapper fileApplicationRecordMapper;
	/**
	 * 根据当前登录人 申请件编号查询上传记录
	 * @TODO
	 * @param pageInfo
	 * @return
	 * PageInfo<FileUploadRecord>
	 * @author changj@yuminsoft.com
	 * @date2017年7月13日
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<FileUploadRecord> getPage(PageInfo<FileUploadRecord> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		FileUploadRecord fr = (FileUploadRecord) pageInfo.getQueryParam();
		Page<FileUploadRecord> page = (Page)fileUploadRecordmapper.selectFileUploadRecordByUserAndApplicationCaseNo(fr);
        return PageUtils.convertPage(page);
	}
	/**
	 * 
	 * @查询同一申请件编号的所有记录
	 * @param applicationCaseNo
	 * @return
	 * List<FileUploadRecord>
	 * @author changj@yuminsoft.com
	 * @date2017年7月14日
	 */
	public List<FileUploadRecord> findByApplicationCaseNo(String applicationCaseNo){
		Example example=new Example(FileUploadRecord.class);
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(applicationCaseNo)){
			criteria.andEqualTo("applicationCaseNo", applicationCaseNo);
			criteria.andEqualTo("status", Consts.DATA_VALID);
		}else{
			criteria.andEqualTo("status", Consts.CODE_FAILURE);
		}

		return fileUploadRecordmapper.selectByExample(example);
	}
	/**
	 * 
	 * @TODO 返回第一条申请件编号
	 * @param userName
	 * @return
	 * String
	 * @author changj@yuminsoft.com
	 * @date2017年7月14日
	 */
	public String getFirstOne(String userName) {
		FileUploadRecord fr = new FileUploadRecord();
		fr.setUserName(userName);
		fr.setStatus(Consts.DATA_VALID);
		List<FileUploadRecord> list = fileUploadRecordmapper.selectFileUploadRecordByUserAndApplicationCaseNo(fr);
		if(list!=null &&list.size()>0){
			return list.get(0).getApplicationCaseNo();
		}
		return null;
	}
	/**
	 * 
	 * @删除上传记录&申请记录
	 * @param idList
	 * @param status
	 * @return
	 * int
	 * @author changj@yuminsoft.com
	 * @date2017年7月20日
	 */
	@Transactional
	public int removeRecord(String idList, String status) {
		Map<String, Object> map = new HashMap<String, Object>();

//		map.put("flowstatus", Constants.FLOW_STATUS_DELETE);
		map.put("memo", "文件已删除");
		map.put("uploadIds", idList);
		fileApplicationRecordMapper.updateByConditionForUpload(map);
		map.put("idList", idList);
		map.put("date", new Date());
		map.put("status", status);
		return fileUploadRecordmapper.updateByCondition(map);
	}
	/**
	 * 功能描述：根据参数获取list
	 * 输入参数：
	 * @param record
	 * @return
	 * 返回类型：List<FileUploadRecord>
	 * 创建人：tianx
	 * 日期：2017年7月13日
	 */
	public List<FileUploadRecord> select(FileUploadRecord record){
		record.setStatus(Constants.DATA_VALID);
		return fileUploadRecordmapper.select(record);
	}
	
	/**
	 * 功能描述：根据参数获取总数
	 * 输入参数：
	 * @param record
	 * @return
	 * 返回类型：List<FileUploadRecord>
	 * 创建人：tianx
	 * 日期：2017年7月13日
	 */
	public int selectCount(FileUploadRecord record){
		record.setStatus(Constants.DATA_VALID);
		return fileUploadRecordmapper.selectCount(record);
	}
	
	/**
	 * 功能描述：新增上传记录
	 * 输入参数：
	 * @param record
	 * 返回类型：void
	 * 创建人：tianx
	 * 日期：2017年7月17日
	 */
	public void insert(FileUploadRecord record){
		record.setStatus(Constants.DATA_VALID);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		fileUploadRecordmapper.insert(record);
	}
	
	/**
	 * 功能描述：
	 * 输入参数：
	 * @param record
	 * @return
	 * 返回类型：List<FileUploadRecord>
	 * 创建人：tianx
	 * 日期：2017年7月20日
	 */
	public List<FileUploadRecord> selectByExample(FileUploadRecord record){
		Example example = new Example(FileUploadRecord.class);
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(record.getApplicationCaseNo())){
			criteria.andEqualTo("applicationCaseNo", record.getApplicationCaseNo());
		}
		if(StringUtils.isNotBlank(record.getUserNum())){
			criteria.andEqualTo("userNum", record.getUserNum());
		}
		if(StringUtils.isNotBlank(record.getFileName())){
			criteria.andLike("fileName", record.getFileName());
		}
		if(StringUtils.isNotBlank(record.getFileType())&&Constants.FILE_TYPE_VIDEO.equals(record.getFileType())){
			criteria.andEqualTo("fileType", record.getFileType());
		}else if(StringUtils.isNotBlank(record.getFileType())&&!Constants.FILE_TYPE_VIDEO.equals(record.getFileType())){
			criteria.andNotEqualTo("fileType", Constants.FILE_TYPE_VIDEO);
		}
		criteria.andEqualTo("status", Constants.DATA_VALID);
		example.setOrderByClause("UPLOAD_tIME desc");
		return fileUploadRecordmapper.selectByExample(example);
	}
	
	/**
	 * 功能描述：
	 * 输入参数：
	 * @param record
	 * @return
	 * 返回类型：FileUploadRecord
	 * 创建人：tianx
	 * 日期：2017年7月25日
	 */
	public FileUploadRecord selectByPrimaryKey(FileUploadRecord record){
		record.setStatus(Constants.DATA_VALID);
		return fileUploadRecordmapper.selectByPrimaryKey(record);
	}
	
	/**
	 * 功能描述：
	 * 输入参数：
	 * @param record
	 * @return
	 * 返回类型：FileUploadRecord
	 * 创建人：tianx
	 * 日期：2017年7月28日
	 */
	public FileUploadRecord selectOne(FileUploadRecord record){
		record.setStatus(Constants.DATA_VALID);
		return fileUploadRecordmapper.selectOne(record);
	}
	/**
	 * 文件重命名
	 * 
	 * @param c
	 * @return
	 */
	public ResultVo fileRename(FileUploadRecord c){
		FileUploadRecord query = new FileUploadRecord();
		query.setId(c.getId());
		FileUploadRecord record = selectOne(query);
		if(null == record){
			return  ResultVo.returnMsg(false, "修改失败,该文件不存在或已失效!");
		}
		query.setId(null);
		query.setFileName(c.getFileName());
		List<FileUploadRecord> list = select(query);
		if(list.size()>0){
			return  ResultVo.returnMsg(false, "修改失败,该文件名已存在!");
		}
		record.setFileName(c.getFileName());
		fileUploadRecordmapper.updateByPrimaryKeySelective(record);
		return  ResultVo.returnMsg(true, "修改成功");
	}
}