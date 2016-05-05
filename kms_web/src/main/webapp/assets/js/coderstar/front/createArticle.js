/**
 *
 */
(function($){
    //文章对象
    var Article = function(){
        this._init()
    };
    Article.prototype = {
       _init: function(){
           //初始化方法
           this._bindEvent();
           var createQuestionForm = $("#createQuestionForm");
           createQuestionForm.bootstrapValidator(this._formValidate());
       },
       _bindEvent: function(){
           var _this = this;
           $("#addTagBtn").click(function () {
               _this._addTag();
           });
           var tagTxt = $("#tagTxt");
           tagTxt.keyup(function (e) {
               var keycode = e.which;
               if (keycode == 32) {
                   _this._addTag();
               }
           });
       },
       _addTag: function(){
           //页面显示标签
           var tagTxt = $("#tagTxt");
           if ((tagTxt.val() + "").trim() != "") {
               if (tagTxt.val().length > 24) {
                   BootstrapDialog.alert({title: "提示信息", message: "不能超过24个字！"});
               } else {
                   var html = "<span title='双击我删除' ondblclick='$(this).remove()' class=\"cs-question-tags\" style=\"display:inline-block;\"><i class=\"icon icon-edit\"></i>" + tagTxt.val() + "</span>";
                   $("#tagItems").append(html);
                   tagTxt.val("");
                   var tagValues = $(".cs-question-tags");
                   var tagString = "";
                   $.each(tagValues, function (idx, tag) {
                       tagString += ";" + $(tag).text();
                   });
                   $("input[name='tags']").val(tagString);
               }
           }
       },
        _formValidate: function(){
            //form表单进行验证
            return {
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    title: {
                        message: '文章标题格式不正确',
                        validators: {
                            notEmpty: {
                                message: '文章标题禁止为空'
                            },
                            stringLength: {
                                min: 6,
                                max: 128,
                                message: '标题长度应该在6到128之间'
                            }
                        }
                    },
                    categoryIds: {
                        validators: {
                            notEmpty: {
                                message: '语言分类是必选的'
                            }
                        }
                    }
                }
            };
        }
    };
    $(function(){
        new Article();
    });
})(jQuery);

