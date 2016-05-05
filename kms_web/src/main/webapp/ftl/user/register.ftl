<@override name="title">注册</@override>
<@override name="header">
<link type="text/css" rel="stylesheet" href="${assetsPath}js/lib/bootstrap-validator/css/bootstrapValidator.css">
<script type="text/javascript" src="${assetsPath}js/lib/bootstrap-validator/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="${assetsPath}js/ras/user/register.js"></script>
</@override>
<@override name="body">
<div class="col-sm-2"></div>
<form id="register_form" class="form-horizontal col-sm-8" action="${basePath}user/register.do" method="post">
    <div class="form-group text-center">
        <p>
            <small class="text-danger">${errorMsg}</small>
        </p>
    </div>
    <div class="form-group">
        <label class="col-sm-4" for="name">用户名:</label>
        <div class="col-sm-8">
            <input type="text" id="name" name="name" placeholder="用户名" class="form-control" value="${name}"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4" for="email">邮箱:</label>
        <div class="col-sm-8">
            <input type="text" placeholder="邮箱" class="form-control" id="email" name="email" value="${email}"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4" for="mobile">手机号:</label>
        <div class="col-sm-8">
            <input type="text" placeholder="手机号" class="form-control" id="mobile" name="mobile" value="${mobile}"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4" for="password">密码:</label>
        <div class="col-sm-8">
            <input type="password" placeholder="密码" class="form-control" id="password" name="password"
                   value="${password}"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4" for="password">重复输入密码:</label>
        <div class="col-sm-8">
            <input type="password" placeholder="重复输入密码" class="form-control" id="password2" name="password2"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-12 text-center"><input type="submit" class=" btn btn-large btn-info" value="注册">
            <small>(已经有账号?立即<a href="${basePath}user/login.html">登陆</a>!)</small>
        </div>
    </div>
</form>
<div class="col-sm-2"></div>
</@override>
<@extends name="layout/index.ftl"/>