layui.define(['jquery'], function(exports){
    let $ = layui.jquery;
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
        getDictValue: function(dictTypeKey,dictValueKey) {
            let dict = sessionStorage.getItem(dictTypeKey);
            let data = [];
            if (dict) {
                data = JSON.parse(dict);
            } else {
                // 获取字典
                $.ajax({
                    type: 'get',
                    url:  '/sysDict/getDictValues/' + dictTypeKey,
                    contentType:'application/json;charset=UTF-8',
                    dataType: 'json',
                    async : false,
                    success: function(result) {
                        if (result.code === 200) {
                            data = result.data;
                        }
                    }
                });
                window.sessionStorage.setItem(dictTypeKey, JSON.stringify(data));
            }
            for(let i = 0; i < data.length; i++) {
                if (data[i].dictValueKey === dictValueKey) {
                    return data[i].dictValueName;
                }
            }
            return "未知";
        }
    };
    exports('crud', crud);
});