layui.define(['jquery'], function(exports){
    var $ = layui.jquery;
    var crud = {
        /**
         * 获取XmSelectTree id列表
         * @param select XmSelect
         * @returns {*} id列表
         */
        getXmSelectTreeIds: function (select) {
            var value = select.getValue()
            var ids = value.map(x => {return x.id});
            for(var i = 0;i < value.length;i++) {
                if($.inArray(value[i].parentId,ids)==-1) {
                    ids.push(value[i].parentId);
                }
            }
            return ids;
        }
    };
    exports('crud', crud);
});