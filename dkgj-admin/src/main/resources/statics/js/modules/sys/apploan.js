$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/apploan/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 50, key: true},
            {
                label: '名称',
                name: 'title',
                index: 'title',
                width: 100,
                formatter: function (cellValue, options, rowObject) {
                    return "<img src='" + rowObject["logo"] + "' width='20' height='20'>" + cellValue;
                }
            },
            {
                label: '状态',
                name: 'state',
                index: 'state',
                width: 100,
                formatter: function (cellValue, options, rowObject) {
                    switch (cellValue) {
                        case 1:
                            return "上架";
                            break;
                        case 2:
                            return "暂时下架";
                            break;
                        case 3:
                            return "<span class=\"label label-danger\">等待上架</span>";
                            break;
                    }
                }
            },
            {
                label: '贷款类型',
                name: 'tags',
                index: 'tags',
                width: 100,
                formatter: function (cellValue, options, rowObject) {
                    switch (cellValue) {
                        case 'HIGH':
                            return "贷款额度高";
                            break;
                        case 'LOW':
                            return "贷款利率低";
                            break;
                        case 'FAST':
                            return "放款速度快";
                            break;
                    }
                }
            },
            {label: '最高可贷', name: 'maxloan', index: 'maxLoan', width: 100},
            {
                label: '申请链接',
                name: 'applyurl',
                index: 'applyUrl',
                width: 100,
                formatter: function (cellValue, options, rowObject) {
                    return "<div style='width: 40px;text-align:center;border-radius:5px;background: #8391a5;color: #ffffff;' class='mark_data' data='" + cellValue + "'>链接</div>";
                }
            },
            {label: '申请人数', name: 'applynum', index: 'applyNum', width: 100},
            {
                label: '角标',
                name: 'badge',
                index: 'badge',
                width: 100,
                formatter: function (cellValue, options, rowObject) {
                    switch (cellValue) {
                        case 'RECOMMENDED':
                            return "推荐";
                            break;
                        case 'HOT':
                            return "爆款";
                            break;
                        case 'OPTIMIZATION':
                            return "优选";
                            break;
                    }
                }
            },
            {label: '创建时间', name: 'createdat', index: 'createdAt', width: 140},
            {label: '权重', name: 'weights', index: 'weights', width: 100}
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


    //初始化
    zaneDate({
        elem: '#qcreatedAt',
        type: 'doubleday'
    })


    $("#drop-area").dmUploader({
        url: baseURL + 'sys/oss/upload',
        //... More settings here...
        onInit: function () {
            console.log('Callback: Plugin initialized');
        },
        onUploadSuccess: function (id, data) {
            console.log(data);
            if (data.code == 0) {
                upImgUrl = data.url;
                $('#fileCallback').val(data.url);
                $('.file').css({'background-image': 'url(' + data.url + ')', 'background-size': '100% 100%'});
            } else {
                alert(data.msg);
            }
        }
    });

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {},
        showList: true,
        title: null,
        loan: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.loan = {};
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
            var url = vm.loan.id == null ? "sys/apploan/save" : "sys/apploan/update";
            vm.loan.logo = upImgUrl;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.loan),
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
                    url: baseURL + "sys/apploan/delete",
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
            $.get(baseURL + "sys/apploan/info/" + id, function (r) {
                vm.loan = r.loan;
                upImgUrl = r.loan.logo;
                $('.file').css({'background-image': 'url(' + upImgUrl + ')', 'background-size': '100% 100%'});
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'id': vm.q.id,
                    'title': vm.q.title,
                    'state': vm.q.state,
                    'tags': vm.q.tags,
                    'applyurl': vm.q.applyurl,
                    'badge': vm.q.badge,
                    'createdAt': $('#qcreatedAt').val()
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});