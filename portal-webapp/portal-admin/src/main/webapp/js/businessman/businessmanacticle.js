$(function () {
    $("#jqGrid").jqGrid({
        url: '../businessmanacticle/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true},
            {label: '文章类型', name: 'acticleType', index: 'acticle_type', width: 80},
            {label: '发布范围角色编码', name: 'acticleRoles', index: 'acticle_roles', width: 80},
            {label: '发布范围群组编码', name: 'acticleGroups', index: 'acticle_groups', width: 80},
            {label: '是否APP推送', name: 'appPush', index: 'app_push', width: 80},
            {label: '标题', name: 'title', index: 'title', width: 80},
            {label: '标签：最多三个 如 aa,bb,cc', name: 'tagInfo', index: 'tag_info', width: 80},
            {label: '创建时间', name: 'createTime', index: 'CREATE_TIME', width: 80},
        ],
        viewrecords: true,     // 是否显示行号，默认值是false，不显示
        height: 385,            // 表格高度
        rowNum: 50,             // 一页显示的行记录数
        rowList: [50, 100],     // 翻页控制条中 每页显示记录数可选集合
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",          // 翻页DOM
        jsonReader: {                   // 重写后台返回数据的属性
            root: "page.list",          // 将rows修改为page.list
            page: "page.currPage",      // 将page修改为page.currPage
            total: "page.totalPage",    // 将total修改为page.totalPage
            records: "page.totalCount"  // 将records修改为page.totalCount
        },
        prmNames: {                     // 改写请求参数属性
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
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
        businessmanActicle: {},
        editor1: null
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    id: vm.businessmanActicle.id,
                    remark: vm.businessmanActicle.remark
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.businessmanActicle = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id);
        },
        saveOrUpdate: function (event) {
            var url = vm.businessmanActicle.id == null ? "../businessmanacticle/save" : "../businessmanacticle/update";
            vm.businessmanActicle.abstracts = KindEditor.instances[0].html();
            vm.businessmanActicle.content = KindEditor.instances[1].html();

            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.businessmanActicle),
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
                    url: "../businessmanacticle/delete",
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
            $.get("../businessmanacticle/info/" + id, function (r) {
                vm.businessmanActicle = r.businessmanActicle;
                KindEditor.instances[0].html(vm.businessmanActicle.abstracts);
                KindEditor.instances[1].html(vm.businessmanActicle.content);
                $("#cover_pic_show").prop("src", vm.businessmanActicle.coverImg);
                $("#attachFile").val(vm.businessmanActicle.attachFile);
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
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
    'height': 25,
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
            vm.businessmanActicle.coverImg = url;
            alert('上传成功', function (index) {
            });
        }
    },
    'onUploadError': function (file, errorCode, errorMsg, errorString) {
        alert('文件:' + file.name + '上传失败!');
    }
});

//上传附件
$('#input_file').uploadify({
    'successTimeout': 10 * 60000,
    'swf': rcContextPath+ '/statics/uploadify/uploadify.swf',
    'uploader': '../upload/itemImgUpload',
    'height': 20,
    'whith': 120,
    'auto': true,
    'fileDataName': 'file',
    'buttonText': '',
    'fileTypeExts': '*.pdf;*.avi;*.mp4;*.png;*.jpg;*.doc',
    'multi': false,
    'method': 'post',
    'debug': false,
    'onUploadSuccess': function (file, data, response) {
        var data = eval("(" + data + ")");
        if (data.RetCode == 0) {
            var url = data.RetUrl;
            $("#attachFile").val(url);
            vm.businessmanActicle.attachFile = url;
            alert('上传成功', function (index) {
            });
        }
    },
    'onUploadError': function (file, errorCode, errorMsg, errorString) {
        alert('文件:' + file.name + '上传失败!');
    }

});