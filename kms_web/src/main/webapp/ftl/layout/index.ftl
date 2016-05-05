<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><@block name="title"></@block>-用户中心</title>
    <link rel="stylesheet" type="text/css" href="${assetsPath}js/lib/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${assetsPath}js/lib/bootstrap-3.3.5-dist/css/bootstrap.min.css">
    <script type="text/javascript" src="${assetsPath}js/lib/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${assetsPath}js/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<@block name="header"></@block>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">切换</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">ChinaCA</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">系统简介 <span class="sr-only">(current)</span></a></li>
                <li><a href="#">文档</a></li>
                <li><a href="#">联系我们</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">快速导航 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${basePath}user/login.html">用户中心</a></li>
                        <li><a href="#">注册管理中心</a></li>
                        <li><a href="#">证书管理中心</a></li>
                        <li><a href="#">密钥管理中心</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="container">
<@block name="body"></@block>
</div>
<footer class="container text-center">
    <p>版权所有@2016-9999</p>
</footer>
</body>
</html>