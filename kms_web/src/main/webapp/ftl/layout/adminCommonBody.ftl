<div class="ui-mask">
    <p>正在加载页面,请稍后...</p>
</div>
<div class="admin-con" data-options="region:'north',border:false" style="height:72px;background:#666;text-align:center">
    <div id="header-inner">
        <table cellpadding="0" cellspacing="0" style="width:100%;">
            <tbody>
            <tr>
                <td rowspan="2" style="width:20px;">
                </td>
                <td style="height:52px;">
                    <div style="color:#fff;font-size:22px;font-weight:bold;">
                        <a style="color:#fff;font-size:22px;font-weight:bold;text-decoration:none">密钥管理系统</a>
                    </div>
                    <div style="color:#fff">
                        <a style="color:#fff;text-decoration:none">让一切XX使用的更方便!</a>
                    </div>
                </td>
                <td style="padding-right:5px;text-align:right;vertical-align:bottom;">
                    <div id="topmenu">
                        <a href="${basePath}key/index.html">密钥管理</a>
                        <a href="/questionMgr/index">问题管理</a>
                        <a href="/userMgr/index">用户管理</a>
                        <a href="/payMgr/index">支付管理</a>
                        <a href="/systemMgr/index">系统管理</a>
                    </div>

                </td>
                <td style="padding-right:5px;text-align:right;vertical-align:bottom;">
                    <div id="user_toolbar">
                        <p>欢迎你,<a style="color:#FFF" iconCls="icon-user" class="easyui-linkbutton" plain="true"
                                  id="login_username">master</a>&nbsp;&nbsp;
                            <a style="color:#FFF" class="easyui-linkbutton" iconCls="icon-control_power_blue"
                               plain="true" href="${basePath}admin/logout">退出</a></p>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="container-fluid" style="display:none;">
        <div class="row">
            <div class="ism_logo logo-width col-md-2">
                <h1 class="logo-img">后台管理系统</h1>
            </div>

            <div class="ism_nav col-md-8">

            </div>


            <div class="ism_user col-md-2">
                <div class="ism_user">
                    <img class="img-circle" src="/images/use-img.jpg"/>

                    <p>
                        <a href="">${session.user.name}</a>
                        <span>2014-05-06</span>
                    </p>

                </div>
            </div>
        </div>
    </div>
</div>