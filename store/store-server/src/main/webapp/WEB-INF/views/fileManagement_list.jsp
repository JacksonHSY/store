<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>

<script type="text/javascript">

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
		
		$('#file_Management_List').datagrid('options').queryParams = queryParams;
		$('#file_Management_List').datagrid("options").url="${ctx}/fileManage/fileManagementPage";
		$("#file_Management_List").datagrid('load');
	
	});
});

function formatFileType(val, row) {
	 var fileType = "";
	if(val == "1"){
		fileType = "视频";
	}/* else if(val == "2"){
		fileType = "联系人核实";
	}else if(val == "3"){
		fileType = "银行流水核实";
	}else if(val == "4"){
		fileType = "保单贷核实";
	}else if(val == "5"){
		fileType = "随车贷核实";
	}else if(val == "6"){
		fileType = "水电煤单据核实";
	}else if(val == "7"){
		fileType = "其他核实";
	} */
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
	}/* else if(val == "4"){
		flowStatus = "已下载";
	}else if(val == "5"){
		flowStatus = "已删除";
	} */
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

function formatdate(val, row) {
	if (val != null && val != "") {
		var date = new Date(val);
		var dateStr = date.format("yyyy-MM-dd");
		return dateStr
	} else {
		return "";
	}
}

//构建操作链接
function builderOperationLinks(value,rec){
	/* var id = rec.id,links; */
	var applicationDesc = rec.applicationDesc,links;
	links = '<a href="#" onclick="showDetails(&quot;'+applicationDesc+'&quot;);">申请详情</a>';
	return links;
}

function showDetails(applicationDesc){
	$('#desc').val(applicationDesc);
	$('#apply_win').dialog('open');	
}
function closeDialog(){
	$('#apply_win').dialog('close');
}

</script>
<body>
	
	<div id="apply_win" class="easyui-window" title="申请详情" closed="true" style="width:500px;height:300px" data-options="iconCls:'icon-upload',modal:true">
			<div style="margin-left:50px;margin-top:5px">
				<form id="apply_form" enctype="multipart/form-data" method="post" target="upload_file">
					<input hidden="true" name="fileUploadId" id="fileUploadId">
					<div style="margin-left:5px;margin-top:15px">申请描述：</div>
					<div style="margin-top:20px"><textarea rows="5" cols="30" id="desc" disabled="disabled" name="applicationDesc" style="width:85%;"></textarea>
					</div>
				</form>
			</div>
			<div style="margin-left:70px;margin-top: 20px">
				<div style="float: left;margin-left:80%;text-align:center"><a href="#" id="back" class="easyui-linkbutton" style="width:100%" onclick="closeDialog();">返回</a></div>
			</div>
	</div>

	<div id="tb" style="padding: 5px; height: auto">	  
	   <form action="" id="queryCustomerOnlineForm" method="post">
		<table border="0" width="100%" style="margin-left: 20px;">
		<tr>
		<td>
			文件ID:<input id="fileKey" name="fileKey" placeholder="请输入查询内容">
		</td>
		<td>
			客户姓名:<input id="customerName" name="customerName" placeholder="请输入查询内容">
		</td>
		<td>
			申请件编号:<input id="applicationCaseNo" name="applicationCaseNo" placeholder="请输入查询内容">
		</td>
		<td>
			流转状态:
			<select id="flowStatus"  class="easyui-combobox" editable="false" style="width: 100px" data-options="panelHeight:'auto'">
			    <option value="">全部</option> 
	    		<option value="1">申请中</option>   
		    	<option value="2">拒绝</option>   
		    	<option value="3">同意</option>  
		    	<!-- <option value="4">已下载</option>   
		    	<option value="5">已删除</option>  -->  		    	 
			</select> 
		</td>
		<td>
			文件类型:
			<select id="fileType"  class="easyui-combobox" editable="false" style="width: 100px" data-options="panelHeight:'auto'">
			    <option value="">全部</option>   
	    		<option value="1">视频</option> 
	    		<!-- <option value="2">联系人核实</option>   
		    	<option value="3">银行流水核实</option>  
		    	<option value="4">保单贷核实</option>   
	    		<option value="5">随车贷核实</option> 
	    		<option value="6">水电煤单据核实</option>   
		    	<option value="7">其他核实</option>  -->		    	 
			</select> 
		</td>
		</tr>
		<tr>
			<td colspan="2">
				申请时间:
				<input id="applyDateBegin" name="applyDateBegin" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'applyDateEnd\')}',dateFmt:'yyyy-MM-dd'})"/>
		   		-<input id="applyDateEnd" name="applyDateEnd" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'applyDateBegin\')}',dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td colspan="2">
				上传时间:
				<input id="uploadDateBegin" name="uploadDateBegin" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'uploadDateEnd\')}',dateFmt:'yyyy-MM-dd'})"/>
		   		-<input id="uploadDateEnd" name="uploadDateEnd" type="text" class="query Wdate" onclick="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'uploadDateBegin\')}',dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td>
				<a href="#" id="fileManagement_SearchBt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			</td>
		</tr>
	  </table>	
	</form>				
	</div>
	<!-- <div id="fileManagement_dlg" class="easyui-dialog" style="text-align:center;width:800px; " closed="true" buttons="#fileManagement_dlg-buttons">
		
	</div> -->
	<table id="file_Management_List" class="easyui-datagrid" striped="true"  toolbar="#tb" rownumbers="true" pagination="true" 
		singleSelect="true" fitColumns="true" fit="true" style="overflow-x:hidden"  pageSize="15"  pageList=[15,30,45,60]
		data-options="url :'${ctx}/fileManage/fileManagementPage',
		      emptyMsg: '暂无数据'
		">
		<thead>
			<tr>
			    <th field="id" width="60" sortable="true" hidden="hidden"></th>
				<th field="customerName" width="80" align='center'>客户姓名</th>
				<th field="applicationCaseNo" width="90"  align='center'>申请件编号</th>				
				<th field="fileType" width="60" formatter="formatFileType" align='center'>文件类型</th>
				<th field="fileKey" width="110" align='center'>文件ID</th>
				<th field="fileName" width="110" align='center'>文件名称</th>
				<th field="fileSize" width="60" align='center' formatter='bytesToSize'>文件大小</th>
				<th field="uploadTime" width="80" formatter="formatterdate" align='center' >上传时间</th>
				<th field="flowStatus" width="80" formatter="formatFlowStatus" align='center'>流转状态</th>
				<th field="refusalReasons" width="100" align='center'>拒绝原因</th>
				<th field="applyTime" width="60" formatter="formatdate" align='center'>申请时间</th>
				<th field="opt" width="60" formatter="builderOperationLinks" align='center'>操作</th>
				<th field="applicationDesc" width="60" align='center' hidden="hidden">申请描述</th>
			</tr>
		</thead>
	</table>
</body>

