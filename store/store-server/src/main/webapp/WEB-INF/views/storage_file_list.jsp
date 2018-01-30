<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<%@ include file="/static/common/jscsslibs/easyui.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicrud.jsp"%>
<%@ include file="/static/common/jscsslibs/easyuicommon.jsp"%>
<%@ include file="/static/common/jscsslibs/sysstyle.jsp"%>
<%@ include file="/static/common/jscsslibs/tools.jsp"%>
<!-- 实体名 -->
<c:set var="entityName" value="storageFile" />
<!-- 命名空间 -->
<c:set var="namespacePath" value="${ctx}/storageFile" />
<script type="text/javascript">
var applicationCaseNo;
var ids = [];
/* postAjax(false, '${ctx}/${entityName}/firstOne',
	function success(data){
	applicationCaseNo = data;}, 'json'); */
$(function() {
	$("#${entityName}_List").datagrid({
        onClickRow : function(index, row){
            applicationCaseNo=row["applicationCaseNo"];
            $("#msg").text("申请件编号:"+applicationCaseNo);  
          	$('#${entityName}_Dtl').datagrid('reload', {
         		'applicationCaseNo' : applicationCaseNo
           	});
        	$("#showResult" ).css("display", "block"); 
        }
	});
	$('#${entityName}_Dtl').datagrid({
	    url:"${namespacePath}/findByApplicationCaseNo?applicationCaseNo"+applicationCaseNo,
	    striped:true,
	    singleSelect:true,
	    rownumbers:true,
	    fitColumns:true,
	    selectOnCheck:true,
	    columns:[[
	  			{field : 'id',title : '',align : 'center',width : fixWidth(0.05),sortable : true,formatter : controllIfSelected},      
	  			{field:'fileType',title:'文件类型',width:fixWidth(0.1),align:'center',formatter: function(value,row,index){
					if (value=="1"){
						return "视频文件";
					} else {
						return "音频文件";
					}
				}},
				{field:'applicationCaseNo',title:'申请件编号',width:fixWidth(0.2),align:'center'},
	  			{field:'fileName',title:'文件名称',width:fixWidth(0.2),align:'center'},
	  			{field:'fileKey',title:'文件ID',width:fixWidth(0.1),align:'center'},
	  			{field:'fileSize',title:'文件大小',width:fixWidth(0.1),formatter : bytesToSize, align:'center'},
	  		 	{field:'uploadTime',title:'上传时间',  
                    formatter:function(value,row,index){  
                        var unixTimestamp = new Date(value);  
                        return unixTimestamp.toLocaleString();  
                        }  
                 }, 
	  			{field:'opt',title:'操作',width:fixWidth(0.1),align : 'center',formatter : builderOperationLinks},
	  	]]
	});
	
	$('#searchBt').click(function() {
		var queryParams = $('#${entityName}_List').datagrid('options').queryParams;
		//设置值
		queryParams.applicationCaseNo = $('#applicationCaseNo').val(); 
		$('#${entityName}_List').datagrid('options').queryParams = queryParams;
		$('#${entityName}_List').datagrid('load');
		$("#showResult" ).css("display", "none"); 
	});

});

//构建操作链接
function builderOperationLinks(value,rec){
	var id = rec.id,links;
	links = '<a href="#" onclick="removeRecordByOne('+id+',1);">删除</a>';
	return links;
}
//列表勾选框事件
function controllIfSelected(value, rec) {
	return "<input type=\"checkbox\" onchange='opSelected(" + value
			+ ",this)' />";
}
//----------------------------------删除--------------------------------------------
function removeRecord(status) {
	if (ids.length == 0) {
/* 		$.messager.alert('温馨提示','请选定一行记录再操作!','info'); */
		alert("请选定一行记录再操作");
		return ;
	}
	/* $.messager.confirm('确认',"确定删除吗?" , function(r) { */
		/* confirm("确定删除吗?" , function(r) {
		if (r) {
			$.post('${namespacePath}/removeRecord?ids='+ids, {
				status:status
			}, function(result) {
				if (result.status){
					$('#${entityName}_Dtl').datagrid('reload');	// reload the user data
					ids=[];
				} else {
					$.messager.show({
						title: 'Error',
						msg: result.data
					});
				}
			}, 'json');
		}
	}); */
	if(confirm("确定要删除选中的数据吗？")){
		$.post('${namespacePath}/removeRecord?ids='+ids, {
			status:status
		}, function(result) {
			if (result.status){
				$('#${entityName}_List').datagrid('reload');
				$('#${entityName}_Dtl').datagrid('reload');
				ids=[];
				$.messager.show({
					title: '操作结果',
					msg: '删除成功'
				});
			} else {
				$.messager.show({
					title: '操作结果',
					msg: result.data
				});
			}
		}, 'json');
	}
}
	
function removeRecordByOne(id,status) {
	if(confirm("确定要删除选中的数据吗？")){
		$.post('${namespacePath}/removeRecord?ids='+id, {
			status:status
		}, function(result) {
			if (result.status){
				$('#${entityName}_List').datagrid('reload');
				$('#${entityName}_Dtl').datagrid('reload');
				ids=[];
				$.messager.show({
					title: '操作结果',
					msg: '删除成功'
				});
			} else {
				$.messager.show({
					title: '操作结果',
					msg: '删除失败'
				});
			}
		}, 'json');
	}
}
</script>
<body class="easyui-layout">
    <div data-options="region:'west',split:true" style="width:350px;">
    <div style="text-align: left; width: 100%;float: left; ">
		<form action="" id="${entityName}_query">
			<table><tr><td>
				申请件编号： <input id="applicationCaseNo" name="applicationCaseNo">
				<a href="#" id="searchBt" class="easyui-linkbutton" iconCls="icon-search">查询</a>
				</td>
				</tr>
			</table>	
		</form>
	</div>
	<table id="${entityName}_List" class="easyui-datagrid" striped="true"  toolbar="#tb" rownumbers="true" pagination="true" pageSize="15"
		singleSelect="true" fitColumns="true" fit="true" style="overflow-x:hidden" pageList=[15,30,45,60] url="${ctx}/${entityName}/list">
		<thead>
			<tr>
				<th field="applicationCaseNo" width="30" align="center">申请件编号</th>
			</tr>
		</thead>
	</table>
	</div>
	<!-- 右侧列表 -->
	 <div data-options="region:'center'" style="padding:10px;">
		<div region="center" border="false" id='showResult'>
			<div style="text-align: left; width: 100%; float: left;">
				<span id="msg" style="padding: 5px 5px; margin-left: -10px; color: #2c759a; font-weight: bold;"></span>
			</div > 
			<div >
				<button class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRecord(1);">删除</button>	
			</div>
			<table id="${entityName}_Dtl"></table>
		</div>
	</div>
</body>

