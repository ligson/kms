/**
 * Created by Administrator on 2015/4/8 0008.
 */
$(function () {
    var userGrid = $("#tt");
    userGrid.datagrid({
        columns: [[
            {field: 'ck', title: 'ckID', width: 40, checkbox: 'true'},
            {field: 'id', title: 'ID', width: 40},
            {field: 'nickName', title: '昵称', width: 100},
            {field: 'balance', title: '余额', width: 100},
            {field: 'blockedFund', title: '冻结资金', width: 100}
        ]]
    });
});
