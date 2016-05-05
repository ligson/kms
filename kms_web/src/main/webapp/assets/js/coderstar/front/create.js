/**
 *
 * 创建文章或者提问工具包，主要方便统一进行操作
 * User: liyangli
 * Date: 2015/7/24
 * Time: 00:10
 */
(function($){

    //创建对象，基础类
    var Create = function(){
        this.validateField = {
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                title: {
                    message: '标题格式不正确',
                    validators: {
                        notEmpty: {
                            message: '标题禁止为空'
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
        this._init.call(this);
    };
    Create.prototype = {
        _init: function(){
            //初始化方法
            this._bindEvent();
            var createQuestionForm = $("#"+this.__findFormId());
            createQuestionForm.bootstrapValidator(this.__formValidate());
        },
        __findFormId: function(){
            //获取form id
            return "createQuestionForm";
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
        __formValidate: function(){
            //form表单进行验证的属性
            return this.validateField;
        }
    };

    //文章对象
    var Article = function(){
        Create.call(this);
    };
    Article.prototype = new Create();

    //提问对象
    var Question = function(){
        Create.call(this);
    };
    Question.prototype = new Create();
    Question.prototype.__formValidate = function(){
        var def = {fields:{money: {
            validators: {
                numeric: {
                    message: '请输入数字格式'
                },
                greaterThan: {
                    value: 0,
                    message: "悬赏金额请大于0"
                },
                lessThan: {
                    value: maxMoney,
                    message: "悬赏数字不能超过账户余额" + maxMoney
                }
            }
        }}};
        var _this = this;
        $.extend(true,_this.validateField,def);
        return _this.validateField;
    };
    $(function(){
        //通过页面进行标识对应类型。
        var type = $("#type").val();
        if(type=="article"){
            new Article();
        }else if(type == "question"){
            new Question();
        }
    });
})(jQuery);
