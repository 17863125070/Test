/*
$(function(){
    var option = {
        url: '../file/filelist',
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页
        pageSize:15,
        showRefresh: true,  //显示刷新按钮
        search: true,
        toolbar: '#toolbar',
        buttonsToolbar:'toolbar',
        striped : true,    //设置为true会有隔行变色效果
        columns: [
            {field: 'ck', checkbox: true},
            {title:'文件ID',field:'fileId'},
            { title: '文件名称', field: 'fileName'},
            { title: '文件路径', field: 'filePath'},
            { title:'文件类型', field:'fileType'},
            { title: '文件版本', field: 'fileVersion'},
            { title: '所属人员', field: 'userName'}
        ]};
    $('#table').bootstrapTable(option);
});
var ztree;

var vm = new Vue({
    el:'#dtapp',
    data:{
        showList: true,
        title: null,
        file:{},
        sysUser:{},
    },
    methods:{
        del: function(){
            var rows = getSelectedRows();
            if(rows == null){
                layer.alert("没有选择数据！");
                return ;
            }
            var id = 'fileId';
            //提示确认框
            layer.confirm('您确定要删除所选数据吗？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function(index, layero){
                var ids = new Array();
                //遍历所有选择的行数据，取每条数据对应的ID
                $.each(rows, function(i, row) {
                    ids[i] = row[id];
                });

                $.ajax({
                    type: "POST",
                    url: "../file/delfile",
                    contentType: 'application/json',
                    data: JSON.stringify(ids),
                    success : function(r) {
                        if(r.code === 0){
                            layer.alert('删除成功');
                            $('#table').bootstrapTable('refresh');
                        }else{
                            layer.alert(r.msg);
                        }
                    },
                    error : function() {
                        layer.alert('服务器没有返回数据，可能服务器忙，请重试');
                    }
                });
            });
        },
        add: function(){
            vm.showList = false;
            vm.selectUser();
            vm.title = "新增";
            vm.file = {};
        },
        save: function (event) {
            var url =  "../file/save" ;
            var msg =  "上传文件成功" ;
            if (vm.file.fileId== '' || vm.file.fileId == undefined || vm.file.fileId == null) {
                layer.alert("文件ID不能为空，请重新输入");
                return;
            }
            if (vm.file.fileName== '' || vm.file.fileName == undefined || vm.file.fileName == null) {
                layer.alert("文件名称不能为空，请重新输入");
                return;
            }
            if (vm.file.filePath== '' || vm.file.filePath == undefined || vm.file.filePath == null) {
                layer.alert("文件路径不能为空，请重新输入");
                return;
            }
            if (vm.file.fileType== '' || vm.file.fileType == undefined || vm.file.fileType == null) {
                layer.alert("文件类型不能为空，请重新输入");
                return;
            }
            if (vm.file.userName== '' || vm.file.userName == undefined || vm.file.userName == null) {
                layer.alert("用户名不能为空，请重新输入");
                return;
            }

            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.file),
                contentType: 'application/json',
                success: function(r){
                    if(r.code === 0){
                        layer.alert('操作成功', function(index){
                            layer.close(index);
                            vm.reload();
                        });
                    }else{
                        layer.alert(r.msg);
                    }
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            $("#table").bootstrapTable('refresh');
        },
        selectUser: function () {
            $.ajax({
                type: "GET",
                url: "../sysuser/user/info",
                success: function (r) {
                    if (r.code === 0) {
                        vm.sysUser = r.user;
                        vm.file.userId = vm.sysUser.userId;
                    } else {
                        layer.alert(r.msg);
                    }
                }
            });
        },
        chooseType:function (item) {
            vm.$nextTick(function() {
                var option = {
                    url: '../sysmenu/menulisttwo?name='+item,
                    pagination: true,	//显示分页条
                    sidePagination: 'server',//服务器端分页
                    pageSize:15,
                    showRefresh: true,  //显示刷新按钮
                    search: true,//查询按钮
                    toolbar: '#toolbar',
                    striped : true,     //设置为true会有隔行变色效果
                    columns: [
                        {
                            field: 'menuId',
                            title: '序号',
                            width: 40,
                            formatter: function(value, row, index) {
                                var pageSize = $('#table').bootstrapTable('getOptions').pageSize;
                                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;
                                return pageSize * (pageNumber - 1) + index + 1;
                            }
                        },
                        {checkbox:true},
                        { title: '菜单ID', field: 'menuId'},
                        {field:'name', title:'菜单名称', formatter: function(value,row){
                                if(row.type === 0){
                                    return value;
                                }
                                if(row.type === 1){
                                    return '<span style="margin-left: 40px;">├─ ' + value + '</span>';
                                }
                                if(row.type === 2){
                                    return '<span style="margin-left: 80px;">├─ ' + value + '</span>';
                                }
                            }},
                        { title: '上级菜单', field: 'parentName'},
                        { title: '菜单图标', field: 'icon', formatter: function(value){
                                return value == null ? '' : '<i class="'+value+' fa-lg"></i>';
                            }},
                        { title: '菜单URL', field: 'url'},
                        { title: '授权标识', field: 'perms'},

                        { title: '排序号', field: 'orderNum'}
                    ]};
                $('#table').bootstrapTable("destroy");
                $('#table').bootstrapTable(option);
            })
        },

    },
    created: function () {
        this.selectUser();
    }
});*/
