var index_tabs;
var layout_west_tree;
var index_tabs_menu;

$(function () {
    $('#index_layout').layout({fit: true});
    // 初始化 tab
    index_tabs = $('#index_tabs').tabs({
        fit: true,
        border: false,
        onContextMenu: function (e, title) {
            e.preventDefault();
            index_tabs_menu.menu('show', {
                left: e.pageX,
                top: e.pageY
            }).data('tabTitle', title);
        }
        /*tools: [{
            iconCls: 'fa fa-home',
            handler: function () {
                index_tabs.tabs('select', 0);
            }
        }, {
            iconCls: 'fa fa-refresh',
            handler: function () {
                refreshTab();
            }
        }, {
            iconCls: 'fa fa-close',
            handler: function () {
                var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                var tab = index_tabs.tabs('getTab', index);
                if (tab.panel('options').closable) {
                    index_tabs.tabs('close', index);
                }
            }
        }]*/
    });
    index_tabs.tabs("resize");

    index_tabs_menu = $('#tabs_menu').menu({
        onClick: function (item) {
            var curTabTitle = $(this).data('tabTitle');
            var type = $(item.target).attr('type');
            if (type === 'refresh') {
                refreshTab();
                return;
            }
            if (type === 'close') {
                var t = index_tabs.tabs('getTab', curTabTitle);
                if (t.panel('options').closable) {
                    index_tabs.tabs('close', curTabTitle);
                }
                return;
            }
            var allTabs = index_tabs.tabs('tabs');
            var closeTabsTitle = [];
            $.each(allTabs, function () {
                var opt = $(this).panel('options');
                if (opt.closable && opt.title != curTabTitle
                    && type === 'closeOther') {
                    closeTabsTitle.push(opt.title);
                } else if (opt.closable && type === 'closeAll') {
                    closeTabsTitle.push(opt.title);
                }
            });
            for (var i = 0; i < closeTabsTitle.length; i++) {
                index_tabs.tabs('close', closeTabsTitle[i]);
            }
        }
    });

    $.post(ctxPath + "/index/getMenus", {}, function (v) {
    	var data = v.data;
        var idField, textField, parentField;
        idField = 'code';
        textField = 'text';
        parentField = 'parentCode';
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
            } else {
                data[i]['text'] = data[i][textField];
                treeData.push(data[i]);
            }
        }

        $.each(treeData, function (i, item) {

            var content = '';
            if (item.children && item.children.length) {
                var ul = $('<ul></ul>');
                $.each(item.children, function (i, temp) {
                    ul.append('<li url="' + temp.url + '" title="' + temp.name + '" data-open="' + temp.openMode + '" >' + temp.name + '</li>');
                });
                content = ul.get(0);
            }
            $('.easyui-accordion').accordion('add', {
                title: item.name,
                content: content,
                selected: false
            });
        });

        $('.easyui-accordion').delegate('li', 'click', function () {
            var url = $(this).attr("url");
            var title = $(this).attr("title");
            var iconCls = $(this).attr("iconCls");
            var openMode = $(this).attr("data-open");
            var opts = {
                title: title,
                border: false,
                closable: true,
                cache: true,
                fit: true,
                iconCls: iconCls
            };
            if (url && url.indexOf("http") == -1) {
                url = ctxPath + url;
            }
            if (openMode == 'iframe') {
                opts.content = '<iframe src="' + url + '?'+ Math.random() +'" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
                addTab(opts);
            } else if (openMode == 'modal') {
                $("body").append('<div id="__tmpModalDiv" class="easyui-window" title="站内消息" data-options="modal:true"/>');
                $('#__tmpModalDiv').window({
                    href:url,
                    width:'50%',
                    height:600,
                    onClose: function () {
                        $(this).window('destroy');
                        $(this).remove();
                    },
                });
            } else if (url) {
                opts.href = url;
                addTab(opts);
            }

            $('.easyui-accordion').find("li").removeClass("active");
            $(this).addClass("active");

        })

    });

    layout_west_tree = $('#layout_west_tree').tree({
        url: ctxPath + '/index/getMenus',
        idField: 'code',
        parentField: 'parentCode',
        lines: true,
        onClick: function (node) {

        }
    });
});

function addTab(opts) {
    var t = $('#index_tabs');
    if (t.tabs('exists', opts.title)) {
        t.tabs('select', opts.title);
    } else {
        t.tabs('add', opts);
        t.tabs('resize');
    }
}

function refreshTab() {
    var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
    var tab = index_tabs.tabs('getTab', index);
    var options = tab.panel('options');
    if (options.content) {
        index_tabs.tabs('update', {
            tab: tab,
            options: {
                content: options.content
            }
        });
    } else {
        tab.panel('refresh', options.href);
    }
}