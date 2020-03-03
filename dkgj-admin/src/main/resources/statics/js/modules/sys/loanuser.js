var dateTime = new Date();
dateTime = dateTime.setDate(dateTime.getDate() + 1);
dateTime = new Date(dateTime);

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/loanuser/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 50, key: true},
            {label: '手机号', name: 'mobile', index: 'mobile', width: 80},
            {label: '渠道', name: 'wxId', index: 'wx_id', width: 80},
            {label: '注册IP', name: 'ip', index: 'ip', width: 80},
            {label: '最后登录IP', name: 'lastLoginIp', index: 'last_login_ip', width: 80},
            {label: '最后登录时间', name: 'lastLoginTime', index: 'last_login_time', width: 80},
            {
                label: '最后登录OS',
                name: 'lastLoginOs',
                index: 'last_login_os',
                width: 80,
                formatter: function (cellValue, options, rowObject) {
                    switch (cellValue) {
                        case 1:
                            return "IOS";
                            break;
                        case 2:
                            return "Android";
                            break;
                        default:
                            return "";
                    }
                }
            },
            {label: '创建时间', name: 'createdAt', index: 'created_at', width: 80}/*,
			{ label: '', name: 'updatedat', index: 'updatedAt', width: 80 }, 			
			{ label: '', name: 'deletedat', index: 'deletedAt', width: 80 }			*/
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
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    //初始化
    zaneDate({
        elem: '#qcreatedAt',
        type: 'doubleday'
    })

    var queryDate = dateFormat(new Date(), "yyyy/MM/dd") + "-" + dateFormat(dateTime, "yyyy/MM/dd");
    $('#qcreatedAt').val(queryDate);
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            createdAt: dateFormat(new Date(), "yyyy/MM/dd") + "-" + dateFormat(dateTime, "yyyy/MM/dd")
        },
        showList: true,
        title: null,
        user: {},
        options: null
    },
    updated: function () {
        this.q.createdAt = $('#qcreatedAt').val();
    },
    mounted: function () {
        $.get(baseURL + "sys/dept/channel/options", function (r) {
            vm.options = r;
        });
    },
    methods: {
        download: function () {
            window.location.href = baseURL + "sys/loanuser/export?id=" + (vm.q.id == undefined ? "" : vm.q.id)
            + "&mobile=" + (vm.q.mobile == undefined ? "" : vm.q.mobile)
            + "&wxId=" + (vm.q.wxId == undefined ? "" : vm.q.wxId)
            + "&state=" + (vm.q.state == undefined ? "" : vm.q.state)
                + "&createdAt=" + $('#qcreatedAt').val();
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.user = {};
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
            var url = vm.user.id == null ? "sys/loanuser/save" : "sys/loanuser/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
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
                    url: baseURL + "sys/loanuser/delete",
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
            $.get(baseURL + "sys/loanuser/info/" + id, function (r) {
                vm.user = r.user;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            vm.q.createAt = $('#qcreatedAt').val();
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: {
                    'id': vm.q.id,
                    'mobile': vm.q.mobile,
                    'wxId': vm.q.wxId,
                    'state': vm.q.state,
                    'createdAt': $('#qcreatedAt').val()
                }
            }).trigger("reloadGrid");
        }
    }
});