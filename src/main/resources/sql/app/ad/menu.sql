INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('广告表管理', '0', '1', 'layui-icon-home', '/app/ad/listPage', 'C', 'Y', 'ad:list');
SELECT @parentId := LAST_INSERT_ID();
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('添加广告表', @parentId, '1', '', '#', 'F', 'Y', 'ad:add');
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('编辑广告表', @parentId, '2', '', '#', 'F', 'Y', 'ad:edit');
INSERT INTO `sys_menu` (menu_name,parent_id,order_num,icon,url,menu_type,visible,perms) VALUES ('删除广告表', @parentId, '3', '', '#', 'F', 'Y', 'ad:del');