/**
 * Created by ligson on 2015/7/10.
 */
$(function () {

    var userGrid = $("#tt");
    userGrid.datagrid({
        fit:true,
        columns: [[
            {field: 'ck', title: 'ckID', width: 40, checkbox: 'true'},
            {field: 'id', title: 'ID', width: 40},
            {
                field: 'userId', title: '用户ID', width: 40, formatter: function (value, rowData, rowIndex) {
                return rowData.user.id;
            }
            },
            {
                field: 'user', title: '用户名', width: 50, formatter: function (value) {
                return value.nickName;
            }
            },
            {field: 'createDate', title: '交易日期', width: 100},
            {
                field: 'state', title: '状态', width: 50, formatter: function (value, rowData, rowIndex) {
                if (value == 1) {
                    return "正在充值";
                } else if (value == 2) {
                    return "充值成功";
                } else if (value == 3) {
                    return "充值失败";
                } else {
                    return "";
                }
            }
            },
            {
                field: 'type', title: '支付类型', width: 50, formatter: function (value, rowData, rowIndex) {
                return value == 1 ? "支付宝" : "xx";
            }
            },
            {field: 'outOrder', title: '外部订单号', width: 100},
            {field: 'guid', title: '系统订单号', width: 100},
            {field: 'money', title: '交易金额', width: 100},
            {field: 'comments', title: '注释', width: 100}

        ]],
        toolbar: [{
            id: 'btnadd',
            text: '通过',
            iconCls: 'icon-add',
            handler: function () {
                allowWithdraw();
            }
        }, {
            id: 'btnupdate',
            text: '拒绝',
            iconCls: 'icon-save',
            handler: function () {
                editeLanguage();
            }

        }]
    });
});

function allowWithdraw() {
    var userGrid = $("#tt");
    var rows = userGrid.datagrid('getSelections');
    if (rows) {
        var ids = "";
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            ids += row.id + ",";
        }
        $.post("/payMgr/withdraw", {ids: ids}, function (data) {
            var msg = "";
            for (var i = 0; i < data.length; i++) {
                if (data[i].success) {
                    msg += "记录ID:" + data[i].id + "成功!";
                } else {
                    msg += "记录ID:" + data[i].id + "失败!" + data[i].msg;
                }
            }
            alert(msg);
        }, "json");
    } else {
        alert("请至少选择一条记录!");
    }
}


