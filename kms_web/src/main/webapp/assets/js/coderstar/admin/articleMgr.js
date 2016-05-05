$(function () {
    var userGrid = $("#tt");
    userGrid.datagrid({
        fit:true,
        columns: [[
            {field: 'ck', title: 'ckID', width: 40, checkbox: 'true'},
            {field: 'id', title: 'ID', width: 40, hidden: true},
            {
                field: 'title', title: '问题标题', width: 200, formatter: function (value, row, index) {
                return "<a href='/article/view?id=" + row.id + "' target='_blank'>" + row.title + "</a>";
            }
            },
            {
                field: 'state', title: '状态', width: 20, formatter: function (value, row, index) {
                if (row.state == 1) {
                    return "<font color='red'>审核</font>";
                } else if (row.state == 0) {
                    return "<font color='blue'>发布</font>";
                }
            }
            },
            /*{field: 'description', title: '问题描述',width:200},*/
            {field: 'attentionNum', title: '关注数', width: 25,sortable:true},
            {field: 'replyNum', title: '回复数', width: 25,sortable:true},
            {field: 'viewNum', title: '浏览数', width: 25,sortable:true},
            {
                field: "recommendNum", title: "推荐数",sortable:true
            },
            {
                field: 'createDate', title: '创建时间', width: 50, sortable:true,formatter: function (value) {
                return Date.convertTxtFormat(value);
            }
            },
            {
                field: "poster", title: "海报", formatter: function (value) {
                return "<img src='" + value + "'/ width=100 height=80 onerror='javascript:this.src=\"/images/nopic.gif\"'>";
            }
            },
            {
                field: "recommend", title: "推荐", formatter: function (value, row, index) {
                return "<input type='button' value='推荐' onclick='recommendArticle(" + row.id + "," + row.recommendNum + ")'/>";
            }
            }
        ]],
        nowrap: false,
        toolbar: [{
            id: 'btnadd',
            text: '批量审核',
            iconCls: 'icon-ok',
            handler: function () {
                update();
            }
        }, {
            id: 'btndelete',
            text: '批量删除',
            iconCls: 'icon-cut',
            handler: function () {
                deleteArticle();
            }
        }, {
            id: 'syncIndexBtn',
            text: '同步所有索引',
            iconCls: 'icon-save',
            handler: function () {
                syncQuestionIndex();
            }
        }]
    });

    $("#recommendForm").form({
        url: baseUrl + "articleMgr/recommendArticle",
        ajax: false
    });

});
function syncQuestionIndex() {
    $.post("/articleMgr/syncIndex", {}, function (data) {
        if (data.success) {
            $.messager.alert("同步完成");
        }
    });
}
function update() {
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
    if (!confirm("确认要批量审核选中的问题信息？"))
        return;

    var url = 'auditArticle';
    $.ajax({
        //设定地址与传递参数到后台
        url: url + "?id=" + idString,
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
function deleteArticle() {
    var url = 'deleteArticle';
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
            var msg = "";
            for (var i = 0; i < data.length; i++) {
                var d = data[i];
                if (d.success) {
                    msg += "文章id:" + d.id + "删除成功";
                } else {
                    msg += "文章id:" + d.id + "删除失败," + d.msg;
                }
            }
            $.messager.alert('提示', msg, 'success');
            $("#tt").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
            $('#tt').datagrid('reload');
        }, error: function () {
            $.messager.alert('提示', '删除失败!', 'error');
        }
    })
}

function recommendArticle(articleId, recommendNum) {
    $("#recommendForm").find("input[name='id']").val(articleId);
    $("#recommendDlg").dialog("open");
    $("#recommendNumTbx").textbox("setText",recommendNum);
}
function submitRecommendForm() {
    //$("#recommendForm").form("submit");
    var rf = $("#recommendForm");
    var poster = document.getElementById("poster");
    var id = rf.find("input[name='id']").val();
    var recommendNum = rf.find("input[name='recommendNum']").val();

    var formData = new FormData();
    formData.append("poster", poster.files[0]);
    formData.append("id", id);
    formData.append("recommendNum", recommendNum);
    $.ajax({
        url: "/articleMgr/recommendArticle",
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function (data1) {
            if (data1.success) {
                $("#recommendDlg").dialog("close");
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