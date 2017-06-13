/*e.preventDefault();//阻止元素发生默认的行为(例如,当点击提交按钮时阻止对表单的提交*/

/* 多行文本框控件 textarea
 acc  是 class="component" 的DIV
 e 是 class="leipiplugins" 的控件
 */
LPB.plugins['textarea'] = function (active_component, leipiplugins) {
    var plugins = 'textarea', popover = $(".popover");
    var jsonObj = JSON.parse($(leipiplugins).val());
    //右弹form  初始化值
    $(popover).find("#chart_banner_config_title").val(jsonObj.title);
    $(popover).find("#chart_banner_config_subtitle").val(jsonObj.subtitle);
    $(popover).find("#chart_banner_config_date").val(jsonObj.date);

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
                case 'chart_banner_config_title':
                    jsonObj.title = attr_val;
                    break;
                case 'chart_banner_config_subtitle':
                    jsonObj.subtitle = attr_val;
                    break;
                case 'chart_banner_config_date':
                    jsonObj.date = attr_val;
                    break;
            }
            active_component.popover("hide");
            LPB.genSource();//重置源代码
        });
        $(leipiplugins).text(JSON.stringify(jsonObj));
    });
}