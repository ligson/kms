$(function () {
    var userGrid = $("#tt");
    userGrid.datagrid({
        fit:true,
        columns: [[
            {field: 'ck', title: 'ckID', width: 40, checkbox: 'true'},
            {field: 'id', title: 'ID', width: 40, hidden: true},
            {field: 'name', title: '语言名称', width: 100},
            {field: 'sortIndex', title: '显示顺序', width: 100},
            {field: 'description', title: '描述', width: 100},
            {field: 'questionNum', title: '问题总数', width: 100},
            {
                field: "poster", title: "海报", formatter: function (value) {
                return "<img src='" + value + "'/ width=100 height=80 onerror='javascript:this.src=\"/images/nopic.gif\"'>";
            }
            },
        ]],
        toolbar: [{
            id: 'btnadd',
            text: '添加',
            iconCls: 'icon-add',
            handler: function () {
                newLanguage();
            }
        }, {
            id: 'btnupdate',
            text: '修改',
            iconCls: 'icon-save',
            handler: function () {
                editeLanguage();
            }

        }, '-', {
            id: 'btncut',
            text: '删除',
            iconCls: 'icon-cut',
            handler: function () {
                deleteLanguage();
            }
        }]
    });
});
function newLanguage() {
    $("#dlg").dialog("open").dialog('setTitle', '新增编程语言');
    $('#fm').form('clear');//清空窗体数据
}
function save() {
    var url = 'addCategory';
    var data = $('#fm').serialize();
    if (!$('#fm').form('validate'))return;
    $.ajax({
        //设定地址与传递参数到后台
        url: url,
        data: data,
        type: 'post',
        dateType: 'json',
        //判断结果是否正确
        success: function (result) {
            if (result.success) {
                $.messager.alert('提示', '保存成功!', 'success');
                $('#dlg').dialog('close');
                $('#tt').datagrid('reload');
            }
        }, error: function () {
            $.messager.alert('提示', '保存失败!', 'error');
        }
    })
}
function update() {
    var url = 'editCategory';
    var fm = $("#fm");
    var id = fm.find("input[name='id']").val();
    var name = fm.find("input[name='name']").val();
    var sortIndex = fm.find("input[name='sortIndex']").val();
    var desc = fm.find("textarea[name='description']").val();
    var poster = document.getElementById("poster").files[0];
    var formData = new FormData();
    formData.append("id", id);
    formData.append("name", name);
    formData.append("sortIndex", sortIndex);
    formData.append("description", desc);
    formData.append("poster", poster);

    //var data = fm.serialize();
    if (!fm.form('validate'))return;
    $.ajax({
        url: "/questionMgr/editCategory",
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function (data1) {
            if (data1.success) {
                $("#dlg").dialog("close");
                $('#tt').datagrid('reload');
            } else {
                alert(data1.msg);
            }
        },
        error: function (data2) {
            alert(data2);
        }
    });
}
function editeLanguage() {
    var rows = $('#tt').datagrid('getSelected');
    if (rows) {
        $("#dlg").dialog("open").dialog('setTitle', '编辑编程语言');
        $('#id').val(rows.id);
        $('#name').val(rows.name);
        $('#sortIndex').val(rows.sortIndex);
        $('#description').val(rows.description);
        $('#btnSave').attr('onclick', 'update()');
    }
    else {
        $.messager.alert('提示', '请选择要修改的数据');
        
    }
}

function deleteLanguage() {
    var url = 'deleteCategory';
    var selected = $('#tt').datagrid("getSelections");
    if (selected.length == 0) {
        $.messager.alert('提示', '请选择要删除的数据!', 'info');
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
    if (!confirm("确认要删除选中语言信息？"))
        return;

    $.ajax({
        //设定地址与传递参数到后台
        url: url + "?ids=" + idString,
        type: 'post',
        dateType: 'json',
        //判断结果是否正确
        success: function (data) {
            $.messager.alert('提示', '删除成功!', 'success');
            $("#tt").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
            $('#tt').datagrid('reload');
        }, error: function () {
            $.messager.alert('提示', '删除失败!', 'error');
        }
    })
}
