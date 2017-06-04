$(function () {
    $("#dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 780,
        modal: true,
        buttons: {
            "确定": function () {
                $(this).dialog("close");
            },
            "取消": function () {
                $(this).dialog("close");
            }
        },
        close: function () {
        }
    });

    // 初始化表格属性
    vm.getCindexAperture();
    vm.getReportDimIndex();
    vm.getCindexRefer();
});

// 显示字段属性框
function showialogForm(event) {
    $("#dialog-form").dialog("open");
    $(".validateTips").html("至少选择一项");
}
