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

// 数组转字符串
function arrayToString(arr) {
    //var arr = [1,2,3,4,'啊啊','merge'];
    var str = arr.join(',');
    //console.log(str); // 1,2,3,4,啊啊,merge
    return str;
}

// 字符串转数组
function stringToArray(str) {
    //var str = '1,2,3,4,啊啊,merge';
    var arr = str.split(',');
    //console.log(arr);     // ["1", "2", "3", "4", "啊啊", "merge"]   数组
    return arr;
}
