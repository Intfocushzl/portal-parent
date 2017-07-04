var reportModulePage = {};
var contentJsonObj = [];
var module_id;
var returnJsonObj;

// 初始化
$(function () {
    module_id = getQueryString("id");
    if (getStringValue(module_id) != "") {
        $.get("../reportmodulepage/info/" + module_id, function (r) {
            var r_obj = JSON.parse(r);
            reportModulePage = r_obj.reportModulePage;
            rendererModule(reportModulePage);
        });
    }
});

// 获取 window.location.href参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

// 返回
$("#btnBack").click(function () {
    history.go(-1);
});

// 保存
$("#saveOrUpdateModule").click(function () {
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
            returnJsonObj = JSON.parse(r);
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

// 显示json字符串
$("#jsonstrtab").click(function () {
    $("#jsonstr").val(JSON.stringify(selfModuleJson()));
});

// 封装控件为json
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

// 初始化渲染模板
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

//打开json view
function open_jsonview() {
    window.open("http://" + window.location.host + "/statics/plugins/jquery-jsonview/json-view.html?jsonstr=" + escape($("#jsonstr").val()));
};
