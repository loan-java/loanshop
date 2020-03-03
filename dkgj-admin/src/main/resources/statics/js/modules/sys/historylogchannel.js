var originalData;
var dateTime = new Date();
dateTime = dateTime.setDate(dateTime.getDate() - 1);
dateTime = new Date(dateTime);
var createdAt;


$(function () {

	var colData = [
		{label: 'ID', name: 'id', index: 'id', width: 50, key: true},
		{label: '渠道名', name: 'channelName', index: 'channel', width: 80},
		{
			label: '点击数：0',
			name: 'clicknum',
			index: 'clicknum',
			width: 80,
			formatter: function (cellValue, options, rowObject) {
				return cellValue;
			}
		},
		{
			label: 'UV：0',
			name: 'uvNum',
			index: 'uv_num',
			width: 80,
			formatter: function (cellValue, options, rowObject) {
				return cellValue;
			}
		},
		{
			label: '注册数：0',
			name: 'regnum',
			index: 'regNum',
			width: 80,
			formatter: function (cellValue, options, rowObject) {
				return cellValue;
			}
		},
		{label: 'app下载数：0', name: 'clickdownnum', index: 'clickdownnum', width: 80},
		{label: 'app打开数：0', name: 'appOpenNum', index: 'app_open_num', width: 80},
		{label: '创建时间', name: 'createdat', index: 'createdat', width: 80}
	];

	if (isChannelShow) {
		colData = [
			{label: 'ID', name: 'id', index: 'id', width: 50, key: true},
			{label: '渠道名', name: 'channelName', index: 'channel', width: 80},
			{
				label: '注册数：0',
				name: 'regnum',
				index: 'regnum',
				width: 80,
				formatter: function (cellValue, options, rowObject) {
					if (isChannelShow) {
						return rowObject['regnumTmp'];
					} else {
						return cellValue + "/" + rowObject['regnumTmp'];
					}

				}
			},
			{label: '创建时间', name: 'createdat', index: 'createdat', width: 80}
		];
	}
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/historylogchannel/list',
        datatype: "json",
        colModel: colData,
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
		loadComplete: function (res) {
			// res是服务器返回的数据
			originalData = res;
			$("#jqGrid").jqGrid('setLabel', "clicknum", "点击数：" + originalData.page.clickNum);
			$("#jqGrid").jqGrid('setLabel', "regnum", "注册数：" + originalData.page.regNum);
			$("#jqGrid").jqGrid('setLabel', "uvNum", "UV：" + originalData.page.uvNum);
			$("#jqGrid").jqGrid('setLabel', "appOpenNum", "app打开数：" + originalData.page.appOpenNum);
			$("#jqGrid").jqGrid('setLabel', "clickdownnum", "app下载数：" + originalData.page.appDownNum);

			createdAt = dateFormat(new Date(), "yyyy/MM/dd") + "-" + dateFormat(dateTime, "yyyy/MM/dd");
			$('#qcreatedAt').val(createdAt);

			createdAt = originalData.page.params.createdAt;
			if (createdAt != undefined && createdAt != null) {
				$('#qcreatedAt').val(createdAt);
			}
		},
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });

	//初始化
	zaneDate({
		elem: '#qcreatedAt',
		type: 'doubleday'
	})
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q: {
			createdAt: dateFormat(dateTime, "yyyy/MM/dd") + "-" + dateFormat(new Date(), "yyyy/MM/dd")
		},
		showList: true,
		title: null,
		historyLogChannel: {}
	},
	updated: function () {
		this.q.createdAt = $('#qcreatedAt').val();
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.historyLogChannel = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.historyLogChannel.id == null ? "sys/historylogchannel/save" : "sys/historylogchannel/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.historyLogChannel),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/historylogchannel/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "sys/historylogchannel/info/"+id, function(r){
                vm.historyLogChannel = r.historyLogChannel;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			vm.q.createAt = $('#qcreatedAt').val();
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
				postData: {
					'id': vm.q.id,
					'channel': vm.q.channel,
					'createdAt': $('#qcreatedAt').val()
				}
            }).trigger("reloadGrid");
		}
	}
});