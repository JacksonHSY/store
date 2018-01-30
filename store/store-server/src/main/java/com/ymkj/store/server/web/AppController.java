package com.ymkj.store.server.web;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;
import com.ymkj.store.server.entity.FileUploadRecord;
import com.ymkj.store.server.service.FileUploadRecordService;
import com.ymkj.store.server.utils.DateUtil;

@Slf4j
@Controller
@RequestMapping(value = "/app/")
public class AppController { 
	
	@Autowired
	private FileUploadRecordService fileUploadRecordService;

	/**
	 * 保存上传记录
	 * TODO
	 * @param request
	 * @return
	 * Response
	 * @author liangj@yuminsoft.com
	 * @date2017年7月18日
	 */
	@ResponseBody
	@RequestMapping(value = "uploadRecordSave",method = RequestMethod.POST)
	public Response uploadRecordSave(HttpServletRequest request){
		Response response = new Response();
		FileUploadRecord fur = new FileUploadRecord();
		if(StrUtils.isNotBlank(request.getParameter("customerName"))){
			fur.setCustomerName(request.getParameter("customerName"));//客户姓名
		}else{
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("保存上传记录失败!客户姓名不能为空");
			return response;
		}
		if(StrUtils.isNotBlank(request.getParameter("customerNo"))){
			fur.setCustomerNo(request.getParameter("customerNo"));//客户编号
		}else{
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("保存上传记录失败!客户编号不能为空");
			return response;
		}
		if(StrUtils.isNotBlank(request.getParameter("applicationCaseNo"))){
			fur.setApplicationCaseNo(request.getParameter("applicationCaseNo"));//申请件编号
		}else{
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("保存上传记录失败!申请件编号不能为空");
			return response;
		}
		if(StrUtils.isNotBlank(request.getParameter("fileName"))){
			fur.setFileName(request.getParameter("fileName"));//文件名称
		}
		if(StrUtils.isNotBlank(request.getParameter("fileKey"))){
			fur.setFileKey(request.getParameter("fileKey"));//文件key
		}
		if(StrUtils.isNotBlank(request.getParameter("fileSize"))){
			fur.setFileSize(new BigDecimal(request.getParameter("fileSize")));//文件大小
		}	
		if(StrUtils.isNotBlank(request.getParameter("fileType"))){
			fur.setFileType(request.getParameter("fileType"));//文件类型
		}else{
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("保存上传记录失败!文件类型不能为空");
			return response;
		}	
		if(StrUtils.isNotBlank(request.getParameter("downloadUrl"))){
			fur.setDownloadUrl(request.getParameter("downloadUrl"));//下载路径
		}
		if(StrUtils.isNotBlank(request.getParameter("userName"))){
			fur.setUserName(request.getParameter("userName"));//上传人姓名
		}
		if(StrUtils.isNotBlank(request.getParameter("userNum"))){
			fur.setUserNum(request.getParameter("userNum"));//上传人工号
		}
		if(StrUtils.isNotBlank(request.getParameter("uploadTime"))){
			fur.setUploadTime(DateUtil.strToDate(request.getParameter("uploadTime"), DateUtil.DATE_TIME_FORMAT));//上传时间
		}
		if(StrUtils.isNotBlank(request.getParameter("certifyBusinessDepart"))){
			fur.setCertifyBusinessDepart(request.getParameter("certifyBusinessDepart"));//证件营业部
		}
		fur.setUploadSource("2");//上传来源(1、PC;2、APP;3、其他)
		try {
			log.info("保存上传记录开始...");
			fileUploadRecordService.insert(fur);
			log.info("保存上传记录结束...");
			response.setCode(Consts.CODE_SUCCESS);
			response.setMsg("保存上传记录成功!");
		} catch (Exception e) {
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("系统异常!请稍后重试");
		}	
		return response;
	}
	
}
