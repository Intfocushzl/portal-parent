$(function () {
    $("#jqGrid").jqGrid({
        url: '../app/users/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: '用户ID', name: 'id', index: 'id', width: 80, key: true},
            {label: '用户名', name: 'userName', index: 'user_name', width: 80},
            {label: '工号', name: 'userNum', index: 'user_num', width: 80},
            {label: '邮箱', name: 'email', index: 'email', width: 80},
            {label: '手机', name: 'mobile', index: 'mobile', width: 80},
            {label: '电话', name: 'tel', index: 'tel', width: 80},
            {label: '职位', name: 'position', index: 'position', width: 80},
            {label: '备注', name: 'memo', index: 'memo', width: 80},
        ],
        viewrecords: true,
        height: 385,            // 表格高度
        rowNum: 50,             // 一页显示的行记录数
        rowList: [50, 100],     // 翻页控制条中 每页显示记录数可选集合
        rownumbers: true,       // 是否显示行号，默认值是false，不显示
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
            $("#jqGrid").jqGrid('setFrozenColumns');
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
        appUsers: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    id: vm.appUsers.id,
                    userName: vm.appUsers.userName,
                    userNum: vm.appUsers.userNum
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.appUsers = {};
            vm.getRoleList(-1);
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var ids = $("span.active").attr("id") + "";
            if (ids == "undefined") {
                alert("请选择角色");
                return;
            }
            var arr = new Array;
            arr.push(ids.substring(4));
            vm.appUsers.roleIdList = arr;
            var url = vm.appUsers.id == null ? "../app/users/save" : "../app/users/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.appUsers),
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
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../app/users/delete",
                    data: JSON.stringify(ids),
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
        getInfo: function (id) {
            $.get("../app/users/info/" + id, function (r) {
                vm.appUsers = r.appUsers;
                vm.getRoleList(id);
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        getRoleList: function (id) {
            $.get("../app/users/roleSelect/" + id, function (result) {
                // var objs = jQuery.parseJSON(); //由JSON字符串转换为JSON对象
                var objs = result.list;

                var htmlString = "<p><span class='tags'>";
                for (var i = 0; i < objs.length; i++) {
                    if (objs[i].active == 1) {
                        htmlString += "<span id='span" + objs[i].id + "' class='active'  onClick='spanClick(" + objs[i].id + ")'>" + objs[i].roleName + "</span>";
                    } else {
                        htmlString += "<span id='span" + objs[i].id + "'  onClick='spanClick(" + objs[i].id + ")'>" + objs[i].roleName + "</span>";
                    }
                }
                htmlString += "</span> </p>";
                document.getElementById("tagsdlg").innerHTML = htmlString;
            });

        }
    }
});


function spanClick(spanid) {

    // if ($("#span" + spanid).hasClass('active')) {
    //     $("#span" + spanid).removeClass("active");
    // } else {
    //     $("#span" + spanid).addClass("active");
    // }
    $("span").each(function () {
        $(this).removeClass("active");
    });
    $("#span" + spanid).addClass("active");
}

function selectRoleIds() {
    var role_id = $("span.active").attr("id").substring(4);
    console.log(role_id);
}