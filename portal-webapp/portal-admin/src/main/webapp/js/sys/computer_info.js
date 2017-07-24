$(function () {
    $("#btnQueryClear").hide();
});

// 初始化信息
function initProperties() {
    $("#os_name").val(vm.properties["os.name"]);
    $("#java_runtime_version").val(vm.properties["java.runtime.version"]);
    $("#java_vm_version").val(vm.properties["java.vm.version"]);
    $("#user_name").val(vm.properties["user.name"]);
    $("#user_home").val(vm.properties["user.home"]);
    $("#user_dir").val(vm.properties["user.dir"]);
    $("#java_io_tmpdir").val(vm.properties["java.io.tmpdir"]);
    if (vm.computerInfo.jvmFreeMemory < 2563){
        $("#m_danger").show();
    } else{
        $("#m_danger").hide();
    }
}

var vm = new Vue({
    el: '#rrapp',
    data: {
        computerInfo: {},
        properties: {}
    },
    created: function () {
        this.getInfo()
    },
    methods: {
        getInfo: function () {
            $.get("../sys/computer/info", function (r) {
                vm.computerInfo = r.computerInfo;
                vm.properties = r.properties;
                initProperties();
            });
        },
        queryAuto: function () {
            // 5秒刷新
            $("#btnQueryAtuo").hide();
            $("#btnQueryClear").show();
            time = setInterval(function () {
                vm.getInfo();
            }, 5000);
        },
        queryClear: function () {
            $("#btnQueryAtuo").show();
            $("#btnQueryClear").hide();
            clearInterval(time), function () {
                vm.getInfo();
            }
        }
    }
});