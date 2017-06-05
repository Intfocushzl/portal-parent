var
    this_$ = function (id) {
        return document.getElementById(id);
    },
    autosaveNotification,
    // 不适用jquery选择器
    reportHeadersContainer = this_$('reportHeadersContainer'),
    saveOrUpdate = this_$('saveOrUpdate'),	    // 保存数据
    addBtn = this_$('addBtn'),
    hot,
    source,                // 根据行号获取data source中的该行数据
    reportHeadersConsole = $('#reportHeadersConsole'),
    reportHeadersFormatConsole = $('#reportHeadersFormatConsole'),
    reportOuterHtml = $('#reportOuterHtml'),
    reportHotData = $('#reportHotData'),
    reportMergedCellInfoCollection = $('#reportMergedCellInfoCollection'),
    mergedCellInfoCollectionArr,
    mergedCellInfoCollectionArrLength,
    table_tbody = $(".htCore").find("tbody"),
    table_tbody_tr,
    table_tbody_tr_td;


hot = new Handsontable(reportHeadersContainer, {
    //data: getData(),    // 初始化数据
    startRows: 1,		        // 初始行数
    startCols: 1,	            // 初始列数
    //rowHeaders: true,	        // 显示行标题
    //colHeaders: true,	        // 显示列标题
    minSpareRows: 0,	        // 行数不足，自动扩展N行
    //contextMenu: true,	    // 右键菜单默认全部
    contextMenu: ["row_above", "row_below", "col_left", "col_right", "remove_row", "remove_col", "mergeCells"],
    mergeCells: true,	        // 合并单元功能,将mergeCells选项设置为true或数组。
    //mergeCells: getMergeData(),
    // 使用onChange调跟踪表中更改,source：行或列对象
    afterChange: function (change, source) {
        /*if (source === 'loadData') {
         //alert(source);
         //return;     // 不保存这个改变
         } */
        //alert(JSON.stringify(change));
        if (change != null) {
            table_tbody = $(".htCore").find("tbody");
            table_tbody_tr = table_tbody.find("tr:eq(" + change[0][0] + ")");
            table_tbody_tr_td = table_tbody_tr.find("td:eq(" + change[0][1] + ")");
            var thisCellValue = table_tbody_tr_td.text();
            //var thisCellValue = change[0][3];
            //alert("当前值" + thisCellValue);
            if (thisCellValue == null || thisCellValue == undefined || thisCellValue == '') {
                alert("内容不得为空");
                return;
            }
            // 检查当前值是否正确
            /*ajax('json/save.json', 'GET', JSON.stringify({data: change}), function (data) {
             reportHeadersConsole.innerText = '检查当前值是否正确存...';
             });*/
        }
    }
});

// 初始化数据
function getData() {
    return [
        [""]
    ];
}

// 初始化合并单元格
function getMergeData() {
    return [
        {"row": 0, "col": 0, "rowspan": 1, "colspan": 1}
    ];
}

// 初始化合并单元格清除
function getMergeDataClear() {
    // 删除合并单元格数组中元素
    mergedCellInfoCollectionArr = hot.mergeCells.mergedCellInfoCollection;
    mergedCellInfoCollectionArrLength = mergedCellInfoCollectionArr.length;
    if (mergedCellInfoCollectionArrLength > 0) {
        mergedCellInfoCollectionArr.splice(0, mergedCellInfoCollectionArrLength);
    }
}

// 合并单元格
function optMerge() {
    // 合并单元格数组
    mergedCellInfoCollectionArr = hot.mergeCells.mergedCellInfoCollection;
    mergedCellInfoCollectionArrLength = mergedCellInfoCollectionArr.length;
    var countCols = hot.countCols();     // 总列数
    var countRows = hot.countRows();     // 总行数
    var mergedCellRow = 0;		            // 当前合并单元起始行
    var mergedCellCol = 0;		            // 当前合并单元起始列
    var mergedCellRowspan = 0;	            // 当前合并单元格跨行
    var mergedCellColspan = 0;	            // 当前合并单元格跨列
    var mergedCellValue;		            // 当前合并单元格的值
    table_tbody = $(".htCore").find("tbody");
    table_tbody_tr;
    table_tbody_tr_td;

    // 设置合并单元内的值相同
    if (mergedCellInfoCollectionArrLength > 0) {
        // 遍历所有合并的单元格
        for (var k = 0; k < mergedCellInfoCollectionArrLength; k++) {
            mergedCellRow = mergedCellInfoCollectionArr[k].row;
            mergedCellCol = mergedCellInfoCollectionArr[k].col;
            mergedCellRowspan = mergedCellInfoCollectionArr[k].rowspan;
            mergedCellColspan = mergedCellInfoCollectionArr[k].colspan;

            table_tbody_tr = table_tbody.find("tr:eq(" + mergedCellRow + ")");
            table_tbody_tr_td = table_tbody_tr.find("td:eq(" + mergedCellCol + ")");
            mergedCellValue = table_tbody_tr_td.text();

            // 获取第 mergedCellRow + mergedCellRowspan 行
            for (var row_j = mergedCellRow; row_j < (mergedCellRow + mergedCellRowspan); row_j++) {
                // 获取第 mergedCellRow + mergedCellColspan 列
                for (var col_i = mergedCellCol; col_i < (mergedCellCol + mergedCellColspan); col_i++) {
                    //table_tbody_tr = table_tbody.find("tr:eq(" + row_j + ")");
                    //table_tbody_tr_td = table_tbody_tr.find("td:eq(" + col_i + ")");
                    //table_tbody_tr_td.html(mergedCellValue);
                    // 更新当前行的，source：行或列对象
                    source = hot.getSourceDataAtRow(row_j);
                    // 设置新值到一个单元格
                    hot.setDataAtCell(row_j, col_i, mergedCellValue, source);
                }
            }
        }
    }

    // 获取最后一行所有单元格的值
    var getDataAtRow = hot.getDataAtRow(countRows - 1);
    reportHeadersConsole.innerText = JSON.stringify(getDataAtRow);
    vm.portalReport.reportHeadersConsole = JSON.stringify(getDataAtRow);
}

// 新增
Handsontable.Dom.addEvent(addBtn, 'click', function () {
    // 清除单元格格式
    getMergeDataClear();
    // 初始化数据
    hot.loadData(getData());
    // 渲染表格
    hot.render();
});

// 保存所有单元格的数据
Handsontable.Dom.addEvent(saveOrUpdate, 'click', function () {
    // 处理合并单元格
    optMerge();
    // 设置表格数据
    reportHotData.innerText = JSON.stringify(hot.getData());
    vm.portalReport.reportHotData = JSON.stringify(hot.getData());
    // 设置合并单元格数据
    reportMergedCellInfoCollection.innerText = JSON.stringify(hot.mergeCells.mergedCellInfoCollection);
    vm.portalReport.reportMergedCellInfoCollection = JSON.stringify(hot.mergeCells.mergedCellInfoCollection);
    // 设置HTML
    // encodeURI() 函数不转义：;/?:@&=+$,# (使用 encodeURIComponent 对这些字符进行编码),decodeURI 方法返回一个字符串值
    reportOuterHtml.innerText = encodeURI($(".ht_master .handsontable table").prop("outerHTML"));
    //reportOuterHtml.innerText = encodeURI($(".htCore").prop("outerHTML"));
    vm.portalReport.reportOuterHtml = encodeURI($(".htCore").prop("outerHTML"));
    // 设置格式化数据
    vm.portalReport.reportHeadersFormatConsole = JSON.stringify(vm.headersFormat);
    // 保存或更新
    vm.saveOrUpdate();
});
