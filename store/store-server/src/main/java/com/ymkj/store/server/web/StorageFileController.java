package com.ymkj.store.server.web;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.pms.biz.api.vo.response.ResEmployeeVO;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.StrUtils;
import com.ymkj.sso.client.ShiroUtils;
import com.ymkj.store.server.entity.FileUploadRecord;
import com.ymkj.store.server.service.FileUploadRecordService;
import com.ymkj.store.server.utils.ResultVo;
/**
 * 
 * 存储文件管理
 * @author changj@yuminsoft.com
 * @date2017年7月13日
 * @version 1.0
 */
@Controller
@RequestMapping("storageFile")
public class StorageFileController {

	@Autowired
	private FileUploadRecordService fileUploadRecordService;
	
	@RequestMapping("")
	public String list(Model model, HttpServletRequest request){
		return "storage_file_list";
	}
	/**
	 * 
	 * @申请件列表
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param applicationCaseNo
	 * @param request
	 * @return
	 * ResultVo
	 * @author changj@yuminsoft.com
	 * @date2017年7月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public ResultVo list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order,
			String applicationCaseNo, ServletRequest request) {
		PageInfo<FileUploadRecord> body = new PageInfo<FileUploadRecord>();
		body.setPageNo(page);
		body.setPageSize(rows);
		FileUploadRecord fr = new FileUploadRecord();
//		fr.setUserNum(ShiroUtils.getAccount());
		if(StrUtils.isNotEmpty(applicationCaseNo)){
			fr.setApplicationCaseNo(applicationCaseNo);
		}
		body.setQueryParam(fr);
		body = fileUploadRecordService.getPage(body);
		return ResultVo.returnPage(body);		
	}
	
	/**
	 * 
	 * @TODO  查询申请件编号下的所有明细
	 * @param applicationCaseNo
	 * @return
	 * List<FileUploadRecord>
	 * @author changj@yuminsoft.com
	 * @date2017年7月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/findByApplicationCaseNo",method = RequestMethod.POST)
	public List<FileUploadRecord> findByApplicationCaseNo(String applicationCaseNo, HttpServletRequest request) {
		return  fileUploadRecordService.findByApplicationCaseNo(applicationCaseNo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/firstOne",method = RequestMethod.POST)
	public String firstOne() {
		ResEmployeeVO currentUser = ShiroUtils.getCurrentUser();
		return fileUploadRecordService.getFirstOne(currentUser.getName());
	}
	
	/**
	 * 
	 * @TODO
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 * ResultVo
	 * @author changj@yuminsoft.com
	 * @date2017年7月14日
	 */
	@RequestMapping("/removeRecord")
	@ResponseBody
	public ResultVo removeRecord(String ids,String status) throws Exception {
		String idList = "("+ids+")";
		try {
			fileUploadRecordService.removeRecord(idList,status);
			return  ResultVo.returnMsg(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  ResultVo.returnMsg(false, "删除失败");
	}
	/**
	 * 文件重命名
	 * @return
	 */
	@RequestMapping("/fileRename")
	@ResponseBody
	public ResultVo fileRename(@RequestParam(value = "newfileName") String newfileName,@RequestParam(value = "fileName") String fileName,@RequestParam(value = "id") Long id,HttpServletRequest request){
		FileUploadRecord c = new FileUploadRecord();
		c.setId(id);
		if(!newfileName.equals(fileName)){
			c.setFileName(newfileName);
			return fileUploadRecordService.fileRename(c);
		}else{
			return  ResultVo.returnMsg(false, "与原文件名一致");
		}
		
	}
}
