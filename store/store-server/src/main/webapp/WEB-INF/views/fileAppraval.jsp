<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>

<script type="text/javascript">


$(function(){
	$('#file_Management_List').datagrid({
		url: '${ctx}/fileAppravalmanage/fileManagementPage',
        fit: true,
        toolbar: "#tb",
        rownumbers: true,
        singleSelect: true,
        showFooter: true,
        pageSize:15,
        pageList: [15,30, 45, 60],
        queryParams: {
        	fileKey : $("#fileKey").val(),
    		customerName : $("#customerName").val(),
    		applicationCaseNo : $("#applicationCaseNo").val(),
    		applicationCaseNo : $("#applicationCaseNo").val(),
    	
    		flowStatus : $('#flowStatus').combobox('getValue'), 
    		fileType : $('#fileType').combobox('getValue'),
    		applyDateBegin : $("#applyDateBegin").val(),
    		applyDateEnd : $("#applyDateEnd").val(),
    		uploadDateBegin : $("#uploadDateBegin").val(),
    		uploadDateEnd : $("#uploadDateEnd").val(),
    		dealDateBegin : $("#dealDateBegin").val(),
    		dealDateEnd : $("#dealDateEnd").val(),
    		certifyBusinessDepart : $("certifyBusinessDepart").val()
        },
        emptyMsg: '暂无数据',
        columns:[[

			{field: 'id', width: '5', hidden: 'hidden', title: ''},
			{field: 'customerName', width: '100', align: 'center', formatter:formatterCustomer,  title: '客户姓名'},
			{field: 'applicationCaseNo', width: '100', align: 'center', title: '申请件编号'},
			{field: 'fileType', width: '100', align: 'center', formatter:formatFileType ,title: '文件类型'},
			
			{field: 'fileKey', width: '100', align: 'center', title: '文件ID'},
			{field: 'fileName', width: '100', align: 'center', title: '文件名称'},
			{field: 'fileSize', width: '100', align: 'center', formatter : bytesToSize, title: '文件大小' },
			{field: 'uploadTime', width: '150',align: 'center', formatter:formatterdate, title: '上传时间'},
			{field: 'flowStatus', width: '100', align: 'center', formatter:formatFlowStatus,title: '流转状态'},
			
			{field: 'refusalReasons', width: '100', align: 'center', title: '备注'},
			{field: 'applyTime', width: '200', align: 'center', formatter:formatterdate, title: '申请时间'},
			{field: 'auditTime', width: '100', align: 'center', formatter:formatterdate,title: '处理时间'},
			{field: 'userName', width: '100', align: 'center', title: '申请人姓名'},
			{field: 'userNum', width: '100', align: 'center', title: '工号'},
			{field: 'status', width: '5', hidden: 'hidden', title: ''},
			{field: 'opt', width: '150',formatter:builderOperationLinks, align: 'center', title: '操作'},
			/* {field: 'certifyBusinessDepart', width: '60', align: 'center', title: '进件营业部'}, */
                  
                  ]],
       pagination: true
	});
});

$(function() {
	$('#fileManagement_SearchBt').click(function() {
		var queryParams = $('#file_Management_List').datagrid('options').queryParams;
		
		queryParams.fileKey = $("#fileKey").val();
		queryParams.customerName = $("#customerName").val();
		queryParams.applicationCaseNo = $("#applicationCaseNo").val();
		queryParams.applicationCaseNo = $("#applicationCaseNo").val();
	
		queryParams.flowStatus = $('#flowStatus').combobox('getValue'); 
		queryParams.fileType = $('#fileType').combobox('getValue');
		
		queryParams.applyDateBegin = $("#applyDateBegin").val();
		queryParams.applyDateEnd = $("#applyDateEnd").val();
		queryParams.uploadDateBegin = $("#uploadDateBegin").val();
		queryParams.uploadDateEnd = $("#uploadDateEnd").val();
		queryParams.dealDateBegin = $("#dealDateBegin").val();
		queryParams.dealDateEnd = $("#dealDateEnd").val();
		queryParams.certifyBusinessDepart = $("#certifyBusinessDepart").val();
		
		
		$('#file_Management_List').datagrid('options').queryParams = queryParams;
		$('#file_Management_List').datagrid("options").url="${ctx}/fileAppravalmanage/fileManagementPage";
		$("#file_Management_List").datagrid('load');
	
	});
});


function formatFileType(val, row) {
	 var fileType = "";
	if(val == "1"){
		fileType = "视频";
	}else if(val == "2"){
		fileType = "回访音频";
	}else if(val == "3"){
		fileType = "核查音频";
	}
	return fileType;
}

function formatFlowStatus(val, row) {
	var flowStatus = "";
	if(val == "1"){
		flowStatus = "申请中";
	}else if(val == "2"){
		flowStatus = "拒绝";
	}else if(val == "3"){
		flowStatus = "同意";
	}else if(val == "4"){
		flowStatus = "已下载";
	}else if(val == "5"){
		flowStatus = "已删除";
	}
	return flowStatus;
}

function formatterdate(val, row) {
	if (val != null && val != "") {
		var date = new Date(val);
		var dateStr = date.format("yyyy-MM-dd hh:mm:ss");
		return dateStr
	} else {
		return "";
	}
}
//显示标记
function formatterCustomer(value, row, index){
	
if(row.flowStatus==1){
	 return '<font color="red">' + value + '</font>';
		
	}else{
		return value;
	}
}
//构建操作链接
function builderOperationLinks(value,rec,index){
	/* var id = rec.id,links; */
	var applicationDesc = rec.applicationDesc,links;
	if(rec.flowStatus==1 && rec.status == '0'){
		links = '<a href="#" id="show" onclick="showDetails(&quot;'+applicationDesc+'&quot;);">审核</a>';
	}if(rec.flowStatus==3 && rec.status == '0'){
		links = '<a href="#" id="down" onclick="downloadFile('+index+');">下载</a>'; 
		
	}if(rec.status == '1'){
		links = '--'; 
		
	}
	/* links = '<a href="#" id="show" onclick="showDetails('+applicationDesc+');">申请详情</a>'+' <a href="#" id="down" onclick="downloadFile();">下载</a>'; */
	return links;
}

function showDetails(applicationDesc){

	$('#description').val(applicationDesc);
	$('#dialog').dialog('open');
	
	 if($('input[type=radio][name=reason]').val()==2){
	        $('#confuse').removeAttr("disabled");
	        $('#accept').attr("disabled",false);  
	  }
	  if($('input[type=radio][name=reason]').val()==3){
	        $('#accept').removeAttr("disabled");
	        $('#confuse').attr("disabled",false);
	  }
}
/**
 * 记录勾选框
 */
var ids = [];

function downloadFile(index){
	$('#file_Management_List').datagrid('selectRow',index);// 关键在这里  
	var row = $('#file_Management_List').datagrid('getSelected'); 
	var filekey = row.fileKey;
	var fileName = row.customerName+'_'+row.fileName;
	fileName = escape(encodeURIComponent(fileName));
	$("#down_key").val(filekey);
	$("#down_file_name").val(fileName);
	//var fileKey = $('#file_Management_List').datagrid('getSelected').fileKey;
	$('#download_vedio_form').form('submit', {
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
	/* window.location.href="${ctx}/fileAppravalmanage/download?key="+filekey+"&fileName="+fileName;  */
}
//列表勾选框事件
function controllIfSelected(value, rec) {
	return "<input type=\"checkbox\" onchange='opSelected(" + value
			+ ",this)' />";
}
//确认，下载并关闭窗口
$(function(){
	$('#yes').click(function(){
		//更新状态
		if($("input[type='radio']").is(':checked')==false){
			alert("请审核");
		}else{
			
		
		var id = $('#file_Management_List').datagrid('getSelected').id;
		var flowstatus = $('input:radio[name="reason"]:checked').val();
		var  reason = $("#confuse").val();
		if(flowstatus==2 && reason=='' ){
			$.messager.show({
				title: '提示',
				msg: '请填写拒绝原因'
			});
			return;
		}
		$.post('${ctx}/fileAppravalmanage/updateRecord', {
			flowstatus : flowstatus,id:id,reason:reason
		}, function(result) {
			$.messager.show({
				title: '提示',
				msg: result.data
			});
			$("#file_Management_List").datagrid('load');
		}, 'json');}
		//判断是否选中
		/* if($("input[type='checkbox']").is(':checked')==true){
			//调用下载的方法。
			
			
			$("#check").attr("checked",false);
			
		} */
		
		$("#acc").attr("checked",false);
		$("#con").attr("checked",false);
		$("#confuse").val('');
		$('#dialog').dialog('close');
	});
	
});

//取消关闭dialog
$(function(){
	
	$('#no').click(function(){
		//关闭窗口
		
		 $("#check").attr("checked",false);
		$("#acc").attr("checked",false);
		$("#con").attr("checked",false);
		$("#confuse").val('');
		$('#dialog').dialog('close');
		
	});
	 $("input[type='radio']").click(function(){
		    if($(this).val()==2){
		    	  $('#confuse').attr("readonly",false);
		    }else{
		    	  $('#confuse').attr("readonly",true);
		    }
	 });
	
});
</script>

<body>
	 <div id="dialog" title="申请详情" class="easyui-dialog" style="width: 480px; height:500px;" maximizable="false"  closed="true" buttons="#dlg-buttons" modal="true" data-options="iconCls:'icon-save',resizable:false,modal:true"> 
	 	
		<div id="inputdia">
			<div  style="margin-left:50px;margin-top:45px"><span >申请描述：</span></div>
			<div style="margin-left:50px;margin-top:30px"><textarea rows="5" cols="30" id="description" readonly="readonly" style="width:80%;">文字不得超过150个</textarea>
			</div>
			<!-- <input id="description" readonly="readonly" type="text" style="width:450px;height:250px;"> -->
		</div>
		<div id="select" style="margin-left:50px;margin-top:45px">
			 <input type="radio" id="con" name="reason" value="2" >拒绝</input> <input readonly="true" type="text" id="confuse"><br/>
            <input type="radio" id="acc" name="reason" value="3">同意</input>  <!-- <input type="checkbox" id="check" >下载文件</input> --> <br/>
            <div style="margin-left:50px;margin-top:45px">
	            <a href="#" class="easyui-linkbutton"  id="yes"  value="确认" style="float:left; margin-left:50px;">确认</a>
	        	  <a href="#"class="easyui-linkbutton" id="no"  value="取消" style="float:left; margin-left:80px;">取消</a>
        	</div>
		</div>
	</div>
	<div id="tb" style=" padding: 5px;">
	  
	   <form action="" id="queryCustomerOnlineForm" style="height: auto; width:auto" method="post">
		<table border="0" width="100%" style="margin-left: 20px;height: auto; width:auto">
		<tr>
		<td style="height: auto; width:auto">
			文件ID:<input id="fileKey"  name="fileKey" placeholder="请输入查询内容">
		</td>
		<td style="height: auto; width:auto">
			客户姓名:<input id="customerName" name="customerName" placeholder="请输入查询内容">
		</td>
		<td style="height: auto; width:auto">
			申请件编号:<input id="applicationCaseNo" name="applicationCaseNo" placeholder="请输入查询内容">
		</td>
		<td style="height: auto; width:auto">
			流转状态:
			<select id="flowStatus"  class="easyui-combobox" editable="false" style="width: 100px" data-options="panelHeight:'auto'">
			    <option value="">全部</option> 
	    		<option selected="selected" value="1">申请中</option>   
		    	<option value="2">拒绝</option>   
		    	<option value="3">同意</option>  
		    	<!-- <option value="4">已下载</option>   
		    	<option value="5">已删除</option>  -->  		    	 
			</select> 
		</td>
		<td style="height: auto; width:auto">
			文件类型:
			<select id="fileType"  class="easyui-combobox" editable="false" style="width: 100px" data-options="panelHeight:'auto'">
			    <option value="">全部</option>   
	    		<option value="1">视频</option> 
			</select> 
		</td>
		<td style="height: auto; width:auto">
			进件营业部:
			<input id="certifyBusinessDepart" type="text" style="width:100px;"  name="certifyBusinessDepart"  />
		</td>
		</tr>
		<tr>
			<td style="white-space:nowrap;"colspan="2">
				申请时间:
				<input id="applyDateBegin" name="applyDateBegin" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'applyDateEnd\')}',dateFmt:'yyyy-MM-dd'})"/>
		   		-<input id="applyDateEnd" name="applyDateEnd" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'applyDateBegin\')}',dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td style="white-space:nowrap;"colspan="2">
				上传时间:
				<input id="uploadDateBegin" name="uploadDateBegin" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'uploadDateEnd\')}',dateFmt:'yyyy-MM-dd'})"/>
		   		-<input id="uploadDateEnd" name="uploadDateEnd" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'uploadDateBegin\')}',dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td style="white-space:nowrap;">
				处理时间:
				<input id="dealDateBegin" name="dealDateBegin" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'dealDateEnd\')}',dateFmt:'yyyy-MM-dd'})"/>
		   		-<input id="dealDateEnd" name="dealDateEnd" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'dealDateBegin\')}',dateFmt:'yyyy-MM-dd'})"/>
			</td>
			
		</tr>
		<tr>
			<td>
				<a href="#" id="fileManagement_SearchBt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			</td>
		</tr>
	  </table>	
	</form>
		
		
	</div>
	<table id="file_Management_List"  <%-- class="easyui-datagrid" striped="true"  toolbar="#tb" rownumbers="true" pagination="true" 
		singleSelect="true" fitColumns="true" fit="true" style="overflow-x:hidden"  pageSize="15"  pageList=[15,30,45,60]
		data-options="url :'${ctx}/fileAppravalmanage/fileManagementPage',
	 			view: myview,
		      emptyMsg: '暂无数据'
		" --%>>
		<!-- <thead>
			<tr>
				<th field="ck" formatter:controllIfSelected ></th>
			    <th field="id" hidden="true" width="10" sortable="true" ></th>
				<th field="customerName" width="80" align='center'>客户姓名</th>
				<th field="applicationCaseNo" width="90"  align='center'>申请件编号</th>				
				<th field="fileType" width="60" formatter="formatFileType" align='center' sortable="true">文件类型</th>
				<th field="fileKey" width="80" align='center'>文件ID</th>
				<th field="fileName" width="60" align='center'>文件名称</th>
				<th field="fileSize" width="60" align='center'>文件大小</th>
				<th field="uploadTime" width="60" formatter="formatterdate" align='center' >上传时间</th>
				<th field="flowStatus" width="80" formatter="formatFlowStatus" align='center' sortable="true">流转状态</th>
				<th field="refusalReasons" width="50" align='center'>备注</th>
				<th field="applyTime" width="100" formatter="formatterdate" align='center'>申请时间</th>
				<th field="auditTime" width="100" formatter="formatterdate" align='center'>处理时间</th>
				<th field="userName" width="100" align='center'>申请人姓名</th>
				<th field="userNum" width="100" align='center'>工号</th>
				<th field="opt" width="100" formatter="builderOperationLinks" align='center'>操作</th>
				<th field="certifyBusinessDepart" width="50"  align='center'>进件营业部</th>
			</tr>
		</thead> -->
	</table>
	<form id="download_vedio_form" method="get" action="${ctx}/fileAppravalmanage/download">
		<input hidden="true" id="down_key" name="key" >
		<input hidden="true" id="down_file_name" name="fileName">
		
	</form>
</body>