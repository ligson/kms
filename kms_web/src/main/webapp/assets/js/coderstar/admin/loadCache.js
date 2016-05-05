/**
 * Created by ligson on 2015/12/27.
 * @author ligson
 */

function loadIdxCache() {
    $.post(baseUrl + "systemMgr/loadIndexCache", {}, function (data) {
        alert(data.success ? "加载成功" : "加载失败");
    }, "json");
}
