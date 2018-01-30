package com.ymkj.store.server.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ymkj.pms.biz.api.vo.response.ResEmployeeVO;
import com.ymkj.springside.modules.exception.BusinessException;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.sso.client.ShiroUtils;
import com.ymkj.store.server.common.Constants;
import com.ymkj.store.server.entity.FileApplicationRecord;
import com.ymkj.store.server.entity.FileUploadRecord;
import com.ymkj.store.server.pojo.ApplyParamDto;
import com.ymkj.store.server.pojo.UploadParamDto;
import com.ymkj.store.server.service.FileApplicationRecordService;
import com.ymkj.store.server.service.FileUploadRecordService;
import com.ymkj.store.server.service.UFileService;
import com.ymkj.store.server.utils.NumberUtil;

/**
 * @Description：文件上传Controller
 * @ClassName: UploadController.java
 * @Author：tianx
 * @Date：2017年7月13日
 * -----------------变更历史-----------------
 * 如：who  2017年7月13日  修改xx功能
 */
@Controller
@RequestMapping("/upload")
@Log4j
public class FileUploadController {

	@Autowired
	private UFileService uFileService;
	@Autowired
	private FileUploadRecordService uploadRecordService;
	@Autowired
	private FileApplicationRecordService fileApplicationRecordService;
	
	private List<String> nameStrs = new ArrayList<String>();
	/**
	 * 功能描述：文件详情页面
	 * 输入参数：
	 * @param paramDto
	 * @param request
	 * @return
	 * 返回类型：ModelAndView
	 * 创建人：tianx
	 * 日期：2017年7月27日
	 */
	@RequestMapping("/fileDetail")
	public ModelAndView index(UploadParamDto paramDto, HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("store/fileUploadDetail");
		try {
			request.setCharacterEncoding("UTF-8");
			if(StringUtils.isNotBlank(paramDto.getLoanDateStr())){
				modelAndView.addObject("loanDateStr", paramDto.getLoanDateStr());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerNo())){
				modelAndView.addObject("customerNo", paramDto.getCustomerNo());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerNo())){
				modelAndView.addObject("applicationCaseNo", paramDto.getApplicationCaseNo());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerName())){
				modelAndView.addObject("customerName", java.net.URLDecoder.decode(paramDto.getCustomerName(),"UTF-8"));
			}
			if(StringUtils.isNotBlank(paramDto.getCertifyBusinessDepart())){
				modelAndView.addObject("certifyBusinessDepart", new String(paramDto.getCertifyBusinessDepart().getBytes("iso-8859-1"),"utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			log.error("FileUploadController.index() 参数解码异常");
			e.printStackTrace();
		}
		return modelAndView;
	}
	/**
	 * 功能描述：文件详情页面
	 * 输入参数：
	 * @param paramDto
	 * @param request
	 * @return
	 * 返回类型：ModelAndView
	 * 创建人：tianx
	 * 日期：2017年7月27日
	 */
	@RequestMapping("/fileDetailForUpload")
	public ModelAndView fileDetailForUpload(UploadParamDto paramDto, HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("store/fileUploadDetailForUpload");
		try {
			request.setCharacterEncoding("UTF-8");
			if(StringUtils.isNotBlank(paramDto.getLoanDateStr())){
				modelAndView.addObject("loanDateStr", paramDto.getLoanDateStr());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerNo())){
				modelAndView.addObject("customerNo", paramDto.getCustomerNo());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerNo())){
				modelAndView.addObject("applicationCaseNo", paramDto.getApplicationCaseNo());
			}else{
				modelAndView = new ModelAndView("err");
				return modelAndView;
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerName())){
				modelAndView.addObject("customerName", java.net.URLDecoder.decode(paramDto.getCustomerName(),"UTF-8"));
			}
			if(StringUtils.isNotBlank(paramDto.getCertifyBusinessDepart())){
				modelAndView.addObject("certifyBusinessDepart", new String(paramDto.getCertifyBusinessDepart().getBytes("iso-8859-1"),"utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			log.error("FileUploadController.index() 参数解码异常");
			e.printStackTrace();
		}
		return modelAndView;
	}
	/**
	 * 
	 * @TODO
	 * @param paramDto
	 * @param request
	 * @return
	 * ModelAndView
	 * @author changj@yuminsoft.com
	 * @date2017年9月11日
	 */
	@RequestMapping("/fileDetailForFind")
	public ModelAndView fileDetailForFind(UploadParamDto paramDto, HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("store/fileUploadDetailForFind");
		try {
			request.setCharacterEncoding("UTF-8");
			if(StringUtils.isNotBlank(paramDto.getLoanDateStr())){
				modelAndView.addObject("loanDateStr", paramDto.getLoanDateStr());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerNo())){
				modelAndView.addObject("customerNo", paramDto.getCustomerNo());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerNo())){
				modelAndView.addObject("applicationCaseNo", paramDto.getApplicationCaseNo());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerName())){
				modelAndView.addObject("customerName", java.net.URLDecoder.decode(paramDto.getCustomerName(),"UTF-8"));
			}
			if(StringUtils.isNotBlank(paramDto.getCertifyBusinessDepart())){
				modelAndView.addObject("certifyBusinessDepart", new String(paramDto.getCertifyBusinessDepart().getBytes("iso-8859-1"),"utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			log.error("FileUploadController.fileDetailForFind() 参数解码异常");
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	/**
	 * 
	 * @TODO
	 * @param paramDto
	 * @param request
	 * @return
	 * ModelAndView
	 * @author changj@yuminsoft.com
	 * @date2017年9月11日
	 */
	@RequestMapping("/fileDetailForXinDai")
	public ModelAndView fileDetailForXinDai(UploadParamDto paramDto, HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("store/fileUploadDetailForXinD");
		try {
			request.setCharacterEncoding("UTF-8");
			if(StringUtils.isNotBlank(paramDto.getLoanDateStr())){
				modelAndView.addObject("loanDateStr", paramDto.getLoanDateStr());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerNo())){
				modelAndView.addObject("customerNo", paramDto.getCustomerNo());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerNo())){
				modelAndView.addObject("applicationCaseNo", paramDto.getApplicationCaseNo());
			}else{
				modelAndView = new ModelAndView("err");
				return modelAndView;
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerName())){
				modelAndView.addObject("customerName", java.net.URLDecoder.decode(paramDto.getCustomerName(),"UTF-8"));
			}
			if(StringUtils.isNotBlank(paramDto.getCertifyBusinessDepart())){
				modelAndView.addObject("certifyBusinessDepart", new String(paramDto.getCertifyBusinessDepart().getBytes("iso-8859-1"),"utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			log.error("FileUploadController.fileDetailForFind() 参数解码异常");
			e.printStackTrace();
		}
		return modelAndView;
	}
	@RequestMapping("/fileDetailForXinDaiManage")
	public ModelAndView fileDetailForXinDaiManage(UploadParamDto paramDto, HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("store/fileUploadDetailForXinDManage");
		try {
			request.setCharacterEncoding("UTF-8");
			if(StringUtils.isNotBlank(paramDto.getLoanDateStr())){
				modelAndView.addObject("loanDateStr", paramDto.getLoanDateStr());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerNo())){
				modelAndView.addObject("customerNo", paramDto.getCustomerNo());
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerNo())){
				modelAndView.addObject("applicationCaseNo", paramDto.getApplicationCaseNo());
			}else{
				modelAndView = new ModelAndView("err");
				return modelAndView;
			}
			if(StringUtils.isNotBlank(paramDto.getCustomerName())){
				modelAndView.addObject("customerName", java.net.URLDecoder.decode(paramDto.getCustomerName(),"UTF-8"));
			}
			if(StringUtils.isNotBlank(paramDto.getCertifyBusinessDepart())){
				modelAndView.addObject("certifyBusinessDepart", new String(paramDto.getCertifyBusinessDepart().getBytes("iso-8859-1"),"utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			log.error("FileUploadController.fileDetailForFind() 参数解码异常");
			e.printStackTrace();
		}
		return modelAndView;
	}
	/**
	 * 功能描述：获取文件上传记录
	 * 输入参数：
	 * @param fileType
	 * @param applicationCaseNo
	 * @param request
	 * @return
	 * 返回类型：List<FileUploadRecord>
	 * 创建人：tianx
	 * 日期：2017年7月27日
	 */
	@RequestMapping("/getUploadRecords")
	@ResponseBody 
	public List<FileUploadRecord> getUploadRecords(String fileType, String applicationCaseNo, HttpServletRequest request){
		FileUploadRecord record = new FileUploadRecord();
//		BeanUtils.copyProperties(recordDto, record);
		record.setApplicationCaseNo(applicationCaseNo);
		if(StringUtils.isBlank(fileType)){
			record.setFileType(Constants.FILE_TYPE_VIDEO);
		}else{
			record.setFileType(fileType);
		}
		return uploadRecordService.selectByExample(record);
	}
	/**
	 * 
	 * @TODO
	 * @param fileType
	 * @param applicationCaseNo
	 * @param request
	 * @return
	 * List<FileUploadRecord>
	 * @author changj@yuminsoft.com
	 * @date2017年9月21日
	 */
	@RequestMapping("/getUploadRecordsForXinD")
	@ResponseBody 
	public List<FileUploadRecord> getUploadRecordsForXinD(String fileType, String applicationCaseNo, HttpServletRequest request){
		FileUploadRecord record = new FileUploadRecord();
		record.setApplicationCaseNo(applicationCaseNo);
		return uploadRecordService.selectByExample(record);
	}
	/**
	 * 功能描述：处理文件上传至UCLOUD
	 * 输入参数：
	 * @param paramDto
	 * @param request
	 * @return
	 * 返回类型：Object
	 * 创建人：tianx
	 * 日期：2017年7月27日
	 */
	@RequestMapping("/doUpload")
	@ResponseBody 
	public Object doUpload(UploadParamDto paramDto, HttpServletRequest request){
		Response response = Response.success();
		ResEmployeeVO currentUser = ShiroUtils.getCurrentUser();
		if(currentUser == null){
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("当前登录账号异常,请联系管理员");
			return response;
		}
		try {
			if(null == paramDto.getFiles() || paramDto.getFiles().length == 0){
				BusinessException exception = new BusinessException(Consts.CODE_FAILURE,null);
				throw exception;
			}
			if(Constants.FILE_TYPE_VIDEO.equals(paramDto.getFileType()) && paramDto.getFiles().length > 3){
				BusinessException exception = new BusinessException(Consts.CODE_FAILURE,"视频文件上传个数不得超过三个!");
				throw exception;
			}
			for(int i=0;i<paramDto.getFiles().length;i++){
				MultipartFile file = paramDto.getFiles()[i];
				if(file.isEmpty()){
					break;
				}
				String fileName = file.getOriginalFilename();
				FileUploadRecord record = new FileUploadRecord();
				if(Constants.FILE_TYPE_VIDEO.equals(paramDto.getFileType())){
					FileUploadRecord query = new FileUploadRecord();
					query.setApplicationCaseNo(paramDto.getApplicationCaseNo());
					query.setFileType(Constants.FILE_TYPE_VIDEO);
					int count = uploadRecordService.selectCount(query);
					if(count>2){
						log.error("视频文件上传个数不得超过三个!");
						BusinessException exception = new BusinessException(Consts.CODE_FAILURE,"视频文件上传个数不得超过三个,请删除后重新上传!");
						throw exception;
					}
					if(!ArrayUtils.contains(Constants.ALLOW_UPLOAD_FILE_MIME_VIDEO, file.getContentType().toLowerCase())){
						log.error("文件格式"+file.getContentType().toLowerCase()+"错误");
						response.setCode(Consts.CODE_FAILURE);
						response.setMsg("上传视频文件格式（"+file.getContentType()+"）错误!");
						return response;
					}
					record.setFileType(Constants.FILE_TYPE_VIDEO);
				}else{
					if(!ArrayUtils.contains(Constants.ALLOW_UPLOAD_FILE_MIME_AUDIO, file.getContentType().toLowerCase())){
						log.error("上传音频文件格式（"+file.getContentType()+"）错误!");
						BusinessException exception = new BusinessException(Consts.CODE_FAILURE,"上传音频文件格式（"+file.getContentType()+"）错误!");
						throw exception;
					}
					record.setFileType(paramDto.getFileType());
					
					String prefix = fileName.substring(fileName.lastIndexOf(".")+1);
					String subName = (String)Constants.FILE_TYPE.get(paramDto.getFileType());
					String preName = fileName .substring(0,fileName .lastIndexOf("."));//文件名(不包含后缀)
					//文件命名
					if(preName.indexOf(subName) == -1){//不包含类型文件名
						preName = subName+"-1";
					}
					FileUploadRecord query = new FileUploadRecord();
					query.setApplicationCaseNo(paramDto.getApplicationCaseNo());
					query.setFileType(paramDto.getFileType());
					query.setFileName(preName.toString());
					List<FileUploadRecord> records = uploadRecordService.selectByExample(query);
					if(records.size()>0){
						int count = records.size()+1;
						String recordPreName = "";
						if(records.get(0).getFileName().indexOf(".") != -1){
							recordPreName = records.get(0).getFileName().substring(0,records.get(0).getFileName().lastIndexOf("."));
						}else{
							recordPreName = records.get(0).getFileName();
						}
						preName = recordPreName+"-"+count;
					}
					if(nameStrs.contains(preName)){
						int size = nameStrs.size()+1;
						preName = preName+"-"+size;
					}
					
					fileName = preName+"."+ prefix;
					nameStrs.add(preName);
				}
				String fileKey = NumberUtil.generateFileKey(paramDto.getApplicationCaseNo(), paramDto.getFileType());
				Boolean flag =uFileService.putMultipartFile(fileKey, file);
				if(!flag){
					log.error("文件上传失败,上传至Ucloud时出错！");
					BusinessException exception = new BusinessException(Consts.CODE_FAILURE,"文件上传失败,上传至Ucloud时出错!");
					throw exception;
				}
				record.setApplicationCaseNo(paramDto.getApplicationCaseNo());
				record.setCertifyBusinessDepart(paramDto.getCertifyBusinessDepart());
				record.setCustomerName(paramDto.getCustomerName());
				record.setCustomerNo(paramDto.getCustomerNo());
				record.setUploadSource(Constants.FILE_SOURCE_PC);
				record.setUserName(currentUser.getName());
				record.setUserNum(ShiroUtils.getAccount());
				record.setFileKey(fileKey);
//				record.setDownloadUrl(uFileService.downloadUrl(Constants.BUCKET_NAME, fileKey));
				record.setFileName(fileName);
//				record.setUploadTime(DateUtil.getCommStyleTime(paramDto.getUploadTime(), DateUtil.DATE_TIME_FORMAT));
				record.setUploadTime(new Date());
				record.setFileSize(new BigDecimal(file.getSize()));
				if(Constants.FILE_TYPE_VIDEO.equals(paramDto.getFileType())){
					FileUploadRecord vedio = new FileUploadRecord();
					vedio.setApplicationCaseNo(paramDto.getApplicationCaseNo());
					vedio.setFileType(Constants.FILE_TYPE_VIDEO);
					int count = uploadRecordService.selectCount(vedio);
					if(count>2){
						log.error("FileUploadController.doUpload（）；视频文件已存在,请删除后重试!");
						BusinessException exception = new BusinessException(Consts.CODE_FAILURE,"视频文件已存在,请删除后重试!");
						throw exception;
					}
				}
				uploadRecordService.insert(record);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String code = Consts.CODE_FAILURE;
			String msg = "系统异常，请稍后重试";
			if(ex instanceof BusinessException){
				code = ((BusinessException) ex).getCode();
				msg = ((BusinessException) ex).getMessage();
			}
			response.setCode(code);
			response.setMsg(msg);
			return response;
		}
		return response;
	}
	
	/**
	 * 功能描述：申请查看
	 * 输入参数：
	 * @param paramDto
	 * @param request
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年7月27日
	 */
	@RequestMapping("/applyFileView")
	@ResponseBody 
	public Response applyFileView(ApplyParamDto paramDto, HttpServletRequest request){
		Response response = Response.success();
		ResEmployeeVO currentUser = ShiroUtils.getCurrentUser();
		if(null == paramDto.getFileUploadId()){
			log.error("FileUploadController.applyFileView(): 申请查看文件上传记录ID(paramDto.getFileUploadId())为空！");
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("文件申请查看失败,请联系管理员!");
			return response;
		}
		
		FileUploadRecord uploadRecord = new FileUploadRecord();
		uploadRecord.setId(paramDto.getFileUploadId());
		uploadRecord.setStatus(Constants.DATA_VALID);
		int count = uploadRecordService.selectCount(uploadRecord);
		if(count < 1){
			log.error("FileUploadController.applyFileView(): ID(="+paramDto.getFileUploadId()+")文件已被删除！");
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("文件已被删除!");
			return response;
		}
		FileApplicationRecord record = new FileApplicationRecord();
		record.setFileUploadId(paramDto.getFileUploadId());
		List<FileApplicationRecord> query = fileApplicationRecordService.selectApplyRecords(paramDto.getFileUploadId());
		if(null != query && query.size()>0){
			log.error("FileUploadController.applyFileView(): ID(="+paramDto.getFileUploadId()+")文件已被申请！");
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("文件已被申请!");
			return response;
		}
		record.setApplicationDesc(paramDto.getApplicationDesc());
		record.setApplyTime(new Date());
		record.setUserName(currentUser.getName());
		record.setUserNum(ShiroUtils.getAccount());
		fileApplicationRecordService.insert(record);
		return response;
	}
	
	/**
	 * 功能描述：获取文件申请查看描述
	 * 输入参数：
	 * @param fileUploadId
	 * @param fileType
	 * @param request
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年7月27日
	 */
	@RequestMapping("/getApplyDesc")
	@ResponseBody 
	public Response applyFileView(@RequestParam(value="fileUploadId", required=true)Long fileUploadId,String fileType, HttpServletRequest request){
		Response response = Response.success();
		FileApplicationRecord record = new FileApplicationRecord();
		record.setFileUploadId(fileUploadId);
		List<FileApplicationRecord> query = fileApplicationRecordService.selectApplyRecords(fileUploadId);
		if(null != query && query.size()>0){
			response.setData(query.get(0).getApplicationDesc());
			return response;
		}
		response.setCode(Consts.CODE_FAILURE);
		return response;
	}
	
	/**
	 * 功能描述：音频申请查看(下载)
	 * 输入参数：
	 * @param fileUploadId
	 * @param fileType
	 * @param request
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年7月26日
	 */
	@RequestMapping(value = "/getAudioApplyDesc",method = RequestMethod.GET)
	@ResponseBody 
	public Response applyAudioFileView(@RequestParam(value="fileUploadId", required=true)Long fileUploadId, HttpServletRequest request,HttpServletResponse resp){
		Response response = Response.success();
		FileUploadRecord queryUpload = new FileUploadRecord();
		queryUpload.setId(fileUploadId);
		queryUpload.setStatus(Constants.DATA_VALID);
		FileUploadRecord upload = uploadRecordService.selectOne(queryUpload);
		if(null == upload){
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("文件下载失败,文件不存在或已删除!");
			return response;
		}
		if(StringUtils.isBlank(upload.getFileKey())){
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("文件下载失败,文件KEY为空!");
			return response;
		}
		try {
			StringBuffer fileName = new StringBuffer(upload.getCustomerName());
			fileName.append("_");
			fileName.append(upload.getFileName());
			
			uFileService.getFileStream(upload.getFileKey(),new String(fileName.toString().getBytes("UTF-8"), "ISO8859-1"),resp);
		} catch (Exception e) {
			log.info("Ucloud获取文件流失败！",e);
			e.printStackTrace();
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("文件下载失败,文件KEY为空!");
			return response;
		}
		return response;
	}
	
	/**
	 * 功能描述：音频申请查看(下载)
	 * 输入参数：
	 * @param fileUploadId
	 * @param fileType
	 * @param request
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年7月26日
	 */
	@RequestMapping(value = "/getApplyDescs",method = RequestMethod.POST)
	@ResponseBody 
	public Response applyAudioFileViews(Long[] ids, HttpServletRequest request,HttpServletResponse resp){
		Response response = Response.success();
		String tmpFileName = NumberUtil.getNumberForPK()+".zip";  
		try {
			uFileService.getFileStreams(ids,tmpFileName,resp);
		} catch (Exception e) {
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("文件下载失败!");
			return response;
		}
		return response;
	}
}
