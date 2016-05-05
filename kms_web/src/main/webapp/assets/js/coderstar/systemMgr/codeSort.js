$("#codeInfoGrid").datagrid({
    url: "codeInfoGridList",
    idField: 'id', fit: true, fitColumns: true, singleSelect: false,
    rownumbers: true, nowrap: false, pagination: true, pageSize: 10,
    pageList: [10, 20, 30, 40, 50],
    columns: [[
        { field: 'ck', checkbox: true },
        { field: 'codeSortType', title: '代码类别', width: 30 ,formatter:function(value,row,index){
            var s = '<a href="#" onclick="detail('+row.id+')">'+row.codeSortType+'</a> ';
            return s;
        }},
        { field: 'codeSortName', title: '代码名称', width: 30 },
        { field: 'flag', title: '有效标识', width: 10 ,formatter:function(value){
            if(value=='1'){
                return "有效";
            }else{
                return "无效";
            }
        }},
        { field: 'remark', title: '备注', width: 25 }
    ]],
    toolbar: [{
        text: '查询',
        iconCls: 'icon-search'

    }, '-',
        {
            text: '新建',
            iconCls: 'icon-add',
            handler: addCodeSort
        }, '-',
        {
            text: '编辑',
            iconCls: 'icon-edit',
            handler: editCodeSort
        }, '-',
        {
            text: '删除',
            iconCls: 'icon-remove',
            handler: deleteCodeSort

        }]
});
//关闭弹出窗口
function addCodeSort() {
    var d=$("<div/>").dialog({
        href: "addCodeSortPage",
        title: "添加代码分类",
        height: 230,
        width: 400,
        modal: true,
        iconCls: "icon-add",
        buttons: [
            {
                text: '确定添加',
                iconCls: 'icon-add',
                handler: function () {
                    $("#hidtype").val("submit");
                    var url="addCodeSort";
                    $("#addCodeSortForm").form("submit", {
                        url: url,
                        onSubmit: function () {
                            return $(this).form('validate');
                        },
                        success: function (result) {
                            d.dialog('destroy');
                            $.messager.alert("提示信息", "操作成功");
                            $("#datagrid").datagrid("reload");
                        }
                    });
                }
            },
            {
                text: '取消添加',
                iconCls: 'icon-cancel',
                handler: function () {d.dialog('destroy');
                }
            }
        ],
        onClose: function () {
            $(this).dialog('destroy');
        }
    });
}
function editCodeSort() {
    //alert(1);
    var r = $("#datagrid").datagrid("getChecked");
    var row = $("#datagrid").datagrid("getSelected");
    if (r.length < 1) { $.messager.alert("错误", "请选择一个要修改的用户"); return; }
    if (r.length > 1) {
        $.messager.alert("错误", "一次只能修改一个用户");
        $("#datagrid").datagrid('clearSelections').datagrid('clearChecked');
        return;
    }

    var updDialog=$("<div/>").dialog({
        href: "editCodeSortPage",
        title: "修改用户",
        height: 230,
        width: 400,
        modal: true,
        iconCls: "icon-edit",
        buttons: [
            {
                text: '确定修改',
                iconCls: 'icon-edit',
                handler: function () {
                    $("#hidtype").val("submit");
                    $("#editeCodeSortForm").form("submit", {
                        url: "editCodeSort?id="+row.id,
                        onSubmit: function () {
                            return $(this).form('validate');
                        },
                        success: function (result) {
                            updDialog.dialog('destroy');
                            $.messager.alert("提示", "操作成功");
                            $("#datagrid").datagrid("reload").datagrid('clearSelections').datagrid('clearChecked');
                        }
                    });
                }
            },
            {
                text: '取消修改',
                iconCls: 'icon-cancel',
                handler: function () { updDialog.dialog('destroy'); }
            }
        ],
        onClose: function () {
            updDialog.dialog('destroy');
        },
        onLoad: function () {
            console.log(row);
            console.log(r);
            $("#editeCodeSortForm").form("load",row );

        }
    });

}

function deleteCodeSort() {
    var rows = $("#datagrid").datagrid("getChecked");
    if (rows.length < 1) { $.messager.alert("错误", "请选择要删除的代码分类"); return; }
    $.messager.confirm('提示！', '确定删除这' + rows.length + '个代码分类吗？', function (r) {
        if (r) {
            para = {};
            para.id = "";
            $.each(rows, function (i, row) {
                alert(rows.length);
                if(i<rows.length-1){
                    para.id +=row.id +",";
                }else{
                    para.id+=row.id;
                }
            });
            $.ajax({
                url: "deleteCodeSort",
                data: para,
                type: "POST",
                dataType: "text",
                success: function (result) {
                    $.messager.alert("提示", "操作成功");
                    $("#datagrid").datagrid("reload").datagrid('clearSelections').datagrid('clearChecked');
                }
            });
        }
    });
}
/**
 *根据代码类型查询代码详细信息
 */
function detail(index) {
    var url="codeInfoList?id="+index;
    var detailDialog=$("<div/>").dialog({
        href: "codeInfoList",
        title: "代码明细",
        height: 480,
        width: 600,
        modal: true,
        iconCls: "icon-group_key"
    });
}