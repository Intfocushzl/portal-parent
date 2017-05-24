$(function () {
    $("#jqGrid").jqGrid({
        url: '../portalreport/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, hidden: true},
            {label: '唯一编码', name: 'code', index: 'code', width: 80, key: true},
            {label: '标题简介', name: 'title', index: 'title', width: 80},
            {
                label: '执行唯一编码',
                name: 'executeCode',
                index: 'execute_code',
                width: 80
            },
            {label: '执行类型', name: 'executeType', index: 'execute_type', width: 80},
            {label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
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
        portalReport: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    code: vm.portalReport.code,
                    remark: vm.portalReport.remark
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.portalReport = {};
        },
        update: function (event) {
            var code = getSelectedRow();
            if (code == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(code)
        },
        saveOrUpdate: function (event) {
            var code = vm.portalReport.code;
            var id = vm.portalReport.id;
            var url = vm.portalReport.id == null ? "../portalreport/save" : "../portalreport/update";
            if(id == null){
                $.get("../portalreport/info/"+code,function(r) {
                    console.log(r);
                    if (r.portalReport != null) {
                        alert("唯一编码已存在，请重新输入");
                    }else{
                        vm.addAndUpdate(url);
                    }
                })
            }else{
                vm.addAndUpdate(url);
            }
        },
        addAndUpdate:function(url){
            vm.portalReport.executeCode = $("#onlycode").val();
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.portalReport),
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
            var codes = getSelectedRows();
            if (codes == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../portalreport/delete",
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
            });
        },
        getInfo: function (code) {
            $.get("../portalreport/info/" + code, function (r) {
                vm.portalReport = r.portalReport;
                vm.portalReport.codeOld = vm.portalReport.code;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        onlycodeshow: function () {
            var type = $("input[name='type']");
            var typevalue = null;
            for (var i = 0; i < type.length; i++) {
                if (type[i].checked == true) {
                    typevalue = type[i].value;
                }
            }
            if (typevalue == 1) {
                this.getProCode();
            } else if (typevalue == 2) {
                this.getSqlCode();
            }
        },
        getSqlCode: function () {
            $("#onlycode").empty();
            $.get("../portalexecutesql/sqlList/", function (r) {
                for(var i = 0;i < r.sqlList.length;i++){
                    $("#onlycode").append("<option value='"+r.sqlList[i].sqlcode+"'>"+r.sqlList[i].sqlcode+"<==>"+r.sqlList[i].title+"</option>");
                }
            });
        },
        getProCode: function () {
            $("#onlycode").empty();
            $.get("../portalprocedure/proList/", function (r) {
                for(var i = 0;i < r.proList.length;i++) {
                    $("#onlycode").append("<option value='" + r.proList[i].procode + "'>" + r.proList[i].procode +"<==>"+ r.proList[i].title + "</option>");
                }
            });
        }

    }
});