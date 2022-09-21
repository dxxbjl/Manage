layui.define(['jquery','cookie'], function(exports){
    let $ = layui.jquery,
        cookie = layui.cookie;
    let options = function(name) {
        let list = [];
        if (name) {
            // 获取字典
            list = JSON.parse(decodeURI($.cookie(name)));
        }
        return list;
    }
    let crud = {
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