package com.ymkj.store.server.utils;

import org.apache.http.Consts;

import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @author bianxj
 *	返回结果值对象
 * 
 */
@Getter
@Setter
public class ResultVo {
	
	private Object rows;
	private int total;
	private boolean status;
	private Object data;
	private Object footer ;
	
	private static final String SUCCESS = "操作成功！";
	private static final String FAILURE = "操作失败！";
	
	
	//返回分页数据
	public static ResultVo returnPage(PageInfo pageInfo){
		return new ResultVo(pageInfo.getResults(), pageInfo.getTotalRecord(),Boolean.TRUE,null); 
	}
	//返回带合计行footer的分页数据
	public static ResultVo returnPageWithFooter(PageInfo pageInfo,Object footer){
		return new ResultVo(pageInfo.getResults(), pageInfo.getTotalRecord(),Boolean.TRUE,null,footer); 
	}
	//返回操作结果
	public static ResultVo returnMsg(Response response){
		Boolean flag = Consts.CODE_SUCCESS.equals(response.getCode());
		return new ResultVo(null,1,flag,flag?SUCCESS:FAILURE);
	}
	
	//返回查询结果
	public static ResultVo returnData(Response response){
		Boolean flag = Consts.CODE_SUCCESS.equals(response.getCode());
		return new ResultVo(null,1,flag,flag?response.getData():response.getMsg());
	}
	public ResultVo(Object rows, int total, boolean status, Object data) {
		super();
		this.rows = rows;
		this.total = total;
		this.status = status;
		this.data = data;
	}
	public ResultVo(Object rows, int total, boolean status, Object data,Object footer) {
		super();
		this.rows = rows;
		this.total = total;
		this.status = status;
		this.data = data;
		this.footer=footer;
	}
	
	public ResultVo(){
		super();
	}
	
	public static ResultVo returnMsg(boolean flag, String msg){
		return new ResultVo(null, 0, flag, msg);
	}
	
}
