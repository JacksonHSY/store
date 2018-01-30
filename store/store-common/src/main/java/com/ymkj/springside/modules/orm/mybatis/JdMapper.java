package com.ymkj.springside.modules.orm.mybatis;

import java.io.Serializable;

import tk.mybatis.mapper.common.Mapper;

import com.ymkj.springside.modules.orm.AbstractEntity;

/**
 * JdMapper
 * <p/>
 * Author: Hao Chen
 * Date: 2016年1月4日 11:31:17
 * Mail: haoc@zendaimoney.com
 */
public interface JdMapper<T extends AbstractEntity, ID extends Serializable> extends Mapper<T> {	

}
