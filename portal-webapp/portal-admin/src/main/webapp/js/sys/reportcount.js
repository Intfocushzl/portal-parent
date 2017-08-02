$(function () {
    $("#jqGrid").jqGrid({
        url: '../sysoperationlog/reprtcount',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: '工号', name: 'jobNumber', index: 'jobNumber', width: 50, key: true },
            {label: '姓名', name: 'NAME', index: 'NAME', width: 80 },
            {label: 'code', name: 'code', index: 'code', width: 80 },
            {label: 'title', name: 'title', index: 'title', width: 80 },
            {label: '次数', name: 'cnt', index: 'cnt', width: 80 },
            {label: '请求时间', name: 'startTime', index: 'startTime', width: 80 },
           /* {label: 'ip', name: 'ip', index: 'ip', width: 80 },
            {label: '失败信息', name: 'error', index: 'error', width: 80 },
            {label: '请求状态', name: 'status', index: 'status', width: 80 }*/
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
        /*q:{
            jobnumber: null,
            code:null,
            startTime:null,
            endTime:null
        },*/
        showList: true,
        title: null,
        sysOperationLog: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    //keyword: $("#keyword").val()
                    jobNumber: vm.sysOperationLog.jobnumber,
                    code: vm.sysOperationLog.code,
                    startTime: $("#startTime").val(),
                    endTime: $("#endTime").val()
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.sysOperationLog = {};
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
            var url = vm.sysOperationLog.id == null ? "../sysoperationlog/save" : "../sysoperationlog/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.sysOperationLog),
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
                    url: "../sysoperationlog/delete",
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
            $.get("../sysoperationlog/info/"+id, function(r){
                vm.sysOperationLog = r.sysOperationLog;
                //更新输入框高度
                autosize.update($('textarea'));
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


var $fdate = $(".form_date");
$fdate.datetimepicker({
    language: 'zh-CN',
    format: "yyyy-mm-dd",
    weekStart: 0,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0,
});



// 图片自适应
$(function () {
    $("img").autoIMG();
});


// textarea 高度自动扩展
autosize($('textarea'));