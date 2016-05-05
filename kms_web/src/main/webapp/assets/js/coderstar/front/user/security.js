/**
 * Created by ligson on 2015/7/22.
 * 密码修改
 */
function saveClick() {
    var old_password=$('#old_password').val();
    var new_password=$('#new_password').val();
    var re_password=$('#re_password').val();
    if(old_password==''){
        BootstrapDialog.alert({title: "提示信息", message: "当前密码不能为空!"});
        return;
    }
    if(new_password==''){
        BootstrapDialog.alert({title: "提示信息", message: "新密码不能为空!"});
        return;
    }
    if(re_password==''){
        BootstrapDialog.alert({title: "提示信息", message: "确认密码不能为空!"});
        return;
    }
    if(re_password!=new_password){
        BootstrapDialog.alert({title: "提示信息", message: "二次输入的密码不一致!"});
        return;
    }
    var params = $('#setting_form').serialize();
    var url = $('#setting_form').attr('action');
    $.ajax({
        url: url,
        dateType: 'json',
        type: 'post',
        data: params,
        success: function (result) {
            if (result.success) {
                BootstrapDialog.alert({title: "提示信息", message: "保存成功!"});
            } else {
                if (result.isExsit) {
                    BootstrapDialog.alert({title: "提示信息", message: "原密码输入不正确!"});
                } else {
                    BootstrapDialog.alert({title: "提示信息", message: "保存失败!"});
                }
            }

        }, error: function () {
            BootstrapDialog.alert({title: "提示信息", message: "服务器也有生病的时候，您懂得！"});
        }
    })
}
