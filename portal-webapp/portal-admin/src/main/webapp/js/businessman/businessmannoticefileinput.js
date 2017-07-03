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
            uploadJson: '../upload/kindEditorImgUpload?tag=4',  //指定上传图片的服务器端程序
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

//初始化图像信息
function initCoverImgNew() {
    // 上传封面图
    $("#file-cover").fileinput({
        language: 'zh',			// 设置语言
        uploadUrl: '../upload/itemUpload',			// 上传的地址
        uploadExtraData: {tag: 1},
        uploadAsync: true,		// 默认异步上传
        showUpload: false,		// 是否显示上传按钮
        showRemove: false,		// 显示移除按钮
        showPreview: true,		// 是否显示预览
        showCaption: true,		// 是否显示标题
        dropZoneEnabled: true,  // 是否显示拖拽区域
        showCancel: false,      // 是否显示取消按钮
        showClose: false,		// 是否显示关闭按钮
        browseOnZoneClick: true,    // 点击预览框上传
        allowedFileTypes: ['image'],
        allowedFileExtensions: ['jpg', 'png', 'gif'],	// 接收的文件后缀
        overwriteInitial: true,	// 覆盖
        maxFileSize: 5120,		// 限制5M,单位为kb，如果为0表示不限制文件大小
        maxFilesNum: 1			// 选择上传的文件数量
    })
}
function initCoverImg(coverImgUrl) {
    // 上传封面图
    $("#file-cover").fileinput({
        language: 'zh',			// 设置语言
        uploadUrl: '../upload/itemUpload',			// 上传的地址
        uploadExtraData: {tag: 1},
        uploadAsync: true,		// 默认异步上传
        showUpload: false,		// 是否显示上传按钮
        showRemove: false,		// 显示移除按钮
        showPreview: true,		// 是否显示预览
        showCaption: true,		// 是否显示标题
        dropZoneEnabled: true,  // 是否显示拖拽区域
        showCancel: false,      // 是否显示取消按钮
        showClose: false,		// 是否显示关闭按钮
        browseOnZoneClick: true,    // 点击预览框上传
        allowedFileTypes: ['image'],
        allowedFileExtensions: ['jpg', 'png', 'gif'],	// 接收的文件后缀
        overwriteInitial: true,	// 覆盖
        maxFileSize: 5120,		// 限制5M,单位为kb，如果为0表示不限制文件大小
        maxFilesNum: 1,			// 选择上传的文件数量
        initialPreview: ["<img src='" + coverImgUrl + "' class='file-preview-image'>"] // 预览图片的设置
    })
}
$("#file-cover").on("filebatchselected", function (event, files) {
    $(this).fileinput("upload");
}).on("fileuploaded", function (event, data) {
    if (data.response) {
        var dataResponse = data.response;
        if (dataResponse.RetCode == 0) {
            var url = dataResponse.RetUrl;
            vm.businessmanNotice.coverImg = url;
            console.info("封面图上传成功 " + vm.businessmanNotice.coverImg);
        } else {
            alert("封面图上传失败");
        }
        // 删除上张上传成功的图片
        deleteFile_0("form-group-file-cover");
    }
}).on('fileloaded', function () {
    // 删除上张上传成功的图片
    deleteFile_0("form-group-file-cover");
});


// 删除第一个文件
function deleteFile_0(objId) {
    console.info($("#" + objId).find($(".file-preview-frame")).length);
    if ($("#" + objId).find($(".file-preview-frame")).length > 2) {
        $("#" + objId).find($(".file-preview-frame")).eq(0).remove();
    }
}