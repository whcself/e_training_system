INSERT etsys.batch(batch_name,credit,bat_describe)
VALUES ('2018S101',1,'车削,磨削,钳工,铣削,焊接,快速成型及逆向工程,数控线切割,铸造');

INSERT batch(batch_name,credit,bat_describe)VALUES
('2018S201',2,'车削,磨削,钳工,铣削,焊接,热处理,快速成型及逆向工程,数控车,数控线切割,铸造,激光切割,锻造,加工中心,Cimatron');

INSERT batch(batch_name,credit,bat_describe) VALUES
('2018S501',5,'车削,磨削,钳工,铣削,焊接,热处理,快速成型及逆向工程,数控车,数控线切割,铸造,激光切割,锻造,加工中心,Cimatron,激光焊接,数控车仿真');
SELECT * FROM batch;
/************************************************/

INSERT users(account,pwd,role) VALUES
('131283','123456','admin');

INSERT users(account,pwd,role) VALUES
('704171','123456','teacher');

INSERT users(account,pwd,role) VALUES
('123404','123456','teacher');

INSERT users(account,pwd,role) VALUES
('134197','123456','teacher');
SELECT * FROM users;
/************************************************/
INSERT teacher(tid,tname,role,material_privilege,overtime_privilege) VALUES
('134197','张怀亮','带队老师',0,0);

INSERT teacher(tid,tname,role,material_privilege,overtime_privilege) VALUES
('704171','傅宏','带队老师',0,0);

INSERT teacher(tid,tname,role,material_privilege,overtime_privilege) VALUES
('123404','李伟虹','带队老师',0,0);

INSERT INTO `etsys`.`teacher` (`tid`, `tname`, `role`, `material_privilege`, `overtime_privilege`) VALUES ('333333', '张聪', '管理员', '1', '1');
INSERT INTO `etsys`.`teacher` (`tid`, `tname`, `role`, `material_privilege`, `overtime_privilege`) VALUES ('444444', '胡成', '管理员', '2', '1');
INSERT INTO `etsys`.`teacher` (`tid`, `tname`, `role`, `material_privilege`, `overtime_privilege`) VALUES ('291293', '唐斯', '管理员', '0', '1');
INSERT INTO `etsys`.`teacher` (`tid`, `tname`, `role`, `material_privilege`, `overtime_privilege`) VALUES ('291930', '张韶涵', '管理员', '1', '0');


SELECT * FROM teacher;
/**********在插入教师组的时候会有请求路径包含中文被拦截的情况************************/
INSERT t_group(t_group_id,tid,t_num)VALUES
('车削组','134197',50);
INSERT t_group(t_group_id,tid,t_num)VALUES
('1802','134197',50);
INSERT t_group(t_group_id,tid,t_num)VALUES
('磨削组','134197',50);
INSERT t_group(t_group_id,tid,t_num)VALUES
('钳工组','134197',50);
INSERT t_group(t_group_id,tid,t_num)VALUES
('铣削组','134197',50);
INSERT t_group(t_group_id,tid,t_num)VALUES
('焊接组','134197',50);
INSERT t_group(t_group_id,tid,t_num)VALUES
('快速成型及逆向工程组','134197',50);
INSERT t_group(t_group_id,tid,t_num)VALUES
('数控线切割组','134197',50);
INSERT t_group(t_group_id,tid,t_num)VALUES
('车削组','704171',50);
INSERT t_group(t_group_id,tid,t_num)VALUES
('焊接组','123404',50);
INSERT INTO `etsys`.`t_group` (`t_group_id`, `tid`, `t_num`) VALUES ('铣削组', '291930', '24');
INSERT INTO `etsys`.`t_group` (`t_group_id`, `tid`, `t_num`) VALUES ('钳工组', '333333', '24');
INSERT INTO `etsys`.`t_group` (`t_group_id`, `tid`, `t_num`) VALUES ('焊接组', '444444', '24');
INSERT INTO `etsys`.`t_group` (`t_group_id`, `tid`, `t_num`) VALUES ('快速成型及逆向工程组', '704171', '24');

SELECT * FROM t_group;
/**********************************/
INSERT s_group(s_group_id,batch_name,num)VALUES
('A1','2018S101',30);
INSERT s_group(s_group_id,batch_name,num)VALUES
('A2','2018S101',30);
INSERT s_group(s_group_id,batch_name,num)VALUES
('A3','2018S101',30);
INSERT s_group(s_group_id,batch_name,num)VALUES
('B1','2018S101',30);
INSERT s_group(s_group_id,batch_name,num)VALUES
('B2','2018S101',30);
INSERT s_group(s_group_id,batch_name,num)VALUES
('B3','2018S101',30);
INSERT s_group(s_group_id,batch_name,num)VALUES
('A1','2018S201',30);
INSERT s_group(s_group_id,batch_name,num)VALUES
('D','2018S201',30);
INSERT s_group(s_group_id,batch_name,num)VALUES
('E','2018S201',30);
SELECT * FROM s_group;
/***************************************/
INSERT student(sid,s_group_id,batch_name,sname,clazz) VALUES
('302160126','A1','2018S101','钟鸣','无机1601');

INSERT student(sid,s_group_id,batch_name,sname,clazz) VALUES
('901170104','A1','2018S101','覃金滨','自动化1707');

SELECT * FROM student;
/**************************************************************/
INSERT proced (pro_name,t_group_id,batch_name,weight)VALUES
('车削','车削组','2018S101',0.11);
INSERT proced (pro_name,t_group_id,batch_name,weight)VALUES
('磨削','磨削组','2018S101',0.11);
INSERT proced (pro_name,t_group_id,batch_name,weight)VALUES
('钳工','钳工组','2018S101',0.12);
INSERT proced (pro_name,t_group_id,batch_name,weight)VALUES
('qiangong','钳工组','2018S101',0.12);


SELECT * FROM proced;
/**************************************************************/
INSERT score(sid,pro_name,pro_score)VALUES
('1203160126','车削',88);
INSERT score(sid,pro_name,pro_score)VALUES
('1203160126','磨削',88);
INSERT score(sid,pro_name,pro_score)VALUES
('1203160126','钳工',88);
INSERT score(sid,pro_name,pro_score)VALUES
('1203160126','qiangong',88);

SELECT * FROM score

INSERT INTO `etsys`.`experiment` (`batch_name`, `class_time`, `time_quant`, `exp_id`, `s_group_id`, `pro_name`, `t_group_id`, `tid`, `submit_time`) VALUES ('2018S101', '1', '第二周周三56节', '1', 'A', '钳工', '钳工组', '123404', '2018-10-10');
