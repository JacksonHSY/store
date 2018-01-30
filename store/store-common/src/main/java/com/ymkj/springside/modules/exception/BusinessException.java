package com.ymkj.springside.modules.exception;

/**
 * 通用业务异常类
 * 
 * @author longjw@yuminsoft.com
 * @date 2017年5月3日 上午11:50:36
 * @version V1.0
 */
public class BusinessException extends RuntimeException {
	

	private static final long serialVersionUID = 3952989781119779700L;

	public BusinessException() {
		super();
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public BusinessException(String code, String message, Throwable e) {
		super(message, e);
		this.code = code;
	}

	public BusinessException(Throwable e) {
		super(e);
	}
	
	/** 业务类型代码 */
	private String code;

	public String getCode() {
		return code;
	}
}
