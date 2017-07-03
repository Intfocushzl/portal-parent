(function () {
    var LPB = window.LPB = window.LPB || {
            plugins: [],
            genSource: function () {
                var $temptxt = $("<div>").html($("#build").html());
                //scrubbbbbbb
                $($temptxt).find(".component").attr({
                    "title": null,
                    "data-original-title": null,
                    "data-type": null,
                    "data-content": null,
                    "rel": null,
                    "trigger": null,
                    "style": null
                });
                $($temptxt).find(".valtype").attr("data-valtype", null).removeClass("valtype");
                $($temptxt).find(".component").removeClass("component");
                $($temptxt).find("form").attr({"id": null, "style": null});
                $("#source").val($temptxt.html().replace(/\n\ \ \ \ \ \ \ \ \ \ \ \ /g, "\n"));
            }

        };

    /* 表单名称控件 form_name
     acc  是 class="component" 的DIV
     e 是 class="leipiplugins" 的控件
     */
    LPB.plugins['form_name'] = function (active_component, leipiplugins) {
        var plugins = 'form_name', popover = $(".popover");
        //右弹form  初始化值
        $(popover).find("#orgvalue").val($(leipiplugins).val());
        //右弹form  取消控件
        $(popover).delegate(".btn-danger", "click", function (e) {
            e.preventDefault();
            active_component.popover("hide");
        });

        //右弹form  确定控件
        $(popover).delegate(".btn-info", "click", function (e) {
            e.preventDefault();//阻止元素发生默认的行为(例如,当点击提交按钮时阻止对表单的提交
            var inputs = $(popover).find("input");
            $.each(inputs, function (i, e) {
                var attr_name = $(e).attr("id");//属性名称
                var attr_val = $("#" + attr_name).val();
                if (attr_name == 'orgvalue') {
                    $(leipiplugins).attr("value", attr_val);
                    active_component.find(".leipiplugins-orgvalue").text(attr_val);
                }
                active_component.popover("hide");
                LPB.genSource();//重置源代码
            });
        });

    }
})();

$(document).ready(function () {
    $("#navtab").delegate("#sourcetab", "click", function (e) {
        LPB.genSource();
    });

    /**
     * 当按下鼠标按钮时
     */
    $("form").delegate(".component", "mousedown", function (md) {
        $(".popover").remove();

        md.preventDefault();
        var tops = [];
        var mouseX = md.pageX;  // 相对于文档的左边缘
        var mouseY = md.pageY;  // 相对于文档的上边缘
        var $temp;
        var timeout;
        var $this = $(this);
        var delays = {
            main: 0,
            form: 500
        }
        var type;

        if ($this.parent().parent().parent().parent().attr("id") === "components") {
            type = "main";
        } else {
            type = "form";
        }
        console.info("====type:===" + type);

        var delayed = setTimeout(function () {
            if (type === "main") {
                $temp = $("<form class='form-horizontal span6' id='temp' style='width: 360px'></form>").append($this.clone());
            } else {
                if ($this.attr("id") !== "legend") {
                    $temp = $("<form class='form-horizontal span6' id='temp' style='width: 360px'></form>").append($this);
                }
            }

            $("body").append($temp);

            var half_box_height = ($temp.height() / 2);
            var half_box_width = ($temp.width() / 2);
            var $target = $("#target");
            var tar_pos = $target.position();
            var $target_component = $("#target .component");

            $temp.css({
                "position": "absolute",
                "top": mouseY - half_box_height + "px",
                "left": mouseX - half_box_width + "px",
                "opacity": "0.9"
            }).show()

            /**
             * 当鼠标指针在指定的元素中移动时 上下移动
             */
            $(document).delegate("body", "mousemove", function (mm) {
                var mm_mouseX = mm.pageX;   // 相对于文档的左边缘
                var mm_mouseY = mm.pageY;   // 相对于文档的上边缘

                $temp.css({
                    "top": mm_mouseY - half_box_height + "px",
                    "left": mm_mouseX - half_box_width + "px"
                });

                console.info("==================mousemove========");
                console.info("mm_mouseX:" + mm_mouseX);
                console.info("tar_pos.left:" + tar_pos.left);
                console.info("$target.width():" + $target.width());
                console.info("half_box_width:" + half_box_width);
                console.info("mm_mouseY:" + mm_mouseY);
                console.info("tar_pos.top:" + tar_pos.top);
                console.info("$target.height():" + $target.height());
                console.info("$temp.height():" + $temp.height());
                console.info("tar_pos.top + $target.height() + $temp.height():" + tar_pos.top + $target.height() + $temp.height());
                console.info("==================mousemove========");
                if (mm_mouseX > tar_pos.left &&
                    mm_mouseX < tar_pos.left + $target.width() &&
                    mm_mouseY > tar_pos.top &&
                    mm_mouseY < tar_pos.top + $target.height() + $temp.height()
                ) {
                    $("#target").css("background-color", "#fafdff");
                    $target_component.css({"border-top": "1px dashed #ccc", "border-bottom": "1px dashed #ccc"});
                    tops = $.grep($target_component, function (e) {
                        return ($(e).position().top - mm_mouseY + half_box_height > 0 && $(e).attr("id") !== "legend");
                    });
                    if (tops.length > 0) {
                        $(tops[0]).css("border-top", "1px solid #22aaff");
                    } else {
                        if ($target_component.length > 0) {
                            $($target_component[$target_component.length - 1]).css("border-bottom", "1px solid #22aaff");
                        }
                    }
                    console.info("==================mousemove tops.length:" + tops.length + "========");
                } else {
                    $("#target").css("background-color", "#fff");
                    $target_component.css({"border-top": "1px dashed #ccc", "border-bottom": "1px dashed #ccc"});
                    $target.css("background-color", "#fff");
                    console.info("==================mousemove not========");
                }
            });

            /**
             * 当松开鼠标按钮时 添加移除
             */
            $("body").delegate("#temp", "mouseup", function (mu) {
                mu.preventDefault();

                var mu_mouseX = mu.pageX;
                var mu_mouseY = mu.pageY;
                var tar_pos = $target.position(); // 返回匹配元素相对于父元素的位置（偏移）,返回的对象包含两个整型属性：top 和 left，以像素计

                $("#target .component").css({"border-top": "1px dashed #ccc", "border-bottom": "1px dashed #ccc"});

                console.info("====================mouseup======");
                console.info("mu_mouseX:" + mu_mouseX);
                console.info("half_box_width:" + half_box_width);
                console.info("tar_pos.left:" + tar_pos.left);
                console.info("$target.width():" + $target.width());
                console.info("移动高度 mm_mouseY:" + mu_mouseY);
                console.info("模板高度 half_box_height:" + half_box_height);
                console.info("mu_mouseY + half_box_height:" + (mu_mouseY + half_box_height));
                console.info("mu_mouseY - half_box_height:" + (mu_mouseY - half_box_height));
                console.info("target相对高度 tar_pos.top:" + tar_pos.top);
                console.info("target高度 $target.height():" + $target.height());
                console.info("tar_pos.top + $target.height():" + (tar_pos.top + $target.height()));
                console.info("====================mouseup======");

                // acting only if mouse is in right place
                if (mu_mouseX > tar_pos.left &&
                    mu_mouseX < tar_pos.left + $target.width() &&
                    mu_mouseY + half_box_height > tar_pos.top &&
                    mu_mouseY - half_box_height < tar_pos.top + $target.height()
                ) {
                    $temp.attr("style", null);
                    // where to add
                    if (tops.length > 0) {
                        $($temp.html()).insertBefore(tops[0]);
                    } else {
                        $("#target fieldset").append($temp.append("\n\n\ \ \ \ ").html());
                    }
                    console.info("==================== mouseup tops.length:" + tops.length + "======");
                } else {
                    // no add
                    $("#target .component").css({"border-top": "1px dashed #ccc", "border-bottom": "none"});
                    tops = [];
                    console.info("==================== mouseup remove ======");
                }

                //clean up & add popover
                $target.css("background-color", "#fff");
                $(document).undelegate("body", "mousemove");
                $("body").undelegate("#temp", "mouseup");
                $("#target .component").popover({trigger: "manual"});
                $temp.remove();
                LPB.genSource();
            });
        }, delays[type]);

        $(document).mouseup(function () {
            clearInterval(delayed);
            return false;
        });
        $(this).mouseout(function () {
            clearInterval(delayed);
            return false;
        });
    });

    //activate legend popover
    $("#target .component").popover({trigger: "manual"});
    //popover on click event
    $("#target").delegate(".component", "click", function (e) {
        e.preventDefault();
        //$(".popover").hide();
        var active_component = $(this);
        active_component.popover("show");
        //class="leipiplugins"
        var leipiplugins = active_component.find(".leipiplugins"), plugins = $(leipiplugins).attr("leipiplugins");//leipiplugins="text"
        //exec plugins
        if (typeof(LPB.plugins[plugins]) == 'function') {
            try {
                LPB.plugins[plugins](active_component, leipiplugins);
            } catch (e) {
                alert('控件异常，请反馈给开发工程师！');
            }
        } else {
            alert("控件有误或不存在，请与我们联系！");
        }

    });
});