$(function () {
    $("#jqGrid").jqGrid({
        url: '../sysmailset/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true },
            {label: '唯一主键', name: 'code', index: 'code', width: 80 }, 
            {label: '发送邮箱', name: 'fromAddress', index: 'from_address', width: 80 }, 
            {label: '发送邮箱密码', name: 'fromAddressPwd', index: 'from_address_pwd', width: 80 }, 
            {label: '简单邮件传输协议', name: 'smtp', index: 'smtp', width: 80 }, 
            {label: 'debug模式 0 开启 1 关闭', name: 'debug', index: 'debug', width: 80 }, 
            {label: '需要身份验证  0需要 1 不需要', name: 'auth', index: 'auth', width: 80 }, 
            {label: '发送邮件的服务Ip', name: 'host', index: 'host', width: 80 }, 
            {label: '端口', name: 'port', index: 'port', width: 80 }, 
            {label: '发件抄送人邮箱', name: 'copyto', index: 'copyto', width: 80 }, 
            {label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 
        ],
        viewrecords: true,     // 是否显示行号，默认值是false，不显示
        height: 385,            // 表格高度
        rowNum: 50,             // 一页显示的行记录数
        rowList : [50,100],     // 翻页控制条中 每页显示记录数可选集合
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",          // 翻页DOM
        jsonReader : {                   // 重写后台返回数据的属性
            root: "page.list",          // 将rows修改为page.list
            page: "page.currPage",      // 将page修改为page.currPage
            total: "page.totalPage",    // 将total修改为page.totalPage
            records: "page.totalCount"  // 将records修改为page.totalCount
        },
        prmNames : {              // 改写请求参数属性
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            //设置高度
            $("#jqGrid").jqGrid('setGridHeight', getWinH());
        }
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        sysMailSet: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    id: vm.sysMailSet.id,
                    remark: vm.sysMailSet.remark
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.sysMailSet = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.sysMailSet.id == null ? "../sysmailset/save" : "../sysmailset/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.sysMailSet),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                     });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../sysmailset/delete",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function(id){
            $.get("../sysmailset/info/"+id, function(r){
                vm.sysMailSet = r.sysMailSet;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        }
    }
});