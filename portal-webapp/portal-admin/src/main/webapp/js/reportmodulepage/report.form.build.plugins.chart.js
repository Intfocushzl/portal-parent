/*
 * e.preventDefault();//阻止元素发生默认的行为(例如,当点击提交按钮时阻止对表单的提交
 * <!-- 图表控件 张海 walk_hai@163.com-->
 */

/*
 * 横幅标题栏控件 banner
 * acc  是 class="component" 的DIV
 * e 是 class="leipiplugins" 的控件
 */
LPB.plugins['banner'] = function (active_component, leipiplugins) {
    var popover = $(".popover");
    var jsonStr = $(leipiplugins).val();
    var jsonObj = {
        "type": "banner",
        "name": "",
        "config": {
            "title": "",
            "subtitle": "",
            "date": "",
            "info": ""
        }
    };
    if (getStringValue(jsonStr) != "") {
        jsonObj = JSON.parse($(leipiplugins).val());
        //右弹form  初始化值
        $(popover).find("#orgname").val(jsonObj.name);
        if (jsonObj.config.title !== undefined) {
            $(popover).find("#banner_config_title").val(jsonObj.config.title);
        }
        if (jsonObj.config.subtitle !== undefined) {
            $(popover).find("#banner_config_subtitle").val(jsonObj.config.subtitle);
        }
        if (jsonObj.config.date !== undefined) {
            $(popover).find("#banner_config_date").val(jsonObj.config.date);
        }
        if (jsonObj.config.info !== undefined) {
            $(popover).find("#banner_config_info").val(jsonObj.config.info);
        }
    }
    // 右弹form  取消控件
    $(popover).delegate(".btn-danger", "click", function (e) {
        active_component.popover("hide");
    });
    // 右弹form  确定控件
    $(popover).delegate(".btn-info", "click", function (e) {
        var inputs = $(popover).find("input");
        $.each(inputs, function (i, e) {
            var attr_name = $(e).attr("id");//属性名称
            var attr_val = $(e).val();
            switch (attr_name) {
                case 'banner_config_title':
                    jsonObj.config.title = attr_val;
                    break;
                case 'banner_config_subtitle':
                    jsonObj.config.subtitle = attr_val;
                    break;
                case 'banner_config_date':
                    jsonObj.config.date = attr_val;
                    break;
                case 'banner_config_info':
                    jsonObj.config.info = attr_val;
                    break;
                case 'orgname':
                    attr_val = getAttrVal(attr_val, "banner");
                    jsonObj.name = attr_val;
                    active_component.find(".leipiplugins-orgname").html(attr_val);
                    break;
            }
            active_component.popover("hide");
            LPB.genSource();// 重置源代码
        });
        setLeipipluginsVal(leipiplugins, jsonObj);
    });
}


/*
 * 信息栏控件 info
 * acc  是 class="component" 的DIV
 * e 是 class="leipiplugins" 的控件
 */
LPB.plugins['info'] = function (active_component, leipiplugins) {
    var popover = $(".popover");
    var jsonStr = $(leipiplugins).val();
    var jsonObj = {
        "type": "info",
        "name": "",
        "config": {
            "text": ""
        }
    };
    if (getStringValue(jsonStr) != "") {
        jsonObj = JSON.parse($(leipiplugins).val());
        //右弹form  初始化值
        $(popover).find("#orgname").val(jsonObj.name);
        if (jsonObj.config.text !== undefined) {
            $(popover).find("#info_config_text").val(jsonObj.config.info);
        }
    }
    // 右弹form  取消控件
    $(popover).delegate(".btn-danger", "click", function (e) {
        active_component.popover("hide");
    });
    // 右弹form  确定控件
    $(popover).delegate(".btn-info", "click", function (e) {
        var inputs = $(popover).find("input");
        $.each(inputs, function (i, e) {
            var attr_name = $(e).attr("id");//属性名称
            var attr_val = $(e).val();
            switch (attr_name) {
                case 'info_config_text':
                    jsonObj.config.info = attr_val;
                    break;
                case 'orgname':
                    attr_val = getAttrVal(attr_val, "info");
                    jsonObj.name = attr_val;
                    active_component.find(".leipiplugins-orgname").html(attr_val);
                    break;
            }
            active_component.popover("hide");
            LPB.genSource();// 重置源代码
        });
        setLeipipluginsVal(leipiplugins, jsonObj);
    });
}


/*
 * 折线图控件 chart_line
 * acc  是 class="component" 的DIV
 * e 是 class="leipiplugins" 的控件
 */
LPB.plugins['chart_line'] = function (active_component, leipiplugins) {
    var popover = $(".popover");
    var jsonStr = $(leipiplugins).val();
    var jsonObj = {
        "type": "chart_line",
        "name": "",
        "config": {
            "chart_type": "line-or-bar",
            "title": "no-set",
            "dataUrl": "",
            "legend": [],
            "xAxis": [],
            "yAxis": [
                {
                    "type": "value",
                    "name": ""
                }
            ],
            "series": [
                {
                    "name": "",
                    "type": "line",
                    "data": []
                }
            ]
        }
    };

    if (getStringValue(jsonStr) != "") {
        jsonObj = JSON.parse($(leipiplugins).val());
        // 右弹form  初始化值
        $(popover).find("#orgname").val(jsonObj.name);
        if (jsonObj.config.title !== undefined) {
            $(popover).find("#chart_line_config_title").val(jsonObj.config.title);
        }
    }
    // 加载数据源
    forSelectOption("#chart_line_config_data_url", jsonObj.config.dataUrl);
    // 右弹form  取消控件
    $(popover).delegate(".btn-danger", "click", function (e) {
        active_component.popover("hide");
    });
    // 右弹form  确定控件
    $(popover).delegate(".btn-info", "click", function (e) {
        jsonObj.config.dataUrl = $("#chart_line_config_data_url").val();
        var inputs = $(popover).find("input");
        $.each(inputs, function (i, e) {
            var attr_name = $(e).attr("id");// 属性名称
            var attr_val = $(e).val();
            switch (attr_name) {
                case 'chart_line_config_title':
                    jsonObj.config.title = attr_val;
                    break;
                case 'orgname':
                    attr_val = getAttrVal(attr_val, "chart_line");
                    jsonObj.name = attr_val;
                    active_component.find(".leipiplugins-orgname").html(attr_val);
                    break;
            }
            active_component.popover("hide");
            LPB.genSource();//重置源代码
        });
        setLeipipluginsVal(leipiplugins, jsonObj);
    });
}


/*
 * 表格tab切换 tables_v3
 * acc  是 class="component" 的DIV
 * e 是 class="leipiplugins" 的控件
 */
LPB.plugins['tables_v3'] = function (active_component, leipiplugins) {
    var popover = $(".popover");
    var jsonStr = $(leipiplugins).val();
    var jsonObj = {
        "type": "tables_v3",
        "name": "",
        "config": []
    };
    var jsonConfigObj;
    var document;
    // 右弹form  初始化值
    if (getStringValue(jsonStr) != "") {
        jsonObj = JSON.parse($(leipiplugins).val());
        $(popover).find("#orgname").val(jsonObj.name);
        if (jsonObj.config.length > 0) {
            $.each(jsonObj.config, function (n, value) {
                // 创建tab
                document = addTab();
                if (value.title !== undefined) {
                    $("#a_" + document).html(value.title);
                    $(popover).find("#tables_v3_config_title_" + document).val(value.title);
                }
                // 加载数据源
                forSelectOption("#tables_v3_config_data_url_" + document, value.dataUrl);
            });
        } else {
            // 创建tab
            addTab();
        }
    } else {
        // 创建tab
        addTab();
    }
    // 初始化tab绑定事件
    tabsEvent();

    // 右弹form  取消控件
    $(popover).delegate(".btn-danger", "click", function (e) {
        active_component.popover("hide");
    });
    // 右弹form  确定控件
    $(popover).delegate(".btn-info", "click", function (e) {
        // 获取控件名称
        var inputs = $(popover).find("input");
        $.each(inputs, function (i, e) {
            var attr_name = $(e).attr("id");// 属性名称
            var attr_val = $(e).val();
            switch (attr_name) {
                case 'orgname':
                    attr_val = getAttrVal(attr_val, "tables_v3");
                    jsonObj.name = attr_val;
                    active_component.find(".leipiplugins-orgname").html(attr_val);
                    break;
            }
        });

        // 清空config数组
        jsonObj.config = [];
        var tabDocuments = $(".tab_document");
        // 遍历所有tab div
        $.each(tabDocuments, function (j, tabDoc) {
            document = $(tabDoc).attr("document");//tab_div 随机码
            jsonConfigObj = {
                "title": "",
                "dataUrl": "",
                "table": {
                    "head": [],
                    "data": []
                }
            };
            // 遍历 所有input内容
            var inputs = $(tabDoc).find("input");
            var nameString = "";
            jsonConfigObj.dataUrl = $('#tables_v3_config_data_url_' + document).val();
            $.each(inputs, function (i, e) {
                var attr_name = $(e).attr("id");// 属性名称
                var attr_val = $(e).val();
                switch (attr_name) {
                    case 'tables_v3_config_title_' + document:
                        nameString = nameString + "_" + attr_val;
                        jsonConfigObj.title = attr_val;
                        break;
                }
                active_component.popover("hide");
                LPB.genSource();    // 重置源代码
            });
            jsonObj.config[j] = jsonConfigObj;
        });
        setLeipipluginsVal(leipiplugins, jsonObj);
        // 删除 tab div
        $("#content div").html("");
    });
}

/**
 * 初始化tabs绑定事件
 */
function tabsEvent() {
    var contentname;
    $('#tabs').on('click', '.tab', function () {
        contentname = $(this).attr("document") + "_content";
        $(".tab_document").hide();
        $("#tabs li").removeClass("current");
        $("#" + contentname).show();
        $(this).parent().addClass("current");
    });
    $('#tabs').on('click', '.remove', function () {
        var document = $(this).parent().find(".tab").attr("document");
        contentname = document + "_content";
        $("#" + contentname).remove();
        $(this).parent().remove();
        if ($("#tabs li.current").length == 0 && $("#tabs li").length > 0) {
            var firsttab = $("#tabs li:first-child");
            firsttab.addClass("current");
            var firsttabid = $(firsttab).find("a.tab").attr("document");
            $("#" + firsttabid + "_content").show();
        }
    });
};

/**
 * tab控件添加标签
 * @returns {string}
 */
function addTab() {
    var document = "document_" + RndNum(10);

    $("#tabs li").removeClass("current");
    // 隐藏所有tab div
    $(".tab_document").hide();

    $("#tabs").append("<li class='current' id='li_" + document + "'><a class='tab' document='" + document + "' id='a_" + document + "' href='#'>"
        + "标题"
        + "</a><a href='#' class='remove'>x</a></li>");

    $("#content").append("<div class='tab_document content-label-input' document='" + document + "' id='" + document + "_content'>"
        + "<label class='control-label content-left'>标题</label>"
        + "<input type='text'class='tables_v3_config_title content-left' document='" + document + "' id='tables_v3_config_title_" + document + "' placeholder='标题' onchange='onchangeTabTitle(this)'>"
        + "<label class='control-label content-left'>数据源</label>"
        + "<select id='tables_v3_config_data_url_" + document + "' class='selectpicker form-control content-left' data-live-search='true' title='数据源'></select>"
        + "</div>");

    // 加载数据源
    forSelectOption("#tables_v3_config_data_url_" + document, "");

    // 显示新增tab div
    $("#" + document + "_content").show();
    return document;
}

/**
 * 修改tab标题
 * @param obj
 */
function onchangeTabTitle(obj) {
    $("#a_" + $(obj).attr("document")).html($(obj).val());
}

/**
 * 生成的json赋给控件
 * @param leipiplugins
 * @param jsonObj
 */
function setLeipipluginsVal(leipiplugins, jsonObj) {
    $(leipiplugins).val(JSON.stringify(jsonObj));
    $(leipiplugins).text(JSON.stringify(jsonObj));
    $(leipiplugins).html(JSON.stringify(jsonObj));
}

/**
 * 获取控件名称
 * @param attr_val
 * @param attr_name
 * @returns {*}
 */
function getAttrVal(attr_val, attr_name) {
    if (getStringValue(attr_val) != "") {
        return attr_val;
    }
    return $("#" + attr_name).attr("title");
}