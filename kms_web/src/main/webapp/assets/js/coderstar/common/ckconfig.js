/**
 * Created by Administrator on 2015/4/11 0011.
 */
$(function () {
    var toolbar = [
        ['Font', 'FontSize'],
        ['TextColor', 'BGColor'],
        ['Bold', 'Italic', 'Strike', 'Underline'],
        ['Subscript', 'Superscript'],
        ['NumberedList', 'BulletedList'],
        ['Outdent', 'Indent'],
        ['Image', 'Flash', 'Table'],
        ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
        ['Link', 'Unlink', 'Anchor'],
        ['Smiley', 'SpecialChar', 'PageBreak'],
        ['CodeSnippet', 'Preview', 'Source']
    ];
    var config = {
        readOnly: !pageConfig.isLogin,
        extraPlugins: 'codesnippet',
        toolbar: toolbar,
        codeSnippet_theme: 'monokai_sublime',
        filebrowserUploadUrl: '/user/uploadFile',
        filebrowserImageUploadUrl: "/user/uploadImage",
        skin: "bootstrapck"
    };

    CKEDITOR.replace('ckeditor01', config);
})
;
