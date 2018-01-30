function ccBoxMes(objname,num,str){
	var obj = document.getElementsByName(objname);
	var len = 0;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked==true){len=len+1;}
	}
	switch(num){
		case 0:if(len<=1){return true;}else{
		$.messager.alert("提示",'至多选择一条记录！', 'warning');
		return false;}
		case 'n':if(len>=1){return true;}else{
			$.messager.alert("提示",'至少选择一条记录！', 'warning');
		return false;}
		case 1:if(len==1){return true;}else{
			$.messager.alert("提示",'选择一条记录！', 'warning');return false;}
	}
}

function commonOp(url){
	jQuery("form:first").attr("action",url);
	jQuery("form:first").submit();
}

function commonClear(){
	jQuery("form:first")[0].reset();
	
}

function selectOper(checkIdName,oper,allOrNo){
	if(oper=='all'){
		selectAll(checkIdName);
	}
	if(oper=='no'){
		selectNo(checkIdName);
	}
	if(oper=='reverse'){
		selectReverse(checkIdName);
	}
	if(oper=='an'){
		if(allOrNo.checked){
			
			selectAll(checkIdName);
		}else{
			selectNo(checkIdName);
		}
	}
		
}
function selectAll(checkIdName){
	jQuery("input[name="+checkIdName+"]").each(function() {  
		jQuery(this).attr("checked", true);  
	 });  
}
function selectNo(checkIdName){
	jQuery("input[name="+checkIdName+"]").each(function() {  
		jQuery(this).attr("checked", false);  
	 });  
}
function selectReverse(checkIdName){
	jQuery("input[name="+checkIdName+"]").each(function() {  
		if(jQuery(this).attr("checked"))  
		{  
			jQuery(this).attr("checked", false);  
		 }  
		 else  
		 {  
			 jQuery(this).attr("checked", true);  
		 }  
		 });  
}

function createFrame(url){ 
	var s = '<iframe scrolling="auto" frameborder="0" name="'+url+'" src="'+url+'" style="width:100%;height:100%;padding:0px"></iframe>';
	return s;
}

var parentTabs='tabs'; //主页tabs列表id
function createTabsInframePage(text,url){ 
	 
	 if(!parent.$('#'+parentTabs).tabs('exists',text)){
		 parent.$('#'+parentTabs).tabs('add',{
				title:text, 
				closable:true,
				loadingMessage:'数据装载中......'
		});
		 var currTab=parent.$('#tabs').tabs('getTab', text);
	}else{
		parent.$('#'+parentTabs).tabs('select',text);
	}
	 var currTab =  parent.$('#'+parentTabs).tabs('getSelected'); //获得当前tab
	 parent.$('#'+parentTabs).tabs('update', { tab: currTab, options: { content: createFrame(url)} });
	 tabInframeClose();
	}

function tabInframeClose(){
	/*双击关闭TAB选项卡*/
	parent.$("."+parentTabs+"-inner").dblclick(function(){
		var subtitle = $(this).children("."+parentTabs+"-closable").text();
		parent.$('#'+parentTabs).tabs('close',subtitle);
	})
	/*单击关闭选项卡*/
	parent.$("."+parentTabs+"-inner").next().click(function(){
		var subtitle = $(this).prev().children("."+parentTabs+"-closable").text();
		parent.$('#'+parentTabs).tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	parent.$("."+parentTabs+"-inner").bind('contextmenu',function(e){
		parent.$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
		var subtitle =$(this).children("."+parentTabs+"-closable").text();
		parent.$('#mm').data("currtab",subtitle);
		//$('#'+tabsId).tabs('select',subtitle);
		return false;
	});
}



