 var myview = $.extend({},$.fn.datagrid.defaults.view,{
	onAfterRender:function(target){
	    $.fn.datagrid.defaults.view.onAfterRender.call(this,target);
	    var opts = $(target).datagrid('options');
	    var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
	    vc.children('div.datagrid-empty').remove();
	    if (!$(target).datagrid('getRows').length){
	        var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
	        d.css({
	            position:'absolute',
	            left:0,
	            top:50,
	            width:'100%',
	            textAlign:'center'
	        });
	    }
	}
});
function formToObj(form) {
	var obj = {};
	$.each(form.serializeArray(), function(index) {
		if (obj[this['name']]) {
			obj[this['name']] = obj[this['name']] + "," + this['value'];
		} else {
			obj[this['name']] = this['value'];
		}
	});
	return obj;
}
		
function createFrame(url){ 
	var s = '<iframe scrolling="auto" frameborder="0" name="'+url+'" src="'+url+'" style="width:100%;height:100%;padding:0px"></iframe>';
	return s;
}
		
var parentTabs='tabs'; //主页tabs列表id
 
 //tab页内创建新的tab页的方法      Sam.J  2014.8.13
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
 
//tab页内创建新的tab页的绑定方法     Sam.J  2014.8.13
function tabInframeClose(){
	/*双击关闭TAB选项卡*/
	parent.$("."+parentTabs+"-inner").dblclick(function(){
		var subtitle = $(this).children("."+parentTabs+"-closable").text();
		parent.$('#'+parentTabs).tabs('close',subtitle);
	});
	/*单击关闭选项卡*/
	parent.$("."+parentTabs+"-inner").next().click(function(){
		var subtitle = $(this).prev().children("."+parentTabs+"-closable").text();
		parent.$('#'+parentTabs).tabs('close',subtitle);
	});
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

