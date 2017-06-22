$(function () {
    $("#jqGrid").jqGrid({
        url: '../businessmanacticle/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 30, key: true},
            {
                label: '文章类型', name: 'acticleType', index: 'acticle_type', width: 50, formatter: function (type) {
                if (type == 1) {
                    return "文章";
                } else if (type == 2) {
                    return "视频";
                }
            }
            },
            {label: '角色编码', name: 'acticleRoles', index: 'acticle_roles', width: 80},
            {label: '群组编码', name: 'acticleGroups', index: 'acticle_groups', width: 80},
            /*{label: '是否APP推送', name: 'appPush', index: 'app_push', width: 80},*/
            {label: '标题', name: 'title', index: 'title', width: 80},
            {label: '标签', name: 'tagInfo', index: 'tag_info', width: 80},
            {label: '创建时间', name: 'createTime', index: 'CREATE_TIME', width: 80},
            {
                label: '操作', name: 'operation', with: 100, formatter: function (value, options, row) {
                return "<span class='label label-success' onclick='vm.logInfo(" + row.id + ")'>阅读日志</span>" +
                    "&nbsp;&nbsp;<span class='label label-success' onclick='vm.collect(" + row.id + ")'>收藏</span>" +
                    "&nbsp;&nbsp;<span class='label label-success' onclick='vm.comment(" + row.id + ")'>评论</span>";
            }
            }
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


    // 属性设置框
    $("#dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 480,
        modal: true,
        buttons: {
            "确定": function () {

                var tagLen = $("#tabs li").length;

                $("#tabs").append('<li><span class="tab">' + $("#tagList").val() + '</span> &nbsp;<span class="remove">x</span>' +
                    '</li>');
                //<input name="tag_'+tagLen+'" value="'+$("#tagList").val()+'" type="hidden"/>
                // 关闭窗口
                $(this).dialog("close");
            },
            "取消": function () {
                $(this).dialog("close");
            }
        },
        close: function () {
        }
    });

});

function removeTag(obj) {
    $(obj).parent().remove();
}

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: 1,//1,文章列表 2.新增或修改 3.阅读日志 4.收藏 5.评论
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
            vm.showList = 2;
            vm.title = "新增";
            vm.businessmanActicle = {};
            KindEditor.instances[0].html("");
            KindEditor.instances[1].html("");
            $("#cover_pic_show").prop("src", "");
            $("#tabs").html("");
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = 2;
            vm.title = "修改";

            vm.getInfo(id);
        },
        saveOrUpdate: function (status) {
            var url = vm.businessmanActicle.id == null ? "../businessmanacticle/save" : "../businessmanacticle/update";

            var tagLen = $(".tab").length;
            var tag = "";
            for(var i = 0 ;i < tagLen;i++){
                tag += $(".tab").eq(i).html()+",";
            }
            vm.businessmanActicle.tagInfo = tag.substr(0,tag.length-1);
            vm.businessmanActicle.abstracts = KindEditor.instances[0].html();
            vm.businessmanActicle.content = KindEditor.instances[1].html();
            vm.businessmanActicle.status = status;
            console.log(vm.businessmanActicle);
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
                vm.getTagList(vm.businessmanActicle.acticleType);
                vm.businessmanActicle.oldContent = vm.businessmanActicle.content;
                if (vm.businessmanActicle.tagInfo) {
                    var tags = vm.businessmanActicle.tagInfo.split(",");
                    for (var i = 0; i < tags.length; i++) {
                        $("#tabs").append('<li><span class="tab">' + tags[i] + '</span>&nbsp;<span onclick="removeTag(this)" class="remove">x</span>' +
                            '</li>');
                    }
                }
                if (vm.businessmanActicle.attachFile) {
                    $("#id_del_file").css('display', 'block');
                }
                if (vm.businessmanActicle.coverImg) {
                    $("#id_del_cover").css('display', 'block');
                }

            });
        },
        reload: function (event) {
            vm.showList = 1;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        getTagList: function (type) {//获取标签列表
            $("#tagList").empty();
            $.get("../businessmanacticle/tagInfoList?type=" + type, function (r) {
                for (var i = 0; i < r.data.length; i++) {
                    $("#tagList").append("<option value='" + r.data[i].tagName + "'>" + r.data[i].tagName + "</option>");
                }
                // refresh刷新和render渲染操作，必不可少  +r.data[i].id+"<===>"
                $('#tagList').selectpicker('refresh');
                $('#tagList').selectpicker('render');
            });
        },
        logInfo: function (id) {
            vm.showList = 3;
            logJqGrid(id);
        },
        collect: function (id) {
            vm.showList = 4;
            collectGqGrid(id);
        },
        comment: function (id) {
            vm.showList = 5;
            commentGqGrid(id);
        },
        isopen: function (id, result) {//是否启用
            $.ajax({
                type: "POST",
                url: "../businessmancomment/updateIsopen?id=" + id + "&result=" + result,
                /*data: {
                 "id": id,
                 "result": result
                 },*/
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            var articleId = $("#commentGqGrid").getCell(1, "acticleId");
                            //  console.log(articleId);
                            commentGqGrid(articleId);
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        showDialog: function () {
            var type = $("input[name='type']");
            var typevalue = null;
            for (var i = 0; i < type.length; i++) {
                if (type[i].checked == true) {
                    typevalue = type[i].value;
                }
            }
            var tagLen = $("#tabs li").length;

            if (!typevalue) {
                alert("请先选择文章类型");
            } else if (tagLen >= 3) {
                alert("标签最多可选3个");
            } else {
                $("#dialog-form").dialog("open");
                vm.getTagList(typevalue);
            }
        }

    }
});

//阅读日志
function logJqGrid(id) {
    $("#logGqGrid").jqGrid({
        url: '../businessmanacticlelog/getListByArticleId?id=' + id,     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'ID', name: 'id', index: 'id', key: true},
            {label: '文章ID', name: 'acticleId', index: 'acticle_id', key: true},
            {label: '角色编码', name: 'creater', index: 'creater'},
            {label: '创建时间', name: 'createTime', index: 'CREATE_TIME'},
        ],
        viewrecords: true,     // 是否显示行号，默认值是false，不显示
        height: 360,            // 表格高度
        rownumbers: true,
        /* rownumWidth: 25,*/
        autowidth: true,
        autoScroll: true,
        shrinkToFit: true,
        jsonReader: {                   // 重写后台返回数据的属性
            root: "data"         // 将rows修改为page.list
        },
        gridComplete: function () {
            // //隐藏grid底部滚动条
            $("#logGqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hiden"});
        }
    });
}
//收藏
function collectGqGrid(id) {
    $("#collecGqGrid").jqGrid({
        url: '../businessmanfavorites/getListByArticleId?id=' + id,     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'ID', name: 'id', index: 'id', key: true},
            {label: '文章ID', name: 'acticleId', index: 'acticle_id', key: true},
            {label: '用户ID', name: 'userId', index: 'user_id'},
            {
                label: '状态', name: 'status', index: 'status', formatter: function (status) {
                if (status == 1) {
                    return "已收藏";
                } else if (status == 2) {
                    return "取消收藏";
                }
            }
            },
            {label: '创建时间', name: 'createTime', index: 'CREATE_TIME'},
        ],
        viewrecords: true,     // 是否显示行号，默认值是false，不显示
        height: 360,            // 表格高度
        rownumbers: true,
        /* rownumWidth: 25,*/
        autowidth: true,
        autoScroll: true,
        shrinkToFit: true,
        jsonReader: {                   // 重写后台返回数据的属性
            root: "data"         // 将rows修改为page.list
        },
        gridComplete: function () {
            // //隐藏grid底部滚动条
            $("#collecGqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hiden"});
        }
    });
}
//评论
function commentGqGrid(id) {
    $("#commentGqGrid").jqGrid({
        url: '../businessmancomment/getListByActicleId?id=' + id,     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'ID', name: 'id', index: 'id', key: true},
            {label: '文章ID', name: 'acticleId', index: 'acticle_id', key: true},
            {label: '用户ID', name: 'userId', index: 'user_id'},
            {label: '内容', name: 'content', index: 'content',},
            {
                label: '状态', name: 'disabled', index: 'disabled', formatter: function (disabled) {
                if (disabled == 0) {
                    return "启用";
                } else if (disabled == 1) {
                    return "禁用";
                }
            }
            },
            {label: '创建时间', name: 'createTime', index: 'CREATE_TIME'},
            {
                label: '操作', name: 'operation', formatter: function (value, options, row) {
                if (row.disabled == 0) {
                    return "<span class='label label-success' onclick='vm.isopen(" + row.id + "," + 1 + ")'>禁用</span>";
                } else if (row.disabled == 1) {
                    return "<span class='label label-success' onclick='vm.isopen(" + row.id + "," + 0 + ")'>启用</span>";
                }
            }
            }
        ],
        viewrecords: true,     // 是否显示行号，默认值是false，不显示
        height: 360,            // 表格高度
        rownumbers: true,
        /* rownumWidth: 25,*/
        autowidth: true,
        autoScroll: true,
        shrinkToFit: true,
        jsonReader: {                   // 重写后台返回数据的属性
            root: "data"         // 将rows修改为page.list
        },
        gridComplete: function () {
            // //隐藏grid底部滚动条
            $("#commentGqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hiden"});
        }
    });
}


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
                $("#id_del_cover").css('display', 'block');
            });
        }
    },
    'onUploadError': function (file, errorCode, errorMsg, errorString) {
        alert('文件:' + file.name + '上传失败!');
    }
});

//上传附件
$('#input_file').uploadify({
    'successTimeout': 5 * 60000,
    'swf': rcContextPath + '/statics/uploadify/uploadify.swf',
    'uploader': '../upload/itemImgUpload',
    'height': 20,
    'whith': 120,
    'auto': true,
    'fileDataName': 'file',
    'buttonText': '',
    'fileTypeExts': '*.pdf;*.avi;*.mp4;*.png;*.jpg;*.doc;*.docx',
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
                $("#id_del_file").css('display', 'block');
            });
        }
    },
    'onUploadError': function (file, errorCode, errorMsg, errorString) {
        alert('文件:' + file.name + '上传失败!');
    }

});

// 图片自适应
$(function () {
    $("img").autoIMG();
});