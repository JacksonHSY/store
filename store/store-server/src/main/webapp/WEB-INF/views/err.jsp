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
<h2 style="color: red">参数异常:申请件编号不能为空!</h2>
</body>
</html>