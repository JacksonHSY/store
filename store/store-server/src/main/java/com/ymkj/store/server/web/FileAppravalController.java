package com.ymkj.store.server.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.pms.biz.api.vo.response.ResEmployeeVO;
import com.ymkj.springside.modules.exception.BusinessException;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;
import com.ymkj.sso.client.ShiroUser;
import com.ymkj.sso.client.ShiroUtils;
import com.ymkj.store.server.common.Constants;
import com.ymkj.store.server.entity.FileApplicationRecord;
import com.ymkj.store.server.entity.FileUploadRecord;
import com.ymkj.store.server.service.FileAppravalService;
import com.ymkj.store.server.service.FileUploadRecordService;
import com.ymkj.store.server.service.UFileService;
import com.ymkj.store.server.utils.ResultVo;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "fileAppravalmanage")
@Log4j
public class FileAppravalController {
	
	@Autowired
	private FileAppravalService fileAppravalService;
	
	@Autowired
	private UFileService fileService;
	@Autowired
	private FileUploadRecordService fileUploadRecordService;
	/**
	 *  跳转文件管理查询
	 * @param req
	 * @param res
	 * @return
	 * String
	 * @author 
	 * @date2017年7月14日
	 */
	@RequestMapping("")
	public String fileManagementList(HttpServletRequest req, HttpServletResponse  res) {
		return "/fileAppraval";
	}
	
	/**
	 * 文件管理分页查询
	 * @TODO
	 * @param request
	 * @return
	 * PageInfo
	 * 
	 * @date2017年7月14日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/fileManagementPage")
	@ResponseBody
	public ResultVo fileManagementPage(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "order", defaultValue = "desc") String order,
			HttpServletRequest request) {
		//获取输入框参数
		String fileKey = StrUtils.isNotBlank(request.getParameter("fileKey"))?request.getParameter("fileKey"):"";
		String customerName = StrUtils.isNotBlank(request.getParameter("customerName"))?request.getParameter("customerName"):"";
		String applicationCaseNo= StrUtils.isNotBlank(request.getParameter("applicationCaseNo"))?request.getParameter("applicationCaseNo"):"";
		//获取下拉选参数
		String flowStatus = StrUtils.isNotBlank(request.getParameter("flowStatus"))?request.getParameter("flowStatus"):"";
		String fileType = StrUtils.isNotBlank(request.getParameter("fileType"))?request.getParameter("fileType"):"1";
		String certifyBusinessDepart = StrUtils.isNotBlank(request.getParameter("certifyBusinessDepart"))?request.getParameter("certifyBusinessDepart"):"";
		//获取时间区间
		String applyDateBegin = StrUtils.isNotBlank(request.getParameter("applyDateBegin"))?request.getParameter("applyDateBegin") + " 00:00:00":"";
	    String applyDateEnd = StrUtils.isNotBlank(request.getParameter("applyDateEnd"))?request.getParameter("applyDateEnd")+ " 23:59:59":"";
	    String uploadDateBegin = StrUtils.isNotBlank(request.getParameter("uploadDateBegin"))?request.getParameter("uploadDateBegin") + " 00:00:00":"";
	    String uploadDateEnd = StrUtils.isNotBlank(request.getParameter("uploadDateEnd"))?request.getParameter("uploadDateEnd")+ " 23:59:59":"";
	    String dealDateBegin = StrUtils.isNotBlank(request.getParameter("dealDateBegin"))?request.getParameter("dealDateBegin")+" 00:00:00":"";
	    String dealDateEnd = StrUtils.isNotBlank(request.getParameter("dealDateEnd"))?request.getParameter("dealDateEnd")+" 23:59:59":"";
System.out.println(applyDateBegin);
System.out.println(applyDateBegin);
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
	    far.setDealDateBegin(dealDateBegin);
	    far.setDealDateEnd(dealDateEnd);
	    far.setCertifyBusinessDepart(certifyBusinessDepart);
	    
	    requestbody.setPageNo(page);
		requestbody.setPageSize(rows);
		requestbody.setQueryParam(far);
		requestbody = fileAppravalService.getFileManagementPage(requestbody);
		return ResultVo.returnPage(requestbody);
	}
	@RequestMapping("/updateRecord")
	@ResponseBody
	public ResultVo updateRecord(String flowstatus,String id,String reason) throws Exception {
		
		try {
			ShiroUser  shiroUser = ShiroUtils.getShiroUser();
			fileAppravalService.updateRecordByCurrent(flowstatus,id,reason,  ShiroUtils.getCurrentUser().getName(),ShiroUtils.getAccount());
			return  ResultVo.returnMsg(true, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  ResultVo.returnMsg(false, "更新失败");
	}
	
	@RequestMapping(value="/download",method = RequestMethod.GET)
	@ResponseBody 
	public Response fileAppravalPage(@RequestParam(value="key", required=true)String key, String fileName,  HttpServletRequest request,HttpServletResponse resp){
		Response response = Response.success();
		try {
			if(StringUtils.isBlank(key)){
				log.error("FileAppravalController.fileAppravalPage（）；入参文件Key为空!");
				BusinessException exception = new BusinessException(Consts.CODE_FAILURE,"入参文件Key为空!");
				throw exception;
			}
			
			FileUploadRecord queryUpload = new FileUploadRecord();
			queryUpload.setFileKey(key);
			queryUpload.setStatus(Constants.DATA_VALID);
			int count = fileUploadRecordService.selectCount(queryUpload);
			if(count < 1){
				log.error("FileAppravalController.fileAppravalPage（）；文件（fileKey="+key+"）不存在或者已被删除!");
				BusinessException exception = new BusinessException(Consts.CODE_FAILURE,"文件（fileKey="+key+"）不存在或者已被删除!");
				throw exception;
			}
			fileService.getFileStream(key, java.net.URLDecoder.decode(fileName,"UTF-8"), resp);
		} catch (Exception e) {
			e.printStackTrace();
			String code = Consts.CODE_FAILURE;
			String msg = "系统异常，请稍后重试";
			if(e instanceof BusinessException){
				code = ((BusinessException) e).getCode();
				msg = ((BusinessException) e).getMessage();
			}
			response.setCode(code);
			response.setMsg(msg);
			return response;
		}
		return response;
	}

}
