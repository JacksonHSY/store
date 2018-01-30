package com.ymkj.store.server.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * @Description：树节点对象
 * @ClassName: TreeNodeDto.java
 * @Author：tianx
 * @Date：2017年7月31日
 * -----------------变更历史-----------------
 * 如：who  2017年7月31日  修改xx功能
 */
@Getter
@Setter
public class TreeNodeDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 170588147030674947L;

	private Long id;

    /**
    * 节点名称
    */
    private String text;

    /**
    * 节点状态， 'open' 或 'closed'，默认是 'open'。
    */
    private String state="open";

    /**
    * 节点地址
    */
    private String url;
    
    /**
     * 指示节点是否被选中
     */
    private Boolean checked=Boolean.FALSE;
    
    /**
    * 子节点
    */
    private TreeNodeDto[] children;

}