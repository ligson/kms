/**
 * 本工具类是通用工具
 * 主要包含功能：
 * 1、通用头信息中各种事件绑定、数据验证、动作处理
 *
 * 目前主要完成：
 * 1、search提交查询
 * 2、search自动补全
 */
(function($){
  /*  var engine = new Bloodhound({
        identify: function(o) { return o.id_str; },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name', 'screen_name'),
        dupDetector: function(a, b) { return a.id_str === b.id_str; },
        prefetch: '/search/prefetch',
        remote: {
            url: '/search/searchKey?q=%QUERY',
            wildcard: '%QUERY'
        }
    });*/

    // ensure default users are read on initialization
    //engine.get('1090217586', '58502284', '10273252', '24477185')

   /* function engineWithDefaults(q, sync, async) {
        if (q === '') {
            sync(engine.get('1090217586', '58502284', '10273252', '24477185'));
            async([]);
        }

        else {
            engine.search(q, sync, async);
        }
    }*/
    //查询对象
    var Search = function(){
        this._init();
    };
    Search.prototype = {
       _init: function(){
           var _this = this;
           //私有初始化方法
           this._bindEvent();
           //初始化自动注入显示框
           $('.typeahead').typeahead({
                   hint: true,
                   highlight: true,
                   minLength: 1
               },
               {
                   name: 'states',
                   displayKey: 'value',
                   source: _this.bast_match()
               });
       },
       bast_match: function(){
           //自动弹出框，配置数据
           return function findMatches(q, cb) {
               //匹配规则交给后台，前台只需要进行展示对应数据即可
               var type = $("select[name=searchType]").val();
               var words = [];
               $.ajax({
                   url:"/index/searchKey",
                   success: function(data){
                       words = data.hotKeys;
                   },
                   data:{type:type,key:q},
                   type:"POST",
                   dataType:"json",
                   async: false
               });
//               { value: str },数据存储格式
               cb(words);
           };
       },
       _bindEvent: function(){
           //绑定提交事件
           $("#search_form").submit(this._doSubmit);
           //绑定鼠标输入事件

       },
        _doSubmit: function(){
            //执行提交动作
            var ipt = $(this).find("input[name='title']");
            if ((ipt.val() + "").trim().length == 0) {
                return false;
            }
        }
    };
    $(document).ready(function(q){
        new Search();

        $.post("/index/");
    });
})(window.jQuery);

