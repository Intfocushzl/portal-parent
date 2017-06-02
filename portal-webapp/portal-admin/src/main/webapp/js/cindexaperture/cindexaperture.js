$(function () {
    $("#jqGrid").jqGrid({
        url: '../cindexaperture/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true },
            {label: '一级流程', name: 'fprocess', index: 'fprocess', width: 80 }, 
            {label: '二级流程', name: 'sprocess', index: 'sprocess', width: 80 }, 
            {label: '主题', name: 'theme', index: 'theme', width: 80 }, 
            {label: '字段名', name: 'fieldname', index: 'fieldname', width: 80 }, 
            {label: '指标名称', name: 'indexname', index: 'indexname', width: 80 }, 
            {label: '一集群定义', name: 'fristdef', index: 'fristdef', width: 80 }, 
            {label: '二集群定义', name: 'bravodef', index: 'bravodef', width: 80 }, 
            {label: '其他平台定义', name: 'otherdef', index: 'otherdef', width: 80 }, 
            {label: '衡量内容', name: 'measure', index: 'measure', width: 80 }, 
            {label: '数据源', name: 'ds', index: 'ds', width: 80 }, 
            {label: '表名', name: 'tablename', index: 'tablename', width: 80 }, 
            {label: '负责人', name: 'cman', index: 'cman', width: 80 }, 
            {label: '负责人电话', name: 'cmanphone', index: 'cmanphone', width: 80 }, 
            {label: '负责人邮箱', name: 'cmanemail', index: 'cmanemail', width: 80 }, 
            {label: '平台', name: 'platform', index: 'platform', width: 80 }, 
            {label: '连接信息', name: 'connectioninfo', index: 'connectioninfo', width: 80 }, 
            {label: '数据库', name: 'database', index: 'database', width: 80 }, 
            {label: 'sql语法', name: 'sql', index: 'sql', width: 80 }, 
            {label: '创建人', name: 'createdBy', index: 'created_by', width: 80 }, 
            {label: '关联指标', name: 'cindexname', index: 'cindexname', width: 80 }, 
            {label: '关联表', name: 'ctablename', index: 'ctablename', width: 80 }, 
            {label: '血缘分析', name: 'bloodrelation', index: 'bloodrelation', width: 80 }
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
        cIndexAperture: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    id: vm.cIndexAperture.id,
                    remark: vm.cIndexAperture.remark
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.cIndexAperture = {};
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
            var url = vm.cIndexAperture.id == null ? "../cindexaperture/save" : "../cindexaperture/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.cIndexAperture),
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
                    url: "../cindexaperture/delete",
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
            $.get("../cindexaperture/info/"+id, function(r){
                vm.cIndexAperture = r.cIndexAperture;
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