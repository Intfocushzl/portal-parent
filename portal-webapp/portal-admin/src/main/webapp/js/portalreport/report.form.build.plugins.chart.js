/*e.preventDefault();//阻止元素发生默认的行为(例如,当点击提交按钮时阻止对表单的提交*/

/*
 横幅框控件 banner
 acc  是 class="component" 的DIV
 e 是 class="leipiplugins" 的控件
 */
LPB.plugins['banner'] = function (active_component, leipiplugins) {
    var popover = $(".popover");
    var jsonStr = $(leipiplugins).val();
    var jsonObj = {
        "type": "banner",
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
    //右弹form  取消控件
    $(popover).delegate(".btn-danger", "click", function (e) {
        active_component.popover("hide");
    });
    //右弹form  确定控件
    $(popover).delegate(".btn-info", "click", function (e) {
        var inputs = $(popover).find("input");
        if ($(popover).find("textarea").length > 0) {
            inputs.push($(popover).find("textarea")[0]);
        }
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
            }
            active_component.popover("hide");
            LPB.genSource();//重置源代码
        });
        $(leipiplugins).text(JSON.stringify(jsonObj));
    });
}


/*
 折线图控件 chart_line
 acc  是 class="component" 的DIV
 e 是 class="leipiplugins" 的控件
 */
LPB.plugins['chart_line'] = function (active_component, leipiplugins) {
    var popover = $(".popover");
    var jsonStr = $(leipiplugins).val();
    var jsonObj = {
        "type": "chart",
        "config": {
            "chart_type": "line-or-bar",
            "title": "no-set",
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
        //右弹form  初始化值
        if (jsonObj.config.title !== undefined) {
            $(popover).find("#chart_config_title").val(jsonObj.config.title);
        }
    }
    //右弹form  取消控件
    $(popover).delegate(".btn-danger", "click", function (e) {
        active_component.popover("hide");
    });
    //右弹form  确定控件
    $(popover).delegate(".btn-info", "click", function (e) {
        var inputs = $(popover).find("input");
        if ($(popover).find("textarea").length > 0) {
            inputs.push($(popover).find("textarea")[0]);
        }
        $.each(inputs, function (i, e) {
            var attr_name = $(e).attr("id");//属性名称
            var attr_val = $(e).val();
            switch (attr_name) {
                case 'chart_config_title':
                    jsonObj.config.title = attr_val;
                    break;
            }
            active_component.popover("hide");
            LPB.genSource();//重置源代码
        });
        $(leipiplugins).text(JSON.stringify(jsonObj));
    });
}


/*
 表格tab切换 tables_v3
 acc  是 class="component" 的DIV
 e 是 class="leipiplugins" 的控件
 */
LPB.plugins['tables_v3'] = function (active_component, leipiplugins) {
    var popover = $(".popover");
    var jsonStr = $(leipiplugins).val();
    var jsonObj = {
        "type": "tables#v3",
        "config": [
            {
                "title": "",
                "table": {
                    "head": [],
                    "data": []
                }
            }
        ]
    };
    if (getStringValue(jsonStr) != "") {
        jsonObj = JSON.parse($(leipiplugins).val());
        //右弹form  初始化值
        if (jsonObj.config[0].title !== undefined) {
            $(popover).find("#tables_v3_config_title").val(jsonObj.config[0].title);
        }
    }
    //右弹form  取消控件
    $(popover).delegate(".btn-danger", "click", function (e) {
        active_component.popover("hide");
    });
    //右弹form  确定控件
    $(popover).delegate(".btn-info", "click", function (e) {
        var inputs = $(popover).find("input");
        if ($(popover).find("textarea").length > 0) {
            inputs.push($(popover).find("textarea")[0]);
        }
        $.each(inputs, function (i, e) {
            var attr_name = $(e).attr("id");//属性名称
            var attr_val = $(e).val();
            switch (attr_name) {
                case 'tables_v3_config_title':
                    jsonObj.config[0].title = attr_val;
                    break;
            }
            active_component.popover("hide");
            LPB.genSource();//重置源代码
        });
        $(leipiplugins).text(JSON.stringify(jsonObj));
    });
}