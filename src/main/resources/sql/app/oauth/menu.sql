INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('授权表管理', '0', '1', 'layui-icon-home', '/app/oauth/listPage', 'C', 'Y', 'oauth:list');
SELECT @parentId := LAST_INSERT_ID();
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('添加授权表', @parentId, '1', '', '#', 'F', 'Y', 'oauth:add');
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('编辑授权表', @parentId, '2', '', '#', 'F', 'Y', 'oauth:edit');
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('删除授权表', @parentId, '3', '', '#', 'F', 'Y', 'oauth:del');