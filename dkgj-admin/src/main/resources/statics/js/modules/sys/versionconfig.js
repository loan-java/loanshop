$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/versionconfig/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 50, key: true},
            {label: '渠道代码', name: 'channel', index: 'channel', width: 60},
            {
                label: 'APP类型',
                name: 'type',
                index: 'type',
                width: 60,
                formatter: function (cellValue, options, rowObject) {
                    switch (cellValue) {
                        case 1:
                            return 'ios';
                            break;
                        case 2:
                            return 'android';
                            break;
                    }
                }
            },
            {label: '版本号', name: 'version', index: 'version', width: 60},
            {label: '更新描述', name: 'content', index: 'content', width: 60},
            {
                label: '是否强制',
                name: 'isForce',
                index: 'is_force',
                width: 80,
                formatter: function (cellValue, options, rowObject) {
                    switch (cellValue) {
                        case 0:
                            return '<span class="label label-danger">否</span>';
                            break;
                        case 1:
                            return '<span class="label label-default">是</span>';
                            break;
                    }
                }
            },
            {
                label: '下载地址',
                name: 'link',
                index: 'link',
                width: 60,
                formatter: function (cellValue, options, rowObject) {
                    return "<div style='width: 40px;text-align:center;border-radius:5px;background: #8391a5;color: #ffffff;' class='mark_data' data='" + cellValue + "'>链接</div>";
                }
            },
            {label: '创建时间', name: 'createDate', index: 'create_date', width: 120},
            {label: '更新时间', name: 'updateDate', index: 'update_date', width: 120}
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

    var swfPath = baseURL + "statics/plugins/zclip/ZeroClipboard.swf";
    $("#jqGrid").on("mouseover", ".mark_data", function () {
        //TODO...
        var _this = $(this);
        layer.tips(
            "<span id='copyValue' style='color: #0a0a0a;word-wrap:break-word;width: 230px;'>" + _this.attr("data") + "</span>" +
            "<div><a href='" + _this.attr("data") + "' target='_blank'>在新标签中打开</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
            "<a href='javascript:;' id='copyBtn'>复制</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
            "<a href='javascript:;' >推送</a></div>"
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
        showList: true,
        title: null,
        versionConfig: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.versionConfig = {};
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
            var url = vm.versionConfig.id == null ? "sys/versionconfig/save" : "sys/versionconfig/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.versionConfig),
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
                    url: baseURL + "sys/versionconfig/delete",
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
            $.get(baseURL + "sys/versionconfig/info/" + id, function (r) {
                vm.versionConfig = r.versionConfig;
            });
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