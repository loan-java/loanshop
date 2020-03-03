$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/channelmanage/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 50, key: true},
            {
                label: '渠道链接', name: 'channelurl', index: 'channelUrl', width: 80,
                formatter: function (cellValue, options, rowObject) {
                    return "<div style='width: 40px;text-align:center;border-radius:5px;background: #8391a5;color: #ffffff;' class='mark_data' data='" + cellValue + "'>链接</div>";
                }
            },
            {label: '渠道代码', name: 'channelcode', index: 'channelCode', width: 80},
            {label: '渠道名称', name: 'channelname', index: 'channelName', width: 80},
            /*{label: '查询该渠道注册总人数链接', name: 'checkregisterurl', index: 'checkRegisterUrl', width: 80},*/
            {
                label: '状态', name: 'state', index: 'state', width: 80,
                formatter: function (cellValue, options, rowObject) {
                    switch (cellValue) {
                        case 0:
                            return "在线";
                            break;
                        case 1:
                            return "暂时下线";
                            break;
                    }
                }
            },
            {label: '备注', name: 'remark', index: 'remark', width: 80},
            {label: '创建时间', name: 'createdat', index: 'createdAt', width: 80}
            /*{ label: '', name: 'updatedat', index: 'updatedAt', width: 80 },
            { label: '', name: 'deletedat', index: 'deletedAt', width: 80 }, */

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


    var swfPath = baseURL+"statics/plugins/zclip/ZeroClipboard.swf";
    $("#jqGrid").on("mouseover", ".mark_data", function () {
        //TODO...
        var _this = $(this);
        layer.tips(
            "<span id='copyValue' style='color: #0a0a0a;word-wrap:break-word;width: 230px;'>" + _this.attr("data") + "</span>" +
            "<div><a href='" + _this.attr("data") + "' target='_blank'>在新标签中打开</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
            "<a href='javascript:;' id='copyBtn'>复制</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
            "<a href='javascript:;'>推送</a></div>"
            , _this, {
                tips: [1, '#ffffff'],
                area: ['240px', 'auto']
            });

        $("#copyBtn").zclip({
            path: swfPath,
            copy: $('#copyValue').html(),
            beforeCopy: function () {
                //some code
                //alert('开始复制');
            },
            afterCopy: function () {
                alert('复制成功');
            }
        });
    });

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {},
        showList: true,
        edit: false,
        title: null,
        channelManage: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.channelManage = {};
            vm.edit = false;
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.edit = true;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.channelManage.id == null ? "sys/channelmanage/save" : "sys/channelmanage/update";
            vm.channelManage.deductDateStr = $('#deductDate').val();
            console.log(vm.channelManage.state);
            if (vm.channelManage.state == '在线') {
                vm.channelManage.state = 0;
            }
            if (vm.channelManage.state == '暂时下线') {
                vm.channelManage.state = 1;
            }
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.channelManage),
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
                    url: baseURL + "sys/channelmanage/delete",
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
            $.get(baseURL + "sys/channelmanage/info/" + id, function (r) {
                vm.channelManage = r.channelManage;

                console.log(vm.channelManage.state);
                if (vm.channelManage.state === 0) {
                    vm.channelManage.state = '上线';
                }
                if (vm.channelManage.state === 1) {
                    vm.channelManage.state = '暂时下线';
                }
            });
        },
        indexSelect: function (event) {
            console.log(event.target.value);
            if (event.target.value == 1) {
                $('#deduct-div').css("display", "block");
            } else {
                $('#deduct-div').css("display", "none");
            }
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            if (vm.q.state == '全部') {
                vm.q.state = '';
            }
            if (vm.q.state == '上线') {
                vm.q.state = 0;
            }
            if (vm.q.state == '暂时下线') {
                vm.q.state = 1;
            }

            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: {
                    'id': vm.q.id,
                    'channelcode': vm.q.channelcode,
                    'channelname': vm.q.channelname,
                    'state': vm.q.state
                },
            }).trigger("reloadGrid");
        }
    }
});