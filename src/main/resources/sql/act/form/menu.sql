INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('表单管理', '0', '1', 'layui-icon-home', '/act/form/listPage', 'C', 'Y', 'form:list');
SELECT @parentId := LAST_INSERT_ID();
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('添加表单', @parentId, '1', '', '#', 'F', 'Y', 'form:add');
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('编辑表单', @parentId, '2', '', '#', 'F', 'Y', 'form:edit');
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('删除表单', @parentId, '3', '', '#', 'F', 'Y', 'form:del');