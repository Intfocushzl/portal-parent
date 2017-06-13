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
        saveOrUpdate: function (status) {
            var url = vm.businessmanNotice.id == null ? "../businessmannotice/save" : "../businessmannotice/update";
            vm.businessmanNotice.noticeType = $("#noticeType").val();
            vm.businessmanNotice.status = status;
            vm.businessmanNotice.abstracts = KindEditor.instances[0].html();
            vm.businessmanNotice.content = KindEditor.instances[1].html();
            vm.businessmanNotice.expireTime = $("#expireTime").val();
            console.log(JSON.stringify(vm.businessmanNotice));
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
                KindEditor.instances[0].html(vm.businessmanNotice.abstracts);
                KindEditor.instances[1].html(vm.businessmanNotice.contentManuscript);
                $("#cover_pic_show").prop("src", vm.businessmanNotice.coverImg);
                vm.businessmanNotice.oldContent = vm.businessmanNotice.content;
              //  $("#expireTime").val(vm.businessmanNotice.expireTime);
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
 language:  'zh-CN',
 format: "yyyy-mm-dd HH:mm:ss",
 weekStart: 0,
 todayBtn:  1,
 autoclose: 1,
 todayHighlight: 1,
 startView: 2,
 minView: 2,
 forceParse: 0,
    initialDate: "2020-01-01 00:00:00"
 });

//编辑器
var $tab = $('.tab-bar a');
var $pcDes = $('.pc-des');
$pcDes.eq(0).css('display', 'block')
$tab.click(function () {
    var num = $(this).index();
    $(this).addClass('active').siblings().removeClass('active');
    $pcDes.eq(num).css('display', 'block').siblings('.pc-des').css('display', 'none')
});

initkindEditor();
//初始化富文本
function initkindEditor() {
    KindEditor.ready(function (K) {
        vm.editor1 = K.create('textarea[kindEditor="true"]', {
            themeType: "simple",
            uploadJson: '../upload/kindEditorImgUpload',  //指定上传图片的服务器端程序
            fileManagerJson: '',                //指定浏览远程图片的服务器端程序
            allowFileManager: true,             //true时显示浏览服务器图片功能。服务器图片就是我们上传的图片所在的目录。
            resizeType: 1,
            pasteType: 2,
            syncType: "",
            filterMode: true,
            allowPreviewEmoticons: false,
            //先注释页面查看所有图标的data-name
            items: [
                'source', 'undo', 'redo', 'plainpaste', 'wordpaste', 'clearhtml', 'quickformat',
                'selectall', 'fullscreen', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
                'bold', 'italic', 'underline', 'hr', 'removeformat', '|', 'justifyleft', 'justifycenter',
                'justifyright', 'insertorderedlist', 'insertunorderedlist', '|', 'link', 'image', 'multiimage',
                'unlink', 'baidumap', 'emoticons'
            ],
            //设置编辑器创建后执行的回调函数
            afterCreate: function () {
                var self = this;
                K.ctrl(document, 13, function () {
                    self.sync();
                });
                K.ctrl(self.edit.doc, 13, function () {
                    self.sync();
                });
            }, afterBlur: function () {
                this.sync();
            },
            afterChange: function () {
                //富文本输入区域的改变事件，一般用来编写统计字数等判断
                K('.word_count1').html("最多20个字符,已输入" + this.count() + "个字符");
            },
            afterUpload: function (url) {
                //上传图片后的代码
            },
            allowFileManager: false,
            allowFlashUpload: false,
            allowMediaUpload: false,
            allowFileUpload: false
        });
        //可以调整高度，但不能调整宽度
        /*vm.editor1.show({
         resizeMode: 1
         });*/
    });
}

//上传封面图
$('#input_cover').uploadify({
    'swf': rcContextPath + '/statics/uploadify/uploadify.swf',
    'uploader': '../upload/itemImgUpload',
    'height': 45,
    'whith': 120,
    'auto': true,
    'fileDataName': 'file',
    'buttonText': '',
    'fileTypeExts': '*.gif; *.jpg;*.png;*.jpeg',
    'multi': false,
    'method': 'post',
    'debug': false,
    'onUploadSuccess': function (file, data, response) {
        var $showImage = $("#cover_pic_show");
        var data = eval("(" + data + ")");
        if (data.RetCode == 0) {
            var url = data.RetUrl;
            $showImage.prop("src", url);
            $("#coverImg").val(url);
            vm.businessmanNotice.coverImg = url;
            alert('上传成功', function (index) {
            });
        }
    },
    'onUploadError': function (file, errorCode, errorMsg, errorString) {
        alert('文件:' + file.name + '上传失败!');
    }
});