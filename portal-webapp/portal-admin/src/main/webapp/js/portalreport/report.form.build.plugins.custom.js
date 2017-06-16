/*e.preventDefault();//阻止元素发生默认的行为(例如,当点击提交按钮时阻止对表单的提交*/

/* 上传控件 uploadfile
 acc  是 class="component" 的DIV
 e 是 class="leipiplugins" 的控件
 */
LPB.plugins['uploadfile'] = function (active_component, leipiplugins) {
    var plugins = 'uploadfile', popover = $(".popover");
    //右弹form  初始化值
    $(popover).find("#orgname").val($(leipiplugins).attr("title"));
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
                case 'orgname':
                    $(leipiplugins).attr("title", attr_val);
                    active_component.find(".leipiplugins-orgname").text(attr_val);
                    break;
                default:
                    $(leipiplugins).attr(attr_name, attr_val);
            }
            active_component.popover("hide");
            LPB.genSource();//重置源代码
        });

    });
}