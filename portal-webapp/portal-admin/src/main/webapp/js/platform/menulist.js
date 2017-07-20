//打开icons页面
function open_icons() {
    window.open("http://fontawesome.io/icons/");
};

$(function () {
    $("#jqGrid").jqGrid({
        url: '../forfront/menu/list',     // 请求后台json数据的url
        datatype: "json",                // 后台返回的数据格式
        // 列表标题及列表模型
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true},
            {label: '菜单名称', name: 'name', index: 'name', width: 80},
            {label: '菜单地址', name: 'url', index: 'url', width: 80},
            {label: '父菜单id', name: 'pid', index: 'pid', sortable: false, width: 80},
            {
                label: '菜单图标', name: 'icon', sortable: false, width: 50, formatter: function (value, options, row) {
                return value == null ? '' : '<i class="' + value + ' fa-lg"></i>';
            }
            },
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 80},
            // {label: '状态', name: 'status', index: 'status', width: 80 },
            {label: '排序', name: 'sort', index: 'sort', width: 80}
        ],
        viewrecords: true,
        height: 385,            // 表格高度
        rowNum: 50,             // 一页显示的行记录数
        rowList: [50, 100],     // 翻页控制条中 每页显示记录数可选集合
        rownumbers: true,       // 是否显示行号，默认值是false，不显示
        rownumWidth: 25,
        autowidth: true,
        autoScroll: true,
        shrinkToFit: true,

        multiselect: true,
        pager: "#jqGridPager",          // 翻页DOM
        jsonReader: {                   // 重写后台返回数据的属性
            root: "page.list",          // 将rows修改为page.list
            page: "page.currPage",      // 将page修改为page.currPage
            total: "page.totalPage",    // 将total修改为page.totalPage
            records: "page.totalCount"  // 将records修改为page.totalCount
        },
        prmNames: {              // 改写请求参数属性
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
            //设置高度
            $("#jqGrid").jqGrid('setGridHeight', getWinH());
        }
    });
});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        menu: {
            parentName: null,
            pid: 0,
            type: 1,
            sort: 0
        }
    },
    methods: {
        query: function () {
            //vm.reload();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    pid: vm.menu.pid,
                    name: vm.menu.name
                },
                page: 1
            }).trigger("reloadGrid");
        },
        getMenu: function (id) {
            //加载菜单树
            $.get("../forfront/menu/select", function (r) {
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
                var node = ztree.getNodeByParam("id", vm.menu.pid);
                ztree.selectNode(node);

                vm.menu.parentName = node.name;
            })
        },
        bindIcon: function (icon) {
            $('#icon').selectpicker('val', icon);
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.menu = {parentName: null, pid: 0, type: 1, sort: 0};
            vm.getMenu();
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }

            $.get("../forfront/menu/info/" + id, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.menu = r.menu;
                vm.parentName = r.menu.pName;

                vm.getMenu();
                vm.bindIcon(vm.menu.icon);
            });
        },
        del: function (event) {
            var menuIds = getSelectedRows();
            if (menuIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../forfront/menu/delete",
                    data: JSON.stringify(menuIds),
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
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.menu.id == null ? "../forfront/menu/save" : "../forfront/menu/update";
            vm.menu.icon = $('#icon').val();
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.menu),
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
        menuTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.menu.pid = node[0].id;
                    vm.menu.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        getLess: function () {
            var jsonstr = "[{name:\"fa-glass\"},{name:\"fa-music\"},{name:\"fa-search\"},{name:\"fa-envelope-o\"},{name:\"fa-heart\"},{name:\"fa-star\"},{name:\"fa-star-o\"},{name:\"fa-user\"},{name:\"fa-film\"},{name:\"fa-th-large\"},{name:\"fa-th\"},{name:\"fa-th-list\"},{name:\"fa-check\"},{name:\"fa-times\"},{name:\"fa-search-plus\"},{name:\"fa-search-minus\"},{name:\"fa-power-off\"},{name:\"fa-signal\"},{name:\"fa-cog\"},{name:\"fa-trash-o\"},{name:\"fa-home\"},{name:\"fa-file-o\"},{name:\"fa-clock-o\"},{name:\"fa-road\"},{name:\"fa-download\"},{name:\"fa-arrow-circle-o-down\"},{name:\"fa-arrow-circle-o-up\"},{name:\"fa-inbox\"},{name:\"fa-play-circle-o\"},{name:\"fa-repeat\"},{name:\"fa-refresh\"},{name:\"fa-list-alt\"},{name:\"fa-lock\"},{name:\"fa-flag\"},{name:\"fa-headphones\"},{name:\"fa-volume-off\"},{name:\"fa-volume-down\"},{name:\"fa-volume-up\"},{name:\"fa-qrcode\"},{name:\"fa-barcode\"},{name:\"fa-tag\"},{name:\"fa-tags\"},{name:\"fa-book\"},{name:\"fa-bookmark\"},{name:\"fa-print\"},{name:\"fa-camera\"},{name:\"fa-font\"},{name:\"fa-bold\"},{name:\"fa-italic\"},{name:\"fa-text-height\"},{name:\"fa-text-width\"},{name:\"fa-align-left\"},{name:\"fa-align-center\"},{name:\"fa-align-right\"},{name:\"fa-align-justify\"},{name:\"fa-list\"},{name:\"fa-outdent\"},{name:\"fa-indent\"},{name:\"fa-video-camera\"},{name:\"fa-picture-o\"},{name:\"fa-pencil\"},{name:\"fa-map-marker\"},{name:\"fa-adjust\"},{name:\"fa-tint\"},{name:\"fa-pencil-square-o\"},{name:\"fa-share-square-o\"},{name:\"fa-check-square-o\"},{name:\"fa-arrows\"},{name:\"fa-step-backward\"},{name:\"fa-fast-backward\"},{name:\"fa-backward\"},{name:\"fa-play\"},{name:\"fa-pause\"},{name:\"fa-stop\"},{name:\"fa-forward\"},{name:\"fa-fast-forward\"},{name:\"fa-step-forward\"},{name:\"fa-eject\"},{name:\"fa-chevron-left\"},{name:\"fa-chevron-right\"},{name:\"fa-plus-circle\"},{name:\"fa-minus-circle\"},{name:\"fa-times-circle\"},{name:\"fa-check-circle\"},{name:\"fa-question-circle\"},{name:\"fa-info-circle\"},{name:\"fa-crosshairs\"},{name:\"fa-times-circle-o\"},{name:\"fa-check-circle-o\"},{name:\"fa-ban\"},{name:\"fa-arrow-left\"},{name:\"fa-arrow-right\"},{name:\"fa-arrow-up\"},{name:\"fa-arrow-down\"},{name:\"fa-share\"},{name:\"fa-expand\"},{name:\"fa-compress\"},{name:\"fa-plus\"},{name:\"fa-minus\"},{name:\"fa-asterisk\"},{name:\"fa-exclamation-circle\"},{name:\"fa-gift\"},{name:\"fa-leaf\"},{name:\"fa-fire\"},{name:\"fa-eye\"},{name:\"fa-eye-slash\"},{name:\"fa-exclamation-triangle\"},{name:\"fa-plane\"},{name:\"fa-calendar\"},{name:\"fa-random\"},{name:\"fa-comment\"},{name:\"fa-magnet\"},{name:\"fa-chevron-up\"},{name:\"fa-chevron-down\"},{name:\"fa-retweet\"},{name:\"fa-shopping-cart\"},{name:\"fa-folder\"},{name:\"fa-folder-open\"},{name:\"fa-arrows-v\"},{name:\"fa-arrows-h\"},{name:\"fa-bar-chart\"},{name:\"fa-twitter-square\"},{name:\"fa-facebook-square\"},{name:\"fa-camera-retro\"},{name:\"fa-key\"},{name:\"fa-cogs\"},{name:\"fa-comments\"},{name:\"fa-thumbs-o-up\"},{name:\"fa-thumbs-o-down\"},{name:\"fa-star-half\"},{name:\"fa-heart-o\"},{name:\"fa-sign-out\"},{name:\"fa-linkedin-square\"},{name:\"fa-thumb-tack\"},{name:\"fa-external-link\"},{name:\"fa-sign-in\"},{name:\"fa-trophy\"},{name:\"fa-github-square\"},{name:\"fa-upload\"},{name:\"fa-lemon-o\"},{name:\"fa-phone\"},{name:\"fa-square-o\"},{name:\"fa-bookmark-o\"},{name:\"fa-phone-square\"},{name:\"fa-twitter\"},{name:\"fa-facebook\"},{name:\"fa-github\"},{name:\"fa-unlock\"},{name:\"fa-credit-card\"},{name:\"fa-rss\"},{name:\"fa-hdd-o\"},{name:\"fa-bullhorn\"},{name:\"fa-bell\"},{name:\"fa-certificate\"},{name:\"fa-hand-o-right\"},{name:\"fa-hand-o-left\"},{name:\"fa-hand-o-up\"},{name:\"fa-hand-o-down\"},{name:\"fa-arrow-circle-left\"},{name:\"fa-arrow-circle-right\"},{name:\"fa-arrow-circle-up\"},{name:\"fa-arrow-circle-down\"},{name:\"fa-globe\"},{name:\"fa-wrench\"},{name:\"fa-tasks\"},{name:\"fa-filter\"},{name:\"fa-briefcase\"},{name:\"fa-arrows-alt\"},{name:\"fa-users\"},{name:\"fa-link\"},{name:\"fa-cloud\"},{name:\"fa-flask\"},{name:\"fa-scissors\"},{name:\"fa-files-o\"},{name:\"fa-paperclip\"},{name:\"fa-floppy-o\"},{name:\"fa-square\"},{name:\"fa-bars\"},{name:\"fa-list-ul\"},{name:\"fa-list-ol\"},{name:\"fa-strikethrough\"},{name:\"fa-underline\"},{name:\"fa-table\"},{name:\"fa-magic\"},{name:\"fa-truck\"},{name:\"fa-pinterest\"},{name:\"fa-pinterest-square\"},{name:\"fa-google-plus-square\"},{name:\"fa-google-plus\"},{name:\"fa-money\"},{name:\"fa-caret-down\"},{name:\"fa-caret-up\"},{name:\"fa-caret-left\"},{name:\"fa-caret-right\"},{name:\"fa-columns\"},{name:\"fa-sort\"},{name:\"fa-sort-desc\"},{name:\"fa-sort-asc\"},{name:\"fa-envelope\"},{name:\"fa-linkedin\"},{name:\"fa-undo\"},{name:\"fa-gavel\"},{name:\"fa-tachometer\"},{name:\"fa-comment-o\"},{name:\"fa-comments-o\"},{name:\"fa-bolt\"},{name:\"fa-sitemap\"},{name:\"fa-umbrella\"},{name:\"fa-clipboard\"},{name:\"fa-lightbulb-o\"},{name:\"fa-exchange\"},{name:\"fa-cloud-download\"},{name:\"fa-cloud-upload\"},{name:\"fa-user-md\"},{name:\"fa-stethoscope\"},{name:\"fa-suitcase\"},{name:\"fa-bell-o\"},{name:\"fa-coffee\"},{name:\"fa-cutlery\"},{name:\"fa-file-text-o\"},{name:\"fa-building-o\"},{name:\"fa-hospital-o\"},{name:\"fa-ambulance\"},{name:\"fa-medkit\"},{name:\"fa-fighter-jet\"},{name:\"fa-beer\"},{name:\"fa-h-square\"},{name:\"fa-plus-square\"},{name:\"fa-angle-double-left\"},{name:\"fa-angle-double-right\"},{name:\"fa-angle-double-up\"},{name:\"fa-angle-double-down\"},{name:\"fa-angle-left\"},{name:\"fa-angle-right\"},{name:\"fa-angle-up\"},{name:\"fa-angle-down\"},{name:\"fa-desktop\"},{name:\"fa-laptop\"},{name:\"fa-tablet\"},{name:\"fa-mobile\"},{name:\"fa-circle-o\"},{name:\"fa-quote-left\"},{name:\"fa-quote-right\"},{name:\"fa-spinner\"},{name:\"fa-circle\"},{name:\"fa-reply\"},{name:\"fa-github-alt\"},{name:\"fa-folder-o\"},{name:\"fa-folder-open-o\"},{name:\"fa-smile-o\"},{name:\"fa-frown-o\"},{name:\"fa-meh-o\"},{name:\"fa-gamepad\"},{name:\"fa-keyboard-o\"},{name:\"fa-flag-o\"},{name:\"fa-flag-checkered\"},{name:\"fa-terminal\"},{name:\"fa-code\"},{name:\"fa-reply-all\"},{name:\"fa-star-half-o\"},{name:\"fa-location-arrow\"},{name:\"fa-crop\"},{name:\"fa-code-fork\"},{name:\"fa-chain-broken\"},{name:\"fa-question\"},{name:\"fa-info\"},{name:\"fa-exclamation\"},{name:\"fa-superscript\"},{name:\"fa-subscript\"},{name:\"fa-eraser\"},{name:\"fa-puzzle-piece\"},{name:\"fa-microphone\"},{name:\"fa-microphone-slash\"},{name:\"fa-shield\"},{name:\"fa-calendar-o\"},{name:\"fa-fire-extinguisher\"},{name:\"fa-rocket\"},{name:\"fa-maxcdn\"},{name:\"fa-chevron-circle-left\"},{name:\"fa-chevron-circle-right\"},{name:\"fa-chevron-circle-up\"},{name:\"fa-chevron-circle-down\"},{name:\"fa-html5\"},{name:\"fa-css3\"},{name:\"fa-anchor\"},{name:\"fa-unlock-alt\"},{name:\"fa-bullseye\"},{name:\"fa-ellipsis-h\"},{name:\"fa-ellipsis-v\"},{name:\"fa-rss-square\"},{name:\"fa-play-circle\"},{name:\"fa-ticket\"},{name:\"fa-minus-square\"},{name:\"fa-minus-square-o\"},{name:\"fa-level-up\"},{name:\"fa-level-down\"},{name:\"fa-check-square\"},{name:\"fa-pencil-square\"},{name:\"fa-external-link-square\"},{name:\"fa-share-square\"},{name:\"fa-compass\"},{name:\"fa-caret-square-o-down\"},{name:\"fa-caret-square-o-up\"},{name:\"fa-caret-square-o-right\"},{name:\"fa-eur\"},{name:\"fa-gbp\"},{name:\"fa-usd\"},{name:\"fa-inr\"},{name:\"fa-jpy\"},{name:\"fa-rub\"},{name:\"fa-krw\"},{name:\"fa-btc\"},{name:\"fa-file\"},{name:\"fa-file-text\"},{name:\"fa-sort-alpha-asc\"},{name:\"fa-sort-alpha-desc\"},{name:\"fa-sort-amount-asc\"},{name:\"fa-sort-amount-desc\"},{name:\"fa-sort-numeric-asc\"},{name:\"fa-sort-numeric-desc\"},{name:\"fa-thumbs-up\"},{name:\"fa-thumbs-down\"},{name:\"fa-youtube-square\"},{name:\"fa-youtube\"},{name:\"fa-xing\"},{name:\"fa-xing-square\"},{name:\"fa-youtube-play\"},{name:\"fa-dropbox\"},{name:\"fa-stack-overflow\"},{name:\"fa-instagram\"},{name:\"fa-flickr\"},{name:\"fa-adn\"},{name:\"fa-bitbucket\"},{name:\"fa-bitbucket-square\"},{name:\"fa-tumblr\"},{name:\"fa-tumblr-square\"},{name:\"fa-long-arrow-down\"},{name:\"fa-long-arrow-up\"},{name:\"fa-long-arrow-left\"},{name:\"fa-long-arrow-right\"},{name:\"fa-apple\"},{name:\"fa-windows\"},{name:\"fa-android\"},{name:\"fa-linux\"},{name:\"fa-dribbble\"},{name:\"fa-skype\"},{name:\"fa-foursquare\"},{name:\"fa-trello\"},{name:\"fa-female\"},{name:\"fa-male\"},{name:\"fa-gittip\"},{name:\"fa-sun-o\"},{name:\"fa-moon-o\"},{name:\"fa-archive\"},{name:\"fa-bug\"},{name:\"fa-vk\"},{name:\"fa-weibo\"},{name:\"fa-bamboo\"},{name:\"fa-pagelines\"},{name:\"fa-stack-exchange\"},{name:\"fa-arrow-circle-o-right\"},{name:\"fa-arrow-circle-o-left\"},{name:\"fa-caret-square-o-left\"},{name:\"fa-dot-circle-o\"},{name:\"fa-wheelchair\"},{name:\"fa-vimeo-square\"},{name:\"fa-try\"},{name:\"fa-plus-square-o\"},{name:\"fa-space-shuttle\"},{name:\"fa-slack\"},{name:\"fa-envelope-square\"},{name:\"fa-wordpress\"},{name:\"fa-openid\"},{name:\"fa-university\"},{name:\"fa-graduation-cap\"},{name:\"fa-yahoo\"},{name:\"fa-google\"},{name:\"fa-reddit\"},{name:\"fa-reddit-square\"},{name:\"fa-stumbleupon-circle\"},{name:\"fa-stumbleupon\"},{name:\"fa-delicious\"},{name:\"fa-digg\"},{name:\"fa-pied-piper\"},{name:\"fa-pied-piper-alt\"},{name:\"fa-drupal\"},{name:\"fa-joomla\"},{name:\"fa-language\"},{name:\"fa-fax\"},{name:\"fa-building\"},{name:\"fa-child\"},{name:\"fa-paw\"},{name:\"fa-spoon\"},{name:\"fa-cube\"},{name:\"fa-cubes\"},{name:\"fa-behance\"},{name:\"fa-behance-square\"},{name:\"fa-steam\"},{name:\"fa-steam-square\"},{name:\"fa-recycle\"},{name:\"fa-car\"},{name:\"fa-taxi\"},{name:\"fa-tree\"},{name:\"fa-spotify\"},{name:\"fa-deviantart\"},{name:\"fa-soundcloud\"},{name:\"fa-database\"},{name:\"fa-file-pdf-o\"},{name:\"fa-file-word-o\"},{name:\"fa-file-excel-o\"},{name:\"fa-file-powerpoint-o\"},{name:\"fa-file-image-o\"},{name:\"fa-file-archive-o\"},{name:\"fa-file-audio-o\"},{name:\"fa-file-video-o\"},{name:\"fa-file-code-o\"},{name:\"fa-vine\"},{name:\"fa-codepen\"},{name:\"fa-jsfiddle\"},{name:\"fa-life-ring\"},{name:\"fa-circle-o-notch\"},{name:\"fa-rebel\"},{name:\"fa-empire\"},{name:\"fa-git-square\"},{name:\"fa-git\"},{name:\"fa-hacker-news\"},{name:\"fa-tencent-weibo\"},{name:\"fa-qq\"},{name:\"fa-weixin\"},{name:\"fa-paper-plane\"},{name:\"fa-paper-plane-o\"},{name:\"fa-history\"},{name:\"fa-circle-thin\"},{name:\"fa-header\"},{name:\"fa-paragraph\"},{name:\"fa-sliders\"},{name:\"fa-share-alt\"},{name:\"fa-share-alt-square\"},{name:\"fa-bomb\"},{name:\"fa-futbol-o\"},{name:\"fa-tty\"},{name:\"fa-binoculars\"},{name:\"fa-plug\"},{name:\"fa-slideshare\"},{name:\"fa-twitch\"},{name:\"fa-yelp\"},{name:\"fa-newspaper-o\"},{name:\"fa-wifi\"},{name:\"fa-calculator\"},{name:\"fa-paypal\"},{name:\"fa-google-wallet\"},{name:\"fa-cc-visa\"},{name:\"fa-cc-mastercard\"},{name:\"fa-cc-discover\"},{name:\"fa-cc-amex\"},{name:\"fa-cc-paypal\"},{name:\"fa-cc-stripe\"},{name:\"fa-bell-slash\"},{name:\"fa-bell-slash-o\"},{name:\"fa-trash\"},{name:\"fa-copyright\"},{name:\"fa-at\"},{name:\"fa-eyedropper\"},{name:\"fa-paint-brush\"},{name:\"fa-birthday-cake\"},{name:\"fa-area-chart\"},{name:\"fa-pie-chart\"},{name:\"fa-line-chart\"},{name:\"fa-lastfm\"},{name:\"fa-lastfm-square\"},{name:\"fa-toggle-off\"},{name:\"fa-toggle-on\"},{name:\"fa-bicycle\"},{name:\"fa-bus\"},{name:\"fa-ioxhost\"},{name:\"fa-angellist\"},{name:\"fa-cc\"},{name:\"fa-ils\"},{name:\"fa-meanpath\"}]";
            var json = eval(jsonstr)
            $("#icon").empty();
            for (var i = 0; i < json.length; i++) {
                var item = json[i].name;
                $("#icon").append("<option class='fa " + item + "' value='fa " + item + "' style='text-align: left;padding-right: 20px'>fa " + item + "</option>");
            }
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

$(function () {
    $(document).ready()
    {
        vm.getLess();
    }
    ;
});