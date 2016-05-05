/**
 * Created by ligson on 2014/12/4 0004.
 */
var localObj = window.location;
var contextPath = localObj.pathname.split("/")[1];
var baseUrl = localObj.protocol + "//" + localObj.host + "/";
if(contextPath=="ism"){
    baseUrl = baseUrl + contextPath + "/";
}

