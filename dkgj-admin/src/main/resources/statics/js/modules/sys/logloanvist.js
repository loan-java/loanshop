var originalData;
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/logloanvist/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 50, key: true},
            {label: '产品名称', name: 'loanName', index: 'loan_id', width: 80},
            {label: '手机号', name: 'mobile', index: 'user_id', width: 80},
            {label: '点击IP', name: 'ip', index: 'ip', width: 80},
            {label: '用户渠道', name: 'channel', index: 'channel', width: 80},
            {label: '点击时间', name: 'createTime', index: 'create_time', width: 80}
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
            var createdAt = dateFormat(new Date(), "yyyy/MM/dd");
            $('#qcreatedate').val(createdAt);
            originalData = res;
            console.log(res);
            createdAt = originalData.page.params.createDate;
            if (createdAt != undefined && createdAt != null) {
                $('#qcreatedate').val(createdAt);
            }
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    //初始化
    zaneDate({
        elem: '#qcreatedate',
        min: '2019-04-18'
    })
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        q: {},
        logLoanVist: {}
    },
    updated: function () {
        if (originalData != undefined) {
            $('#qcreatedate').val(originalData.page.params.createDate);
            vm.q.createDate = originalData.page.params.createDate;
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        download: function () {
            window.location.href = baseURL + "sys/logloanvist/export?ip=" + (vm.q.ip == undefined ? "" : vm.q.ip)
                + "&mobile=" + (vm.q.mobile == undefined ? "" : vm.q.mobile)
                + "&createDate=" + $('#qcreatedate').val()
                + "&cd=" + (vm.q.cd == undefined ? "" : vm.q.cd)
                + "&loanName=" + (vm.q.loanName == undefined ? "" : vm.q.loanName);
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.logLoanVist = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.logLoanVist.id == null ? "sys/logloanvist/save" : "sys/logloanvist/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.logLoanVist),
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
                    url: baseURL + "sys/logloanvist/delete",
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
            $.get(baseURL + "sys/logloanvist/info/" + id, function (r) {
                vm.logLoanVist = r.logLoanVist;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'createDate': $('#qcreatedate').val(),
                    'mobile': vm.q.mobile,
                    'ip': vm.q.ip,
                    'loanName': vm.q.loanName,
                    'cd': vm.q.cd
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});