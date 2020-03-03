var upImgUrl;
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/popup/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 50, key: true},
            {
                label: '操作系统', name: 'os', index: 'os', width: 80,
                formatter: function (cellValue, options, rowObject) {
                    switch (cellValue) {
                        case 1:
                            return "iOS";
                            break;
                        case 2:
                            return "Android";
                            break;
                    }
                }
            },
            {label: '标题', name: 'name', index: 'name', width: 80},
            {
                label: '图片预览',
                name: 'imgPath',
                index: 'img_path',
                width: 80,
                formatter: function (cellValue, options, rowObject) {
                    return "<img src='" + cellValue + "' width='100' height='50'>";
                }
            },
            {
                label: '状态',
                name: 'state',
                index: 'state',
                width: 80,
                formatter: function (cellValue, options, rowObject) {
                    switch (cellValue) {
                        case 1:
                            return "上架";
                            break;
                        case 2:
                            return "暂时下架";
                            break;
                    }
                }
            },
            {label: '渠道编码', name: 'channel', index: 'channel', width: 80},
            {label: '产品ID', name: 'loanId', index: 'loan_id', width: 80},
            {label: '创建时间', name: 'createDate', index: 'create_date', width: 80}
            //{label: '', name: 'updateDate', index: 'update_date', width: 80}
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

    $("#drop-area").dmUploader({
        url: baseURL + 'sys/oss/upload',
        //... More settings here...
        onInit: function () {
            console.log('Callback: Plugin initialized');
        },
        onUploadSuccess: function (id, data) {
            if (data.code == 0) {
                upImgUrl = data.url;
                $('#fileCallback').val(data.url);
                $('.file').css({'background-image': 'url(' + data.url + ')', 'background-size': '100% 100%'});
            } else {
                alert(data.msg);
            }
        }
    });
    $("#channelSelect").chosen({width: "105%"});
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        popup: {},
        channels: null,
        options: null
    },
    mounted: function () {
        $.get(baseURL + "sys/logloan/get_options", function (r) {
            vm.options = r.data;
        });
        $.get(baseURL + "sys/dept/channel/options", function (r) {
            vm.channels = r;
        });
    },
    updated: function () {
        $("#channelSelect").trigger("chosen:updated");
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.popup = {};
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
            var url = vm.popup.id == null ? "sys/popup/save" : "sys/popup/update";
            vm.popup.imgPath = upImgUrl;
            vm.popup.channel = "";
            $("#channelSelect option:selected").each(function () {
                vm.popup.channel += $(this).val() + ",";
            });
            //console.log(vm.popup.channel);
            var len = $("#channelSelect option:selected").length;
            if (len == 0) {
                vm.popup.channel = "";
            }

            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.popup),
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
                    url: baseURL + "sys/popup/delete",
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
            $.get(baseURL + "sys/popup/info/" + id, function (r) {
                vm.popup = r.popup;
                upImgUrl = r.popup.imgPath;
                $('.file').css({'background-image': 'url(' + upImgUrl + ')', 'background-size': '100% 100%'});
                vm.chosen_multi_select_platformCode("#channelSelect", vm.popup.channel.split(","));
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        chosen_multi_select_platformCode: function (select, arr) {
            var length = arr.length;
            var value = '';
            for (i = 0; i < length; i++) {
                value = arr[i];
                if (value != "") {
                    $(select + " option[value='" + value + "']").attr('selected', 'selected');
                }

            }
            $(select).val(arr);
            $(select).trigger("chosen:updated");
        }
    }
});