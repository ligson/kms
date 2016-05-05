/**
 * Created by Administrator on 2015/4/8 0008.
 */
$(function () {
    var userGrid = $("#tt");
    userGrid.datagrid({
        columns: [[
            {field: 'ck', title: 'ckID', width: 40, checkbox: 'true'},
            {field: 'id', title: 'ID', width: 30},
            {field: 'nickName', title: '昵称', width: 100},
            {
                field: 'sex', title: '性别', width: 30, align: 'center',
                formatter: function (value) {
                    if (value == 1) {
                        return '<a class="icon icon-nan g_icosize" title="男" >男</a>'
                    } else {
                        return '<a class="icon icon-nv g_icosize" title="女" >女</a>'
                    }
                }
            },
            {field: 'cellphone', title: '手机号', width: 80},
            {field: 'email', title: 'Email', width: 100},
            {field: 'registerDate', title: '注册日期', width: 80},
            {field: 'lastLoginDate', title: '上次登录', width: 80},
            {
                field: 'state', title: '状态', width: 40,
                formatter: function (value) {
                    if (value == 0) {
                        return '禁用';
                    } else if (value == 1) {
                        return '正常';
                    } else if (value == 2) {
                        return '待审核'
                    }
                }
            },
            {
                field: 'action', title: '操作', width: 120,
                formatter: function (val, row) {
                    var e = '<a href="javascript:void(0);" style="margin-right: 15px;" onclick="deleterow(' + row.id + ')">删除</a>';
                    e += '<a href="javascript:void(0);" style="margin-right: 15px;" onclick="resetPassword(' + row.id + ')">重置密码</a>';
                    return e;
                }
            }
        ]]
    });

    var resetPwdDlg = $("#resetPwdDlg");
    resetPwdDlg.dialog({
        buttons: [{
            text: '修改',
            iconCls: 'icon-ok',
            handler: function () {
                var password1 = resetPwdDlg.find("input[name='password1']").val();
                var password2 = resetPwdDlg.find("input[name='password2']").val();
                if (password1 == password2 && (password1 + "").trim() != "") {
                    var userId = resetPwdDlg.find("input[name='userId']").val();
                    $.post("/userMgr/resetPassword", {new_password: password1, userId: userId}, function (data) {
                        resetPwdDlg.dialog("close");
                        if (data.success) {
                            $.messager.alert("提示", "修改成功!");
                        } else {
                            $.messager.alert("提示", "修改失败!");
                        }
                    });
                } else {
                    $.messager.alert("提示", "两次密码不一致!");
                }
            }
        }, {
            text: '取消',
            handler: function () {
                resetPwdDlg.dialog("close");
            }
        }]
    });
});

//    formatter:'formatAction'
function getRowIndex(target) {
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}

function deleterow(target) {
    $.messager.confirm('Confirm', 'Are you sure?', function (r) {
        if (r) {
            $('#tt').datagrid('deleteRow', getRowIndex(target));
        }
    });
}
function resetPassword(target) {
    var resetPwdDlg = $("#resetPwdDlg");
    resetPwdDlg.dialog("open");
    resetPwdDlg.find("input[name='userId']").val(target);
}
function plsh() {
    var selected = $('#tt').datagrid("getSelections");
    if (selected.length == 0) {
        $.messager.alert('提示', '请选择要批量审核的数据!', 'info');
        return;
    }
    var idString = "";
    $.each(selected, function (index, item) {
        if (index < selected.length - 1) {
            idString += item.id + ",";
        } else {
            idString += item.id;
        }
    });
    $('#id').val(idString);
    if (!confirm("确认要批量审核选中的问题信息？"))
        return;
    $("#dlg").dialog("open").dialog('setTitle', '批量审核用户信息');

}
function update() {
    var url = 'editeUser';
    var state = $('#state').val();
    var idString = $('#id').val();
    $.ajax({
        //设定地址与传递参数到后台
        url: url + "?id=" + idString + "&state=" + state,
        type: 'post',
        dateType: 'json',
        //判断结果是否正确
        success: function (data) {
            if (data.success) {
                $.messager.alert('提示', '批量审核成功!', 'success');
                $('#dlg').dialog('close');
                $('#tt').datagrid('reload');
            }
        }, error: function () {

        }
    })
}
