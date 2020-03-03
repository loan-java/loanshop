var originalData;
var dateTime = new Date();
dateTime = dateTime.setDate(dateTime.getDate() + 1);
dateTime = new Date(dateTime);
var createdAt = null;

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/logloan/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 50, key: true},
            {
                label: '产品ID',
                name: 'relid',
                index: 'relId',
                width: 80,
                formatter: function (cellValue, options, rowObject) {
                    return cellValue + "-(" + rowObject["loanName"] + ")";
                }
            },
            {label: '访问来源', name: 'refer', index: 'refer', width: 80},
            {label: 'PV：0', name: 'pv', index: 'pv', width: 100},
            {label: '今日PV：0', name: 'todayPv', index: 'today_pv', width: 100},
            {label: 'UV：0', name: 'uv', index: 'uv', width: 100},
            {label: '今日UV：0', name: 'todayUv', index: 'today_uv', width: 100},
            {label: '创建时间', name: 'createdat', index: 'createdAt', width: 100}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        loadComplete: function (res) {
            // res是服务器返回的数据
            originalData = res;
            $("#jqGrid").jqGrid('setLabel', "pv", "PV:" + originalData.page.pvNum);
            $("#jqGrid").jqGrid('setLabel', "uv", "UV：" + originalData.page.uvNum);
            $("#jqGrid").jqGrid('setLabel', "todayPv", "今日PV：" + originalData.page.todayPvSum);
            $("#jqGrid").jqGrid('setLabel', "todayUv", "今日UV：" + originalData.page.todayUvSum);

            var createdAt = dateFormat(new Date(), "yyyy/MM/dd") + "-" + dateFormat(dateTime, "yyyy/MM/dd");
            $('#qcreatedAt').val(createdAt);

            createdAt = originalData.page.params.createdAt;
            if (createdAt != undefined && createdAt != null) {
                $('#qcreatedAt').val(createdAt);
            }
            console.log("jquery：" + createdAt);
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });


    //初始化
    zaneDate({
        elem: '#qcreatedAt',
        type: 'doubleday',
        done: function (fulltime, begintime, endtime) {
            $('#qcreatedAt').val(fulltime);
        }
    })

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        options: [],
        q: {
            id: null,
            relId: null,
            refer: null
        },
        showList: true,
        title: null,
        logloan: {}
    },
    updated: function () {
        if (originalData != undefined) {
            $('#qcreatedAt').val(originalData.page.params.createdAt);
            vm.q.createdAt = originalData.page.params.createdAt;
            console.log(originalData.page.params.createdAt);
        }

    },
    mounted: function () {
        $.get(baseURL + "sys/logloan/get_options", function (r) {
            vm.options = r.data;
        });
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.logloan = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id);
        },
        saveOrUpdate: function (event) {
            var url = vm.logloan.id == null ? "sys/logloan/save" : "sys/logloan/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.logloan),
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
                    url: baseURL + "sys/logloan/delete",
                    contentType: "application/json",
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
            $.get(baseURL + "sys/logloan/info/" + id, function (r) {
                vm.logloan = r.logloan;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            vm.q.createAt = $('#qcreatedAt').val();
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'id': vm.q.id,
                    "relId": vm.q.relId,
                    "refer": vm.q.refer,
                    'note': vm.q.note,
                    "createdAt": $('#qcreatedAt').val()
                },
                page: page
            }).trigger("reloadGrid");

        }
    }
});