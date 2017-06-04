var tdText,
    tdTextOld,
    tdHtml,
    tdHtmlOld,
    tdCol,
    tdRow;

$(function () {
    var indexValue,
        cIndexAperture = $("#cIndexAperture"),
        reportDimIndex = $("#reportDimIndex"),
        cIndexRefer = $("#cIndexRefer");

    // 属性设置框
    $("#dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 780,
        modal: true,
        buttons: {
            "确定": function () {
                //alert(cIndexAperture.val());
                //alert(reportDimIndex.val());
                //alert(cIndexRefer.val());
                indexValue = "";
                if (getStringValue(cIndexAperture.val()) != "") {
                    indexValue = cIndexAperture.val();
                }
                if (getStringValue(reportDimIndex.val()) != "") {
                    if (getStringValue(cIndexAperture.val()) != "") {
                        indexValue = indexValue + "_";
                    }
                    indexValue = indexValue + reportDimIndex.val();
                }
                if (getStringValue(cIndexRefer.val()) != "") {
                    if (getStringValue(cIndexAperture.val()) != "" || getStringValue(reportDimIndex.val()) != "") {
                        indexValue = indexValue + "_";
                    }
                    indexValue = indexValue + cIndexRefer.val();
                }
                if (getStringValue(indexValue) == '') {
                    alert("至少选择一项！");
                    return;
                }
                // 更新当前行的，source：行或列对象
                source = hot.getSourceDataAtRow(tdRow);
                // 设置新值到一个单元格
                hot.setDataAtCell(tdRow, tdCol, indexValue, source);
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
function showialogForm(event, coords, elem) {
    tdCol = coords.col;
    tdRow = coords.row;
    tdTextOld = elem.innerText;
    tdHtmlOld = elem.innerHTML;
    $("#dialog-form").dialog("open");
    $(".validateTips").html("至少选择一项");
}
