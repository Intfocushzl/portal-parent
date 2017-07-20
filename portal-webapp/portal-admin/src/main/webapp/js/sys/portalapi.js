// 获取 window.location.href参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

var repCode = unescape(getQueryString("code"));
var executeType = unescape(getQueryString("executeType"));
var executeCode = unescape(getQueryString("executeCode"));

// 初始化信息
function init() {
    /*$("#code").val(vm.portalReport.code);*/
    vm.getRepCode();
    /*$("#title").val(vm.portalReport.title);*/
    $("#reportTitleName").val(vm.portalReport.reportTitleName);
    $("#remark").val(vm.portalReport.remark);
}

// 测试请求
//var test_url = "http://10.67.241.218/yhportal/api/portal/custom?yongHuiReportCustomCode=REP_000003&reportLabel=2&sDate=2017/6/19&eDate=2017/7/19&holdName=%E7%AC%AC%E4%BA%8C%E9%9B%86%E7%BE%A4&classId=&areaId=&areaMans=&shopId=&token=adminMGdp1nT0Q3qxBqDvFNKW0QM9354d8db013b4bb08d4e116e6fd1893e";
var test_url = "http://10.67.241.218/yhportal/api/portal/custom";
var dev_url = "http://10.67.241.234/yhportal/api/portal/custom";

$("#test_url").val(test_url);
$("#dev_url").val(dev_url);
var token = "CS123456mXB84h6U5FUO7nsdu0Eq7c5373b211054c7b8681fe829c62cc2c";

// 格式化json
$(function () {
    // 初始加载
    vm.intInfo(repCode);

    $('#toggle-formatter').on('click', function () {
        $("#json").JSONView($("#json_str_text").val());
    });

    $('#toggle-btn').on('click', function () {
        $('#json').JSONView('toggle');
    });

    $('#toggle-level1-btn').on('click', function () {
        $('#json').JSONView('toggle', 1);
    });

    $('#toggle-level2-btn').on('click', function () {
        $('#json').JSONView('toggle', 2);
    });

    $('#toggle-level3-btn').on('click', function () {
        $('#json').JSONView('toggle', 3);
    });

    $('#toggle-level4-btn').on('click', function () {
        $('#json').JSONView('toggle', 4);
    });

    $('#toggle-level5-btn').on('click', function () {
        $('#json').JSONView('toggle', 5);
    });

    $('#code').on('change', function () {
        vm.intInfoSelect();
    });

});

var vm = new Vue({
    data: {
        parameters: "",
        portalReport: {},
        portalProcedure: {},
        portalExecuteSql: {}
    },
    methods: {
        intInfo: function (repCode) {
            vm.getInfo(repCode);
        },
        intInfoSelect: function () {
            vm.getInfo($("#code").val());
        },
        getInfo: function (code) {
            $.get(rcContextPath + "/portalreport/info/" + code, function (r) {
                vm.portalReport = r.portalReport;
                init();
                vm.getParameter(r.portalReport);
            });
        },
        getJsonTest: function () {
            $.get(test_url + "?" + vm.parameters, function (r) {
                $(".json_str_text").val(JSON.stringify(r));
                $("#json").JSONView(JSON.stringify(r));
            });
        },
        getJsonDev: function () {
            $.get(dev_url + "?" + vm.parameters, function (r) {
                $(".json_str_text").val(JSON.stringify(r));
                $("#json").JSONView(JSON.stringify(r));
            });
        },
        getParameter: function (portalReportObj) {
            if (portalReportObj.executeType == 1) {
                vm.getProInfo(portalReportObj.executeCode);
            } else if (portalReportObj.executeType == 2) {
                vm.getSqlInfo(portalReportObj.executeCode);
            }
        },
        getProInfo: function (executeCode) {
            $.get(rcContextPath + "/portalprocedure/info/" + executeCode, function (r) {
                vm.portalProcedure = r.portalProcedure;
                vm.parameters = getParametersStr(vm.portalProcedure.parameter);
                $("#parameters").val(vm.parameters);
            });
        },
        getSqlInfo: function (executeCode) {
            $.get(rcContextPath + "/portalexecutesql/info/" + executeCode, function (r) {
                vm.portalExecuteSql = r.portalExecuteSql;
                vm.parameters = getParametersStr(vm.portalExecuteSql.parameter);
                $("#parameters").val(vm.parameters);
            });
        },
        getRepCode: function () {
            $("#code").empty();
            $.get(rcContextPath + "/portalreport/repList/", function (r) {
                for (var i = 0; i < r.repList.length; i++) {
                    vm.selectOption = "<option value='" + r.repList[i].code + "'";
                    if (repCode == r.repList[i].code) {
                        vm.selectOption = vm.selectOption + " selected = 'selected'";
                    }
                    vm.selectOption = vm.selectOption + " >" + r.repList[i].code + " " + r.repList[i].title + "</option>";
                    $("#code").append(vm.selectOption);
                }
                // refresh刷新和render渲染操作，必不可少
                $('#code').selectpicker('refresh');
                $('#code').selectpicker('render');
            });
        },
    }
});

function getParametersStr(parameters) {
    var pmt = "yongHuiReportCustomCode=" + vm.portalReport.code + "&token=" + token;
    if (getStringValue(parameters) != "") {
        pmt = pmt + "&" + parameters.replace(/@@/g, "=&") + "=";
    }
    return pmt;
}

/*返回顶部*/
$(window).scroll(function () {
    var sc = $(window).scrollTop();
    var rwidth = $(window).width()
    if (sc > 0) {
        $("#goTopBtn").css("display", "block");
        $("#goTopBtn").css("left", (rwidth - 48) + "px")
    } else {
        $("#goTopBtn").css("display", "none");
    }
})

$("#goTopBtn").click(function () {
    var sc = $(window).scrollTop();
    $('body,html').animate({scrollTop: 0}, 500);
})

// textarea 高度自动扩展
autosize($('textarea'));