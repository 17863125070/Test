$(function(){
    var option = {
        url: '../sysmenu/menulist',
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页
        pageSize:15,
        showRefresh: true,  //显示刷新按钮
        search: true,
        toolbar: '#toolbar',
        buttonsToolbar:'toolbar',
        /*toolbarAlign:'left',*/
        striped : true,    //设置为true会有隔行变色效果
        //idField: 'menuId',
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
            { title: '类型', field: 'type', formatter: function(value){
                    if(value === 0){
                        return '<span class="label label-primary">目录</span>';
                    }
                    if(value === 1){
                        return '<span class="label label-success">菜单</span>';
                    }
                    if(value === 2){
                        return '<span class="label label-warning">按钮</span>';
                    }
                }},
            { title: '排序号', field: 'orderNum'}
        ]};
    $('#table').bootstrapTable(option);
    /*筛选按钮触发的事件，重新加载表格*/

});
var ztree;

var vm = new Vue({
	el:'#dtapp',
    data:{
        a:'a',
        showList: true,
        title: null,
        menu:{},
        /*菜单根目录*/
        typeList:[]
    },
    methods:{
        del: function(){
            var rows = getSelectedRows();
            if(rows == null){
                layer.alert("没有选择数据！");
                return ;
            }
            var id = 'menuId';
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
                    url: "../sysmenu/delrolemenu",
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
            vm.title = "新增";
            vm.menu = {parentName:null,parentId:0,type:1,orderNum:0};
            vm.getMenu();
        },
        update: function (event) {
            vm.showList = false;
            var id = 'menuId';
            var menuId = getSelectedRow()[id];
            if(menuId == null){
                return ;
            }

            $.get("../sysmenu/menu/info/"+menuId, function(r){
                console.log(r.menu);
                vm.showList = false;
                vm.title = "修改";
                vm.menu = r.menu;
                vm.getMenu();
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.menu.menuId == null ? "../sysmenu/menu/save" : "../sysmenu/menu/update";
            var msg = vm.menu.menuId == null ? "添加菜单成功" : "修改菜单成功";
            if (vm.menu.name== '' || vm.menu.name == undefined || vm.menu.name == null) {
                layer.alert("菜单名称不能为空，请重新输入");
                return;
            }
            if (vm.menu.parentName== '' || vm.menu.parentName == undefined || vm.menu.parentName == null) {
                layer.alert("上级菜单名称不能为空，请重新输入");
                return;
            }
            if (vm.menu.orderNum== '' || vm.menu.orderNum == undefined || vm.menu.orderNum == null) {
                layer.alert("排序号不能为空，请重新输入");
                return;
            }

            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.menu),
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
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                //内容
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.menu.parentId = node[0].menuId;
                    vm.menu.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        getMenu: function(menuId){
            //树形结构设置
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "menuId",
                        pIdKey: "parentId",
                        rootPId: -1
                    },
                    key: {
                        url:"nourl"
                    }
                }
            };

            //加载菜单树
            $.get("../sysmenu/select", function(r){
                //设置ztree的数据
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);

                //编辑（update）时，打开tree，自动高亮选择的条目
                var node = ztree.getNodeByParam("menuId", vm.menu.parentId);
                //选中tree菜单中的对应节点
                ztree.selectNode(node);
                //编辑（update）时，根据当前的选中节点，为编辑表单的“上级菜单”回填值
                vm.menu.parentName = node.name;
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
        selectType: function () {
            $.ajax({
                type: "GET",
                url: "../sysmenu/getName",
                success: function (data) {
                    console.log(data)
                    $.each(data, function (index, value) {
                        vm.typeList.push(value.name);
                    })
                }
            });
        },

    },
    created: function () {
        this.selectType();
    }
});