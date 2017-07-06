$(function () {
    $("#jqGrid").jqGrid({
        url: '../app/menus/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: '类型', name: 'type', index: 'type', width: 80,formatter:function(value){
                if (value==1){
                    return "仪表盘";
                }
                if (value==2){
                    return "分析";
                }
                if (value==3){
                    return "应用";
                }
            } },
            {label: '菜单ID', name: 'menuId', index: 'menuId', width: 80 ,key: true },
            {label: '父菜单', name: 'name', index: 'name', width: 80 },
            {label: '子菜单', name: 'subName', index: 'subName', width: 80 },
            {label: 'URL', name: 'url', index: 'url', width: 80 },
            {label: '创建人', name: 'createUser', index: 'create_user', width: 80 },
            {label: '创建时间', name: 'createdAt', index: 'created_at', width: 80 },
            {label: '修改人', name: 'updateUser', index: 'update_user', width: 80 },
            {label: '修改时间', name: 'updatedAt', index: 'updated_at', width: 80 },
            {label: '分组排序', name: 'groupOrder', index: 'group_order', width: 80 },
            {label: '组内排序', name: 'itemOrder', index: 'item_order', width: 80 },
            {label: '语音更新时间', name: 'audioUpdatedAt', index: 'audio_updated_at', width: 80 }
        ],
        viewrecords: true,
        height: 385,            // 表格高度
        rowNum: 50,             // 一页显示的行记录数
        rowList : [50,100],     // 翻页控制条中 每页显示记录数可选集合
        rownumbers: true,       // 是否显示行号，默认值是false，不显示
        rownumWidth: 25,
        autowidth:true,
        autoScroll: true,
        shrinkToFit:true,

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
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });
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
        appMenu: {
            type:$("input:radio[name=t]:checked").val()==null?1:$("input:radio[name=t]:checked").val()
        },
        kpiList:[],
        analysisList:[],
        appList:[],
        menuList:[]
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    menuId: vm.appMenu.menuId,
                    name: vm.appMenu.name,
                    subName: vm.appMenu.subName,
                    type: vm.appMenu.type
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.appMenu = {type:$("input:radio[name=t]:checked").val()==null?1:$("input:radio[name=t]:checked").val()};
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";
            var rowData = $("#jqGrid").jqGrid("getRowData",id);
            if(rowData == null){
                return ;
            }
            vm.getInfo(rowData)
        },
        saveOrUpdate: function (event) {
            var url = vm.appMenu.menuId == null ? "../app/menus/save" : "../app/menus/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.appMenu),
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
            var menuIds = getSelectedRows();
            if(menuIds == null){
                return ;
            }
            var rowData = $("#jqGrid").jqGrid("getRowData",menuIds[0]);
            if(rowData == null){
                return ;
            }
            var  type=rowData.type;
            confirm('确定要删除选中的记录？', function(){
                var o = new Object();
                o.type=type;
                o.menuIds=menuIds;
                 $.ajax({
                    type: "POST",
                    url: "../app/menus/delete",
                    data: JSON.stringify(o),
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
        getInfo: function(params){
            $.get("../app/menus/info",params, function(r){
                vm.appMenu = r.appMenu;
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

$("input:radio[name=t]").change(function () {
    vm.appMenu={type:$("input:radio[name=t]:checked").val()};
});