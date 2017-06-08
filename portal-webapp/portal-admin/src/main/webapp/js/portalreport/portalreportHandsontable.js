var
    this_$ = function (id) {
        return document.getElementById(id);
    },
    autosaveNotification,
    // 不适用jquery选择器
    reportHeadersContainer = this_$('reportHeadersContainer'),
    reportConsole = this_$('reportConsole'),
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
    table_tbody_tr_td,
    countCols,      // 总列数
    countRows;      // 总行数

hot = new Handsontable(reportHeadersContainer, {
    //data: getData(),    // 初始化数据
    startRows: 6,		        // 初始行数
    startCols: 1,	            // 初始列数
    //rowHeaders: true,	        // 显示行标题
    //colHeaders: true,	        // 显示列标题
    minSpareRows: 0,	        // 行数不足，自动扩展N行
    //contextMenu: true,	    // 右键菜单默认全部
    contextMenu: ["row_above", "row_below", "col_left", "col_right", "remove_row", "remove_col", "mergeCells"],
    mergeCells: true,	        // 合并单元功能,将mergeCells选项设置为true或数组。
    //mergeCells: getMergeData(),
    // 1个或多个单元格的值被改变后调用,source：行或列对象
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
            /*if (thisCellValue == null || thisCellValue == undefined || thisCellValue == '') {
             alert("内容不得为空");
             return;
             }*/
        }
    },
    // afterSelection (r: Number, c: Number, r2: Number, c2: Number)：当一个或多个单元格被选中后调用
    //其中，r：选中的单元格起始行，r2：选中单元格的终止行,c：选中的单元格的起始列，c2：选中的单元格的终止列
    //选中单元格鼠标抬起后调用
    afterSelectionEnd: function (r, c, r2, c2) {
        //getDataAtCell(row,col):根据行列索引获取单元格的值
        reportConsole.innerText = hot.getDataAtCell(r, c);
    },
    // 渲染表格后被调用 isForced：当其值为true表示是通过改变配置或数据引起的渲染，当值为false时表示通过滚动或移动、选中引起的渲染
    afterRender: function (isForced) {
        // 设置只读
        vm.cellReadOnly();
    }
});

// 初始化数据
function getData() {
    return [
        ["", "", "", "", "", ""]
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
    countCols = hot.countCols();     // 总列数
    countRows = hot.countRows();     // 总行数
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

            // 获取第 n 行
            for (var row_j = mergedCellRow; row_j < (mergedCellRow + mergedCellRowspan); row_j++) {
                // 获取第 n 列
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
    /*var getDataAtRow = hot.getDataAtRow(countRows - 1);
     reportHeadersConsole.innerText = JSON.stringify(getDataAtRow);
     vm.portalReport.reportHeadersConsole = JSON.stringify(getDataAtRow);*/
}

// 格式化数据
function getDataJson() {
    countRows = hot.countRows();     // 总行数
    countCols = hot.countCols();     // 总列数

    var cellObj;
    var styleDisplay;
    table_tbody = $(".htCore").find("tbody");
    table_tbody_tr;
    table_tbody_tr_td;

    for (var row_j = 0; row_j < countRows; row_j++) {
        for (var col_i = 0; col_i < countCols; col_i++) {
            table_tbody_tr = table_tbody.find("tr:eq(" + row_j + ")");
            table_tbody_tr_td = table_tbody_tr.find("td:eq(" + col_i + ")");
            //table_tbody_tr_td.html(value);
            styleDisplay = table_tbody_tr_td[0].style.display;
            // 不处理 none隐藏的td
            if (styleDisplay == "") {
                // getCell(row.col,topmost):根据行列索引，获取一个被渲染的单元格，如果该单元格未被渲染则返回null
                // 其中，row,col分别为行索引和列索引，topmost为表示是否是最上层，其值为true/false
                cellObj = hot.getCell(row_j, col_i, true);
                // 封装json
                var rowCol = row_j + "_" + col_i;
                var rowColJson = {};
                rowColJson["row"] = row_j;
                rowColJson["col"] = col_i;
                rowColJson["rowSpan"] = cellObj.rowSpan;
                rowColJson["colSpan"] = cellObj.colSpan;
                rowColJson["indexText"] = cellObj.innerText;
                var cellValue = cellObj.innerText;
                if (getStringValue(cellValue) != "") {
                    rowColJson["indexValue"] = cellValue.split(":")[0];
                    rowColJson["indexNameAppend"] = cellValue.split(":")[1];
                    rowColJson["indexName"] = cellValue.split(":")[2];
                    rowColJson["indexDef"] = cellValue.split(":")[3];
                } else {
                    rowColJson["indexValue"] = "";
                    rowColJson["indexNameAppend"] = "";
                    rowColJson["indexName"] = "";
                    rowColJson["indexDef"] = "";
                }
                createJsonForSave(rowCol, rowColJson);
            }
        }
    }

    reportHeadersFormatConsole.innerText = JSON.stringify(vm.headersFormatNew);
    vm.portalReport.reportHeadersFormatConsole = JSON.stringify(vm.headersFormatNew);
}

// 格式化html数据
function getDataHtml() {
    // 设置只读
    vm.cellReadOnly();

    countRows = hot.countRows();     // 总行数
    countCols = hot.countCols();     // 总列数
    table_tbody = $(".htCore").find("tbody");
    table_tbody_tr;
    table_tbody_tr_td;
    var indexName;

    for (var row_j = 0; row_j < countRows; row_j++) {
        for (var col_i = 0; col_i < countCols; col_i++) {
            table_tbody_tr = table_tbody.find("tr:eq(" + row_j + ")");
            table_tbody_tr_td = table_tbody_tr.find("td:eq(" + col_i + ")");
            var td_value = hot.getDataAtCell(row_j, col_i);
            if (getStringValue(td_value) != "") {
                indexName = td_value.split(":")[2];
                table_tbody_tr_td.html(indexName);
            } else {
                table_tbody_tr_td.html("");
            }
        }
    }
}

// 获取最后一行数据
function getLastHeader() {
    countRows = hot.countRows();     // 总行数
    countCols = hot.countCols();     // 总列数
    vm.portalReport.reportHeadersCountRows = countRows;
    vm.portalReport.reportHeadersCountCols = countCols;

    var row_id = countRows - 1;

    var lastRowHeaders = "";
    for (var col_i = 0; col_i < countCols; col_i++) {
        var lastValue = hot.getDataAtCell(row_id, col_i);
        if (getStringValue(lastValue) != "") {
            lastRowHeaders = lastRowHeaders + lastValue.split(":")[2] + ",";
        } else {
            lastRowHeaders = lastRowHeaders + ",";
        }
    }
    vm.portalReport.reportHeadersConsole = lastRowHeaders.substr(0, lastRowHeaders.length - 1);
}

// 参数：prop = 属性，val = 值
function createJsonForSave(prop, val) {
    // 如果 val 被忽略
    if (typeof val === "undefined") {
        // 删除属性
        delete vm.headersFormatNew[prop];
    }
    else {
        // 添加 或 修改
        vm.headersFormatNew[prop] = val;
    }
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
    getDataHtml();
    // 先设置TABLE的HTML
    // encodeURI() 函数不转义：;/?:@&=+$,# (使用 encodeURIComponent 对这些字符进行编码),decodeURI 方法返回一个字符串值
    reportOuterHtml.innerText = encodeURI($(".ht_master .handsontable table").prop("outerHTML"));
    //reportOuterHtml.innerText = encodeURI($(".htCore").prop("outerHTML"));
    vm.portalReport.reportOuterHtml = encodeURI($(".htCore").prop("outerHTML"));

    // 延迟加载1秒
    clearTimeout(autosaveNotification);
    autosaveNotification = setTimeout(function () {

    }, 1000);

    // 保存table后再渲染表格
    hot.render();

    // 处理合并单元格
    optMerge();

    // 设置表格数据
    reportHotData.innerText = JSON.stringify(hot.getData());
    vm.portalReport.reportHotData = JSON.stringify(hot.getData());

    // 设置合并单元格数据
    reportMergedCellInfoCollection.innerText = JSON.stringify(hot.mergeCells.mergedCellInfoCollection);
    vm.portalReport.reportMergedCellInfoCollection = JSON.stringify(hot.mergeCells.mergedCellInfoCollection);

    // 获取最后一行数据
    getLastHeader();

    // 设置格式化数据
    getDataJson();
    // 保存或更新
    vm.saveOrUpdate();
});
