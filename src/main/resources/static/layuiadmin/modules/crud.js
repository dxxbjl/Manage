layui.define(['jquery'], function(exports){
    let $ = layui.jquery;


    function options(name) {
        let list = [];
        let dict = window.sessionStorage.getItem(name);
        if (dict) {
            list = JSON.parse(dict);
        } else {
            // 获取字典
            $.ajax({
                type: 'get',
                url:  '/sysDict/getDictValues/' + name,
                contentType:'application/json;charset=UTF-8',
                dataType: 'json',
                async : false,
                success: function(result) {
                    if (result.code === 200) {
                        list = result.data;
                    }
                }
            });
            window.sessionStorage.setItem(name, JSON.stringify(list));
        }
        return list;
    }
    let crud = {
        /**
         * 获取XmSelectTree id列表
         * @param select XmSelect
         * @returns {*} id列表
         */
        getXmSelectTreeIds: function (select) {
            let value = select.getValue();
            let ids = value.map(x => {return x.id});
            if (ids === 0) {
                layer.msg('请选择节点');
                return ids;
            }
            return ids;
        },
        /**
         * 获取字典
         */
        getDictValue: function(name,defaultVal) {
            let list = options(name);
            for(let i = 0; i < list.length; i++) {
                if (list[i].dictValueKey === defaultVal) {
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
                if(item.dictValueKey === defaultVal) {
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
                if(item.dictValueKey === defaultVal) {
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