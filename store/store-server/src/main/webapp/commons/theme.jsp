
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="${basePath }/static/index/default/css/pms.css"/>
<script>
    var staticPath = "${basePath}/static";
    var attribute = '';
    // 主题自动选择
    $(function () {
        $.get(ctxPath + "/getAttribute", {}, function (result) {
            if (result.success && result.data) {
                attribute = result.data;
                if (attribute && attribute.defaultTheme) {
                    var link = $("#easyuiTheme");
                    link.attr("href", staticPath + "/easyui/themes/" + attribute.defaultTheme + "/easyui.css");
                }
            }
        });
    });
</script>