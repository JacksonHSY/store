<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017/3/9
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page import="com.ymkj.sso.client.ShiroUtils"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="${basePath }/static/index/default/css/pms.css"/>
<style>
    .header .opt{
        background-color: #fff;
        color:#000;
    }
    .header .l-btn{
        border-radius: 0px;
    }
    .header .l-btn-plain{
        padding: 0px;
    }
    .header .l-btn-plain:hover{

        border:none;
        background-color:#a0a3a5;
        border-radius: 0px;
        color:#fff;
    }
    .header .m-btn-plain-active{
        border:none;
        background-color:#a0a3a5;
        border-radius: 0px;
        color:#fff;
    }
    .header .nav{
        line-height:24px;
        padding: 0px 3px;
        display: inline-block;
    }
    .header .nav:hover{
        background-color:#a0a3a5;
        height:24px;
    }
    .header  select{
        margin-right:10px;
        vertical-align: middle;
        background: transparent;
        color: #fff;
        border: 0px;
        cursor: pointer;
        height:24px;
        margin-bottom:2px;
        FONT: 14px/1.6em "微软雅黑", Verdana, Arial, sans-serif;
    }


</style>
<script>

    var staticPath = "${basePath}/static";
    var pmsUrl = '${pmsSystemVO.url}';
    var account = '${currentAccount}';
    var systemCode = '${currSystemCode}';
    var attribute = '';

    // 退出登录
    function logout() {
        $.messager.confirm('提示', '确定要退出?', function (r) {
            if (r) {
                window.location.href = basePath + '/logout';
            }
        });
    }

    // 切换主题
    function changeTheme() {
        var themeValue = $("#theme").val();
        var link = $("#easyuiTheme");
        link.attr("href", staticPath + "/index/easyui/themes/" + themeValue + "/easyui.css");
        $.getJSON(pmsUrl + "/api/updateDefault?callback=?", {
            "defaultTheme": themeValue,
            "account": account
        }, function (result) {
            if (result.success) {
                $('#switch').dialog('close');
            }
        });

    }

    // 设置默认访问系统
    function updateDefault() {
        var defaultSystemCode = $("#defaultIndex").val();
        if (!defaultSystemCode) {
            $.messager.alert('请选择默认系统!');
            return;
        }
        $.getJSON(pmsUrl + "/api/updateDefault?callback=?", {
            account: account,
            defaultSystemCode: defaultSystemCode
        }, function (result) {
            if (result.success) {
                $('#switch').dialog('close');
            }
        });
    }
    //修改密码
    function editPwd() {
        window.location.href = pmsUrl + "/editPwd?account=" + account + "&systemCode=" + systemCode;
//        window.location.href = "editPwd?account=" + account + "&systemCode=" + systemCode;
    }

    // 获取系统列表
    function getSystems(callback) {
        $.getJSON(pmsUrl + "/api/getSystems?callback=?", {account: account}, function (result) {
            if (result.success && result.data) {
                if (result.data.length > 1) {
                    $("#switchIndex").show();
                }
                $.each(result.data, function (i, item) {
                    $("#defaultIndex").append("<option value='" + item.code + "' url='" + item.url + "'>" + item.name + "</option>");

                    var url = item.url;
                    if (item.code == '${pmsSystemVO.code}') {
                        url = item.url + "/index?command=redirect";
                    }
                    $("#systems").append("<option style='display:inline-block' class='opt' value='" + item.code + "' url='" + url + "'>" + item.name + "</option>");
                })
                $("#systems").val(systemCode);
                callback();
            }

        });
    }

    // 获取员工属性
    function getUserInfo() {
        $.getJSON(pmsUrl + "/api/getUserInfo?callback=?", {account: account}, function (result) {
            if (result.success && result.data) {
                attribute = result.data;
                if (attribute && attribute.defaultSystemCode) {
                    $("#defaultIndex").val(attribute.defaultSystemCode);
                }
                if (attribute && attribute.defaultTheme) {
                    var link = $("#easyuiTheme");
                    link.attr("href", staticPath + "/index/easyui/themes/" + attribute.defaultTheme + "/easyui.css");
                    $("#theme").val(attribute.defaultTheme);
                }
            }
        });
    }

    // 显示消息列表
    function showMsg() {
        $("#msg").window({
            onOpen: function () {
//                $('#empMessage').attr('src', basePath + '/message/empMessageView?account=' + account);
                $('#empMessage').attr('src', pmsUrl+'/message/empMessageView?account='+account);
            }
        });
        $("#msg").window('open');
    }

    function getCoreSystemURL() {
        $.getJSON(pmsUrl + "/api/getCoreSystemURL?callback=?", function (data) {
            $("#coreSystemURLAnchor").prop("href", data.data);
        })
    }

    // 获取未读消息
    var messageWebSocket;
    function updateUnreadCount() {
        messageWebSocket.send(account);
    }
    // web socket连接
    function socket(url){
        var wsUrl = url;
        wsUrl = wsUrl.replace("http", "ws");
        messageWebSocket = new WebSocket(wsUrl + "/api/MessageServer/" + account);
        messageWebSocket.onmessage = function (e) {
            var result = JSON.parse(e.data);
            if (result.success) {
                $("#unreadAccount").text(result.data);
                if (result.data > 0) {
                    $("#unreadCountDiv").css("display", "inline-block");
                    $("#unreadMessage .fa-comments").addClass("faa-flash animated");
                } else {
                    $("#unreadCountDiv").css("display", "none");
                    $("#unreadMessage .fa-comments").removeClass("faa-flash animated");
                }
            }
        };
        messageWebSocket.onclose = function (e) {
            messageWebSocket = -1;
        };
        messageWebSocket.onerror = function (e) {
            messageWebSocket = -1;
        };
        messageWebSocket.onopen = function (e) {
            updateUnreadCount();
        }
    }

    // 更换系统
    function changeSys(){
        var option = $("#systems option:selected");
        window.location.href = option.attr("url");
    }

    $(function () {
    	// ajax 登录状态监听
    	$.ajaxSetup({
    	    complete:function(xhr,status){
    	        if(status == 'success'){
    	            var responseText = xhr.responseText;
    	            if(responseText){
    	                if(responseText.indexOf("login_status")>0){
    	                    var response = $.parseJSON(responseText);
    	                    if(response.login_status == 300){
    	                        window.location.href = ctxPath+"/index";//重新登录
    	                        return true;
    	                    }
    	                }else{
    	                	var temp = xhr.getResponseHeader("account");
    	                	if(temp && temp != account){
    	                		window.location.reload();
    	                		return;
    	                	}
    	                }
    	            }
    	        }else{
    	            $.messager.alert('错误', "网络连接失败!", 'error');
    	        }
    	    }
    	});

    	
        if(pmsUrl == ''){
            pmsUrl = '${basePath}'
        }else{

            // websocket
            socket(pmsUrl);
        }

        $('#header').layout({fit: true});
        getSystems(function () {
            getUserInfo()
        });

        $('#systems').delegate('li', 'click', function () {
            var url = $(this).attr("url");
            window.location = url;
        });

        getCoreSystemURL();
    })
</script>

<div class="header">
    <div style="text-align:right; color:#ffffff; padding:8px 0px">
        <div style="float: left;vertical-align: middle;">
        <span class="logo1" style="display:block;float: left;">
            <img style="width:100%;height:100%;" src="${basePath }/static/index/default/images/logo.png"></span>
        </div>
        <span data-options="plain:true">欢迎您! <%=ShiroUtils.getCurrentUser().getName()%></span> │
        <a href="#" class="easyui-menubutton" data-options="menu:'#user',iconCls:'fa fa-user'">个人中心</a> │
        <a id="unreadMessage" href="javascript:;" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'fa fa-comments'"
           onclick="showMsg()">消息中心
            <div id="unreadCountDiv"
                 style="display: none;width:20px;height:16px;line-height:14px;border-radius:8px;background:#f00;color:#fff;text-align: center;">
                <span id="unreadAccount"></span>
            </div>
        </a> │
        <%--<a href="#" class="easyui-menubutton" data-options="menu:'#theme',iconCls:'fa fa-dashboard'">主题</a> │--%>
        <div class="nav">
            <span class=" fa fa-dashboard" style="vertical-align: middle;font-size: 14px;">&nbsp;</span>
            <select id="theme" onchange="changeTheme();" >
                <option class='opt' value="blue">主题-蓝色</option>
                <option class='opt' value="orange">主题-橙色</option>
                <option class='opt' value="red">主题-红色</option>
            </select>
        </div>
        |
        <div class="nav">
            <span class=" fa fa-window-restore" style="vertical-align: middle;font-size: 14px;">&nbsp;</span>
            <select id="systems" onchange="changeSys();" >

            </select>
        </div>
        |
        <a id="coreSystemURLAnchor" href="#" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'fa fa-arrow-circle-right'">返回旧版</a>
    </div>
    <%--<div class="easyui-layout" id="header" data-options="border:false">
        <div data-options="region:'west',border:false, width:'30%'">
            <div class="logo"></div>
        </div>
        <div data-options="region:'center', border:false,width:'40%'">
            &lt;%&ndash;<div id="systems"
                 style="text-align:center;padding:8px; width:450px; position:absolute; top:5px; left:50%; margin-left:-300px;">
            </div>&ndash;%&gt;
        </div>
    </div>--%>

    <div id="user">
        <div id="switchIndex" style="display:none;" data-options="iconCls:'fa fa-home',itemHeight:30"
             onclick="javascript: $('#switch').dialog('open');">配置首页
        </div>
        <div data-options="iconCls:'fa fa-edit'" onclick="editPwd();">修改密码</div>
        <div data-options="iconCls:'fa fa-user-secret'" onclick="logout()">安全退出</div>
    </div>
    <%--<div id="theme" data-options="onClick:changeTheme">
        <div data-options="name:'blue'">蓝色</div>
        <div data-options="name:'orange'">橙色</div>
        <div data-options="name:'red'">红色</div>
    </div>--%>

    <div id="switch" class="easyui-dialog" title="配置默认首页"
         data-options="iconCls: 'fi-home',closed:true, buttons:'#switch-btn'"
         style="width:400px;height:200px;padding:10px">
        <div class="content">
            配置默认首页:
            <select id="defaultIndex">
            </select>
        </div>
    </div>

    <div id="switch-btn">
        <a href="#" class="easyui-linkbutton" iconCls="fa fa-save" onclick="updateDefault()">确定</a>
        <a href="#" class="easyui-linkbutton" iconCls="fa fa-close"
           onclick="javascript: $('#switch').dialog('close');">关闭</a>
    </div>


    <div id="msg" class="easyui-window" title="站内消息" data-options="modal:true,closed:true,iconCls:'fa fa-comments'"
         style="width:60%;height:500px;overflow: hidden;">
        <iframe id="empMessage" src="" style="border: none;width: 100%;height: 100%;"></iframe>
    </div>
</div>

