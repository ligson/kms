/**
 * Created by Administrator on 2015/4/8 0008.
 */
$(function () {
    var userGrid = $("#tt");
    userGrid.datagrid({
        columns: [[
            {field: 'ck', title: 'ckID', width: 40, checkbox: 'true'},
            {field: 'id', title: 'ID', width: 40},
            {
                field: 'nickName', title: '昵称', width: 100, formatter: function (value, rowData, rowIndex) {
                return rowData.user.nickName;
            }
            },
            {
                field: 'userId', title: '用户ID', width: 100, formatter: function (value, rowData, rowIndex) {
                return rowData.user.id;
            }
            },
            {field: 'money', title: '金额', width: 100},
            {field: 'createDate', title: '充值日期', width: 100}
        ]]
    });
});
