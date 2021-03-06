$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/mlogdevice/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', index: 'id', width: 50, key: true },
			{ label: '应用ID', name: 'appid', index: 'appId', width: 80 },
			{ label: 'UUID', name: 'uuid', index: 'uuid', width: 120 },
			{ label: '浏览信息', name: 'ua', index: 'ua', width: 80 },
			{ label: '创建时间', name: 'createdat', index: 'createdAt', width: 80 }
        ],
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
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

	//初始化
	zaneDate({
		elem: '#qcreatedAt',
		type:'doubleday'
	})
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		options: [],
		q:{},
		showList: true,
		title: null,
		mLogDevice: {}
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
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mLogDevice = {};
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
			var url = vm.mLogDevice.id == null ? "sys/mlogdevice/save" : "sys/mlogdevice/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mLogDevice),
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
				    url: baseURL + "sys/mlogdevice/delete",
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
			$.get(baseURL + "sys/mlogdevice/info/"+id, function(r){
                vm.mLogDevice = r.mLogDevice;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{'id': vm.q.id,
					'appid':vm.q.appid,
					'uuid':vm.q.uuid,
					'createdat':$('#qcreatedAt').val()
				},
                page:page
            }).trigger("reloadGrid");
		}
	}
});