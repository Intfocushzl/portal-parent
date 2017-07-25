$(function () {
    $("#btnQueryClear").hide();
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        visit: {},
        visitData: {}
    },
    created: function () {
        // 页面初始化数据
        this.getInfo()
    },
    methods: {
        getInfo: function () {
            // 近30天访问日志统计
            $.get(rcContextPath + "/sysoperationlog/visit", function (r) {
                vm.visit = r.visit;
                initVisitChart();
            });
            // 当日访问日志统计
            $.get(rcContextPath + "/sysoperationlog/visitData", function (r) {
                vm.visitData = r.visitData;
                initVisitDataChart();
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

// 初始化当日访问信息
function initVisitDataChart() {
    var xAxis_data_h = [];    // X 轴数组 日期
    var series_pv = [];     // pv 数组
    var series_uv = [];     // uv 数组
    var series_ip = [];     // ip 数组
    $.each(vm.visitData, function (i, item) {
        xAxis_data_h[i] = item["_date_h"];
        series_pv[i] = item["_pv"];
        series_uv[i] = item["_uv"];
        series_ip[i] = item["_ip"];
    });
    console.info(xAxis_data_h);
    console.info(series_pv);
    console.info(series_uv);
    console.info(series_ip);

    echarts.dispose(document.getElementById('visitDataChart'));
    var visitDataChart = echarts.init(document.getElementById('visitDataChart'));
    var visitDataChartOption = {
        title: {
            text: '当日访问统计',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['浏览次数(PV)', '独立访客(UV)', 'IP']
        },
        grid: {
            left: '2%',
            right: '3%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: xAxis_data_h
        },
        yAxis: {
            type: 'value'
            /*axisLabel: {
             formatter: '{value} °C'
             }*/
        },
        series: [
            {
                name: '浏览次数(PV)',
                type: 'line',
                data: series_pv,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name: '独立访客(UV)',
                type: 'line',
                data: series_uv,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name: 'IP',
                type: 'line',
                data: series_ip,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
        ]
    };
    visitDataChart.setOption(visitDataChartOption);
}

// 初始化30天访问信息
function initVisitChart() {
    var xAxis_data = [];    // X 轴数组 日期
    var series_pv = [];     // pv 数组
    var series_uv = [];     // uv 数组
    var series_ip = [];     // ip 数组
    $.each(vm.visit, function (i, item) {
        xAxis_data[i] = item["_date"];
        series_pv[i] = item["_pv"];
        series_uv[i] = item["_uv"];
        series_ip[i] = item["_ip"];
    });
    console.info(xAxis_data);
    console.info(series_pv);
    console.info(series_uv);
    console.info(series_ip);

    echarts.dispose(document.getElementById('visitChart'));
    var visitChart = echarts.init(document.getElementById('visitChart'));
    var visitChartOption = {
        title: {
            text: '近30天访问统计',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['浏览次数(PV)', '独立访客(UV)', 'IP']
        },
        grid: {
            left: '2%',
            right: '3%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: xAxis_data
        },
        yAxis: {
            type: 'value'
            /*axisLabel: {
             formatter: '{value} °C'
             }*/
        },
        series: [
            {
                name: '浏览次数(PV)',
                type: 'line',
                data: series_pv,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name: '独立访客(UV)',
                type: 'line',
                data: series_uv,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name: 'IP',
                type: 'line',
                data: series_ip,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
        ]
    };
    visitChart.setOption(visitChartOption);
}
