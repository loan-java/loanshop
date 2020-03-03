$(function () {
	$("#jqGrid").jqGrid({
		url: baseURL + 'sys/logasoxm/list',
		datatype: "json",
		colModel: [
			{label: 'id', name: 'id', index: 'id', width: 50, key: true},
			{label: 'IDFA', name: 'idfa', index: 'idfa', width: 80},
			{label: 'AppID', name: 'appid', index: 'appId', width: 80},
			{label: 'IP', name: 'ip', index: 'ip', width: 80},
			{label: 'DEVICE', name: 'device', index: 'device', width: 80},
			{label: 'OS', name: 'os', index: 'os', width: 80},
			{label: 'KEYWORD', name: 'keyword', index: 'keyword', width: 80},
			{label: 'CHANNEL', name: 'channel', index: 'channel', width: 80},
			{label: '创建时间', name: 'createDate', index: 'create_date', width: 80},
			{
				label: '事件', name: 'status', index: 'status', width: 80,
				formatter: function (cellValue, options, rowObject) {
					switch (cellValue) {
						case 0:
							return "<span class=\"label label-danger\">激活</span>";
							break;
						case 1:
							return "点击";
							break;
						default:
							return "无意义";
					}
				}
			}
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
		logAsoXm: {}
	},
	methods: {
		download: function () {
			window.location.href = baseURL + "sys/logasoxm/export?state=" + vm.q.state + "&createDate=" + $('#qcreatedate').val();
		},
		query: function () {
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.logAsoXm = {};
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
			var url = vm.logAsoXm.id == null ? "sys/logasoxm/save" : "sys/logasoxm/update";
			$.ajax({
				type: "POST",
				url: baseURL + url,
				contentType: "application/json",
				data: JSON.stringify(vm.logAsoXm),
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
					url: baseURL + "sys/logasoxm/delete",
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
			$.get(baseURL + "sys/logasoxm/info/" + id, function (r) {
				vm.logAsoXm = r.logAsoXm;
			});
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
				page: page,
				postData: {
					'createDate': $('#qcreatedate').val(),
					'state': vm.q.state
				}
			}).trigger("reloadGrid");
		}
	}
});