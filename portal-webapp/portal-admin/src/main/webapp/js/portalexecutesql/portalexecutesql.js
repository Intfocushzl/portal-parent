$(function () {
    $("#jqGrid").jqGrid({
        url: '../portalexecutesql/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, hidden: true},
            {label: '唯一编码', name: 'sqlcode', index: 'sqlcode', width: 80, key: true},
            {label: '标题简介', name: 'title', index: 'title', width: 80},
            /* {label: '执行语句', name: 'executeSql', index: 'execute_sql', width: 80 }, */
            {label: '数据源唯一编码', name: 'data_source_code', index: 'data_source_code', width: 80},
            /* {label: '执行参数', name: 'parameter', index: 'parameter', width: 80 },*/
            {label: '创建人', name: 'username', index: 'username', width: 80},
            {label: '创建时间', name: 'create_time', index: 'create_time', width: 80},
        ],
        viewrecords: true,     // 是否显示行号，默认值是false，不显示
        height: 385,            // 表格高度
        rowNum: 50,             // 一页显示的行记录数
        rowList: [50, 100],     // 翻页控制条中 每页显示记录数可选集合
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
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
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
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
        portalExecuteSql: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    sqlcode: vm.portalExecuteSql.sqlcode,
                    title: vm.portalExecuteSql.title
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.portalExecuteSql = {};
            vm.getDataSourceList();
            $("input[name='sqlcode']").removeAttr("readonly");
            vm.getNewMaxCode();
        },
        update: function (event) {
            var sqlcode = getSelectedRow();
            if (sqlcode == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getDataSourceList();
            vm.getInfo(sqlcode);
            $("input[name='sqlcode']").attr("readonly", "readonly");
        },
        saveOrUpdate: function (event) {
            var code = vm.portalExecuteSql.sqlcode;
            var id = vm.portalExecuteSql.id;
            var url = vm.portalExecuteSql.id == null ? "../portalexecutesql/save" : "../portalexecutesql/update";
            if (vm.checkForm()) {
                if (id == null) {
                    $.get("../portalexecutesql/info/" + code, function (r) {
                        console.log(r);
                        if (r.portalExecuteSql != null) {
                            alert("唯一编码已存在，请重新输入");
                        } else {
                            vm.addAndUpdate(url);
                        }
                    })
                } else {
                    vm.addAndUpdate(url);
                }
            }
        },
        addAndUpdate: function (url) {
            vm.portalExecuteSql.dataSourceCode = $("#dataList").val();
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.portalExecuteSql),
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
            var sqlcodes = getSelectedRows();
            if (sqlcodes == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../portalexecutesql/delete",
                    data: JSON.stringify(sqlcodes),
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
        getInfo: function (sqlcode) {
            $.get("../portalexecutesql/info/" + sqlcode, function (r) {
                vm.portalExecuteSql = r.portalExecuteSql;
                vm.portalExecuteSql.sqlcodeOld = vm.portalExecuteSql.sqlcode;
            });
        },
        getDataSourceList: function () {
            $("#dataList").empty();
            $.get("../portaldatasource/dataSourceList/", function (r) {
                for (var i = 0; i < r.dataSourceList.length; i++) {
                    $("#dataList").append("<option value='" + r.dataSourceList[i].code + "'>" + r.dataSourceList[i].code + "<===>" + r.dataSourceList[i].title + "</option>");
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        addRedis: function (event) {
            var codes = getSelectedRows();
            if (codes == null) {
                return;
            }
            $.ajax({
                type: "POST",
                url: "../portalexecutesql/addRedis",
                data: JSON.stringify(codes),
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
        },
        getNewMaxCode: function () {
            $.get("../portalexecutesql/getNewMaxCode/", function (r) {
                console.info(r.newMaxCode);
                $("#sqlcode").val(r.newMaxCode);
                vm.portalExecuteSql.sqlcode = r.newMaxCode;
            });
        },
        checkForm: function () {
            if (getStringValue($("#sqlcode").val) != "") {
                if ($("#sqlcode").val().substr(0, 4) != "SQL_" || $("#sqlcode").val().length != 10) {
                    alert("编码错误，正确格式如：SQL_000001");
                    return false;
                }
            }
            return true;
        }
    }
});