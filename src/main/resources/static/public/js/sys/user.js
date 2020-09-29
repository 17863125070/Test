//定义layer,版本冲突
var layer = layui.layer;
$(function () {
    $("#table").bootstrapTable({
        url: "../sysuser/userlist",
        pagination: true,
        sidePagination: "server",
        showRefresh: true,  //显示刷新按钮
        search: true,
        toolbar: '#toolbar',
        clickToSelect: true,   //是否启用点击选中行
        placeholderName: "姓名、账号",
        striped: true,
        columns: [
            {field: 'ck', checkbox: true},
            {field: 'userId', title: '编号', visible: false},
            {field: 'userName', title: '用户账号'},
            {field: 'cnName', title: '所属人员'},
            {field: 'roleName', title: '角色名称'},
            {
                field: 'mobile', title: '手机号', formatter: function (v, r, i) {
                    if (v == null) {
                        return "--";
                    } else {
                        return v;
                    }
                }
            },
            {
                field: 'status', title: '状态', formatter: function (v, r, i) {
                    if (v == 0) {
                        return "冻结";
                    } else {
                        return "正常";
                    }
                }
            },
            {field: 'createTime', title: '创建时间'}
        ]
    });
});

var vm = new Vue({
    el: '#dtapp',
    data: {
        showList: true,
        isupdate: false,
        title: '',
        user: {},
        listRoles: [],
        objUser:{}
    },
    methods: {
        del: function () {
            //getSelectedRows();：common.js中定义   功能获得用户选择的记录  返回的是数组
            var rows = getSelectedRows();
            if (rows == null) {
                return;
            }
            var id = 'userId';
            //提示确认框
            layer.confirm('您确定要删除所选数据吗？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function (index, layero) {
                var ids = new Array();
                //遍历所有选择的行数据，取每条数据对应的ID
                $.each(rows, function (i, row) {
                    ids[i] = row[id];//得到选择的这一行的id
                });
                $.ajax({
                    type: "POST",
                    url: "../sysuser/deluserrole",
                    contentType: 'application/json',
                    data: JSON.stringify(ids),//把json数组转json字符串
                    success: function (r) {
                        if (r.code === 0) {//成功

                            layer.alert('删除成功');
                            //刷新
                            $('#table').bootstrapTable('refresh');
                        } else {
                            layer.alert(r.msg);
                        }
                    },
                    error: function () {
                        layer.alert('服务器没有返回数据，可能服务器忙，请重试');
                    }
                });
            });
        },
        reset: function () {
            //getSelectedRows();：common.js中定义   功能获得用户选择的记录  返回的是数组
            var rows = getSelectedRows();
            if (rows == null) {
                return;
            }
            var id = 'userId';
            //提示确认框
            layer.confirm('您确定要重置所选用户密码吗？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function (index, layero) {
                $.ajax({
                    type: "POST",
                    url: "../sysuser/resetpassword",
                    contentType: 'application/json',
                    data: JSON.stringify(rows),//把json数组转json字符串
                    success: function (r) {
                        if (r.code === 0) {//成功

                            layer.alert('重置密码成功');
                            //刷新
                            $('#table').bootstrapTable('refresh');
                        } else {
                            layer.alert(r.msg);
                        }
                    },
                    error: function () {
                        layer.alert('服务器没有返回数据，可能服务器忙，请重试');
                    }
                });
            });
        },
        add: function () {
            vm.isupdate = false;
            vm.selectListRoles();
            //将user属性清空
            vm.user = {};
            vm.showList = false;
            vm.title = "新增";
        },
        update: function (event) {
            vm.isupdate = true;
            var id = 'userId';
            var userId = getSelectedRow()[id];
            if (userId == null) {
                return;
            }
            vm.selectListRoles();

            vm.$nextTick(function () {
                $.get("../sysuser/finduserbyid/" + userId, function (r) {
                    //console.log(r.user);
                    vm.showList = false;
                    vm.title = "修改";
                    vm.user = r.user;
                });
            });
        },
        saveOrUpdate: function (event) {
            //有菜单编号时是修改，没有：新增
            var url = vm.user.userId == null ? "../sysuser/saveuser" : "../sysuser/updateuser";
            var msg = vm.user.userId == null ? "添加用户成功" : "修改用户成功";
            if (vm.user.userName == '' || vm.user.userName == undefined || vm.user.userName == null) {
                layer.alert("用户账号不能为空，请重新输入");
                return;
            }
            if (!/^[A-Za-z0-9]+/.test(vm.user.userName)) {
                layer.alert('账户只能输入英文和数字的组合,请重新输入');
                return;
            }
            if (vm.user.cnName == '' || vm.user.cnName == undefined || vm.user.cnName == null) {
                layer.alert("所属用户中文名不能为空，请重新输入");
                return;
            }
            if (vm.user.userId == null) {
                if (vm.user.passWord == '' || vm.user.passWord == undefined || vm.user.passWord == null) {
                    layer.alert("密码不能为空，请重新输入");
                    return;
                }
            }
            if (!/^[A-Za-z0-9]+/.test(vm.user.passWord)) {
                layer.alert('密码只能输入英文和数字的组合,请重新输入');
                return;
            }
            if (vm.user.roleId == '' || vm.user.roleId == undefined || vm.user.roleId == null) {
                layer.alert("用户角色不能为空，请选择");
                return;
            }
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.user),//json字符串
                contentType: 'application/json',
                success: function (r) {
                    if (r.code === 0) {
                        layer.alert(msg, function (index) {
                            layer.close(index);
                            vm.reload();
                        });
                    } else {
                        layer.alert(r.msg);
                    }
                }
            });
        },
        //查询所有角色
        selectListRoles: function () {
            $.ajax({
                type: "GET",
                url: "../sysuser/listroles",
                success: function (r) {
                    if (r.code === 0) {
                        vm.listRoles = r.listRoles;
                        // console.log(JSON.stringify(vm.listRoles));
                    } else {
                        layer.alert(r.msg);
                    }
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.user = {},
                $("#table").bootstrapTable('refresh');
        },
    }
});


