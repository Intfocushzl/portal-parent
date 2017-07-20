var baseUrl = "..";
$(function () {
    $("#jqGrid").jqGrid({
        url: baseUrl + '/forfront/user/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: '账号', name: 'account', index: 'account', width: 80},
            {label: '用户名', name: 'name', index: 'name', width: 80},
            {label: '角色名', name: 'roleName', index: 'roleName', width: 80},
            {label: '备注', name: 'remark', index: 'remark', width: 80},
            {label: '用户ID', name: 'id', index: 'id', width: 50, key: true},
            {
                label: '业态', name: 'type', index: 'type', width: 80, formatter: function (value) {
                if (value === 0) {
                    return '会员店';
                }
                if (value === 1) {
                    return 'Bravo';
                }
                if (value === 2) {
                    return '平台';
                }
                if (value === 3) {
                    return '其他';
                }
            }
            },
            {label: '大区', name: 'largeArea', index: 'largeArea', width: 80},
            {label: '新大区', name: 'areaMans', index: 'areaMans', width: 80},
            {label: '省份', name: 'province', index: 'province', width: 80},
            {label: '城市', name: 'city', index: 'city', width: 80},
            {label: '员工号', name: 'jobNumber', index: 'jobNumber', width: 80},
            {
                label: '状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                if (value === -5) {
                    return '<span class="label label-danger">待处理</span>';
                }
                if (value === -1) {
                    return '<span class="label label-warning">冻结</span>';
                }
                if (value === 0) {
                    return '<span class="label label-default">新注册</span>';
                }
                if (value === 1) {
                    return '<span class="label label-success">正常</span>';
                }
            }
            },
            {label: '门店', name: 'storeNumber', index: 'storeNumber', width: 80},
            {label: '商行', name: 'firm', index: 'firm', width: 80},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 80}
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

    $("#jqNewGrid").jqGrid({
        url: baseUrl + '/forfront/user/newUserList',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: '账号', name: 'account', index: 'account', width: 100},
            {label: '用户名', name: 'name', index: 'name', width: 100},
            {label: '角色名', name: 'roleName', index: 'roleName', width: 100},
            {label: '备注', name: 'remark', index: 'remark', width: 100},
            {label: '用户ID', name: 'id', index: 'id', width: 70, key: true},
            {label: '员工号', name: 'jobNumber', index: 'jobNumber', width: 100},
            {
                label: '业态', name: 'type', index: 'type', width: 100, formatter: function (value) {
                if (value === 0) {
                    return '会员店';
                }
                if (value === 1) {
                    return 'Bravo';
                }
                if (value === 2) {
                    return '平台';
                }
                if (value === 3) {
                    return '其他';
                }
            }
            }, {
                label: '状态', name: 'status', index: 'status', width: 100, formatter: function (value) {
                    if (value === -5) {
                        return '<span class="label label-danger">待处理</span>';
                    }
                    if (value === -1) {
                        return '<span class="label label-warning">冻结</span>';
                    }
                    if (value === 0) {
                        return '<span class="label label-default">新注册</span>';
                    }
                    if (value === 1) {
                        return '<span class="label label-success">正常</span>';
                    }
                }
            },
            {
                label: '操作',
                name: 'operation',
                index: 'operation',
                with: 100,
                formatter: function (value, options, row) {
                    return "<span class='label label-success' onclick='vm.userInfo(" + row.id + ")'>查看</span>&nbsp;&nbsp;<span class='label label-success'onclick='vm.pass(" + row.id + ")'>同意</span>" +
                        "&nbsp;&nbsp;<span class='label label-success' onclick='vm.refuse(" + row.id + ")'>拒绝</span>";
                }
            },
            {label: '大区', name: 'largeArea', index: 'largeArea', width: 100},
            {label: '新大区', name: 'areaMans', index: 'areaMans', width: 100},
            {label: '省份', name: 'province', index: 'province', width: 100},
            {label: '城市', name: 'city', index: 'city', width: 100},
            {label: '门店', name: 'storeNumber', index: 'storeNumber', width: 100},
            {label: '商行', name: 'firm', index: 'firm', width: 100},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 100}
        ],

        viewrecords: true,
        height: 400,            // 表格高度
        rowNum: 10,             // 一页显示的行记录数
        rowList: [10, 20, 50, 100],     // 翻页控制条中 每页显示记录数可选集合
        rownumbers: true,       // 是否显示行号，默认值是false，不显示
        autowidth: true,
        // autoScroll: true,
        shrinkToFit: true,

        multiselect: true,
        pager: "#jqNewGridPager",          // 翻页DOM
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
            $("#jqNewGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            // //设置高度
            // $("#jqNewGrid").jqGrid('setGridHeight', getWinH());
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: 1,
        title: null,
        user: {
            roleIdList: [],
        }
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    jobNumber: vm.user.jobNumber,
                    name: vm.user.name
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = 2;
            vm.title = "新增";
            var o = new Object();
            o.type = (vm.user.type == null ? 1 : vm.user.type);
            $("#type").selectpicker("val",o.type);
            vm.getRoleList(o);
            vm.getGroupList(null);
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = 2;
            vm.title = "修改";

            vm.getInfo(id);
        },
        saveOrUpdate: function (event) {
            vm.user.roleId = $("select#roleList option:selected").val();
            vm.user.areaMans = $("select#areaMansList option:selected").val();

            if (vm.user.largeArea == "全部") {
                vm.user.largeArea = "ALL";
            }
            var url = vm.user.id == null ? baseUrl + "/forfront/user/save" : baseUrl + "/forfront/user/update";
            console.log(vm.user);
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.user),
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
                    url: baseUrl + "/forfront/user/delete",
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
        audit: function () {
            vm.showList = 3;
            vm.title = "审核";
        }
        ,
        getInfo: function (id) {
            $.get(baseUrl + "/forfront/user/info/" + id, function (r) {
                vm.user = r.user;
                vm.user.roleId = r.user.roleId;
                if (vm.user.largeArea == "ALL") {
                    vm.user.areaMans = "ALL";
                    vm.user.largeArea = "全部";
                }
                var o = new Object();
                o.type = vm.user.type;
                $("#type").selectpicker("val",o.type);
                vm.getRoleList(o);
                vm.getGroupList(vm.user.jobNumber);
            });
        },
        getRoleList: function (params) {
            var roleId = vm.user.roleId;
            $("#roleList").empty();
            $.get("../forfront/role/select", params, function (r) {
                var roleList = r.list;
                if (roleId == "undefined" && roleList.length > 0) {
                    vm.user.roleId = roleList[0].value;
                }
                for (var i = 0; i < roleList.length; i++) {
                    $("#roleList").append("<option value='" + roleList[i].roleId + "'>" + roleList[i].name + "</option>");
                }
                // refresh刷新和render渲染操作，必不可少  +r.data[i].id+"<===>"
                $('#roleList').selectpicker('refresh');
                $('#roleList').selectpicker('render');

                $("#roleList").selectpicker('val', roleId);
            });
        },
        reload: function (event) {
            vm.showList = 1;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        newReload: function (event) {
            vm.showList = 1;
            var page = $("#jqNewGrid").jqGrid('getGridParam', 'page');
            $("#jqNewGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        onTypeChange: function () {
            var o = new Object();
            o.type = $("select[name='type']").val();
            vm.getRoleList(o);
        },
        getAreaMans: function () {
            $.get("../common/getDataList?reportLabel=2", function (r) {
                var areaMansList = r.data.list;
                if (areaMansList.length > 0) {
                    vm.user.areaMans = "ALL";
                    vm.user.largeArea = "全部";
                }
                $("#areaMansList").empty();
                $("#areaMansList").append("<option value='ALL'>全部</option>");
                for (var i = 0; i < areaMansList.length; i++) {
                    $("#areaMansList").append("<option value='" + areaMansList[i].value + "'>" + areaMansList[i].label + "</option>");
                }
                // refresh刷新和render渲染操作，必不可少  +r.data[i].id+"<===>"
                $('#areaMansList').selectpicker('refresh');
                $('#areaMansList').selectpicker('render');

                $("#areaMansList").selectpicker('val', vm.user.areaMans);
            });
        },
        getGroupList: function (userNum) {
            var areaMans=$("#areaMansList").selectpicker('val');
            var url = "../app/groups/select?areaMans="+areaMans;
            $.get(url, function (r) {
                var groupList = r.groupList;
                $("#groupList").empty();
                for (var i = 0; i < groupList.length; i++) {
                    $("#groupList").append("<option value='" + groupList[i].id + "'>" + groupList[i].groupName + "</option>");
                }
                // refresh刷新和render渲染操作，必不可少  +r.data[i].id+"<===>"
                $('#groupList').selectpicker('refresh');
                $('#groupList').selectpicker('render');

                if (userNum != null) {
                    $.get("../app/groups/selectGroups?userNum=" + userNum, function (r) {
                        console.log(r);
                        var list = r.groupList;
                        if (list.length > 0) {
                            vm.user.groupId = list[0].id;
                            $("#groupList").selectpicker('val', vm.user.groupId);
                        }
                    });
                } else {
                    if (groupList.length > 0) {
                        vm.user.groupId = groupList[0].id;
                        $("#groupList").selectpicker('val', vm.user.groupId);
                    }
                }

            });
        },
        newUserReload: function (event) {
            vm.showList = 3;
            var page = $("#jqNewGrid").jqGrid('getGridParam', 'page');
            $("#jqNewGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        userInfo: function (id) {
            if (id == null) {
                return;
            }
            vm.showList = 4;
            vm.title = "审核";

            vm.getInfo(id);
        },
        pass: function (id) {
            if (id == null) {
                return;
            }
            $.get("../forfront/user/info/" + id, function (r) {
                vm.user = r.user;
                var url = "../forfront/user/newUser/pass";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.user),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.newReload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        refuse: function (id) {
            if (id == null) {
                return;
            }
            var url = "../forfront/user/newUser/refuse";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(id),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.newReload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        }
    }
});

$(window).on('load', function () {
    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });
});
vm.getAreaMans();
$("select#areaMansList").change(function () {
    var pid = $("select#areaMansList option:selected").val();
    if (pid=="ALL"){
        $("#largeArea").val("ALL");
    }else {
        $.get("../common/getDataList?reportLabel=5&pid=" + pid, function (r) {
            var largeAreaList = r.data.list;
            if (largeAreaList.length > 0) {
                vm.user.largeArea = largeAreaList[0].value;
                $("#largeArea").val(largeAreaList[0].value);
            }
        });
    }
    vm.getGroupList(vm.user.jobNumber);
});

$("select#groupList").change(function () {
    vm.user.groupId= $("#groupList").selectpicker('val');
});