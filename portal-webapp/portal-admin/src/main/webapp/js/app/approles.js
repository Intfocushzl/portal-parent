$(function () {
    $("#jqGrid").jqGrid({
        url: '../app/roles/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: '角色id', name: 'id', index: 'id', width: 50},
            {label: 'PC角色id', name: 'roleId', index: 'roleId', width: 50, key: true},
            {label: '角色名', name: 'roleName', index: 'role_name', width: 80},
            {label: '备注说明', name: 'memo', index: 'memo', width: 80}
        ],
        viewrecords: true,
        height: 385,            // 表格高度
        rowNum: 50,             // 一页显示的行记录数
        rowList: [50, 100],     // 翻页控制条中 每页显示记录数可选集合
        rownumbers: true,       // 是否显示行号，默认值是false，不显示
        rownumWidth: 25,
        autowidth: true,
        autoScroll: true,
        shrinkToFit: true,

        multiselect: true,
        pager: "#jqGridPager",          // 翻页DOM
        jsonReader: {                   // 重写后台返回数据的属性
            root: "page.list",          // 将rows修改为page.list
            page: "page.currPage",      // 将page修改为page.currPage
            total: "page.totalPage",    // 将total修改为page.totalPage
            records: "page.totalCount"  // 将records修改为page.totalCount
        },
        prmNames: {              // 改写请求参数属性
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            //设置高度
            $("#jqGrid").jqGrid('setGridHeight', getWinH());
        }
    });
});

var ztree1;
var ztree2;
var ztree3;

var setting = {
    view: {
        selectedMulti: false
    },
    check: {
        enable: true
    },
    data: {
        standardData: {
            enable: true
        }
    },
    callback: {
        onCheck: onCheck
    }
};

var clearFlag = false;
function onCheck(e, treeId, treeNode) {
    if (clearFlag) {
        clearCheckedOldNodes();
    }
}
function clearCheckedOldNodes() {
    zTree1 = $.fn.zTree.getZTreeObj("menuTree"),
        nodes = zTree.getChangeCheckedNodes();
    for (var i = 0, l = nodes.length; i < l; i++) {
        nodes[i].checkedOld = nodes[i].checked;
    }
}

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        appRoles: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    id: vm.appRoles.id,
                    roleName: vm.appRoles.roleName
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.appRoles = {};
            vm.getMenuTree(-1);
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            // vm.getMenuTree(id);
            vm.getInfo(id);
        },
        saveOrUpdate: function (event) {
            //获取选择的菜单
            var nodes1 = ztree1.getCheckedNodes(true);
            var menuList = new Array();
            for (var i = 0; i < nodes1.length; i++) {
                var o = new Object();
                o.menuId = nodes1[i].id;
                o.title = nodes1[i].name;
                o.type = 1;
                menuList.push(o);
            }
            var nodes2 = ztree2.getCheckedNodes(true);
            for (var i = 0; i < nodes2.length; i++) {
                var o = new Object();
                o.menuId = nodes2[i].id;
                o.title = nodes2[i].name;
                o.type = 2;
                menuList.push(o);
            }
            var nodes3 = ztree3.getCheckedNodes(true);
            for (var i = 0; i < nodes3.length; i++) {
                var o = new Object();
                o.menuId = nodes3[i].id;
                o.title = nodes3[i].name;
                o.type = 3;
                menuList.push(o);
            }
            console.log(menuList);
            vm.appRoles.menuList = menuList;
            console.log(JSON.stringify(vm.appRoles));
            var url = vm.appRoles.id == null ? "../app/roles/save" : "../app/roles/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.appRoles),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../app/roles/delete",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (roleId) {
            var rowData = $("#jqGrid").jqGrid("getRowData", roleId);
            if (rowData == null) {
                return;
            }
            var id = rowData.id;
            $.get("../app/roles/info/" + roleId, function (r) {
                console.log(r);
                vm.appRoles = r.appRoles;
                vm.getMenuTree(id);
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        getMenuTree: function (roleId) {
            //加载菜单树
            $.get("../app/roles/selectKpisMenu?roleId=" + roleId, function (r) {
                ztree1 = $.fn.zTree.init($("#menuTree1"), setting, r.list);
                //展开所有节点
                ztree1.expandAll(false);

                //勾选角色所拥有的菜单
                var menuIds = r.menuKpiList;
                for (var i = 0; i < menuIds.length; i++) {
                    var node = ztree1.getNodeByParam("id", menuIds[i].menuId);
                    ztree1.checkNode(node, true, false);
                }
            });
            //加载菜单树
            $.get("../app/roles/selectAnalysesMenu?roleId=" + roleId, function (r) {
                ztree2 = $.fn.zTree.init($("#menuTree2"), setting, r.list);
                //展开所有节点
                ztree2.expandAll(false);

                //勾选角色所拥有的菜单
                var menuIds = r.menuAnalysesList;
                for (var i = 0; i < menuIds.length; i++) {
                    var node = ztree2.getNodeByParam("id", menuIds[i].menuId);
                    ztree2.checkNode(node, true, false);
                }
            });
            //加载菜单树
            $.get("../app/roles/selectAppsMenu?roleId=" + roleId, function (r) {
                ztree3 = $.fn.zTree.init($("#menuTree3"), setting, r.list);
                //展开所有节点
                ztree3.expandAll(false);

                //勾选角色所拥有的菜单
                var menuIds = r.menuAppList;
                for (var i = 0; i < menuIds.length; i++) {
                    var node = ztree3.getNodeByParam("id", menuIds[i].menuId);
                    ztree3.checkNode(node, true, false);
                }
            });
        },
    }
});