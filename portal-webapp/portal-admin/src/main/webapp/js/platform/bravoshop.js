$(function () {
    $("#jqGrid").jqGrid({
        url: '../bravoshop/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: '老店编号', name: 'shopid', index: 'ShopID', width: 50, key: true },
            {label: '新店编号', name: 'sapShopid', index: 'SAP_ShopID', width: 80 }, 
            {label: '门店名称', name: 'sname', index: 'SName', width: 80 }, 
            {label: '门店编码', name: 'shopno', index: 'ShopNo', width: 80 }, 
            {label: '是否开店', name: 'congou', index: 'Congou', width: 80 , formatter: function (value) {
                if (value === 1) {
                    return '<span class="label label-success" >正常营业中</span>';
                }
                if (value === 2) {
                    return '<span class="label label-danger" >关店</span>';
                }
            }
            },
            {label: '开店日期', name: 'opendate', index: 'OpenDate', width: 80 },
            {label: '区域编码', name: 'sno', index: 'sNo', width: 80 }, 
            {label: '老大区ID', name: 'areaid', index: 'AreaID', width: 80 },
            {label: '老大区称', name: 'areaname', index: 'AreaName', width: 80 },
            {label: '新大区ID', name: 'area', index: 'Area', width: 80 },
            {label: '新大区名称', name: 'areamans', index: 'AreaMans', width: 80 },
            {label: '业态编码', name: 'typeComm', index: 'type_comm', width: 80 },
            {label: '业态', name: 'typename', index: 'typename', width: 80 },
            {label: '省份', name: 'province', index: 'Province', width: 80 },
            {label: '城市', name: 'city', index: 'City', width: 80 }, 
            {label: '店组编号', name: 'shopgroupid', index: 'ShopGroupID', width: 80 }, 
            {label: '店组名称', name: 'shopgroupname', index: 'ShopGroupName', width: 80 }, 
            {label: '更新时间', name: 'updatetime', index: 'updatetime', width: 80 }
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
        bravoShop: {}
    },
    methods: {
        query: function () {
            //vm.reload();
            if ($("select#select option:selected").val()=="areaname"){
                vm.bravoShop.areaname= $("#show").val();
                vm.bravoShop.areamans="";
                vm.bravoShop.shopid="";
                vm.bravoShop.sapShopid="";
                vm.bravoShop.sname="";
            }
            if ($("select#select option:selected").val()=="areamans"){
                vm.bravoShop.areamans=$("#show").val();
                vm.bravoShop.areaname="";
                vm.bravoShop.shopid="";
                vm.bravoShop.sapShopid="";
                vm.bravoShop.sname="";
            }
            if ($("select#select option:selected").val()=="shopid"){
                vm.bravoShop.shopid=$("#show").val();
                vm.bravoShop.areamans="";
                vm.bravoShop.areaname="";
                vm.bravoShop.sapShopid="";
                vm.bravoShop.sname="";
            }
            if ($("select#select option:selected").val()=="sapShopid"){
                vm.bravoShop.sapShopid=$("#show").val();
                vm.bravoShop.areamans="";
                vm.bravoShop.shopid="";
                vm.bravoShop.areaname="";
                vm.bravoShop.sname="";
            }
            if ($("select#select option:selected").val()=="sname"){
                vm.bravoShop.sname=$("#show").val();
                vm.bravoShop.areamans="";
                vm.bravoShop.shopid="";
                vm.bravoShop.sapShopid="";
                vm.bravoShop.areaname="";
            }
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    keyword: $("#serchword").val()
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.bravoShop = {};
        },
        update: function (event) {
            var shopid = getSelectedRow();
            if(shopid == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(shopid)
        },
        saveOrUpdate: function (event) {
            var url = vm.bravoShop.shopid == null ? "../bravoshop/save" : "../bravoshop/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.bravoShop),
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
            var shopids = getSelectedRows();
            if(shopids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../bravoshop/delete",
                    data: JSON.stringify(shopids),
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
        getInfo: function(shopid){
            $.get("../bravoshop/info/"+shopid, function(r){
                vm.bravoShop = r.bravoShop;
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

// document.getElementById('select').onchange=function (){
//     document.getElementById('show').value=this.value;
// };
$("#show").attr('placeholder',$("select#select option:selected").text());
$("select#select").change(function(){
    $("#show").attr('placeholder',$("select#select option:selected").text());
});