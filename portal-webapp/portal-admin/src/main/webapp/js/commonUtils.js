// 判断空
function getStringValue(indexValue) {
    if ($.trim(indexValue) == null || $.trim(indexValue) == undefined || $.trim(indexValue) == "") {
        return "";
    }
    return indexValue;
}

// 输出指定位数的随机数的随机整数
function RndNum(n) {
    var rnd = "";
    for (var i = 0; i < n; i++)
        rnd += Math.floor(Math.random() * 10);
    return rnd;
}