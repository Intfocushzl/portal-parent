// 返回
$("#btnBack").click(function () {
    history.go(-1);
});

// 保存
$("#saveOrUpdateModule").click(function () {
    alert("保存模板-暂未处理", function () {

    });
});

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
        if (getStringValue($this.val()) != ""){
            jsonObj[0].parts.push(JSON.parse($this.val()));
        }
    });
    $("#jsonstr").val(JSON.stringify(jsonObj));
});
