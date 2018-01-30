Date.prototype.format = function(format){ 
    var o =  { 
    "M+" : this.getMonth()+1, //month 
    "d+" : this.getDate(), //day 
    "h+" : this.getHours(), //hour 
    "m+" : this.getMinutes(), //minute 
    "s+" : this.getSeconds(), //second 
    "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
    "S" : this.getMilliseconds() //millisecond 
    };
    if(/(y+)/.test(format)){ 
    	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    }
    for(var k in o)  { 
	    if(new RegExp("("+ k +")").test(format)){ 
	    	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
	    } 
    } 
    return format; 
};



var YMKJ= {
    formatText: function(value,row,index){
        if(value){
            value = value.replace(/</g, "&lt;").replace(/>/g, "&gt;");
        }else{
            value = "";
        }
        var span = '<span title='+value+' class="easyui-tooltip" data-options="position:\'right\', trackMouse:true">'+value+'</span>';
        return span;
    },
	// 格式化时间
	formatDateTime : function(val,row){
		var now = new Date(val);
    	return now.format("yyyy-MM-dd hh:mm:ss");
	},
	//状态格式化函数
    formatStatus: function(val, row) {
    	if (val != null) {
    		if(val==1){
    			return '禁用';
    		}else{
    			return '启用';
    		}
    	}else{
    		return '';
    	}
    },
	//格式化在职状态函数t:在职, f:不在职
	formatInActive:function(val, row){
	    if (val != null) {
            if(val=='t'){
                return '是';
            }else{
                return '否';
            }
        }else{
            return '';
        }
	},
	//格式化员工类型函数(0: 实习, 1: 正式)
	formatUserType:function(val, row){
	    if (val != null) {
            if(val==1){
                return '正式员工';
            }else{
                return '实习生';
            }
        }else{
            return '';
        }
	},
	//格式化角色类型0:自定义角色, 1:公共角色
	formatRoleType:function(val,row){
		if (val != null) {
            if(val==1){
                return '公共角色';
            }else{
                return '自定义角色';
            }
        }else{
            return '';
        }
	},
    //格式化菜单类型0:自定义角色, 1:公共角色
    formatResType:function(val,row){
        if (val != null) {
            if(val=='menu'){
                return '菜单';
            }else if(val == 'tab'){
                return '页签';
            }else if(val == 'button'){
                return '按钮';
            }else if(val == 'field'){
                return '字段';
            }else if(val == 'page'){
                return '页面';
            }
        }else{
            return '';
        }
    }
}