var reportModulePage = {};
var contentJsonObj = [];

// 初始化
$(function () {
    var id = getQueryString("id");
    if (getStringValue(id) != "") {
        $.get("../reportmodulepage/info/" + id, function (r) {
            var r_obj = JSON.parse(r);
            reportModulePage = r_obj.reportModulePage;
            contentJsonObj = JSON.parse(reportModulePage.content);

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
    alert("保存模板-暂未处理", function () {

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


