/**
 * Created by ligson on 2014/12/2 0002.
 */

var cityName1;
var cityFid1;
var cityCode1;
var cityLevel1;
var cityDescription1;
var cityType1;
var addForm;

function addArea() {

    var node = $("#cityTree").tree("getSelected");
    if (node) {
        //cityName1.textbox("setText", node.text);
        cityFid1.textbox("setValue", node.id);
        cityLevel1.textbox("setValue", node.level + 1);
        //cityCode1.textbox("setText", node.code);
        //cityDescription1.textbox("setText", node.description);
        //cityType1.combobox("setValue", node.type);
    } else {
        cityLevel1.textbox("setText", 1);
    }
    $("#addAreaDlg").dialog("open");
}

$(function () {
    addForm = $("#addCityForm");
    cityName1 = addForm.find("input[name='name']");
    cityName1.textbox({
        required: true
    });

    cityCode1 = addForm.find("input[name='code']");
    cityCode1.textbox({
        required: true
    });
    cityLevel1 = addForm.find("input[name='level']");
    cityLevel1.textbox({
        required: true,
        editable: false
    });
    cityDescription1 = addForm.find("input[name='description']");
    cityDescription1.textbox({
        multiline: true
    });
    cityType1 = addForm.find("select[name='type']");
    cityType1.combobox();
    cityFid1 = addForm.find("input[name='fId']");
    cityFid1.textbox();

    addForm.form({
        success: function (data) {
            data = eval("(" + data + ")");
            if (data.success) {
                $.messager.alert("提示信息", "添加成功!", "info");
                $("#addAreaDlg").dialog("close");
            } else {
                $.messager.alert("提示信息", "天津失败!", "error");
            }
        }
    });
    var form = $("#modifyCityForm");
    var cityId = form.find("input[name='cityId']");
    cityId.textbox();
    var cityName = form.find("input[name='name']");
    cityName.textbox({
        required: true
    });
    var cityCode = form.find("input[name='code']");
    cityCode.textbox({
        required: true
    });
    var cityType = form.find("select[name='type']");
    cityType.combobox({});
    var cityLevel = form.find("input[name='level']");
    cityLevel.textbox({
        required: true,
        editable: false
    });
    var cityDescription = form.find("textarea[name='description']");
    cityDescription.textbox({
        multiline: true
    });
    $("#cityTree").tree({
        url: baseUrl + "marketsMgr/cityList",
        onClick: function (node) {
            cityId.textbox("setValue", node.id);
            cityName.textbox("setText", node.text);
            cityCode.textbox("setText", node.code);
            cityLevel.textbox("setText", node.level);
            cityDescription.textbox("setText", node.description);
        },
        onContextMenu: function (e, node) {
            e.preventDefault();
            $("#treeMenu").menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }
    });

    form.form({
        success: function (data) {
            data = eval("(" + data + ")");
            if (data.success) {
                $.messager.alert("提示信息", "修改成功!", "info");
                var nodeId = cityId.textbox("getValue");
                if (nodeId) {
                    var node = $("#cityTree").tree("find", nodeId);
                    if (node) {
                        $("#cityTree").tree("update", {
                            target: node.target,
                            text: cityName.textbox("getText"),
                            type: cityType.combobox("getValue"),
                            description: cityDescription.textbox("getText"),
                            code: cityCode.textbox("getText")
                        });
                    }
                }
            } else {
                $.messager.alert("提示信息", "修改失败!", "error");
            }
        }
    });
});
function removeit() {
    var cityTree = $("#cityTree");
    var node = cityTree.tree("getSelected");
    if (node) {
        $.post(baseUrl + "marketsMgr/removeCity", {cityId: node.id}, function (data) {
            if (data.success) {
                $.messager.alert("提示信息", "删除成功!", "info");
                if (node) {
                    cityTree.tree("remove", node.target);
                }
            } else {
                $.messager.alert("提示信息", "删除失败!" + data.msg, "error");
            }
        });
    }
}