<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<%@ include file="/static/common/uploadJs.jsp"%>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${customerName}-文件上传列表</title>
</head>
<style>
input[type="file"] {
  color: transparent;
}
/*a  upload */
.a-upload {
    padding: 0px 10px;
    height: 20px;
    line-height: 20px;
    position: relative;
    cursor: pointer;
    color: #fff;
    background: #5cb85c;
    border: 0px;
    border-radius: 0px;
    overflow: hidden;
    display: inline-block;
    *display: inline;
    *zoom: 1
}

.a-upload  input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
    filter: alpha(opacity=0);
    cursor: pointer
}

.a-upload:hover {
    color: #fff;
    background: #5cb85c;
    text-decoration: none
}
</style>
<script type="text/javascript">
var isVedio=true;//判断是否生成预览标识
var isRightType=true;//判断文件格式是否允许标识
var isOverSize=true;//判断文件大小是否允许
var isSameName = true;//视频文件是否重名标识
var uploadType = '1';//上传文件类型
var tempType = '1';
var videoUploadNum = 0;//已上传文件个数
var videoNames = new Array();
var videoUploadNames = new Array();//已上传视频集合
$(function(){
	
    $('#cancel_apply').bind('click', function(){
		$('#apply_win').window('close');
    });
    
    $('#apply_submit').bind('click', function(){
    	$('#apply_form').form('submit', {
            url:'${ctx}/upload/applyFileView',
            onSubmit: function(){
            },
            success:function(data){
            	var v = eval('(' + data + ')');
            	if(v.code == '0000'){
	            	$.messager.alert('提示','申请成功!','info');
            	}else{
            		$.messager.alert('提示','申请失败,'+v.msg,'info');
            	}
            	$('#apply_win').window('close');
            }
    	});
    });
    
    $("#desc").on("input propertychange", function() {  
        var $this = $(this);  
        var _val = $this.val();  
        if (_val.length > 150) {  
        	$.messager.alert('提示','文字不得超过150个!','info');
        	$this.val(_val.substring(0, 150));  
        }  
    }); 
    
    $("#mydiv1").mouseleave(function() {
    	var objDiv1 = $("#mydiv1");
    	$(objDiv1).css("display", "none");
    })
    $("#mydiv2").mouseleave(function() {
    	var objDiv2 = $("#mydiv2");
    	$(objDiv2).css("display", "none");
    })
});

function openUpload(value){
	var data=$('#file_upload_list').datagrid('getData');
    videoNames.splice(0,videoNames.length);
    if(null != data && data.rows.length > 0){
        for(j = 0,len=data.rows.length; j < len; j++) {
            videoUploadNames.push(data.rows[j].fileName);
        }
    }
    videoUploadNum = data.total;
	if('1' == uploadType && 2 < data.total){
		$.messager.alert('提示','只允许上传三个视频!','alert');
		return;
	}
	tempType = value;
	$("#fileType").val(value);
	$('#upload_win').window('open');
	$('#files_table').html('');
	return;
}

function typeFormat(value,row,index){
	if (value=="1"){
		return "视频";
	}else if(value=="2"){
		return "联系人核实";
	}else if(value=="3"){
		return "银行流水核实";
	}else if(value=="4"){
		return "保单贷核实";
	}else if(value=="5"){
		return "随车贷核实";
	}else if(value=="6"){
		return "水电煤单据核实";
	}else if(value=="7"){
		return "其他核实";
	}
}

/**
 * 单位转换：btyes to Mb
 */
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1000, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
   return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}

//查询文件上传记录
function showFileRecords(type){
	uploadType = type;
	if('1' == type){
		var objDiv2 = $("#mydiv2");
    	$(objDiv2).css("display", "none");
	}else{
		var objDiv1 = $("#mydiv1");
    	$(objDiv1).css("display", "none");
	}
	$('#fileType').val(uploadType);
	$('#file_upload_list').datagrid('load',{
		fileType: type
	});
}

//弹出申请查看窗口
function showApplyView(index){
	$('#file_upload_list').datagrid('selectRow',index);// 关键在这里  
	var row = $('#file_upload_list').datagrid('getSelected'); 
	if (row){
		if('1' == uploadType){//视频查看
			$("#fileUploadId").val(row.id);
			$.post("${ctx}/upload/getApplyDesc",{fileUploadId:row.id,fileType:uploadType},
			  function(v){
				$('#apply_win').window('open');
				$('#desc').val("");
				if(v.code=='0000'){
					$('#desc').val(v.data);
					$('#desc').attr("disabled",true);
					/* $("#apply_submit").unbind("click"); */
					$('#desc_btn_div').css("display",'none');
					/* $('#apply_submit').css("display",'none'); */
				}
			  }, "json");
		}else{//音频查看
			$("#uploadId").val(row.id);
		
			//$('#apply_audio_form').submit();
			
			$('#apply_audio_form').form('submit', {
	            onSubmit: function(){
	            },
	            success:function(data){
	            	var v = eval('(' + data + ')');
	            	if(v.code == '0000'){
	            		$.messager.show({  
	                        title: "操作提示",  
	                        msg: "下载成功!",  
	                        showType: 'slide',  
	                        timeout: 1000  
	                    });
	            	}else{
	            		$.messager.show({  
	                        title: "操作提示",  
	                        msg: '申请失败,'+v.msg,  
	                        showType: 'slide',  
	                        timeout: 1000  
	                    });
	            	}
	            }
	    	});
			/* $.post("${ctx}/upload/getAudioApplyDesc",{fileUploadId:row.id,fileType:uploadType},
			  function(v){
				/* if(v.code=='0000'){
					$.messager.alert('提示',v.msg,'alert');
				} 
			  }, "json"); */
		}
	}else{
		$.messager.alert('提示','请选择一条记录!','alert');
	}
}

function show(obj,id) { 
	var objDiv = $("#"+id+uploadType+"");
	$(objDiv).css("display","block"); 
	/* $(objDiv).css("left", event.clientX); 
	$(objDiv).css("top", event.clientY + 10);  */
	$(objDiv).css("left", "237px"); 
	$(objDiv).css("top", "137px");
} 
function hide(obj,id) {
	var objDiv = $("#"+id+uploadType+"");
	$(objDiv).css("display", "none"); 
} 

//操作列
function formatOper(val,row,index){
	if('1'==uploadType){
		return '<a href="#" onclick="showApplyView('+index+')">申请查看</a>'; 
	}else{
		return '<a href="#" onclick="showApplyView('+index+')">下载</a>'; 
	}
     
}  
</script>
<body class="easyui-layout">
	<div data-options="region:'north',split:true" style="height:100px">
    		<div style="padding: 42px; border-bottom:0px solid #f2f3f5;font-size:15px;font-weight:blod;">
    			<div style="float: left;margin-left:9px;"><span>客户姓名：</span><span>${customerName}</span></div>
				<div style="float: left;margin-left:250px;"><span>申请件编号：</span><span>${applicationCaseNo}</span></div>
				<div style="float: left;margin-left:250px;"><span>放款时间：</span><span>${loanDateStr}</span></div>
			</div>
	</div>
    <div data-options="region:'west',split:true" style="width:200px;">
    	<ul id="tt" class="easyui-tree">
		    <li>
				<span>文件类型</span>
				<ul >
           			<li >
           				<span>
           					<div onclick="showFileRecords('1');" style="height:30px">
							  	<font style="">视频文件</font>
           					</div>
           				</span>
           			</li>
           			<li>
           				<span>
           					<div onclick="showFileRecords('2');" style="height:30px">
							  	<font style="">音频文件</font>
           					</div>
           				</span>
           			</li>  
				</ul>
			</li>
		</ul>
    </div>
    <div data-options="region:'center'" style="padding:5px;">
    	<!-- 以下表头工具栏-->
		<div border="false" style="overflow:hidden; border-bottom:1px solid #ccc;" >
		<!-- 	<div style="padding: 5px; border-bottom:1px solid #f2f3f5">
				<span  id="uploadBtn" class="easyui-linkbutton" iconCls="icon-upload" plain="true" onMouseOver="javascript:show(this,'mydiv');" >上传</span> 
				<span class="datagrid-btn-separator" id="applyBtn"><a href="#" id="apply_button" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="showApplyView();" >申请查看</a></span>
			</div>	 -->
		</div>
		<!-- 以下列表 -->
		<div border="false">
			<table id="file_upload_list" class="easyui-datagrid" width="auto" url="${ctx}/upload/getUploadRecords?applicationCaseNo=${applicationCaseNo}" title="文件上传列表" rownumbers="true" fitColumns="true" singleSelect="true">
				<thead>
					<tr >
						<th field="id" hidden="true"></th>
						<th field="fileType" width="100px" align="center" formatter="typeFormat">文件类型</th>
						<th field="fileName" width="100px" align="center">文件名称</th>
						<th field="fileKey" width="100px" align="center">文件ID</th>
						<th field="fileSize" width="100px" align="center" formatter="bytesToSize">文件大小</th>
						<th field="uploadTime" width="100px" align="center" formatter="formatterdate">上传时间</th>
						<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper">操作</th> 
					</tr>
				</thead>
			</table>
		</div>
		
    </div>
    
    <div id="upload_win" class="easyui-window" title="上传" closed="true" style="width:60%;height:50%;" data-options="iconCls:'icon-upload',modal:true">
			<div data-options="region:'north',split:true" style="height:100px">
				 <div class="container">
				    <!-- The file upload form used as target for the file upload widget -->
				    <form id="fileupload"  method="POST" enctype="multipart/form-data">
				    	<input hidden="true" name="customerName" value="${customerName}">
						<input hidden="true" name="fileType" id="fileType" >
						<input hidden="true" name="customerNo" value="${customerNo}">
						<input hidden="true" name="applicationCaseNo" value="${applicationCaseNo}">
						<input hidden="true" name="certifyBusinessDepart" value="${certifyBusinessDepart}">
				        <!-- Redirect browsers with JavaScript disabled to the origin page -->
				        <!-- <noscript><input type="hidden" name="redirect" value="https://blueimp.github.io/jQuery-File-Upload/"></noscript> -->
				        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
				        <div class="row fileupload-buttonbar">
				            <div class="col-lg-7">
				                <!-- The fileinput-button span is used to style the file input field as button -->
				                <span class="btn btn-success fileinput-button" style="width:100px;height:34px;">
				                    <i class="glyphicon glyphicon-plus" ></i>
				                    <span></span>
				                    <a href="javascript:;" class="a-upload">
									    <input type="file" name="files" multiple='true'>上传文件
									</a>
				                    <!-- <input type="file" name="files" multiple='true' style="background-color:#5cb85c;border:0px;"> -->
				                </span>
				                <button type="submit" class="btn btn-primary start">
				                    <i class="glyphicon glyphicon-upload"></i>
				                    <span>开始上传</span>
				                </button>
				                <button type="reset" class="btn btn-warning cancel">
				                    <i class="glyphicon glyphicon-ban-circle"></i>
				                    <span>全部取消</span>
				                </button>
				                <input type="checkbox" class="toggle">
				            </div>
				            <!-- The global progress state -->
				            <div class="col-lg-5 fileupload-progress fade">
				                <!-- The global progress bar -->
				                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
				                    <div class="progress-bar progress-bar-success" style="width:0%;"></div>
				                </div>
				                <!-- The extended global progress state -->
				                <div class="progress-extended">&nbsp;</div>
				            </div>
				        </div>
				        <!-- The table listing the files available for upload/download -->
				        <table role="presentation" class="table table-striped"><tbody class="files" id="files_table"></tbody></table>
				    </form>
				</div>
				<!-- The blueimp Gallery widget -->
				<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
				    <div class="slides"></div>
				    <h3 class="title"></h3>
				    <a class="prev">‹</a>
				    <a class="next">›</a>
				    <a class="close">×</a>
				    <a class="play-pause"></a>
				    <ol class="indicator"></ol>
				</div>
	    </div>
	</div>
	
	<div id="apply_win" class="easyui-window" title="申请详情" closed="true" style="width:500px;height:400px" data-options="iconCls:'icon-upload',modal:true">
			<div style="margin-left:50px;margin-top:5px">
				<form id="apply_form" enctype="multipart/form-data" method="post" target="upload_file">
					<input hidden="true" name="fileUploadId" id="fileUploadId">
					<div style="margin-left:5px;margin-top:15px">申请描述：</div>
					<div style="margin-top:30px"><textarea rows="5" cols="30" id="desc" name="applicationDesc" style="width:80%;">文字不得超过150个</textarea>
					</div>
				</form>
			</div>
			<div style="margin-left:70px;margin-top: 40px" id="desc_btn_div">
				<div style="float: left;margin-left:9px;text-align:center"><a href="#" id="apply_submit" class="easyui-linkbutton" style="width:100%">申请</a></div>
				<div style="float: left;margin-left:200px;text-align:center"><a href="#" id="cancel_apply" class="easyui-linkbutton" style="width:100%">取消</a></div>
			</div>
	</div>
	<!-- 上传按钮悬浮DIV -->
	<div id="mydiv1" style="position:fixed;display:none;border:1px solid silver;background:silver;left:237px;top:137px;">
		<a href="#" id="apply_button" class="easyui-linkbutton" iconCls="icon-upload" plain="true" onclick="openUpload('1');" >上传视频</a>
	</div> 
	<div id="mydiv2" style="position:fixed;display:none;border:1px solid silver;background:silver;left:237px;top:137px;">
		<a href="#" id="apply_button1" class="easyui-linkbutton" iconCls="icon-upload" plain="true" onclick="openUpload('2');" >联系人核实</a><br>
		<a href="#" id="apply_button2" class="easyui-linkbutton" iconCls="icon-upload" plain="true" onclick="openUpload('3');" >银行流水核实</a><br>
		<a href="#" id="apply_button3" class="easyui-linkbutton" iconCls="icon-upload" plain="true" onclick="openUpload('4');" >保单贷核实</a><br>
		<a href="#" id="apply_button4" class="easyui-linkbutton" iconCls="icon-upload" plain="true" onclick="openUpload('5');" >随车贷核实</a><br>
		<a href="#" id="apply_button5" class="easyui-linkbutton" iconCls="icon-upload" plain="true" onclick="openUpload('6');" >水电煤单据核实</a><br>
		<a href="#" id="apply_button6" class="easyui-linkbutton" iconCls="icon-upload" plain="true" onclick="openUpload('7');" >其他核实</a>
	</div>
	<form id="apply_audio_form" method="get" action="${ctx}/upload/getAudioApplyDesc">
		<input hidden="true" name="fileUploadId" id="uploadId">
	</form>
<script id="template-upload" type="text/x-tmpl">
{% if (isVedio && isRightType && isOverSize && isSameName) { %}
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled style="display:none;">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
</body>