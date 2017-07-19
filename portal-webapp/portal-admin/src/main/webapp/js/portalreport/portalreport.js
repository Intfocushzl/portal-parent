$(function () {
    $("#jqGrid").jqGrid({
        url: '../portalreport/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, hidden: true},
            {
                label: '唯一编码', name: 'code', index: 'code', width: 80, key: true,
                formatter: function (value, options, row) {
                    return '<a class="a_text" onclick="vm.info(\'' + row.code + '\')">' + value + '</a>';
                }
            },
            {label: '标题', name: 'title', index: 'title', width: 80},
            {
                label: '数据脚本',
                name: 'execute_code',
                index: 'execute_code',
                width: 80
            },
            {
                label: '执行类型', name: 'execute_type', index: 'execute_type', width: 80,
                formatter: function (value, options, row) {
                    return value === 1 ?
                        '<span class="label label-info">存储</span>' :
                        '<span class="label label-warning">SQL</span>';
                }
            },
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
        }
        ,
        prmNames: {              // 改写请求参数属性
            page: "page",
            rows: "limit",
            order: "order"
        }
        ,
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            //设置高度
            $("#jqGrid").jqGrid('setGridHeight', getWinH());
        }
    });
})
;

var vm = new Vue({
        el: '#rrapp',
        data: {
            showList: true,
            title: null,
            headersFormatNew: {},
            selectOption: null,
            executeCodeOld: null,
            portalReport: {}
        },
        methods: {
            query: function () {
                // vm.reload();
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
                $("input[name='code']").removeAttr("readonly");
                vm.getNewMaxCode();
            },
            update: function (event) {
                $("#saveOrUpdate").css("display", "");
                var code = getSelectedRow();
                if (code == null) {
                    return;
                }
                vm.showList = false;
                vm.title = "修改";
                vm.getInfo(code);
                $("input[name='code']").attr("readonly", "readonly");
                // 根据单元格选中值默认选中
                /*vm.bindCindexAperture("Profit");
                 vm.bindReportDimIndex("tm");
                 vm.bindCindexRefer("tb");*/
            },
            info: function (code) {
                $("#saveOrUpdate").css("display", "none");
                vm.showList = false;
                vm.title = "详情";
                vm.getInfo(code);
                $("input[name='code']").attr("readonly", "readonly");
            },
            toTest: function () {
                window.open("http://" + window.location.host + "/sys/portalapi.html?code=" + escape(vm.portalReport.code));
            },
            saveOrUpdate: function () {
                var code = vm.portalReport.code;
                var id = vm.portalReport.id;
                var url = vm.portalReport.id == null ? "../portalreport/save" : "../portalreport/update";
                if (vm.checkForm()) {
                    if (id == null) {
                        $.get("../portalreport/info/" + code, function (r) {
                            console.log(r);
                            if (r.portalReport != null) {
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
                    vm.executeCodeOld = vm.portalReport.executeCode;
                    if (vm.portalReport.executeType == 1) {
                        vm.getProCode(vm.executeCodeOld);
                    } else if (vm.portalReport.executeType == 2) {
                        vm.getSqlCode(vm.executeCodeOld);
                    }

                    // 清除单元格格式
                    getMergeDataClear();
                    // 延迟加载1秒
                    clearTimeout(autosaveNotification);
                    autosaveNotification = setTimeout(function () {

                    }, 1000);
                    var reportHotDataArr = eval(vm.portalReport.reportHotData);
                    // 渲染合并单元格
                    var mergedCellInfoCollectionJsonArray = eval('(' + vm.portalReport.reportMergedCellInfoCollection + ')');
                    for (var i in mergedCellInfoCollectionJsonArray) {
                        hot.mergeCells.mergedCellInfoCollection.push(mergedCellInfoCollectionJsonArray[i]);
                    }
                    // 加载hansontable数据
                    hot.loadData(reportHotDataArr);
                    // 渲染表格
                    hot.render();
                    // 只显示名称
                    getDataHtml();
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
                    url: "../portalreport/addRedis",
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
            onlycodeshow: function () {
                var type = $("input[name='type']");
                var typevalue = null;
                for (var i = 0; i < type.length; i++) {
                    if (type[i].checked == true) {
                        typevalue = type[i].value;
                    }
                }
                if (typevalue == 1) {
                    this.getProCode(vm.executeCodeOld);
                } else if (typevalue == 2) {
                    this.getSqlCode(vm.executeCodeOld);
                }
            },
            getSqlCode: function (executeCodeOld) {
                $("#onlycode").empty();
                $.get("../portalexecutesql/sqlList/", function (r) {
                    for (var i = 0; i < r.sqlList.length; i++) {
                        vm.selectOption = "<option value='" + r.sqlList[i].sqlcode + "'";
                        if (executeCodeOld == r.sqlList[i].sqlcode) {
                            vm.selectOption = vm.selectOption + " selected = 'selected'";
                        }
                        vm.selectOption = vm.selectOption + " >" + r.sqlList[i].sqlcode + "<==>" + r.sqlList[i].title + "</option>";
                        $("#onlycode").append(vm.selectOption);
                    }
                    // refresh刷新和render渲染操作，必不可少
                    $('#onlycode').selectpicker('refresh');
                    $('#onlycode').selectpicker('render');
                });
            },
            getProCode: function (executeCodeOld) {
                $("#onlycode").empty();
                $.get("../portalprocedure/proList/", function (r) {
                    for (var i = 0; i < r.proList.length; i++) {
                        vm.selectOption = "<option value='" + r.proList[i].procode + "'";
                        if (executeCodeOld == r.proList[i].procode) {
                            vm.selectOption = vm.selectOption + " selected = 'selected'";
                        }
                        vm.selectOption = vm.selectOption + " >" + r.proList[i].procode + "<==>" + r.proList[i].title + "</option>";
                        $("#onlycode").append(vm.selectOption);
                    }
                    // refresh刷新和render渲染操作，必不可少
                    $('#onlycode').selectpicker('refresh');
                    $('#onlycode').selectpicker('render');
                });
            },
            getCindexAperture: function () {
                // 属性定义
                $.get("../cindexaperture/listOpt/", function (r) {
                    $("#cIndexAperture").append("<option value='' style='text-align: left;padding-right: 20px' >空-请选择</option>");
                    for (var i = 0; i < r.data.length; i++) {
                        vm.selectOption = "<option class='" + r.data[i].fieldname + "' value='" + r.data[i].fieldname + ":" + r.data[i].indexname + ":" + r.data[i].indexname + "' style='text-align: left;padding-right: 20px' >" + r.data[i].fieldname + ":" + r.data[i].indexname + "</option>";
                        $("#cIndexAperture").append(vm.selectOption);
                    }
                    // refresh刷新和render渲染操作，必不可少
                    $('#cIndexAperture').selectpicker('refresh');
                    $('#cIndexAperture').selectpicker('render');
                });
            },
            getReportDimIndex: function () {
                // 维度定义
                $.get("../reportdimindex/listOpt/", function (r) {
                    $("#reportDimIndex").append("<option value='' style='text-align: left;padding-right: 20px' >空-请选择</option>");
                    for (var i = 0; i < r.data.length; i++) {
                        vm.selectOption = "<option class='" + r.data[i].dimlab + "' value='" + r.data[i].dimlab + ":" + r.data[i].dimname + ":" + r.data[i].dimname + "' style='text-align: left;padding-right: 20px' >" + r.data[i].dimlab + ":" + r.data[i].dimname + "</option>";
                        $("#reportDimIndex").append(vm.selectOption);
                    }
                    // refresh刷新和render渲染操作，必不可少
                    $('#reportDimIndex').selectpicker('refresh');
                    $('#reportDimIndex').selectpicker('render');
                });
            },
            getCindexRefer: function () {
                // 指标定义
                $.get("../cindexrefer/listOpt/", function (r) {
                    $("#cIndexRefer").append("<option value='' style='text-align: left;padding-right: 20px' >空-请选择</option>");
                    for (var i = 0; i < r.data.length; i++) {
                        vm.selectOption = "<option class='" + r.data[i].referchar + "' value='" + r.data[i].referchar + ":" + r.data[i].refername + ":" + r.data[i].def + "' style='text-align: left;padding-right: 20px' >" + r.data[i].referchar + ":" + r.data[i].refername + "</option>";
                        $("#cIndexRefer").append(vm.selectOption);
                    }
                    // refresh刷新和render渲染操作，必不可少
                    $('#cIndexRefer').selectpicker('refresh');
                    $('#cIndexRefer').selectpicker('render');
                });
            },
            cellReadOnly: function () {
                for (var i = 0; i < hot.countRows(); i++) {
                    for (var k = 0; k < hot.countCols(); k++) {
                        hot.getCellMeta(i, k).readOnly = true;
                    }
                }
            },
            getNewMaxCode: function () {
                $.get("../portalreport/getNewMaxCode/", function (r) {
                    console.info(r.newMaxCode);
                    $("#code").val(r.newMaxCode);
                    vm.portalReport.code = r.newMaxCode;
                });
            },
            checkForm: function () {
                if (getStringValue($("#code").val) != "") {
                    if ($("#code").val().substr(0, 4) != "REP_" || $("#code").val().length != 10) {
                        alert("编码错误，正确格式如：REP_000001");
                        return false;
                    }
                }
                return true;
            }

        }
    })
    ;
