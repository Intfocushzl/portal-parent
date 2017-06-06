var tdCol,
    tdRow,
    tdElem;

$(function () {
    var indexValue,
        indexNameAppend,
        indexName,
        indexDef,
        selectedText,
        tdText,
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
                indexValue = "";
                indexNameAppend = "";
                indexName = "";
                indexDef = "";
                selectedText = "";
                tdText = "";
                if (getStringValue(cIndexAperture.val()) != "") {
                    indexValue = cIndexAperture.val().split(":")[0];
                    indexName = cIndexAperture.val().split(":")[1];
                    indexNameAppend = indexName;
                    indexDef = cIndexAperture.val().split(":")[2];
                    //selectedText = cIndexAperture.find("option:selected").text().split(":")[1];
                    //indexName = selectedText;
                }
                if (getStringValue(reportDimIndex.val()) != "") {
                    if (getStringValue(cIndexAperture.val()) != "") {
                        indexValue = indexValue + "_";
                        indexNameAppend = indexNameAppend + "_";
                    }
                    indexValue = indexValue + reportDimIndex.val().split(":")[0];
                    indexName = reportDimIndex.val().split(":")[1];
                    indexNameAppend = indexNameAppend + indexName;
                    indexDef = reportDimIndex.val().split(":")[2];
                }
                if (getStringValue(cIndexRefer.val()) != "") {
                    if (getStringValue(cIndexAperture.val()) != "" || getStringValue(reportDimIndex.val()) != "") {
                        indexValue = indexValue + "_";
                        indexNameAppend = indexNameAppend + "_";
                    }
                    indexValue = indexValue + cIndexRefer.val();
                    indexName = cIndexRefer.val().split(":")[1];
                    indexNameAppend = indexNameAppend + indexName;
                    indexDef = cIndexRefer.val().split(":")[2];
                }
                if (getStringValue(indexValue) == '') {
                    alert("至少选择一项！");
                    return;
                }

                // 更新当前行的，source：行或列对象
                source = hot.getSourceDataAtRow(tdRow);
                // 设置新值到一个单元格
                tdText = indexValue + ":" + indexNameAppend + ":" + indexName + ":" + indexDef;
                hot.setDataAtCell(tdRow, tdCol, tdText, source);

                // 关闭窗口
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
    tdElem = elem;
    $("#dialog-form").dialog("open");
    $(".validateTips").html("至少选择一项");
}