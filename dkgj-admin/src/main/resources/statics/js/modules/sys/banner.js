var upImgUrl;
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/banner/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 50, key: true},
            {label: '名称', name: 'title', index: 'title', width: 80},
            {
                label: '图片',
                name: 'imgurl',
                index: 'imgUrl',
                width: 80,
                formatter: function (cellValue, options, rowObject) {
                    return "<img src='" + rowObject["imgurl"] + "' width='100' height='50'>";
                }
            },
            {
                label: '链接',
                name: 'gourl',
                index: 'goUrl',
                width: 80,
                formatter: function (cellValue, options, rowObject) {
                    return "<div style='width: 40px;text-align:center;border-radius:5px;background: #8391a5;color: #ffffff;' class='mark_data' data='" + cellValue + "'>链接</div>";
                }
            },
            /*{ label: '', name: 'intro', index: 'intro', width: 80 },*/
            {label: '排序值', name: 'seq', index: 'seq', width: 80},
            /*{ label: '', name: 'byuid', index: 'byUid', width: 80 }, */
            {
                label: '位置', name: 'pos', index: 'pos', width: 80,
                formatter: function (cellValue, options, rowObject) {
                    switch (cellValue) {
                        case 1:
                            return "首页头部";
                            break;
                        default:
                            return "";
                    }
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
            {label: '创建时间', name: 'createdat', index: 'createdAt', width: 80}/*,
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

    var swfPath = baseURL + "statics/plugins/zclip/ZeroClipboard.swf";
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
        options:null,
        banner: {}
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
            vm.banner = {};
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
            var url = vm.banner.id == null ? "sys/banner/save" : "sys/banner/update";
            vm.banner.imgurl = upImgUrl;
            console.log(upImgUrl);
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.banner),
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
                    url: baseURL + "sys/banner/delete",
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
            $.get(baseURL + "sys/banner/info/" + id, function (r) {
                vm.banner = r.banner;
                upImgUrl = r.banner.imgurl;
                $('.file').css({'background-image': 'url(' + upImgUrl + ')', 'background-size': '100% 100%'});
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: {
                    'id': vm.q.id,
                    'title': vm.q.title,
                    'state': vm.q.state,
                    'createdat': $('#qcreatedAt').val()
                }
            }).trigger("reloadGrid");
        }
    }
});