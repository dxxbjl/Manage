INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('用户表管理', '0', '1', 'layui-icon-home', '/app/user/listPage', 'C', 'Y', 'user:list');
SELECT @parentId := LAST_INSERT_ID();
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('添加用户表', @parentId, '1', '', '#', 'F', 'Y', 'user:add');
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('编辑用户表', @parentId, '2', '', '#', 'F', 'Y', 'user:edit');
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('删除用户表', @parentId, '3', '', '#', 'F', 'Y', 'user:del');