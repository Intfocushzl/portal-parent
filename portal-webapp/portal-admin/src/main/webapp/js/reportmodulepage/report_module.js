var reportModulePage = {};
var contentJsonObj = [];
var module_id;
var returnJsonObj;
var selectOptionJsonObjSql;
var selectOptionJsonObjPro;
var selectOptionJsonObjReport;
var selectOptionJsonObjOpenApi;

/**
 * 初始化数据
 * <!-- 图表控件 张海 walk_hai@163.com-->
 */
$(function () {
    module_id = getQueryString("id");
    if (getStringValue(module_id) != "") {
        $.get("../reportmodulepage/info/" + module_id, function (r) {
            var r_obj = getJsonObj(r);
            reportModulePage = r_obj.reportModulePage;
            rendererModule(reportModulePage);
            $("#aaaaaaa").selectpicker('refresh');
        });
    }

    // sql
    $.get("../portalexecutesql/sqlList/", function (r) {
        selectOptionJsonObjSql = getJsonObj(r);
    });

    // 存储
    $.get("../portalprocedure/proList/", function (r) {
        selectOptionJsonObjPro = getJsonObj(r);
    });

    // 报表编码
    $.get("../portalreport/repList/", function (r) {
        selectOptionJsonObjReport = getJsonObj(r);
    });

    // 开放接口
    $.get("../portalopenapireport/repList/", function (r) {
        selectOptionJsonObjOpenApi = getJsonObj(r);
    });

});

/**
 * 数据源选择
 * @param data_url_id
 * @param executeCode
 */
function forSelectOption(data_url_id, executeCode) {
    $(data_url_id).empty();
    var selectOption;
    /*
    // sql语句
    for (var i = 0; i < selectOptionJsonObjSql.sqlList.length; i++) {
        selectOption = "<option value='" + selectOptionJsonObjSql.sqlList[i].sqlcode + "'";
        if (executeCode == selectOptionJsonObjSql.sqlList[i].sqlcode) {
            selectOption = selectOption + " selected = 'selected'";
        }
        selectOption = selectOption + " >" + selectOptionJsonObjSql.sqlList[i].sqlcode + "<==>" + selectOptionJsonObjSql.sqlList[i].title + "</option>";
        $(data_url_id).append(selectOption);
    }
    // pro存储
    for (var i = 0; i < selectOptionJsonObjPro.proList.length; i++) {
        selectOption = "<option value='" + selectOptionJsonObjPro.proList[i].procode + "'";
        if (executeCode == selectOptionJsonObjPro.proList[i].procode) {
            selectOption = selectOption + " selected = 'selected'";
        }
        selectOption = selectOption + " >" + selectOptionJsonObjPro.proList[i].procode + "<==>" + selectOptionJsonObjPro.proList[i].title + "</option>";
        $(data_url_id).append(selectOption);
    }
    */
    // 报表信息
    for (var i = 0; i < selectOptionJsonObjReport.repList.length; i++) {
        selectOption = "<option value='" + selectOptionJsonObjReport.repList[i].code + "'";
        if (executeCode == selectOptionJsonObjReport.repList[i].code) {
            selectOption = selectOption + " selected = 'selected'";
        }
        selectOption = selectOption + " >" + selectOptionJsonObjReport.repList[i].code + " | " + selectOptionJsonObjReport.repList[i].title + "</option>";
        $(data_url_id).append(selectOption);
    }
    // 开放接口
    for (var i = 0; i < selectOptionJsonObjOpenApi.repList.length; i++) {
        selectOption = "<option value='" + selectOptionJsonObjOpenApi.repList[i].code + "'";
        if (executeCode == selectOptionJsonObjOpenApi.repList[i].code) {
            selectOption = selectOption + " selected = 'selected'";
        }
        selectOption = selectOption + " >" + selectOptionJsonObjOpenApi.repList[i].code + " | " + selectOptionJsonObjOpenApi.repList[i].title + "</option>";
        $(data_url_id).append(selectOption);
    }
    // refresh刷新和render渲染操作，必不可少
    $(data_url_id).selectpicker('refresh');
    $(data_url_id).selectpicker('render');
}

/**
 * 获取 window.location.href参数
 * @param name
 * @returns {null}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

/**
 * 返回
 */
$("#btnBack").click(function () {
    history.go(-1);
});

/**
 * 保存
 */
$(".saveOrUpdate").click(function () {
    reportModulePage.status = $(this).attr("status");
    // 标题
    reportModulePage.title = $("#form_name").val();
    // json 字符串
    reportModulePage.content = JSON.stringify(selfModuleJson());
    // 专题类型 0：PC专题 1：APP专题
    reportModulePage.pageType = 1;
    var url = (module_id == null || module_id == "") ? "../reportmodulepage/save" : "../reportmodulepage/update";
    $.ajax({
        type: "POST",
        contentType: 'application/json',
        url: url,
        data: JSON.stringify(reportModulePage),
        success: function (r) {
            returnJsonObj = getJsonObj(r);
            if (returnJsonObj === 0) {
                alert('操作成功', function (index) {
                    vm.reload();
                });
            } else {
                alert(returnJsonObj.msg);
            }
        }
    });
});

/**
 * 显示json字符串
 */
$("#jsonstrtab").click(function () {
    $("#jsonstr").val(JSON.stringify(selfModuleJson()));
});

/**
 * 封装控件为json
 * @returns {*[]}
 */
function selfModuleJson() {
    var fromName = $("#form_name").val();
    var jsonObj = [
        {
            "title": fromName,
            "parts": []
        }
    ];
    var $plugjsonstr = $('#build .plugjsonstr');
    $plugjsonstr.each(function () {
        var $this = $(this);
        if (getStringValue($this.val()) != "") {
            jsonObj[0].parts.push(JSON.parse($this.val()));
        }
    });
    return jsonObj;
}

/**
 * 初始化渲染模板
 * @param reportModulePage
 */
function rendererModule(reportModulePage) {
    contentJsonObj = JSON.parse(reportModulePage.content)[0];

    // 设置标题
    if (contentJsonObj.title !== undefined) {
        $("#form_name").val(contentJsonObj.title);
        $("#legend .leipiplugins-orgvalue").html(contentJsonObj.title);
    }

    // 循环遍历控件
    if (contentJsonObj.parts !== undefined) {
        var partsObj = contentJsonObj.parts;
        //console.log(JSON.stringify(partsObj));
        var moduleObj;
        var moduleObjTemp;
        var moduleObjTempId;
        var moduleObjTempNewId;
        for (var i = 0, len = partsObj.length; i < len; i++) {
            moduleObj = partsObj[i];
            // 遍历所有属性
            /*for (var key in moduleObj) {
             console.log(key + ':' + moduleObj[key]);
             }*/
            moduleObjTemp = $("#" + moduleObj.type).append("\n\n\ \ \ \ ");
            moduleObjTempId = moduleObjTemp.attr("id");
            moduleObjTempNewId = moduleObjTempId + "_" + RndNum(6);
            moduleObjTemp.attr("id", moduleObjTempNewId);
            console.log(moduleObjTempNewId);
            $("#target fieldset").append(moduleObjTemp.prop("outerHTML"));
            // 还原模板原始id
            moduleObjTemp.attr("id", moduleObjTempId);
            // 设置值
            $("#" + moduleObjTempNewId + " textarea").val(JSON.stringify(moduleObj));
            $("#" + moduleObjTempNewId + " textarea").html(JSON.stringify(moduleObj));
            $("#" + moduleObjTempNewId).children(".leipiplugins-orgname").html(moduleObj.name);
        }
    }
}

/**
 * 打开json view
 */
function open_jsonview() {
    window.open(
        rcContextPath
        + "/sys/jsonview.html?jsonstr=" + escape($("#jsonstr").val())
    );
};
