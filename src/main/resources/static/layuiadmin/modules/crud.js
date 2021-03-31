layui.define(['jquery'], function(exports){
    var $ = layui.jquery;
    var crud = {
        /**
         * 获取XmSelectTree id列表
         * @param select XmSelect
         * @returns {*} id列表
         */
        getXmSelectTreeIds: function (select) {
            var value = select.getValue();
            var ids = value.map(x => {return x.id});
            if (ids == 0) {
                layer.msg('请选择节点');
                return;
            }
            return ids;
        }
    };
    exports('crud', crud);
});