package com.ymkj.springside.modules.utils;

import java.io.Serializable;

import org.apache.http.Consts;


/**
 * 出参
 * 
 * @author longjw
 */
public class Response implements Serializable {

	private static final long serialVersionUID = -2580532891730777617L;

	/** 状态码 */
	private String code;

	/** 返回信息 */
	private String msg;

	/** 返回数据 */
	private Object data;

	public Response() {
		this(Consts.CODE_SUCCESS, Consts.MSG_SUCCESS);
	}

	public Response(Object data) {
		this(Consts.CODE_SUCCESS, Consts.MSG_SUCCESS);
		this.data = data;
	}

	public Response(String code, String msg) {
		this(code, msg, null);
	}

	public Response(String code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static Response fail(String msg) {
		return new Response(Consts.CODE_FAILURE, msg);
	}

	public static Response success() {
		return new Response();
	}

	public static Response success(Object data) {
		return new Response(data);
	}

	// 自定义响应成功的的消息
	public static Response success(String msg) {
		return new Response(msg);
	}

	public Response(String msg) {
		this(Consts.CODE_SUCCESS, msg, null);
	}

	// 自定义响应成功的的消息+返回数据
	public static Response success(String msg, Object data) {
		return new Response(msg, data);
	}

	public Response(String msg, Object data) {
		this(Consts.CODE_SUCCESS, msg, data);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return ToStringBuilder.build(this);
	}
}
