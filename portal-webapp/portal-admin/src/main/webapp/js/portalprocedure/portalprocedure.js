$(function () {
    $("#jqGrid").jqGrid({
        url: '../portalprocedure/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, hidden: true},
            {label: '编码', name: 'procode', index: 'procode', width: 80, key: true},
            {label: '标题', name: 'title', index: 'title', width: 80},
            {label: '数据源编码', name: 'data_source_code', index: 'data_source_code', width: 80},
            {label: '存储过程名', name: 'proname', index: 'proname', width: 80},
            {label: '数据库', name: 'prodb', index: 'prodb', width: 80},
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
        portalProcedure: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    keyword: $("#keyword").val()
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.portalProcedure = {};
            vm.getDataSourceList();
            $("input[name='code']").removeAttr("readonly");
            vm.getNewMaxCode();
        },
        update: function (event) {
            var procode = getSelectedRow();
            if (procode == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getDataSourceList();
            vm.getInfo(procode);
            $("input[name='code']").attr("readonly", "readonly");
        },
        saveOrUpdate: function (event) {
            var code = vm.portalProcedure.procode;
            var id = vm.portalProcedure.id;
            var url = vm.portalProcedure.id == null ? "../portalprocedure/save" : "../portalprocedure/update";
            if (vm.checkForm()) {
                if (id == null) {
                    $.get("../portalprocedure/info/" + code, function (r) {
                        console.log(r);
                        if (r.portalProcedure != null) {
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
            var url = vm.portalProcedure.id == null ? "../portalprocedure/save" : "../portalprocedure/update";
            vm.portalProcedure.dataSourceCode = $("#dataList").val();
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.portalProcedure),
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
            var procodes = getSelectedRows();
            if (procodes == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../portalprocedure/delete",
                    data: JSON.stringify(procodes),
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
        getInfo: function (procode) {
            $.get("../portalprocedure/info/" + procode, function (r) {
                vm.portalProcedure = r.portalProcedure;
                vm.portalProcedure.procodeOld = vm.portalProcedure.procode;
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
        addRedis: function () {
            var codes = getSelectedRows();
            if (codes == null) {
                return;
            }
            $.ajax({
                type: "POST",
                url: "../portalprocedure/addRedis",
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
            $.get("../portalprocedure/getNewMaxCode/", function (r) {
                console.info(r.newMaxCode);
                $("#procode").val(r.newMaxCode);
                vm.portalProcedure.procode = r.newMaxCode;
            });
        },
        checkForm: function () {
            if (getStringValue($("#procode").val) != "") {
                if ($("#procode").val().substr(0, 4) != "PRO_" || $("#procode").val().length != 10) {
                    alert("编码错误，正确格式如：PRO_000001");
                    return false;
                }
            }
            return true;
        }
    }
});

// textarea 高度自动扩展
autosize($('textarea'));