SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS t_role_permission;
DROP TABLE IF EXISTS t_permission;
DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;




/* Create Tables */

-- 权限表
CREATE TABLE t_permission
(
	permission_id bigint NOT NULL AUTO_INCREMENT COMMENT '权限id',
	permsssion_name varchar(50) COMMENT '权限名称',
	PRIMARY KEY (permission_id)
) COMMENT = '权限表';


-- 角色表
CREATE TABLE t_role
(
	role_id bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
	rolename varchar(50) COMMENT '角色名称',
	PRIMARY KEY (role_id)
) COMMENT = '角色表';


-- 角色权限中间表
CREATE TABLE t_role_permission
(
	role_id bigint NOT NULL COMMENT '角色id',
	permission_id bigint NOT NULL COMMENT '权限id'
) COMMENT = '角色权限中间表';


-- 用户表
CREATE TABLE t_user
(
	user_id int NOT NULL AUTO_INCREMENT COMMENT '用户id',
	username varchar(50) COMMENT '账号',
	password varchar(50) NOT NULL COMMENT '密码',
	role_id bigint NOT NULL COMMENT '角色id',
	PRIMARY KEY (user_id),
	UNIQUE (username)
) COMMENT = '用户表';



/* Create Foreign Keys */

ALTER TABLE t_role_permission
	ADD FOREIGN KEY (permission_id)
	REFERENCES t_permission (permission_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_role_permission
	ADD FOREIGN KEY (role_id)
	REFERENCES t_role (role_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_user
	ADD FOREIGN KEY (role_id)
	REFERENCES t_role (role_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



