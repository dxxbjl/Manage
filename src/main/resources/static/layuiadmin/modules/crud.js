layui.define(['jquery','cookie'], function(exports){
    let $ = layui.jquery,
        cookie = layui.cookie;
    let options = function(name) {
        let list = [];
        if (name && $.cookie(name)) {
            // 获取字典
            list = JSON.parse(decodeURI($.cookie(name)));
        }
        return list;
    }
    // 富文本上传文件适配器
    class UploadAdapter {
        constructor(loader) {
            this.loader = loader;
        }
        upload() {
            return new Promise((resolve, reject) => {
                let file = [];
                this.loader.file.then(res=>{
                    const data = new FormData();
                    file = res;
                    //文件流
                    data.append('file', file);
                    $.ajax({
                        url: ctx + '/common/uploadMinIo',
                        type: 'POST',
                        data: data,
                        dataType: 'json',
                        processData: false,
                        contentType: false,
                        success: function (res) {
                            if (res.code === 200) {
                                resolve({
                                    default: res.data.url
                                });
                            } else {
                                reject(res.message);
                            }
                        }
                    });
                })
            });
        }
        abort() {
        }
    }
    let crud = {
        /**
         * 初始化富文本编辑器
         * @param obj dom对象
         * @param data 回显数据
         */
        initEditor: function(obj,data = {}) {
            ClassicEditor.create(document.querySelector("#" + obj), {
                toolbar: {
                    items: [
                        'heading',
                        '|',
                        'bold',
                        'italic',
                        'link',
                        'bulletedList',
                        'numberedList',
                        '|',
                        'indent',
                        'outdent',
                        '|',
                        'imageUpload',
                        'blockQuote',
                        'insertTable',
                        'mediaEmbed',
                        'undo',
                        'redo'
                    ]
                },
                language: 'zh-cn',
                image: {
                    toolbar: [
                        'imageTextAlternative',
                        'imageStyle:full',
                        'imageStyle:side'
                    ]
                },
                table: {
                    contentToolbar: [
                        'tableColumn',
                        'tableRow',
                        'mergeTableCells'
                    ]
                },
                licenseKey: '',
            }).then(editor => {
                // 加载了适配器
                editor.plugins.get('FileRepository').createUploadAdapter = (loader)=>{
                    return new UploadAdapter(loader);
                };
                window.editor = editor;
                if (data) {
                    window.editor.setData(data);
                }
            }).catch(err => {
                console.error(err.stack);
            });
        },
        /**
         * 获取富文本编辑器数据
         */
        getEditorData: function() {
            return window.editor.getData();
        },
        /**
         * 显示图片
         */
        showImg: function (img) {
            if (img) {
                return `<img onclick="fun.preview(this)" width="100%" height="100%" src="${img}">`
            }
            return '暂无';
        },
        /**
         * post请求
         * @param url 地址
         * @param data 数据
         * @param callback 回调
         */
        post: function (url, data, callback) {
            $.ajax({
                url: url,
                type: 'POST',
                contentType:'application/json;charset=UTF-8',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (result) {
                    layer.msg(result.message,{
                        offset:['50%'],
                        time: 3000
                    },function() {
                        if (result.code === 200) {
                            callback();
                        }
                    });
                }
            });
        },
        /**
         * get请求
         * @param url 地址
         * @param callback 回调
         */
        get: function (url, callback) {
            $.ajax({
                url: url,
                type: 'GET',
                success: function (result) {
                    layer.msg(result.message,{
                        offset:['50%'],
                        time: 3000
                    },function() {
                        if (result.code === 200) {
                            callback();
                        }
                    });
                }
            });
        },
        /**
         * 获取字典
         */
        getDictValue: function(name,defaultVal) {
            let list = options(name);
            for(let i = 0; i < list.length; i++) {
                if (list[i].dictValueKey == defaultVal) {
                    return list[i].dictValueName;
                }
            }
            return "未知";
        },
        /**
         * layui 动态渲染select
         * @param id
         * @param name
         * @param defaultVal
         */
        setSelect:function (id,name,defaultVal) {
            $("#"+id).empty();
            $("#"+id).append(new Option("请选择",""));
            let list = options(name);
            if(!list){
                return;
            }
            $.each(list,function(index,item){
                let option = new Option(item.dictValueName,item.dictValueKey);
                if(item.dictValueKey == defaultVal) {
                    option.selected=true;
                }
                $("#"+id).append(option)
            });
            layui.form.render("select");
            layui.element.render();
        },
        /**
         * layui 动态渲染radio
         * @param id
         * @param name
         * @param defaultVal
         */
        setRadio:function (id,name,defaultVal) {
            $("#"+id).html("");
            let list = options(name);
            if(!list){
                return;
            }
            $.each(list,function(index,item){
                let radioHtml = '<input type="radio" name="'+id+'" value="'+item.dictValueKey+'" title="'+item.dictValueName+'" ';
                if(item.dictValueKey == defaultVal) {
                    radioHtml+='checked';
                }
                radioHtml+='>';
                $("#"+id).append(radioHtml);
            });
            layui.form.render();
            layui.element.render();
        }
    };
    exports('crud', crud);
});