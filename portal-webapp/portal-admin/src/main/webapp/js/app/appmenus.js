$(function () {
    $("#jqGrid").jqGrid({
        url: '../app/menus/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 80, hidden: true},
            {label: '菜单ID', name: 'menuId', index: 'menuId', width: 80, key: true},
            {
                label: '一级标题', name: 'type', index: 'type', width: 80, formatter: function (value) {
                if (value == 1) {
                    return "生意概况";
                }
                if (value == 2) {
                    return "报表";
                }
                if (value == 3) {
                    return "专题";
                }
            }
            },
            {label: '二级标题', name: 'subName1', index: 'subName1', width: 80},
            {label: '三级标题', name: 'subName2', index: 'subName2', width: 80},
            {label: '标题', name: 'title', index: 'title', width: 80},
            // {label: '报表ID', name: 'reportId', index: 'reportId', width: 80 },
            // {label: '模板ID', name: 'templateId', index: 'templateId', width: 80 },
            {label: '链接地址', name: 'url', index: 'url', width: 80},
            {label: '图标', name: 'icon', index: 'icon', width: 80},
            {label: '图标地址', name: 'iconUrl', index: 'iconUrl', width: 80},
            {
                label: '是否公开', name: 'publicly', index: 'publicly', width: 80, formatter: function (value) {
                if (value == null) {
                    return "";
                }
                return value ?
                    '<span class="label label-success">是</span> ' :
                    '<span class="label label-danger">否</span>';
            }
            },
            // {
            //     label: '语音播报', name: 'hasAudio', index: 'hasAudio', width: 80, formatter: function (value) {
            //     if (value == null) {
            //         return "";
            //     }
            //     return value ?
            //         '<span class="label label-success">有</span> ' :
            //         '<span class="label label-danger">无</span>';
            // }
            // },
            // {
            //     label: '报表健康值', name: 'healthValue', index: 'healthValue', width: 80, formatter: function (value) {
            //     if (value == null) {
            //         return "";
            //     }
            //     if (value < 60) {
            //         return '<span class="label label-danger">' + value + '</span>';
            //     }
            //     if (value >= 90) {
            //         return '<span class="label label-success">' + value + '</span>';
            //     }
            //     return '<span class="label label-info">' + value + '</span>';
            // }
            // },
            // {label: '备注', name: 'remark', index: 'remark', width: 80},
            // {label: '父菜单排序', name: 'groupOrder', index: 'groupOrder', width: 80},
            // {label: '子菜单排序', name: 'itemOrder', index: 'itemOrder', width: 80},
            {
                label: '排序',
                name: 'operation',
                index: 'operation',
                width: 120,
                formatter: function (value, options, row) {
                    return "<span class='label label-success' onclick='vm.getParentMenuList(" + row.menuId + ")'>三级菜单</span>&nbsp;&nbsp;<span class='label label-success' onclick='vm.getChildMenuList(" + row.menuId + ")'>四级菜单</span>";
                }
            },
        ],
        viewrecords: true,
        height: 385,            // 表格高度
        rowNum: 50,             // 一页显示的行记录数
        rowList: [50, 100],     // 翻页控制条中 每页显示记录数可选集合
        rownumbers: true,       // 是否显示行号，默认值是false，不显示
        rownumWidth: 25,
        autowidth: true,
        autoScroll: true,
        shrinkToFit: true,

        multiselect: true,
        pager: "#jqGridPager",          // 翻页DOM
        jsonReader: {                   // 重写后台返回数据的属性
            root: "page.list",          // 将rows修改为page.list
            page: "page.currPage",      // 将page修改为page.currPage
            total: "page.totalPage",    // 将total修改为page.totalPage
            records: "page.totalCount"  // 将records修改为page.totalCount
        },
        prmNames: {              // 改写请求参数属性
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            //设置高度
            $("#jqGrid").jqGrid('setGridHeight', getWinH());
        }
    });
});


var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        appMenu: {
            type: $("input:radio[name=t]:checked").val() == null ? 1 : $("input:radio[name=t]:checked").val()
        },
        // nextMenuId:-1,
        kpiList: [],
        analysisList: [],
        appList: [],
        menuList: []
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    menuId: vm.appMenu.menuId,
                    subName2: vm.appMenu.subName2,
                    title: vm.appMenu.title,
                    type: vm.appMenu.type
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.appMenu = {
                type: $("input:radio[name=t]:checked").val() == null ? 1 : $("input:radio[name=t]:checked").val(),
                id: null,
                publicly: false
            };
            vm.getRoleList(null);
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            // var rowData = $("#jqGrid").jqGrid("getRowData",id);
            // if(rowData == null){
            //     return ;
            // }
            // if (rowData.publicly.indexOf("是")){
            //     rowData.publicly=true;
            // }else if (rowData.publicly.indexOf("否")){
            //     rowData.publicly=false;
            // }else {
            //     rowData.publicly=null;
            // }
            // if (rowData.type=="生意概况"){
            //     rowData.type=1;
            // }
            // if (rowData.type=="报表"){
            //     rowData.type=2;
            // }
            // if (rowData.type=="专题"){
            //     rowData.type=3;
            // }
            // rowData.healthValue=0;
            // vm.appMenu = rowData;
            var o = new Object();
            o.type = $("input:radio[name=t]:checked").val();
            o.menuId = id;
            vm.showList = false;
            vm.title = "修改";
            if (vm.appMenu.publicly == null) {
                vm.appMenu.publicly = false;
            }
            vm.getInfo(o);

        },
        saveOrUpdate: function (event) {
            var aSpan = $("span.active");
            var arr = new Array;
            for (var i = 0; i < aSpan.length; i++) {
                if (aSpan[i].className == 'active') {
                    arr.push(aSpan[i].id.substring(4));
                    if (aSpan[i].id == "undefined") {
                        alert(aSpan[i].id + "-->PC端无此角色");
                        return;
                    }
                }
            }
            if (arr == "undefined") {
                alert("请选择角色");
                return;
            }
            vm.appMenu.roleIdList = arr;
            var url = vm.appMenu.id == null ? "../app/menus/save" : "../app/menus/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.appMenu),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var menuIds = getSelectedRows();
            if (menuIds == null) {
                return;
            }
            var rowData = $("#jqGrid").jqGrid("getRowData", menuIds[0]);
            if (rowData == null) {
                return;
            }
            if (rowData.type == "生意概况") {
                rowData.type = 1;
            }
            if (rowData.type == "报表") {
                rowData.type = 2;
            }
            if (rowData.type == "专题") {
                rowData.type = 3;
            }
            var type = rowData.type;
            confirm('确定要删除选中的记录？', function () {
                var o = new Object();
                o.type = type;
                o.menuIds = menuIds;
                $.ajax({
                    type: "POST",
                    url: "../app/menus/delete",
                    data: JSON.stringify(o),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (params) {
            $.get("../app/menus/info", params, function (r) {
                vm.appMenu = r.appMenu;
                vm.getRoleList(params);
                vm.getReportList();
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                type: vm.appMenu.type
            }).trigger("reloadGrid");
        },
        getRoleList: function (rowData) {
            var menuId = rowData == null ? -1 : rowData.menuId;
            $.get("../app/menus/selectRole?menuId=" + menuId, rowData, function (result) {
                // var objs = jQuery.parseJSON(); //由JSON字符串转换为JSON对象
                var objs = result.list;
                var htmlString = "<p><span class='tags'>";
                for (var i = 0; i < objs.length; i++) {
                    if (objs[i].active == 1) {
                        htmlString += "<span id='span" + objs[i].roleId + "' class='active'  onClick='spanClick(" + objs[i].roleId + ")'>" + objs[i].roleName + "</span>";
                    } else {
                        htmlString += "<span id='span" + objs[i].roleId + "'  onClick='spanClick(" + objs[i].roleId + ")'>" + objs[i].roleName + "</span>";
                    }
                }
                htmlString += "</span> </p>";
                $("#tagsdlg").html(htmlString);
            });
        },
        getReportList: function () {
            $.get("../app/menus/selectReport", function (r) {
                var reportList = r.data.list;
                $("#reportList").empty();
                for (var i = 0; i < reportList.length; i++) {
                    $("#reportList").append("<option value='" + reportList[i].kpiId + "'>" + reportList[i].kpiId + "<===>" + reportList[i].title + "</option>");
                }
                // refresh刷新和render渲染操作，必不可少  +r.data[i].id+"<===>"
                $('#reportList').selectpicker('refresh');
                $('#reportList').selectpicker('render');
                if (vm.appMenu.id!=null&&vm.appMenu.type==1){
                    $('#reportList').selectpicker('val', vm.appMenu.link);
                }
            });
        },
        getParentMenuList: function (menuId) {
            var rowData = $("#jqGrid").jqGrid("getRowData", menuId);
            var url = "../app/menus/menuSortParentList";
            var obj = {
                type: vm.appMenu.type,
                subName1: rowData.subName1
            };
            $.get(url, obj, function (r) {
                var list = r.list;
                $('#list1 li').remove();
                for (var i = 0; i < list.length; i++) {
                    var parent = list[i];
                    $("#list1").append("<li value='" + parent.id + " '>" + parent.name + "<span   class='remove' style='float: right'>" + parent.groupOrder + "</span></li>");
                }
                $("#dialog-div1").dialog("open");
            });


            return false;
        },
        getChildMenuList: function (menuId) {
            var rowData = $("#jqGrid").jqGrid("getRowData", menuId);
            var url = "../app/menus/menuSortChildrenList";
            var o = {
                subName2: rowData.subName2,
                type: vm.appMenu.type,
            };
            $.get(url, o, function (r) {
                var list = r.list;
                $('#list2 li').remove();
                for (var i = 0; i < list.length; i++) {
                    var child = list[i];
                    $("#list2").append("<li value='" + child.id + " '>" + child.name + "<span   class='remove' style='float: right'>" + child.itemOrder + "</span></li>");
                }
                $("#dialog-div2").dialog("open");
            });
            return false;
        },
    }
});


// 属性设置框
$("#dialog-div1").dialog({
    autoOpen: false,
    width: 400,
    minHeight: 480,
    modal: true,
    show: {
        effect: "explode",//eblind
        duration: 100
    },
    hide: {
        effect: "explode",
        duration: 100
    },
    buttons: {
        "确定": function () {
            saveOrder1();
            var strs = $("input[name=list1SortOrder]").val().split("|");
            $.ajax({
                type: "GET",
                url: "../app/menus/doParentSort",
                data: {
                    sortStr: $("input[name=list1SortOrder]").val(),
                    menuType: vm.appMenu.type
                },
                success: function (r) {
                    // 关闭窗口
                    $("#dialog-div1").dialog("close");
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        "取消": function () {
            $(this).dialog("close");
        }
    },
    close: function () {
    }
});
$("#dialog-div2").dialog({
    autoOpen: false,
    width: 400,
    minHeight: 480,
    modal: true,
    show: {
        effect: "explode",//eblind
        duration: 100
    },
    hide: {
        effect: "explode",
        duration: 100
    },
    buttons: {
        "确定": function () {
            saveOrder2();
            $.ajax({
                type: "GET",
                url: "../app/menus/doChildrenSort",
                data: {
                    sortStr: $("input[name=list2SortOrder]").val(),
                    menuType: vm.appMenu.type
                },
                success: function (r) {
                    // 关闭窗口
                    $("#dialog-div2").dialog("close");
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        "取消": function () {
            $(this).dialog("close");
        }
    },
    close: function () {
    }
});
function spanClick(spanid) {

    if ($("#span" + spanid).hasClass('active')) {
        $("#span" + spanid).removeClass("active");
    } else {
        $("#span" + spanid).addClass("active");
    }
    // $("span").each(function () {
    //     $(this).removeClass("active");
    // });
    // $("#span" + spanid).addClass("active");
}

// $.get("../app/menus/getNextMenuId", function(r){
//     vm.nextMenuId = r.nextMenuId;
// });
$("input:radio[name=t]").change(function () {
    vm.appMenu = {type: $("input:radio[name=t]:checked").val()};
    vm.query();
});

vm.getReportList();


//可拖拽列表
//Default Action
$(".tab_content").hide(); //Hide all content
$("ul.tabs li:first").addClass("active").show(); //Activate first tab
$(".tab_content:first").show(); //Show first tab content

//On Click Event
$("ul.tabs li").click(function () {
    $("ul.tabs li").removeClass("active"); //Remove any "active" class
    $(this).addClass("active"); //Add "active" class to selected tab
    $(".tab_content").hide(); //Hide all tab content
    var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
    $(activeTab).fadeIn(); //Fade in the active content
    return false;
});

$("#list1").dragsort({
    dragSelector: "li",
    dragBetween: false,
    dragEnd: saveOrder1,
    placeHolderTemplate: "<li></li>"
});

function saveOrder1() {
    var data = $("#list1 li").map(function () {
        return $(this).attr("value");
    }).get();
    $("input[name=list1SortOrder]").val(data.join("|"));
}

$("#list2").dragsort({
    dragSelector: "li",
    dragBetween: false,
    dragEnd: saveOrder2,
    placeHolderTemplate: "<li></li>"
});

function saveOrder2() {
    var data = $("#list2 li").map(function () {
        return $(this).attr("value");
    }).get();
    $("input[name=list2SortOrder]").val(data.join("|"));
}
