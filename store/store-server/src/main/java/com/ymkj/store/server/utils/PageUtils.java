package com.ymkj.store.server.utils;

import java.util.List;

import com.ymkj.springside.modules.orm.PageInfo;

/**
 * Created by spark on 16/8/10.
 */
public class PageUtils {

    public static <E> PageInfo<E> convertPage(com.github.pagehelper.Page<E> page){
        PageInfo<E> resultPage = new PageInfo<E>();
        setPageProp(page, resultPage);
        resultPage.setResults(page.getResult());
        return resultPage;
    }

    public static <E> PageInfo<E> convertPage(com.github.pagehelper.Page page, List<E> list){
        PageInfo<E> resultPage = new PageInfo<E>();
        setPageProp(page, resultPage);
        resultPage.setResults(list);
        return resultPage;
    }

    private static void setPageProp(com.github.pagehelper.Page page, PageInfo resultPage) {
        resultPage.setPageNo(page.getPageNum());
        resultPage.setPageSize(page.getPageSize());
        resultPage.setTotalPage(page.getPages());
        resultPage.setTotalRecord(new Long(page.getTotal()).intValue());
    }

}
