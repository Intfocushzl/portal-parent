// 判断空
function getStringValue(indexValue) {
    if ($.trim(indexValue) == null || $.trim(indexValue) == undefined || $.trim(indexValue) == "") {
        return "";
    }
    return indexValue;
}