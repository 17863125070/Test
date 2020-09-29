//定义layer,版本冲突
var layer = layui.layer;
$(function () {

    var option = {
        url: '../role/selectAllRole',
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页
        showRefresh: true,  //显示刷新按钮
        search: true,
        toolbar: '#toolbar',
        striped: true,     //设置为true会有隔行变色效果
        clickToSelect: true,   //是否启用点击选中行
        searchOnEnterKey: true,   //在搜索框内输入内容并且按下回车键才开始搜索
        responseHandler: function (res) {
            return {
                rows: res.list,
                total: res.total
            }
        },
        queryParams: function (params) {
            return {
                offset: params.offset,
                limit: params.limit,
                //search:params.search //返回模糊查询信息
            }
        },
        columns: [
            /*  {
                             field: 'roleId',
                             title: '序号',
                             width: 40,
                             formatter: function(value, row, index) {
                                 var pageSize = $('#table').bootstrapTable('getOptions').pageSize;
                                 var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;
                                 return pageSize * (pageNumber - 1) + index + 1;
                             }
                         },*/
            {checkbox: true},
            {title: 'roleId', field: 'roleId', visible: false},//隐藏
            {title: '角色名称', field: 'roleName'},
            {title: '创建时间', field: 'createTime'},
            {title: '创建用户ID', field: 'createUserId'},
            {title: '备注', field: 'remark'},
        ]
    };
    $('#table').bootstrapTable(option);

});

var ztree;
var vm = new Vue({
    el: '#dtapp',
    data: {
        showList: true,
        title: null,
        role: {}
    },
    methods: {
        roleAllot: function () {//权限分配

            var rows = getSelectedRow();
            var data = null;
            if (rows == null) {
                return;
            }
            //获得选中行的ID
            var roleId = rows.roleId;
            var url = "../sysmenu/selectByRoleIdAllMenu?roleId=" + roleId;
            var menuIdList = [];
            //根据roleId获取所属权限
            $.ajax({
                type: "GET",
                url: url,
                dataType: "json",
                async:false,
                success: function (model) {
                    menuIdList = model;
                    //查询所有菜单
                    var url = "../sysmenu/selectAllMenu";
                    $.ajax({
                        type: "POST",
                        url: url,
                        dataType: "json",
                        success: function (model) {
                            data = model;

                            layui.use('tree', function () {
                                var tree = layui.tree;

                                //渲染
                                var inst1 = tree.render({
                                    elem: '#test1', //绑定元素
                                    showCheckbox: true
                                    , id: 'demoId' //定义索引
                                    , data: data,
                                });

                                //执行节点勾选
                                tree.setChecked('demoId', menuIdList); //批量勾选
                            });

                        }
                    });
                }
            });

            //打开Model窗口
            $('#myModalSearch').modal('show');


        },
        del: function () {
            var rows = getSelectedRows();

            if (rows == null) {
                return;
            }
            //获得选中行的ID
            var roleId = rows.roleId;
            //提示确认框
            layer.confirm('您确定要删除所选数据吗？', {
                btn: ['确定', '返回'] //按钮
            }, function () {
                var roleIdArr = new Array();
                //遍历所有选择的行数据，取每条数据对应的ID
                $.each(rows, function (i, row) {
                    roleIdArr[i] = row.roleId;


                });
                var url = "../role/deleteRole";
                $.ajax({
                    type: "POST",
                    url: url,
                    dataType: "json",
                    contentType: 'application/json',
                    data: JSON.stringify(roleIdArr),
                    success: function (model) {
                        if (model.code == "0") {
                            layer.msg('操作成功！', {icon: 6});

                            setTimeout('location.reload()', 1000);//setTimeout是js附带的延缓

                        }else if(model.code =="2"){ //删除失败有正在被使用的权限
                            layer.msg(model.msg, {icon: 5});
                        }
                        else {

                            layer.msg('操作失败！', {icon: 5});

                        }

                    },
                    error: function () {
                        layer.alert('服务器没有返回数据，可能服务器忙，请重试');
                    }
                });
            });
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            //添加时清空所有属性
            vm.role = {};
            vm.getMenu();
        },
        update: function (event) {
            var rows = getSelectedRow();
            if (rows == null) {
                return;
            }
            //获得选中行的ID
            vm.role.roleId = rows.roleId;
            //权限名称
            vm.role.roleName = rows.roleName;
            //备注
            vm.role.remark = rows.remark;

            vm.showList = false;
            vm.title = "修改";
            vm.getMenu();
        },

        /*保存或修改权限信息*/
        saveOrUpdate: function (event) {

            /*获取数据库ip*/
            const roleName = $("#roleName").val();
            vm.role.roleName = roleName;
            if (!roleName) {
                layer.alert("权限名称不能为空");
                return;
            }
            const remark = $("#remark").val();
            vm.role.remark = remark;
            if (!remark) {
                layer.alert("备注不能为空");
                return;
            }
            var url = vm.role.roleId == null ? "../role/saveRole" : "../role/updateRole";

            //loading层
            var index = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });

            $.ajax({
                type: "POST",
                url: url,
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify(vm.role),
                success: function (model) {

                    if (model.code == "0") {
                        layer.close(index);
                        layer.msg('操作成功！', {icon: 6});
                        $("#saveSql").attr("disabled", true);
                        setTimeout(function () {
                            location.reload();
                        }, 1000);
                    } else {
                        layer.close(index);
                        layer.msg('操作失败！', {icon: 5});
                    }
                }
            });
        },

        reload: function (event) {
            vm.showList = true;
            $("#table").bootstrapTable('refresh');
        },

        /*menuTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.role.parentId = node[0].menuId;
                    vm.role.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },*/
        getMenu: function (menuId) {

            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "menuId",
                        pIdKey: "parentId",
                        rootPId: -1
                    },
                    key: {
                        url: "nourl"
                    }
                }
            };
        }
    }
});


function sureObject() {//确定按钮

    //点击确定获取被选中的节点
    layui.use('tree', function () {
        var tree = layui.tree;

        //获得选中的节点
        var checkData = tree.getChecked('demoId');
        //存储第二层集合
        var roleChildren = {};
        //存储menuId
        var menuId = [];

        $.each(checkData, function (key, val) {
            menuId.push(val.id);
            roleChildren = val.children;
            if(roleChildren.length>0) {
                $.each(roleChildren, function (key, valChidren) {
                    menuId.push(valChidren.id);
                })
            }
        });

        var rows = getSelectedRow();
        if (rows == null) {
            return;
        }
        //获得选中行的ID
        var roleId = rows.roleId;
        //将roleId加入进menuId数组中，因此数组最后一位是role_Id;

        menuId.push(roleId);
        //返回数据插入到role_menu数据库
        var url = "../sysmenu/chieckAllMenuId";
        //根据roleId获取所属权限
        $.ajax({
            type: "POST",
            url: url,
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify(menuId),
            success: function (model) {
                console.log(model);
                if (model == "1") {
                    layer.msg('操作成功！', {icon: 6});
                    $("#saveSql").attr("disabled", true);
                    setTimeout(function () {
                    }, 1000);
                } else {
                    layer.msg('操作失败！', {icon: 5});
                }

            }
        });
    });


    $('#myModalSearch').modal('hide');
}


function Cancel() {//返回按钮

    $('#myModalSearch').modal('hide');
}
