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
            contentJsonObj = JSON.parse(reportModulePage.content);
            console.log(reportModulePage.content);

            $("#jsonstr").val(JSON.stringify(reportModulePage.content));
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
    reportModulePage.title = "标题";
    // json 字符串
    reportModulePage.content = "{}";
    // 专题类型 0：PC专题 1：APP专题
    reportModulePage.pageType = 1;
    var url = module_id == null ? "../reportmodulepage/save" : "../reportmodulepage/update";
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
    $("#jsonstr").val(JSON.stringify(jsonObj));
});


