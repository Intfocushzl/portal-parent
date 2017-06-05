var tdText,
    tdTextOld,
    tdHtml,
    tdHtmlOld,
    tdCol,
    tdRow,
    tdElem;

$(function () {
    var indexValue,
        indexName,
        selectedText,
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
                indexName = "";
                selectedText = "";
                if (getStringValue(cIndexAperture.val()) != "") {
                    indexValue = cIndexAperture.val();
                    selectedText = cIndexAperture.find("option:selected").text().split(":")[1];
                    indexName = selectedText;
                }
                if (getStringValue(reportDimIndex.val()) != "") {
                    if (getStringValue(cIndexAperture.val()) != "") {
                        indexValue = indexValue + "_";
                        indexName = indexName + "_";
                    }
                    indexValue = indexValue + reportDimIndex.val();
                    selectedText = reportDimIndex.find("option:selected").text().split(":")[1];
                    indexName = indexName + selectedText;
                }
                if (getStringValue(cIndexRefer.val()) != "") {
                    if (getStringValue(cIndexAperture.val()) != "" || getStringValue(reportDimIndex.val()) != "") {
                        indexValue = indexValue + "_";
                        indexName = indexName + "_";
                    }
                    indexValue = indexValue + cIndexRefer.val();
                    selectedText = cIndexRefer.find("option:selected").text().split(":")[1];
                    indexName = indexName + selectedText;
                }
                if (getStringValue(indexValue) == '') {
                    alert("至少选择一项！");
                    return;
                }

                // 封装json
                var rowCol = tdRow + "_" + tdCol;
                var rowColJson = {};
                rowColJson["row"] = tdRow;
                rowColJson["rowSpan"] = tdElem.rowSpan;
                rowColJson["col"] = tdCol;
                rowColJson["colSpan"] = tdElem.colSpan;
                rowColJson["indexValue"] = indexValue;
                rowColJson["indexName"] = indexName;
                rowColJson["name"] = selectedText;
                createJson(rowCol, rowColJson);
                alert(JSON.stringify(vm.headersFormat));

                // 更新当前行的，source：行或列对象
                source = hot.getSourceDataAtRow(tdRow);
                // 设置新值到一个单元格
                hot.setDataAtCell(tdRow, tdCol, selectedText, source);

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
    tdTextOld = elem.innerText;
    tdHtmlOld = elem.innerHTML;
    $("#dialog-form").dialog("open");
    $(".validateTips").html("至少选择一项");
}

// 参数：prop = 属性，val = 值
function createJson(prop, val) {
    // 如果 val 被忽略
    if (typeof val === "undefined") {
        // 删除属性
        delete vm.headersFormat[prop];
    }
    else {
        // 添加 或 修改
        vm.headersFormat[prop] = val;
    }
}