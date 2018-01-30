package com.ymkj.store.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymkj.base.core.biz.api.message.Response;
import com.ymkj.pms.biz.api.service.IEmployeeExecuter;
import com.ymkj.pms.biz.api.vo.request.ReqEmployeeVO;
import com.ymkj.pms.biz.api.vo.response.ResResourceVO;
import com.ymkj.sso.client.ShiroUtils;
import com.ymkj.store.server.common.Constants;
import com.ymkj.store.server.pojo.TreeNodeDto;

/**
 * @Description：首页
 * @ClassName: IndexController.java
 * @Author：tianx
 * @Date：2017年7月31日
 * -----------------变更历史-----------------
 * 如：who  2017年7月31日  修改xx功能
 */
@Controller
@RequestMapping("index")
public class IndexController {
	
	@Autowired
	private IEmployeeExecuter iEmployeeExecuter;
	
	@RequestMapping("")
	public String index(Model model, HttpServletRequest request){
		return "index";
	}
	
	@RequestMapping(value = "/getMenus")
	@ResponseBody 
	public com.ymkj.springside.modules.utils.Response applyAudioFileView(HttpServletRequest request){
		com.ymkj.springside.modules.utils.Response response = com.ymkj.springside.modules.utils.Response.success();
		ReqEmployeeVO vo = new ReqEmployeeVO();
		vo.setUsercode(ShiroUtils.getAccount());
		vo.setSysCode(Constants.STORE_SYS_CODE);
		Response resp =  iEmployeeExecuter.findMenusByAccount(vo);
		if(!resp.getRepCode().equals(Constants.PMS_SUCCESS_CODE)){
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg(resp.getRepMsg());
			return response;
		}
		if(null == resp.getData()){
			response.setCode(Consts.CODE_FAILURE);
			response.setMsg("获取菜单失败,接口无返回数据!");
			return response;
		}
//		response.setData(this.praseTreeNodeFromResp((List<ResResourceVO>) resp.getData()));
		response.setData(resp.getData());
		return response;
	}
	
	/**
	 * 功能描述：将接口返回数据获取Tree一级节点
	 * 输入参数：
	 * @param vos
	 * @return
	 * 返回类型：List<TreeNodeDto>
	 * 创建人：tianx
	 * 日期：2017年7月31日
	 */
	private List<TreeNodeDto> praseTreeNodeFromResp(List<ResResourceVO> vos){
		List<TreeNodeDto> nodes = new ArrayList<TreeNodeDto>();
		if(null != vos && 0 < vos.size()){
			for(ResResourceVO vo : vos){
				if(StringUtils.isBlank(vo.getParentCode())){//一级节点parentCode为空
					TreeNodeDto node = new TreeNodeDto();
					node.setId(vo.getId());
					node.setText(vo.getName());
					node.setUrl(vo.getUrl());
					node.setChildren(this.getChildrenNodes(vos, vo.getCode()));
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
	
	/**
	 * 功能描述：获取节点下所有子节点
	 * 输入参数：
	 * @param vos
	 * @param code
	 * @return
	 * 返回类型：TreeNodeDto[]
	 * 创建人：tianx
	 * 日期：2017年7月31日
	 */
	private TreeNodeDto[] getChildrenNodes(List<ResResourceVO> vos,String code){
		List<TreeNodeDto> nodes = new ArrayList<TreeNodeDto>();
		if(null != vos && 0 < vos.size()){
			for(int i=0; i<vos.size(); i++){
				if(vos.get(i).getParentCode().equals(code)){
					TreeNodeDto node = new TreeNodeDto();
					node.setId(vos.get(i).getId());
					node.setText(vos.get(i).getName());
					node.setUrl(vos.get(i).getUrl());
					node.setChildren(getChildrenNodes(vos,vos.get(i).getCode()));
					nodes.add(node);
				}
			}
		}
		TreeNodeDto[] nodeArray = new TreeNodeDto[nodes.size()];
		return nodes.toArray(nodeArray);
	}
}
