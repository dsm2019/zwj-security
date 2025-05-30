-- 系统用户
CREATE TABLE sys_user (
  id bigint NOT NULL COMMENT 'id',
  username varchar(50) NOT NULL COMMENT '用户名',
  password varchar(100) COMMENT '密码',
  real_name varchar(50) COMMENT '姓名',
  head_url varchar(200) COMMENT '头像',
  gender tinyint unsigned COMMENT '性别   0：男   1：女    2：保密',
  email varchar(100) COMMENT '邮箱',
  mobile varchar(100) COMMENT '手机号',
  dept_id bigint COMMENT '部门ID',
  super_admin tinyint unsigned COMMENT '超级管理员   0：否   1：是',
  status tinyint COMMENT '状态  0：停用   1：正常',
  creator bigint COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  updater bigint COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  primary key (id),
  unique key uk_username (username),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- 部门
CREATE TABLE sys_dept (
  id bigint NOT NULL COMMENT 'id',
  pid bigint COMMENT '上级ID',
  pids varchar(500) COMMENT '所有上级ID，用逗号分开',
  name varchar(50) COMMENT '部门名称',
  sort int unsigned COMMENT '排序',
  creator bigint COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  updater bigint COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  primary key (id),
  key idx_pid (pid),
  key idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门管理';

-- 角色管理
create table sys_role
(
  id                   bigint NOT NULL COMMENT 'id',
  name                 varchar(50) COMMENT '角色名称',
  remark               varchar(100) COMMENT '备注',
  dept_id              bigint COMMENT '部门ID',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  updater              bigint COMMENT '更新者',
  update_date          datetime COMMENT '更新时间',
  primary key (id),
  key idx_dept_id (dept_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='角色管理';

-- 菜单管理
create table sys_menu
(
  id                   bigint NOT NULL COMMENT 'id',
  pid                  bigint COMMENT '上级ID，一级菜单为0',
  name                 varchar(200) COMMENT '名称',
  url                  varchar(200) COMMENT '菜单URL',
  permissions          varchar(500) COMMENT '授权(多个用逗号分隔，如：sys:user:list,sys:user:save)',
  menu_type            tinyint unsigned COMMENT '类型   0：菜单   1：按钮',
  icon                 varchar(50) COMMENT '菜单图标',
  sort                 int COMMENT '排序',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  updater              bigint COMMENT '更新者',
  update_date          datetime COMMENT '更新时间',
  primary key (id),
  key idx_pid (pid),
  key idx_sort (sort)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='菜单管理';

-- 角色用户关系
create table sys_role_user
(
  id                   bigint NOT NULL COMMENT 'id',
  role_id              bigint COMMENT '角色ID',
  user_id              bigint COMMENT '用户ID',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_role_id (role_id),
  key idx_user_id (user_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='角色用户关系';

-- 角色菜单关系
create table sys_role_menu
(
  id                   bigint NOT NULL COMMENT 'id',
  role_id              bigint COMMENT '角色ID',
  menu_id              bigint COMMENT '菜单ID',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_role_id (role_id),
  key idx_menu_id (menu_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='角色菜单关系';

-- 角色数据权限
create table sys_role_data_scope
(
  id                   bigint NOT NULL COMMENT 'id',
  role_id              bigint COMMENT '角色ID',
  dept_id              bigint COMMENT '部门ID',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_role_id (role_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='角色数据权限';

-- 参数管理
create table sys_params
(
  id                   bigint NOT NULL COMMENT 'id',
  param_code           varchar(32) COMMENT '参数编码',
  param_value          varchar(2000) COMMENT '参数值',
  param_type           tinyint unsigned default 1 COMMENT '类型   0：系统参数   1：非系统参数',
  remark               varchar(200) COMMENT '备注',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  updater              bigint COMMENT '更新者',
  update_date          datetime COMMENT '更新时间',
  primary key (id),
  unique key uk_param_code (param_code),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='参数管理';

-- 字典类型
create table sys_dict_type
(
    id                   bigint NOT NULL COMMENT 'id',
    dict_type            varchar(100) NOT NULL COMMENT '字典类型',
    dict_name            varchar(255) NOT NULL COMMENT '字典名称',
    remark               varchar(255) COMMENT '备注',
    sort                 int unsigned COMMENT '排序',
    creator              bigint COMMENT '创建者',
    create_date          datetime COMMENT '创建时间',
    updater              bigint COMMENT '更新者',
    update_date          datetime COMMENT '更新时间',
    primary key (id),
    UNIQUE KEY(dict_type)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='字典类型';

-- 字典数据
create table sys_dict_data
(
    id                   bigint NOT NULL COMMENT 'id',
    dict_type_id         bigint NOT NULL COMMENT '字典类型ID',
    dict_label           varchar(255) NOT NULL COMMENT '字典标签',
    dict_value           varchar(255) COMMENT '字典值',
    remark               varchar(255) COMMENT '备注',
    sort                 int unsigned COMMENT '排序',
    creator              bigint COMMENT '创建者',
    create_date          datetime COMMENT '创建时间',
    updater              bigint COMMENT '更新者',
    update_date          datetime COMMENT '更新时间',
    primary key (id),
    unique key uk_dict_type_value (dict_type_id, dict_value),
    key idx_sort (sort)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='字典数据';

-- 登录日志
create table sys_log_login
(
  id                   bigint NOT NULL COMMENT 'id',
  operation            tinyint unsigned COMMENT '用户操作   0：用户登录   1：用户退出',
  status               tinyint unsigned NOT NULL COMMENT '状态  0：失败    1：成功    2：账号已锁定',
  user_agent           varchar(500) COMMENT '用户代理',
  ip                   varchar(32) COMMENT '操作IP',
  creator_name         varchar(50) COMMENT '用户名',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_status (status),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='登录日志';

-- 操作日志
create table sys_log_operation
(
  id                   bigint NOT NULL COMMENT 'id',
  operation            varchar(50) COMMENT '用户操作',
  request_uri          varchar(200) COMMENT '请求URI',
  request_method       varchar(20) COMMENT '请求方式',
  request_params       text COMMENT '请求参数',
  request_time         int unsigned NOT NULL COMMENT '请求时长(毫秒)',
  user_agent           varchar(500) COMMENT '用户代理',
  ip                   varchar(32) COMMENT '操作IP',
  status               tinyint unsigned NOT NULL COMMENT '状态  0：失败   1：成功',
  creator_name         varchar(50) COMMENT '用户名',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='操作日志';

-- 异常日志
create table sys_log_error
(
  id                   bigint NOT NULL COMMENT 'id',
  request_uri          varchar(200) COMMENT '请求URI',
  request_method       varchar(20) COMMENT '请求方式',
  request_params       text COMMENT '请求参数',
  user_agent           varchar(500) COMMENT '用户代理',
  ip                   varchar(32) COMMENT '操作IP',
  error_info           text COMMENT '异常信息',
  creator              bigint COMMENT '创建者',
  create_date          datetime COMMENT '创建时间',
  primary key (id),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='异常日志';


-- 文件上传
CREATE TABLE sys_oss (
  id bigint NOT NULL COMMENT 'id',
  url varchar(200) COMMENT 'URL地址',
  creator bigint COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='文件上传';

-- 定时任务
CREATE TABLE schedule_job (
  id bigint NOT NULL COMMENT 'id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  cron_expression varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  status tinyint unsigned COMMENT '任务状态  0：暂停  1：正常',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  creator bigint COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  updater bigint COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  PRIMARY KEY (id),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';

-- 定时任务日志
CREATE TABLE schedule_job_log (
  id bigint NOT NULL COMMENT 'id',
  job_id bigint NOT NULL COMMENT '任务id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  status tinyint unsigned NOT NULL COMMENT '任务状态    0：失败    1：成功',
  error varchar(2000) DEFAULT NULL COMMENT '失败信息',
  times int NOT NULL COMMENT '耗时(单位：毫秒)',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id),
  key idx_job_id (job_id),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务日志';

-- 系统用户Token
CREATE TABLE sys_user_token (
  id bigint NOT NULL COMMENT 'id',
  user_id bigint NOT NULL COMMENT '用户id',
  token varchar(100) NOT NULL COMMENT '用户token',
  expire_date datetime COMMENT '过期时间',
  update_date datetime COMMENT '更新时间',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY user_id (user_id),
  UNIQUE KEY token (token)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户Token';

-- 初始数据
INSERT INTO sys_user(id, username, password, real_name, gender, email, mobile, status, dept_id, super_admin, creator, create_date, updater, update_date) VALUES (1067246875800000001, 'admin', '$2a$10$012Kx2ba5jzqr9gLlG4MX.bnQJTD9UWqF57XDo2N3.fPtLne02u/m', '管理员', 0, 'root@renren.io', '13612345678', 1, null, 1, 1067246875800000001, now(), 1067246875800000001, now());

INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000002, 0, '权限管理', null, null, 0, 'icon-safetycertificate', 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000003, 1067246875800000055, '新增', null, 'sys:user:save,sys:dept:list,sys:role:list', 1, null, 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000004, 1067246875800000055, '修改', null, 'sys:user:update,sys:dept:list,sys:role:list', 1, null, 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000005, 1067246875800000055, '删除', null, 'sys:user:delete', 1, null, 3, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000006, 1067246875800000055, '导出', null, 'sys:user:export', 1, null, 4, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000007, 1067246875800000002, '角色管理', 'sys/role', null, 0, 'icon-team', 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000008, 1067246875800000007, '查看', null, 'sys:role:page,sys:role:info', 1, null, 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000009, 1067246875800000007, '新增', null, 'sys:role:save,sys:menu:select,sys:dept:list', 1, null, 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000010, 1067246875800000007, '修改', null, 'sys:role:update,sys:menu:select,sys:dept:list', 1, null, 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000011, 1067246875800000007, '删除', null, 'sys:role:delete', 1, null, 3, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000012, 1067246875800000002, '部门管理', 'sys/dept', null, 0, 'icon-apartment', 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000014, 1067246875800000012, '查看', null, 'sys:dept:list,sys:dept:info', 1, null, 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000015, 1067246875800000012, '新增', null, 'sys:dept:save', 1, null, 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000016, 1067246875800000012, '修改', null, 'sys:dept:update', 1, null, 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000017, 1067246875800000012, '删除', null, 'sys:dept:delete', 1, null, 3, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000025, 1067246875800000035, '菜单管理', 'sys/menu', null, 0, 'icon-unorderedlist', 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000026, 1067246875800000025, '查看', null, 'sys:menu:list,sys:menu:info', 1, null, 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000027, 1067246875800000025, '新增', null, 'sys:menu:save', 1, null, 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000028, 1067246875800000025, '修改', null, 'sys:menu:update', 1, null, 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000029, 1067246875800000025, '删除', null, 'sys:menu:delete', 1, null, 3, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000030, 1067246875800000035, '定时任务', 'job/schedule', null, 0, 'icon-dashboard', 3, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000031, 1067246875800000030, '查看', null, 'sys:schedule:page,sys:schedule:info', 1, null, 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000032, 1067246875800000030, '新增', null, 'sys:schedule:save', 1, null, 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000033, 1067246875800000030, '修改', null, 'sys:schedule:update', 1, null, 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000034, 1067246875800000030, '删除', null, 'sys:schedule:delete', 1, null, 3, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000035, 0, '系统设置', null, null, 0, 'icon-setting', 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000036, 1067246875800000030, '暂停', null, 'sys:schedule:pause', 1, null, 4, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000037, 1067246875800000030, '恢复', null, 'sys:schedule:resume', 1, null, 5, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000038, 1067246875800000030, '立即执行', null, 'sys:schedule:run', 1, null, 6, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000039, 1067246875800000030, '日志列表', null, 'sys:schedule:log', 1, null, 7, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000040, 1067246875800000035, '参数管理', 'sys/params', '', 0, 'icon-fileprotect', 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000041, 1067246875800000035, '字典管理', 'sys/dict-type', null, 0, 'icon-golden-fill', 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000042, 1067246875800000041, '查看', null, 'sys:dict:page,sys:dict:info', 1, null, 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000043, 1067246875800000041, '新增', null, 'sys:dict:save', 1, null, 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000044, 1067246875800000041, '修改', null, 'sys:dict:update', 1, null, 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000045, 1067246875800000041, '删除', null, 'sys:dict:delete', 1, null, 3, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000046, 0, '日志管理', null, null, 0, 'icon-container', 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000047, 1067246875800000035, '文件上传', 'oss/oss', 'sys:oss:all', 0, 'icon-upload', 4, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000048, 1067246875800000046, '登录日志', 'sys/log-login', 'sys:log:login', 0, 'icon-filedone', 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000049, 1067246875800000046, '操作日志', 'sys/log-operation', 'sys:log:operation', 0, 'icon-solution', 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000050, 1067246875800000046, '异常日志', 'sys/log-error', 'sys:log:error', 0, 'icon-file-exception', 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000051, 1067246875800000053, 'SQL监控', '{{ApiUrl}}/druid/sql.html', null, 0, 'icon-database', 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000053, 0, '系统监控', null, null, 0, 'icon-desktop', 3, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000055, 1067246875800000002, '用户管理', 'sys/user', null, 0, 'icon-user', 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000056, 1067246875800000055, '查看', null, 'sys:user:page,sys:user:info', 1, null, 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000057, 1067246875800000040, '新增', null, 'sys:params:save', 1, null, 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000058, 1067246875800000040, '导出', null, 'sys:params:export', 1, null, 4, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000059, 1067246875800000040, '查看', '', 'sys:params:page,sys:params:info', 1, null, 0, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000060, 1067246875800000040, '修改', null, 'sys:params:update', 1, null, 2, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1067246875800000061, 1067246875800000040, '删除', '', 'sys:params:delete', 1, '', 3, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1156748733921165314, 1067246875800000053, '接口文档', '{{ApiUrl}}/doc.html', '', 0, 'icon-file-word', 1, 1067246875800000001, '2025-05-12 10:06:05', 1067246875800000001, '2025-05-12 10:06:05');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1921759723955494914, 0, 'dnf', '', '', 0, 'icon-aim', 4, 1067246875800000001, '2025-05-12 10:49:57', 1067246875800000001, '2025-05-12 10:50:11');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1921759851386839041, 1921759723955494914, '角色', 'dnf/character', '', 0, 'icon-deleteteam', 0, 1067246875800000001, '2025-05-12 10:50:28', 1067246875800000001, '2025-05-12 10:50:48');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1921760047042732034, 1921759851386839041, '全部', '', 'dnf:character:page,dnf:character:list,dnf:character:info,dnf:character:save,dnf:character:update,dnf:character:delete', 1, '', 0, 1067246875800000001, '2025-05-12 10:51:14', 1067246875800000001, '2025-05-12 10:51:55');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1921760487507566594, 1921759723955494914, '历史属性', 'dnf/history-attribute', '', 0, 'icon-compress', 1, 1067246875800000001, '2025-05-12 10:52:59', 1067246875800000001, '2025-05-12 10:52:59');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date) VALUES (1921844396660359169, 1921759723955494914, '地下城', 'dnf/dungeon', '', 0, 'icon-pointmap', 2, 1067246875800000001, '2025-05-12 16:26:25', 1067246875800000001, '2025-05-12 16:26:52');
INSERT INTO sys_menu (id, pid, name, url, permissions, menu_type, icon, sort, creator, create_date, updater, update_date)VALUES (1925841120683118593, 1067246875800000035, '地下城表', 'dnf/completion-record', NULL, 0, 'icon-desktop', 0, 1067246875800000001, now(), 1067246875800000001, now());


INSERT INTO sys_dept(id, pid, pids, name, sort, creator, create_date, updater, update_date) VALUES (1067246875800000062, 1067246875800000063, '1067246875800000066,1067246875800000063', '技术部', 2, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, creator, create_date, updater, update_date) VALUES (1067246875800000063, 1067246875800000066, '1067246875800000066', '长沙分公司', 1, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, creator, create_date, updater, update_date) VALUES (1067246875800000064, 1067246875800000066, '1067246875800000066', '上海分公司', 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, creator, create_date, updater, update_date) VALUES (1067246875800000065, 1067246875800000064, '1067246875800000066,1067246875800000064', '市场部', 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, creator, create_date, updater, update_date) VALUES (1067246875800000066, 0, '0', '人人开源集团', 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, creator, create_date, updater, update_date) VALUES (1067246875800000067, 1067246875800000064, '1067246875800000066,1067246875800000064', '销售部', 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, creator, create_date, updater, update_date) VALUES (1067246875800000068, 1067246875800000063, '1067246875800000066,1067246875800000063', '产品部', 1, 1067246875800000001, now(), 1067246875800000001, now());

INSERT INTO sys_dict_type(id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1160061077912858625, 'gender', '性别', '', 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dict_data(id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1160061112075464705, 1160061077912858625, '男', '0', '', 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dict_data(id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1160061146967879681, 1160061077912858625, '女', '1', '', 1, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dict_data(id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1160061190127267841, 1160061077912858625, '保密', '2', '', 2, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dict_type(id, dict_type, dict_name, remark, sort, creator, create_date, updater, update_date) VALUES (1225813644059140097, 'notice_type', '站内通知-类型', '', 1, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dict_data(id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1225814069634195457, 1225813644059140097, '公告', '0', '', 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dict_data(id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1225814107559092225, 1225813644059140097, '会议', '1', '', 1, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dict_data(id, dict_type_id, dict_label, dict_value, remark, sort, creator, create_date, updater, update_date) VALUES (1225814271879340034, 1225813644059140097, '其他', '2', '', 2, 1067246875800000001, now(), 1067246875800000001, now());

INSERT INTO sys_params(id, param_code, param_value, param_type, remark, creator, create_date, updater, update_date) VALUES (1067246875800000073, 'CLOUD_STORAGE_CONFIG_KEY', '{"type":1,"qiniuDomain":"http://test.oss.renren.io","qiniuPrefix":"upload","qiniuAccessKey":"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ","qiniuSecretKey":"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV","qiniuBucketName":"renren-oss","aliyunDomain":"","aliyunPrefix":"","aliyunEndPoint":"","aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qcloudBucketName":""}', '0', '云存储配置信息', 1067246875800000001, now(), 1067246875800000001, now());

INSERT INTO schedule_job (id, bean_name, params, cron_expression, status, remark, creator, create_date, updater, update_date) VALUES (1067246875800000076, 'testTask', 'renren', '0 0/30 * * * ?', 0, '有参测试，多个参数使用json', 1067246875800000001, now(), 1067246875800000001, now());


--  quartz自带表结构
CREATE TABLE QRTZ_JOB_DETAILS(
  SCHED_NAME VARCHAR(120) NOT NULL,
  JOB_NAME VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  JOB_CLASS_NAME VARCHAR(250) NOT NULL,
  IS_DURABLE VARCHAR(1) NOT NULL,
  IS_NONCONCURRENT VARCHAR(1) NOT NULL,
  IS_UPDATE_DATA VARCHAR(1) NOT NULL,
  REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
  JOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  JOB_NAME VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  NEXT_FIRE_TIME BIGINT(13) NULL,
  PREV_FIRE_TIME BIGINT(13) NULL,
  PRIORITY INTEGER NULL,
  TRIGGER_STATE VARCHAR(16) NOT NULL,
  TRIGGER_TYPE VARCHAR(8) NOT NULL,
  START_TIME BIGINT(13) NOT NULL,
  END_TIME BIGINT(13) NULL,
  CALENDAR_NAME VARCHAR(200) NULL,
  MISFIRE_INSTR SMALLINT(2) NULL,
  JOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  REPEAT_COUNT BIGINT(7) NOT NULL,
  REPEAT_INTERVAL BIGINT(12) NOT NULL,
  TIMES_TRIGGERED BIGINT(10) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_CRON_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  CRON_EXPRESSION VARCHAR(120) NOT NULL,
  TIME_ZONE_ID VARCHAR(80),
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  STR_PROP_1 VARCHAR(512) NULL,
  STR_PROP_2 VARCHAR(512) NULL,
  STR_PROP_3 VARCHAR(512) NULL,
  INT_PROP_1 INT NULL,
  INT_PROP_2 INT NULL,
  LONG_PROP_1 BIGINT NULL,
  LONG_PROP_2 BIGINT NULL,
  DEC_PROP_1 NUMERIC(13,4) NULL,
  DEC_PROP_2 NUMERIC(13,4) NULL,
  BOOL_PROP_1 VARCHAR(1) NULL,
  BOOL_PROP_2 VARCHAR(1) NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_BLOB_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  BLOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_CALENDARS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  CALENDAR_NAME VARCHAR(200) NOT NULL,
  CALENDAR BLOB NOT NULL,
  PRIMARY KEY (SCHED_NAME,CALENDAR_NAME))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_FIRED_TRIGGERS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  ENTRY_ID VARCHAR(95) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  FIRED_TIME BIGINT(13) NOT NULL,
  SCHED_TIME BIGINT(13) NOT NULL,
  PRIORITY INTEGER NOT NULL,
  STATE VARCHAR(16) NOT NULL,
  JOB_NAME VARCHAR(200) NULL,
  JOB_GROUP VARCHAR(200) NULL,
  IS_NONCONCURRENT VARCHAR(1) NULL,
  REQUESTS_RECOVERY VARCHAR(1) NULL,
  PRIMARY KEY (SCHED_NAME,ENTRY_ID))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_SCHEDULER_STATE (
  SCHED_NAME VARCHAR(120) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
  CHECKIN_INTERVAL BIGINT(13) NOT NULL,
  PRIMARY KEY (SCHED_NAME,INSTANCE_NAME))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE QRTZ_LOCKS (
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME VARCHAR(40) NOT NULL,
  PRIMARY KEY (SCHED_NAME,LOCK_NAME))
  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);



CREATE TABLE dnf_character (
                               id bigint NOT NULL COMMENT '角色ID',
                               name varchar(50) NOT NULL COMMENT '角色名字',
                               profession varchar(50) NOT NULL COMMENT '角色职业',
                               career           int          default 0  not null comment '职业id',
                               avatar           varchar(255) default '' null comment '头像',
                               fame int COMMENT '角色名望',
                               sort int COMMENT '名望排行',
                               simulated_damage int COMMENT '模拟伤害',
                               level int COMMENT '角色等级',
                               attack_speed int COMMENT '攻击速度',
                               movement_speed int COMMENT '移动速度',
                               user_id bigint NOT NULL COMMENT '关联用户id',
                               creator bigint COMMENT '创建者',
                               create_date datetime COMMENT '创建时间',
                               updater bigint COMMENT '更新者',
                               update_date datetime COMMENT '更新时间',
                               primary key (id),
                               key idx_name (name),
                               key idx_profession (profession),
                               key idx_fame (fame),
                               key idx_simulated_damage (simulated_damage)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏角色表';

CREATE TABLE dnf_history_attribute(
                               id bigint NOT NULL COMMENT 'ID',
                               character_id bigint NOT NULL COMMENT '角色ID',
                               character_name varchar(50) NOT NULL COMMENT '角色名字',
                               fame int COMMENT '角色名望',
                               simulated_damage int COMMENT '模拟伤害',
                               record_date varchar(20) COMMENT '记录日期',
                               year smallint COMMENT '年份',
                               month tinyint COMMENT '月份',
                               day tinyint COMMENT '日期',
                               week tinyint COMMENT '星期几',
                               creator bigint COMMENT '创建者',
                               create_date datetime COMMENT '创建时间',
                               updater bigint COMMENT '更新者',
                               update_date datetime COMMENT '更新时间',
                               primary key (id),
                               key idx_character_id (character_id),
                               key idx_record_date (record_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='历史属性表';

CREATE TABLE dnf_dungeon(
                                      id bigint NOT NULL COMMENT 'ID',
                                      name varchar(50) NOT NULL COMMENT '地下城名字',
                                      boss varchar(50) NOT NULL COMMENT '最终Boss',
                                      type tinyint COMMENT '地下城类型，1:普通，2:征讨，3:军团，4:攻坚战',
                                      ‘period’ tinyint DEFAULT 1 COMMENT '周期，1：日常，2:周常，3:月常',
                                      parent_id bigint COMMENT '父地下城，军团本' DEFAULT 0,
                                      creator bigint COMMENT '创建者',
                                      create_date datetime COMMENT '创建时间',
                                      updater bigint COMMENT '更新者',
                                      update_date datetime COMMENT '更新时间',
                                      primary key (id),
                                      key idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地下城表';

CREATE TABLE dnf_completion_record(
                                      id bigint NOT NULL COMMENT 'ID',
                                      dungeon_id bigint NOT NULL COMMENT '地下城ID',
                                      character_id bigint NOT NULL COMMENT '角色ID',
                                      completion_period varchar(50) NOT NULL COMMENT 'ID',
                                      completionTime int COMMENT '完成的时间(毫秒)',
                                      creator bigint COMMENT '创建者',
                                      create_date datetime COMMENT '创建时间',
                                      updater bigint COMMENT '更新者',
                                      update_date datetime COMMENT '更新时间',
                                      primary key (id),
                                      key idx_parent_id (dungeon_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地下城表';

-- 创建角色信息表
CREATE TABLE IF NOT EXISTS dnf_character_info (
                                              id BIGINT PRIMARY KEY COMMENT '主键 ID',
                                              guild_name VARCHAR(255) COMMENT '冒险团名称',
                                              name VARCHAR(255) COMMENT '角色名称',
                                              job VARCHAR(255) COMMENT '职业',
                                              fame INT COMMENT '冒险家名望',
                                              hp INT COMMENT '生命',
                                              mp INT COMMENT '魔法',
                                              physical_defense_rate DOUBLE COMMENT '物理防御率',
                                              magic_defense_rate DOUBLE COMMENT '魔法防御率',
                                              strength INT COMMENT '力量',
                                              intelligence INT COMMENT '智力',
                                              stamina INT COMMENT '体力',
                                              spirit INT COMMENT '精神',
                                              physical_attack INT COMMENT '物理攻击',
                                              magic_attack INT COMMENT '魔法攻击',
                                              independent_attack INT COMMENT '独立攻击',
                                              physical_crit_rate DOUBLE COMMENT '物理暴击率',
                                              magic_crit_rate DOUBLE COMMENT '魔法暴击率',
                                              attack_speed DOUBLE COMMENT '攻击速度',
                                              cast_speed DOUBLE COMMENT '施放速度',
                                              move_speed DOUBLE COMMENT '移动速度',
                                              fire INT COMMENT '火属性强化',
                                              ice INT COMMENT '冰属性强化',
                                              light INT COMMENT '光属性强化',
                                              dark INT COMMENT '暗属性强化',
                                              creator BIGINT COMMENT '创建者',
                                              create_date DATETIME COMMENT '创建时间',
                                              updater BIGINT COMMENT '更新者',
                                              update_date DATETIME COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';