<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>密钥管理系统登陆</title>
    <link rel="stylesheet" type="text/css" href="${assetsPath}js/lib/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${assetsPath}js/lib/bootstrap-3.3.5-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${assetsPath}css/login.css">
    <script type="text/javascript" src="${assetsPath}js/lib/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${assetsPath}js/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${assetsPath}js/kms/login.js"></script>
</head>
<body>
<div class="login-container">
    <h2 style="padding-bottom:5px;font-size:14px;font-weight:bold;padding-left:10px;border-bottom:1px solid gainsboro;">
        KMS登陆</h2>

    <form action="${basePath}login.do" method="post">
        <div>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="username" type="text" name="loginName" class="form-control" placeholder="请输入用户名/邮箱/手机号"
                       aria-describedby="basic-addon1" value="">
            </div>
            <div class="input-group" style="margin-top:10px;">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="password" type="password" name="password" class="form-control" placeholder="请输入密码"
                       aria-describedby="basic-addon1" value="">
            </div>
        </div>
        <div style="margin-top:10px;">
            <#if errorMsg>
                <div id="errorMsg" class="alert alert-danger pull-left" role="alert">${errorMsg}</div>
            </#if>
            <input id="btn-login" class="btn btn-success pull-right" type="submit" value="登陆"
                   style="margin-right:10px;">
        </div>
    </form>

</div>
<div class="ui-mask-box">


    <div class="ui-mask-container">
        <h1>您的浏览器有点古董了,换个新的吧!</h1>

        <div class="ui-mask-list">
            <a href="http://chrome.360.cn/" title="360极速浏览器"><img
                    src="${assetsPath}images/browser/360chrome.png"/></a>
            <a href="http://rj.baidu.com/soft/detail/14744.html?ald" title="谷歌chrome极速浏览器"><img
                    src="${assetsPath}images/browser/compatible_chrome.gif"/></a>
            <a href="http://www.firefox.com.cn/download/" title="火狐浏览器"><img
                    src="${assetsPath}images/browser/compatible_firefox.gif"/></a>
            <a href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-9-worldwide-languages" title="IE9浏览器"><img
                    src="${assetsPath}images/browser/compatible_ie.gif"/></a>
        </div>
    </div>
</div>
</body>
</html>