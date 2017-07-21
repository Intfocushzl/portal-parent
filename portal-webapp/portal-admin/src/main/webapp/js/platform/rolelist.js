$(function () {
    $("#jqGrid").jqGrid({
        url: rcContextPath+'forfront/role/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: '角色ID', name: 'roleId', index: 'roleId', width: 50, key: true},
            {label: '角色名称', name: 'name', index: 'name', width: 80},
            {
                label: '状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                if (value === 0) {
                    return '<span class="label label-warning">冻结</span>';
                }
                if (value === 1) {
                    return '<span class="label label-success">激活</span>';
                }
            }
            },
            {label: '创建时间', name: 'createdAt', index: 'created_at', width: 80},
            {label: '更新时间', name: 'updatedAt', index: 'updated_at', width: 80},
            {
                label: '业态', name: 'type', index: 'type', width: 80, formatter: function (value) {
                if (value === 0) {
                    return '<span class="label label-success">会员店</span>';
                }
                if (value === 1) {
                    return '<span class="label label-success">Bravo</span>';
                }
                if (value === 2) {
                    return '<span class="label label-success">平台</span>';
                }
                if (value === 3) {
                    return '<span class="label label-success">其他</span>';
                }
            }
            },
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
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true
    }
};
var ztree;

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            name: null
        },
        showList: true,
        title: null,
        roleList: {},
        role: {}
        , nextRoleId: -1
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.role = {};
            vm.role.roleId=vm.nextRoleId;
            vm.getMenuTree(null);
        },
        update: function () {
            var roleId = getSelectedRow();
            if (roleId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";
            vm.getMenuTree(roleId);
        },
        del: function (event) {
            var roleIds = getSelectedRows();
            if (roleIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: rcContextPath+"/forfront/role/delete",
                    data: JSON.stringify(roleIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getRole: function (roleId) {
            $.get(rcContextPath+"/forfront/role/info/" + roleId, function (r) {
                vm.role = r.role;
                console.log(r.role);
                //勾选角色所拥有的菜单
                var menuIds = vm.role.menuIdList;
                for (var i = 0; i < menuIds.length; i++) {
                    var node = ztree.getNodeByParam("id", menuIds[i]);
                    if (node != null) {
                        ztree.checkNode(node, true, false);
                    }
                }
            });
        },
        getRoleList: function () {
            $.get(rcContextPath+"/forfront/role/select", function (r) {
                vm.roleList = r.list;
            });
        },
        saveOrUpdate: function (event) {
            // if (vm.role.id == null) {
            //     for (var i = 0; i < vm.roleList.length; i++) {
            //         if ($('#roleId').val() == (vm.roleList[i].roleId)) {
            //             alert("角色唯一编码已存在");
            //             return;
            //         }
            //     }
            // }
            //获取选择的菜单
            var nodes = ztree.getCheckedNodes(true);
            var menuIdList = new Array();
            for (var i = 0; i < nodes.length; i++) {
                menuIdList.push(nodes[i].id);
            }
            vm.role.menuIdList = menuIdList;

            var url = vm.role.id == null ? "/forfront/role/save" : "/forfront/role/update";
            $.ajax({
                type: "POST",
                url: rcContextPath+url,
                data: JSON.stringify(vm.role),
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
        getMenuTree: function (roleId) {
            //加载菜单树
            $.get(rcContextPath+"/forfront/menu/perms", function (r) {
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
                //展开所有节点
                ztree.expandAll(false);

                if (roleId != null) {
                    vm.getRole(roleId);
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
        },
        addRedis: function () {
            var roleIds = getSelectedRows();
            if (roleIds == null) {
                return;
            }
            confirm('确定要缓存选中角色的权限菜单？', function () {
                $.ajax({
                    type: "POST",
                    url: rcContextPath+"/forfront/role/addRedis",
                    data: JSON.stringify(roleIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getNextRoleId: function () {
            $.get(rcContextPath+"/forfront/role/getNextRoleId", function (r) {
                vm.nextRoleId = r.data.nextRoleId;
            });
        }
    }
});

vm.getRoleList();
vm.getNextRoleId();

$('#roleId').bind('input propertychange', function () {
    for (var i = 0; i < vm.roleList.length; i++) {
        if ($('#roleId').val() == (vm.roleList[i].roleId)) {
            $('#roleId').css("color", "red");
            break;
        }
        $('#roleId').css("color", "black");
    }
});