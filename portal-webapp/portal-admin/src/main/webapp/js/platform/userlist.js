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
                    return '平台';
                }
                if (value === 1) {
                    return 'Bravo';
                }
                if (value === 2) {
                    return '会员店';
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
                if (value === -1) {
                    return '<span class="label label-warning">冻结</span>';
                }
                if (value === 1) {
                    return '<span class="label label-success">激活</span>';
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
});
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        roleList: {},
        user: {
            roleIdList: []
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
            vm.showList = false;
            vm.title = "新增";
            var o = new Object();
            o.type = (vm.user.type == null ? 1 : vm.user.type);
            this.getRoleList(o);
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id);
        },
        saveOrUpdate: function (event) {
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
        getInfo: function (id) {
            $.get(baseUrl + "/forfront/user/info/" + id, function (r) {
                vm.user = r.user;
                var o = new Object();
                o.type = vm.user.type;
                vm.getRoleList(o);
            });
        },
        getRoleList: function (params) {
            $.get("../forfront/role/select", params, function (r) {
                vm.roleList = r.list;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        onTypeChange: function () {
            var o = new Object();
            o.type = $("select[name='type']").val();
            vm.getRoleList(o);
        },
        getLargeArea: function () {
            // $.get("/yhportal/api/portal/custom?yongHuiReportCustomCode=REP_000030", function (data) {
            //     console.log(data);
            // });
        }
    }
});
vm.getLargeArea();