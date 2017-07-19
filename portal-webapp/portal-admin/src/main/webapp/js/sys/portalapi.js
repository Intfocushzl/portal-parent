// 获取 window.location.href参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

var code = unescape(getQueryString("code"));

// 格式化json
$(function () {
    $('#toggle-formatter').on('click', function () {
        $("#json").JSONView($("#json_str_text").val());
    });

    $('#toggle-btn').on('click', function () {
        $('#json').JSONView('toggle');
    });

    $('#toggle-level1-btn').on('click', function () {
        $('#json').JSONView('toggle', 1);
    });

    $('#toggle-level2-btn').on('click', function () {
        $('#json').JSONView('toggle', 2);
    });

    $('#toggle-level3-btn').on('click', function () {
        $('#json').JSONView('toggle', 3);
    });

    $('#toggle-level4-btn').on('click', function () {
        $('#json').JSONView('toggle', 4);
    });

    $('#toggle-level5-btn').on('click', function () {
        $('#json').JSONView('toggle', 5);
    });
});

/*返回顶部*/
$(window).scroll(function () {
    var sc = $(window).scrollTop();
    var rwidth = $(window).width()
    if (sc > 0) {
        $("#goTopBtn").css("display", "block");
        $("#goTopBtn").css("left", (rwidth - 48) + "px")
    } else {
        $("#goTopBtn").css("display", "none");
    }
})
$("#goTopBtn").click(function () {
    var sc = $(window).scrollTop();
    $('body,html').animate({scrollTop: 0}, 500);
})

// 测试请求
var test_url = "http://10.67.241.218/yhportal/userAuthMenu/listRoleMenu?roleId=1&token=adminMGdp1nT0Q3qxBqDvFNKW0QM9354d8db013b4bb08d4e116e6fd1893e";
//var test_url = "http://10.67.241.218/yhportal/api/portal/custom?yongHuiReportCustomCode=REP_000003&reportLabel=2&sDate=2017/6/19&eDate=2017/7/19&holdName=%E7%AC%AC%E4%BA%8C%E9%9B%86%E7%BE%A4&classId=&areaId=&areaMans=&shopId=&token=adminMGdp1nT0Q3qxBqDvFNKW0QM9354d8db013b4bb08d4e116e6fd1893e";
//var test_url = "http://10.67.241.218/yhportal/api/portal/custom?token=CS123456mXB84h6U5FUO7nsdu0Eq7c5373b211054c7b8681fe829c62cc2c";
var dev_url = "http://10.67.241.234/yhportal/api/portal/custom?token=CS123456mXB84h6U5FUO7nsdu0Eq7c5373b211054c7b8681fe829c62cc2c";

$("#test_url").val(test_url);
$("#dev_url").val(dev_url);

function getJsonTest() {
    $.get(test_url, function (r) {
        $(".json_str_text").val(JSON.stringify(r));
        $("#json").JSONView(JSON.stringify(r));
    });
}

function getJsonDev() {
    $.get(dev_url, function (r) {
        $(".json_str_text").val(JSON.stringify(r));
        $("#json").JSONView(JSON.stringify(r));
    });
}