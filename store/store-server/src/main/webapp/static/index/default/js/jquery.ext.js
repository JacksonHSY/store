/**
 *
 * @requires jQuery
 *
 * 当页面加载完毕关闭加载进度
 * **/
$(window).load(function () {
    $("#loading").fadeOut();

    Array.prototype.unique = function () {
        var n = {}, r = []; //n为hash表，r为临时数组
        for (var i = 0; i < this.length; i++) //遍历当前数组
        {
            if (!n[this[i]]) //如果hash表中没有当前项
            {
                n[this[i]] = true; //存入hash表
                r.push(this[i]); //把当前数组的当前项push到临时数组里面
            }
        }
        return r;
    }
    //按enter键自动查询
    $(document).delegate(".ShareEnter","keydown",function (e){
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (eCode == 13){
            $(this).find(".query").trigger("onclick");

        }
    })

});

// 下拉框获取焦点按enter鍵查詢
$.extend($.fn.combobox.defaults.keyHandler, {enter:function(e){

    $(this).closest(".ShareEnter").find(".query").trigger("onclick");
}});

$.fn.combobox.defaults.onClick = function(){
    $(this).next("span").find("input").focus();
}
// 日期获取焦点按enter鍵查詢
$.extend($.fn.datebox.defaults.keyHandler, {enter:function(e){
    $(this).closest(".ShareEnter").find(".query").trigger("onclick");
}});

$.fn.datebox.defaults.onFocus = function(obj){
    $(obj).next("span").find("input").focus();
}

// 日历增加 情况按钮
var buttons = $.extend([], $.fn.datebox.defaults.buttons);
if (buttons.length < 3) {
    buttons.splice(buttons.length, 0, {
        text: '清空',
        handler: function (target) {
            $(target).datebox('clear');
            $(target).datebox("hidePanel"); // 点击清空按钮之后关闭日期选择面板
            $(target).datebox("options").onFocus(target);           //调用公共方法聚焦
        }
    });
    $.fn.datebox.defaults.buttons = buttons;
}

/**
 * 使panel和datagrid在加载时提示
 *
 * @requires jQuery,EasyUI
 *
 */
$.fn.panel.defaults.loadingMessage = '加载中....';
$.fn.datagrid.defaults.loadMsg = '加载中....';

/**
 * @requires jQuery,EasyUI
 *
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.fn.panel.defaults.onBeforeDestroy = function () {
    var frame = $('iframe', this);
    try {
        if (frame.length > 0) {
            for (var i = 0; i < frame.length; i++) {
                frame[i].src = '';
                frame[i].contentWindow.document.write('');
                frame[i].contentWindow.close();
            }
            frame.remove();
            if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
                try {
                    CollectGarbage();
                } catch (e) {
                }
            }
        }
    } catch (e) {
    }
};

/**
 *
 *
 * @requires jQuery,EasyUI
 *
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function (left, top) {
    var l = left;
    var t = top;
    if (l < 1) {
        l = 1;
    }
    if (t < 1) {
        t = 1;
    }
    var width = parseInt($(this).parent().css('width')) + 14;
    var height = parseInt($(this).parent().css('height')) + 14;
    var right = l + width;
    var buttom = t + height;
    var browserWidth = $(window).width();
    var browserHeight = $(window).height();
    if (right > browserWidth) {
        l = browserWidth - width;
    }
    if (buttom > browserHeight) {
        t = browserHeight - height;
    }
    $(this).parent().css({
        /* 修正面板位置 */
        left: l,
        top: t
    });
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 *
 *
 * @requires jQuery,EasyUI
 *
 * 通用错误提示
 *
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
var easyuiErrorFunction = function (XMLHttpRequest) {
    parent.$.messager.alert('错误', XMLHttpRequest.responseText);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

/**
 *
 *
 * @requires jQuery,EasyUI
 *
 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中
 */
var createGridHeaderContextMenu = function (e, field) {
    e.preventDefault();
    var grid = $(this);
    /* grid本身 */
    var headerContextMenu = this.headerContextMenu;
    /* grid上的列头菜单对象 */
    if (!headerContextMenu) {
        var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
        var fields = grid.datagrid('getColumnFields');
        for (var i = 0; i < fields.length; i++) {
            var fildOption = grid.datagrid('getColumnOption', fields[i]);
            if (!fildOption.hidden) {
                $('<div iconCls="tick" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
            } else {
                $('<div iconCls="bullet_blue" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
            }
        }
        headerContextMenu = this.headerContextMenu = tmenu.menu({
            onClick: function (item) {
                var field = $(item.target).attr('field');
                if (item.iconCls == 'tick') {
                    grid.datagrid('hideColumn', field);
                    $(this).menu('setIcon', {
                        target: item.target,
                        iconCls: 'bullet_blue'
                    });
                } else {
                    grid.datagrid('showColumn', field);
                    $(this).menu('setIcon', {
                        target: item.target,
                        iconCls: 'tick'
                    });
                }
            }
        });
    }
    headerContextMenu.menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;

/**
 * grid tooltip参数
 *
 *
 */
var gridTooltipOptions = {
    tooltip: function (jq, fields) {
        return jq.each(function () {
            var panel = $(this).datagrid('getPanel');
            if (fields && typeof fields == 'object' && fields.sort) {
                $.each(fields, function () {
                    var field = this;
                    bindEvent($('.datagrid-body td[field=' + field + '] .datagrid-cell', panel));
                });
            } else {
                bindEvent($(".datagrid-body .datagrid-cell", panel));
            }
        });

        function bindEvent(jqs) {
            jqs.mouseover(function () {
                var content = $(this).text();
                if (content.replace(/(^\s*)|(\s*$)/g, '').length > 5) {
                    $(this).tooltip({
                        content: content,
                        trackMouse: true,
                        position: 'bottom',
                        onHide: function () {
                            $(this).tooltip('destroy');
                        },
                        onUpdate: function (p) {
                            var tip = $(this).tooltip('tip');
                            if (parseInt(tip.css('width')) > 500) {
                                tip.css('width', 500);
                            }
                        }
                    }).tooltip('show');
                }
            });
        }
    }
};
/**
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 *
 * 简单实现，如需高级功能，可以自由修改
 *
 * 使用说明:
 *
 * 在easyui.min.js之后导入本js
 *
 * 代码案例:
 *
 * $("#dg").datagrid('tooltip'); 所有列
 *
 * $("#dg").datagrid('tooltip',['productid','listprice']); 指定列
 *
 *
 */
$.extend($.fn.datagrid.methods, gridTooltipOptions);

/**
 * Treegrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 *
 * 简单实现，如需高级功能，可以自由修改
 *
 * 使用说明:
 *
 * 在easyui.min.js之后导入本js
 *
 * 代码案例:
 *
 * $("#dg").treegrid('tooltip'); 所有列
 *
 * $("#dg").treegrid('tooltip',['productid','listprice']); 指定列
 *
 *
 */
$.extend($.fn.treegrid.methods, gridTooltipOptions);

/**
 *
 *
 * @requires jQuery,EasyUI
 *
 * 扩展validatebox，添加验证两次密码功能
 */
$.extend($.fn.validatebox.defaults.rules, {
    eqPwd: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '密码不一致！'
    },

    //移动手机号码验证
    mobile: {//value值为文本框中的值
    validator: function (value) {
    if(value[0]==value[1]){
        return false;
    }
    var reg = /^[1-9][0-9]{10}$/;
        return reg.test(value);
    },
    message: '输入手机号码格式不正确'
    },

    // 验证名称，英文字母和中文
    checkName: {
    validator: function (value) {
        return /^[\u4e00-\u9fa5a-zA-Z0-9]+$/i.test(value);
    },
    message: '输入不合法（英文字母或中文）'
    },

    // 验证编码，英文字母和数字
    checkCode: {
    validator: function (value) {
        return /^[a-zA-Z0-9]+$/i.test(value);
    },
    message: '输入不合法（英文字母或数字）'
    },
    //输入框不能输入特殊字符
    checkValue: {
    validator: function (value) {
        return /^[\u4e00-\u9fa5a-zA-Z0-9]+$/i.test(value);
    },
    message: '输入不合法（不能输入特殊字符）'
    }

});

//扩展tree，使其可以获取实心节点
$.extend($.fn.tree.methods, {
    getCheckedExt: function (jq) {// 获取checked节点(包括实心)
        var checked = $(jq).tree("getChecked");
        var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
        $.each(checkbox2, function () {
            var node = $.extend({}, $.data(this, "tree-node"), {
                target: this
            });
            checked.push(node);
        });
        return checked;
    },
    getSolidExt: function (jq) {// 获取实心节点
        var checked = [];
        var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
        $.each(checkbox2, function () {
            var node = $.extend({}, $.data(this, "tree-node"), {
                target: this
            });
            checked.push(node);
        });
        return checked;
    }
});

//扩展tree，使其支持平滑数据格式
$.fn.tree.defaults.loadFilter = function (data, parent) {
    var opt = $(this).data().tree.options;
    var idField, textField, parentField;
    if (opt.parentField) {
        idField = opt.idField || 'id';
        textField = opt.textField || 'text';
        parentField = opt.parentField;
        var i, l, treeData = [], tmpMap = [];
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idField]] = data[i];
            data[i]["id"] = data[i][idField];
        }
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idField] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textField];
                tmpMap[data[i][parentField]]['children'].push(data[i]);

                if (!opt.isOpen) {
                    tmpMap[data[i][parentField]]['state'] = 'closed';
                }
            } else {
                data[i]['text'] = data[i][textField];
                treeData.push(data[i]);
            }
        }
        return treeData;

    }

    return data;
};

// 扩展treegrid，使其支持平滑数据格式
$.fn.treegrid.defaults.loadFilter = function (data, parentId) {
    var opt = $(this).data().treegrid.options;
    var idField, textField, parentField, iconField;
    if (opt.parentField) {
        idField = opt.idField || 'id';
        textField = opt.textField || 'text';
        parentField = opt.parentField;
        iconField = opt.iconCls || 'iconCls';
        var i, l, treeData = [], tmpMap = [];
        for (i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idField]] = data[i];
            data[i]['iconCls'] = data[i][iconField];
        }
        for (i = 0, l = data.length; i < l; i++) {
            if (tmpMap[data[i][parentField]] && data[i][idField] != data[i][parentField]) {
                if (!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textField];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
                if(!tmpMap[data[i][parentField]][parentField]){
                    tmpMap[data[i][parentField]]['state'] = 'open';
                }else{
                    tmpMap[data[i][parentField]]['state'] = 'closed';
                }
            } else {
                data[i]['text'] = data[i][textField];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};

// 扩展combotree，使其支持平滑数据格式
$.fn.combotree.defaults.loadFilter = $.fn.tree.defaults.loadFilter;

/**
 *
 * @requires jQuery,EasyUI
 *
 * 创建一个模式化的dialog
 *
 * @returns $.modalDialog.handler 这个handler代表弹出的dialog句柄
 *
 * @returns $.modalDialog.xxx 这个xxx是可以自己定义名称，主要用在弹窗关闭时，刷新某些对象的操作，可以将xxx这个对象预定义好
 */
$.modalDialog = function (options) {
    if(options.new){
        options.modal = true;
        return $('<div/>').dialog(options);
    }else{
        if ($.modalDialog.handler == undefined) {// 避免重复弹出
            var opts = $.extend({
                title: '',
                width: 840,
                height: 680,
                modal: true,
                onClose: function () {
                    $.modalDialog.handler = undefined;
                    $(this).dialog('destroy');
                },
                onOpen: function () {
                }
            }, options);
            opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
            return $.modalDialog.handler = $('<div/>').dialog(opts);
        }
    }

};


$.cookie = function (key, value, options) {
    if (arguments.length > 1 && (value === null || typeof value !== "object")) {
        options = $.extend({}, options);
        if (value === null) {
            options.expires = -1;
        }
        if (typeof options.expires === 'number') {
            var days = options.expires, t = options.expires = new Date();
            t.setDate(t.getDate() + days);
        }
        return (document.cookie = [encodeURIComponent(key), '=', options.raw ? String(value) : encodeURIComponent(String(value)), options.expires ? '; expires=' + options.expires.toUTCString() : '', options.path ? '; path=' + options.path : '', options.domain ? '; domain=' + options.domain : '', options.secure ? '; secure' : ''].join(''));
    }
    options = value || {};
    var result, decode = options.raw ? function (s) {
            return s;
        } : decodeURIComponent;
    return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

/**
 *
 * @requires jQuery
 *
 * 将form表单元素的值序列化成对象
 *
 * @returns object
 */
$.serializeObject = function (form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};

/**
 *
 * 增加formatString功能
 *
 * 使用方法：$.formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 *
 * @returns 格式化后的字符串
 */
$.formatString = function (str) {
    for (var i = 0; i < arguments.length - 1; i++) {
        str = str.replace("{" + i + "}", arguments[i + 1]);
    }
    return str;
};

/**
 *
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 *
 * @returns list
 */
$.stringToList = function (value) {
    if (value != undefined && value != '') {
        var values = [];
        var t = value.split(',');
        for (var i = 0; i < t.length; i++) {
            values.push('' + t[i]);
            /* 避免他将ID当成数字 */
        }
        return values;
    } else {
        return [];
    }
};

/**
 *
 * @requires jQuery
 *
 * 去掉空格
 * **/
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, '');
};
String.prototype.ltrim = function () {
    return this.replace(/(^\s*)/g, '');
};
String.prototype.rtrim = function () {
    return this.replace(/(\s*$)/g, '');
};

/**
 *
 * @requires jQuery
 *
 * 页面加载加载进度条启用
 * **/
function progressLoad() {
    $("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({
        display: "block",
        width: "100%",
        height: $(window).height()
    }).appendTo("body");
    $("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({
        display: "block",
        left: ($(document.body).outerWidth(true) - 190) / 2,
        top: ($(window).height() - 45) / 2
    });
}

/**
 *
 * @requires jQuery
 *
 * 页面加载加载进度条关闭
 * **/
function progressClose() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}

/**
 *
 * @requires jQuery
 *
 * 防止退格键导致页面回退
 */
$(document).keydown(function (e) {
    var doPrevent;
    if (e.keyCode == 8) {
        var d = e.srcElement || e.target;
        if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA') {
            doPrevent = d.readOnly || d.disabled;
        } else {
            doPrevent = true;
        }
    } else {
        doPrevent = false;
    }
    if (doPrevent)
        e.preventDefault();
});

/**
 * 显示消息
 */
function showMsg(msg) {
    top.window.$.messager.show({
        title: '提示',
        msg: '<div class="light-info"><div class="light-tip icon-tip"></div><div>' + msg || "消息内容！" + '</div></div>',
        timeout: 3000,
        showType: 'slide'
    });
}

/*显示Toolltip*/
function injectTooltip(table,field,position) {
    var doms = table.datagrid('getPanel').find('div.datagrid-body td[field="'+field+'"] div');
    $.each(doms, function (index, dom) {
        $(this).tooltip({
            position: position,
            trackMouse: true,
            onShow: function (e) {
                if (dom.clientWidth == dom.scrollWidth) {
                    $(this).tooltip('arrow').css("display", "none");
                    $(this).tooltip('tip').css("display", "none");
                } else {
                    $(this).tooltip('arrow').css("display", "inherit");
                    $(this).tooltip('tip').css("display", "inherit");
                }
            },
            content: function () {
                return $(this).html();
            }
        })
    })
} /* //按enter键自动查询
$(body).delegate(".ShareEnter","keydown",(function (e){
    var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
    if (eCode == 13){
        console.log(111111);
        $(".query").trigger("onclick");

    }
})
)*/