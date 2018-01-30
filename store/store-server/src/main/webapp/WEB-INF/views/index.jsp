<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>存储管理系统</title>
    <link href="${staticPath }/static/index/default/css/style.css" type=text/css rel=stylesheet>
    <script type="text/javascript" src="${staticPath}/static/index/module/index.js" charset="utf-8"></script>
    <script type="text/javascript">
        if (window != top) {
            top.location.href = location.href;
        }

    </script>
</head>
<body>
<div id="loading"
     style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
    <img src="${staticPath }/static/index/default/images/ajax-loader.gif"
         style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
</div>
<div id="index_layout">
    <div data-options="region:'north',border:false" style="overflow: hidden;height:42px;">
        <%@ include file="/commons/header.jsp" %>
    </div>
    <div data-options="region:'west',split:true" title="我的菜单"
         style="width: 180px; overflow: hidden;overflow-y:auto; padding:0px">

        <div id="menu" class="easyui-accordion" data-options="border:false">
        </div>
    </div>
    <div data-options="region:'center'" style="overflow: hidden;">
        <div id="index_tabs" style="overflow: hidden;">
            <div title="首页" class="index_img" data-options="iconCls:'fa fa-home',border:false" style="overflow: hidden;height: auto">
                <div class="index_img_btn" onClick="">
                    <img src="${staticPath }/static/index/default/images/welcome.png" width="100%">
                </div>
            </div>
        </div>
    </div>
    <div data-options="region:'south',border:false"
         style="height: 30px;line-height:30px; overflow: hidden;text-align: center;background-color: #f3f3f3; border-top:1px solid #dddddd; color:#676767;">Copyright ©
        2017 power by <a href="http://www.yuminsoft.com/" target="_blank" style="color:#676767">上海郁敏科技信息有限公司</a></div>
</div>
<div id="tabs_menu">
    <div data-options="iconCls:'fi-loop'" type="refresh" style="font-size: 12px;">刷新</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'fi-x'" type="close" style="font-size: 12px;">关闭</div>
    <div data-options="iconCls:''" type="closeOther">关闭其他</div>
    <div data-options="iconCls:''" type="closeAll">关闭所有</div>
</div>
</body>
</html>