package com.ymkj.store.server.pojo;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description：入参对象
 * @ClassName: ParamDto.java
 * @Author：tianx
 * @Date：2017年7月14日
 * -----------------变更历史-----------------
 * 如：who  2017年7月14日  修改xx功能
 */
@Getter
@Setter
public class ParamDto implements Serializable {
	
	
	private static final long serialVersionUID = -9163619132639610724L;

    private Integer pageSize;//每页大小
    
    private Integer pageNumber;//当前页
    
}