package com.ymkj.store.server.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.pms.biz.api.vo.response.ResEmployeeVO;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.StrUtils;
import com.ymkj.sso.client.ShiroUser;
import com.ymkj.sso.client.ShiroUtils;
import com.ymkj.store.server.entity.FileApplicationRecord;
import com.ymkj.store.server.service.FileApplicationRecordService;
import com.ymkj.store.server.utils.ResultVo;

@Controller
@RequestMapping(value = "/fileManage/")
public class FileManagementController {
	
	@Autowired
	private FileApplicationRecordService fileApplicationRecordService;
	
	
	/**
	 *  跳转文件管理查询
	 * @param req
	 * @param res
	 * @return
	 * String
	 * @author liangj@yuminsoft.com
	 * @date2017年7月14日
	 */
	@RequestMapping("fileManagement")
	public String fileManagementList(HttpServletRequest req, HttpServletResponse  res) {
		return "/fileManagement_list";
	}
	
	/**
	 * 文件管理分页查询
	 * @TODO
	 * @param request
	 * @return
	 * PageInfo
	 * @author liangj@yuminsoft.com
	 * @date2017年7月14日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("fileManagementPage")
	@ResponseBody
	public ResultVo fileManagementPage(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "order", defaultValue = "desc") String order,
			HttpServletRequest request) {
		//获取当前登录人的工号
		String account = ShiroUtils.getAccount();
		
		//获取输入框参数
		String fileKey = StrUtils.isNotBlank(request.getParameter("fileKey"))?request.getParameter("fileKey"):"";
		String customerName = StrUtils.isNotBlank(request.getParameter("customerName"))?request.getParameter("customerName"):"";
		String applicationCaseNo= StrUtils.isNotBlank(request.getParameter("applicationCaseNo"))?request.getParameter("applicationCaseNo"):"";
		
		//获取下拉选参数
		String flowStatus = StrUtils.isNotBlank(request.getParameter("flowStatus"))?request.getParameter("flowStatus"):"";
		String fileType = StrUtils.isNotBlank(request.getParameter("fileType"))?request.getParameter("fileType"):"";
		
		//获取时间区间
		String applyDateBegin = StrUtils.isNotBlank(request.getParameter("applyDateBegin"))?request.getParameter("applyDateBegin") + " 00:00:00":"";
	    String applyDateEnd = StrUtils.isNotBlank(request.getParameter("applyDateEnd"))?request.getParameter("applyDateEnd")+ " 23:59:59":"";
	    String uploadDateBegin = StrUtils.isNotBlank(request.getParameter("uploadDateBegin"))?request.getParameter("uploadDateBegin") + " 00:00:00":"";
	    String uploadDateEnd = StrUtils.isNotBlank(request.getParameter("uploadDateEnd"))?request.getParameter("uploadDateEnd")+ " 23:59:59":"";
	    
	    PageInfo<FileApplicationRecord> requestbody = new PageInfo<FileApplicationRecord>();
	    
	    FileApplicationRecord far = new FileApplicationRecord();
	    	    	
	    far.setFileKey(fileKey);	    
	    far.setCustomerName(customerName);
	    far.setApplicationCaseNo(applicationCaseNo);
	    far.setFlowStatus(flowStatus);
	    far.setFileType(fileType);
	    far.setApplyDateBegin(applyDateBegin);
	    far.setApplyDateEnd(applyDateEnd);
	    far.setUploadDateBegin(uploadDateBegin);
	    far.setUploadDateEnd(uploadDateEnd);
	    far.setUserNum(account);
	    
	    requestbody.setPageNo(page);
		requestbody.setPageSize(rows);
		requestbody.setQueryParam(far);
		requestbody = fileApplicationRecordService.getFileManagementPage(requestbody);
		return ResultVo.returnPage(requestbody);
	}

}
