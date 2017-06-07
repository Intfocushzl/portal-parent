$(function () {
    $("#jqGrid").jqGrid({
        url: '../businessmannotice/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true },
            {label: '公告类型', name: 'noticeType', index: 'notice_type', width: 80 },
            {label: '发布范围角色编码', name: 'noticeRoles', index: 'notice_roles', width: 80 },
            {label: '发布范围群组编码', name: 'noticeGroups', index: 'notice_groups', width: 80 },
            {label: '是否APP推送', name: 'appPush', index: 'app_push', width: 80 },
            {label: '标题', name: 'title', index: 'title', width: 80 }, 
            /*{label: '正文', name: 'content', index: 'content', width: 80 },
            {label: '摘要', name: 'abstracts', index: 'abstracts', width: 80 }, 
            {label: '封面图', name: 'coverImg', index: 'cover_img', width: 80 }, */
            {label: '文章状态', name: 'status', index: 'status', width: 80 },
            {label: '阅读数', name: 'pageview', index: 'pageview', width: 80 }, 
            {label: '过期时间，有效截止期', name: 'expireTime', index: 'expire_time', width: 80 }, 
            {label: '创建时间', name: 'createTime', index: 'CREATE_TIME', width: 80 }, 
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
        businessmanNotice: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    id: vm.businessmanNotice.id,
                    remark: vm.businessmanNotice.remark
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.businessmanNotice = {};
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
            var url = vm.businessmanNotice.id == null ? "../businessmannotice/save" : "../businessmannotice/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.businessmanNotice),
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
                    url: "../businessmannotice/delete",
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
            $.get("../businessmannotice/info/"+id, function(r){
                vm.businessmanNotice = r.businessmanNotice;
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