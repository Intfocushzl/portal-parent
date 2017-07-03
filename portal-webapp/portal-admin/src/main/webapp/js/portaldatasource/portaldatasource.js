$(function () {
    $("#jqGrid").jqGrid({
        url: '../portaldatasource/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50 , hidden: true},
            {label: '唯一编码', name: 'code', index: 'code', width: 80 , key: true },
            {label: '标题简介', name: 'title', index: 'title', width: 80 }, 
            {label: '连接地址', name: 'url', index: 'url', width: 80 },
            {label: '类型驱动', name: 'jdbc_driver', index: 'jdbc_driver', width: 80 },
            {label: '用户名', name: 'user', index: 'user', width: 80 },
            {label: '创建人', name: 'username', index: 'username', width: 80 },
            {label: '创建时间', name: 'create_time', index: 'create_time', width: 80 },
            /* {label: '用户密码', name: 'password', index: 'password', width: 80 },
            {label: '连接池最小连接', name: 'minConnectionsPerPartition', index: 'min_connections_per_partition', width: 80 },
            {label: '连接池最大连接', name: 'maxConnectionsPerPartition', index: 'max_connections_per_partition', width: 80 },
            {label: '连接选项', name: 'connectionTag', index: 'connection_tag', width: 80 },
             */
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
        portalDataSource: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    code: vm.portalDataSource.code,
                    title: vm.portalDataSource.title
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.portalDataSource = {};
            $("input[name='code']").removeAttr("readonly");
            vm.getNewMaxCode();
        },
        update: function (event) {
            var code = getSelectedRow();
            if(code == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(code);
            $("input[name='code']").attr("readonly","readonly");
        },
        saveOrUpdate: function (event) {
            var code = vm.portalDataSource.code;
            var id = vm.portalDataSource.id;
            var url = vm.portalDataSource.id == null ? "../portaldatasource/save" : "../portaldatasource/update";
            if(id==null){
                $.get("../portaldatasource/info/"+code,function(r){
                    if(r.portalDataSource != null){
                        alert("唯一编码已存在，请重新输入");
                    }else{
                        vm.addAndUpdate(url);
                    }
                })
            }else{
                vm.addAndUpdate(url);
            }
        },
        addAndUpdate:function(url){
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.portalDataSource),
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
            var codes = getSelectedRows();
            if(codes == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../portaldatasource/delete",
                    data: JSON.stringify(codes),
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
        getInfo: function(code){
            $.get("../portaldatasource/info/"+code, function(r){
                vm.portalDataSource = r.portalDataSource;
                vm.portalDataSource.codeOld = vm.portalDataSource.code;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        },
        addRedis: function (event) {
            var codes = getSelectedRows();
            if(codes == null){
                return ;
            }
            $.ajax({
                type:"POST",
                url:"../portaldatasource/addRedis",
                data: JSON.stringify(codes),
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
        },
        getNewMaxCode: function () {
            $.get("../portaldatasource/getNewMaxCode/", function (r) {
                console.info(r.newMaxCode);
                $("#code").val(r.newMaxCode);
                vm.portalDataSource.code = r.newMaxCode;
            });
        },

    }
});