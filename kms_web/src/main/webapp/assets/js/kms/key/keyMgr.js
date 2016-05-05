$(function () {
    var userGrid = $("#tt");
    userGrid.datagrid({
        fit: true,
        columns: [[
            {field: 'ck', title: 'ckID', width: 40, checkbox: 'true'},
            {field: 'id', title: 'ID', width: 40},
            {
                field: 'keyType', title: '类型', width: 50
            },
            {
                field: 'keySize', title: '长度', width: 50
            },
            /*{field: 'description', title: '问题描述',width:200},*/
            {field: 'keyStatus', title: '状态', width: 50, sortable: true},
            {field: 'createTime', title: '创建时间', width: 100, sortable: true},
            {field: 'useTime', title: '使用时间', width: 100, sortable: true},
            {
                field: "expiredTime", title: "过期时间", sortable: true
            },
            {
                field: 'revokeTime', title: '吊销时间', width: 50, sortable: true, formatter: function (value) {
                //return Date.convertTxtFormat(value);
                return value;
            }
            }
        ]],
        nowrap: false,
        toolbar: [{
            id: 'btnadd',
            text: '生成密钥对',
            iconCls: 'icon-ok',
            handler: function () {
                showGenKeyDlg();
            }
        }, {
            id: 'btndelete',
            text: '批量删除',
            iconCls: 'icon-cut',
            handler: function () {
                deleteArticle();
            }
        }]
    });
});

function showGenKeyDlg() {
    $("#showGenKeyDlg").dialog("open");
}
function genKey() {
    var form = $("#genKeyForm");
    if (form.form("validate")) {
        form.form('submit', {
            success: function (data) {
                var data = eval('(' + data + ')');  // change the JSON string to javascript object
                if (data.success) {
                    form.form("clear");
                    $("#showGenKeyDlg").dialog("close");
                } else {
                    alert(data.errorMsg);
                }
            }
        });
    }
//required:true
}