<@override name="title">密钥列表</@override>
<@override name="header">
<script type="text/javascript" src="${assetsPath}js/kms/key/keyMgr.js"></script>
</@override>
<@override name="body">
<table id="tt" title="密钥列表" class="easyui-datagrid" style="height:500px; width: 100%;"
       data-options="singleSelect:false,fix:true" toolbar="#toolbar" pagination="true" rownumbers="true"
       fitColumns="true"
       url="${basePath}key/keyList.json" iconCls="icon-save" pagination="true">
    <thead>
    </thead>
</table>
<div id="showGenKeyDlg" title="创建密钥对" class="easyui-dialog col-sm-4" closed="true" style="padding:30px;">
    <div class="container-fluid">
        <form class="form-horizontal  easyui-form" action="${basePath}key/genKey.do" id="genKeyForm">
            <div class="form-group row">
                <label class="col-sm-3">密钥类型</label>
                <div class="col-sm-6">
                    <select class="easyui-combobox form-control" name="keyType" editable="false" required="true">
                        <option value="1">RSA</option>
                        <option value="2">SM2</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-3">密钥长度</label>
                <div class="col-sm-6">
                    <select class="easyui-combobox form-control" name="keySize" editable="false" required="true">
                        <option value="256">256</option>
                        <option value="512">512</option>
                        <option value="1024">1024</option>
                        <option value="2048">2048</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-3">生成个数</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control easyui-textbox" name="count" validType="number" required="true">
                </div>
            </div>
            <div class="form-group row">
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="genKey()">生成</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
                   onclick="$('#showGenKeyDlg').dialog('close')">取消</a>
            </div>
        </form>
    </div>
</div>
<#--<div id="toolbar">
    <div style="margin: 5px 5px;">
        语言名称：<input type="text" class="easyui-textbox" style="height: 18px;" name="name"/>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    </div>
</div>
<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px;"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">
    </div>
</div>
<div id="recommendDlg" title="推荐文章" class="easyui-dialog" closed="true" style="width:400px;height:180px;">
    <form id="recommendForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value=""/>
        <table style="margin-top:10px;margin-left:10px;">
            <tr>
                <td><label>推荐度:</label></td>
                <td><input id="recommendNumTbx" class="easyui-textbox" type="text" name="recommendNum"
                           data-options="required:true"/></td>
            </tr>
            <tr>
                <td><label>海报:</label></td>
                <td><input class="input" name="poster" type="file" id="poster"/></td>
            </tr>
        </table>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitRecommendForm()">提交</a>
        </div>
    </form>
</div>-->
</@override>
<@extends name="layout/key.ftl"/>