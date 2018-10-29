-- INSERT INTO `etsys`.`t_group` (`t_group_id`, `t_num`, `del_status`) VALUES ('1', '10', '0');
-- INSERT INTO `etsys`.`t_group` (`t_group_id`, `t_num`, `del_status`) VALUES ('2', '10', '0');
--
-- INSERT INTO `etsys`.`teacher` (`tid`, `t_group_id`, `tname`, `role`, `material_privilege`, `overtime_privilege`, `del_status`) VALUES ('1', '1', 'zzd', '管理员', '1', '1', '0');
-- INSERT INTO `etsys`.`teacher` (`tid`, `t_group_id`, `tname`, `role`, `material_privilege`, `overtime_privilege`, `del_status`) VALUES ('2', '2', 'aa', '实训老师', '2', '1', '0');
-- INSERT INTO `etsys`.`teacher` (`tid`, `t_group_id`, `tname`, `role`, `material_privilege`, `overtime_privilege`, `del_status`) VALUES ('3', '1', '胡', '实训老师', '0', '0', '0');
--
-- INSERT INTO `etsys`.`batch` (`del_status`, `batch_name`, `credit`, `bat_describe`) VALUES ('0', '1', '5', '车工,钳工');
-- INSERT INTO `etsys`.`batch` (`del_status`, `batch_name`, `credit`, `bat_describe`) VALUES ('0', '2', '2', '铣工');
-- INSERT INTO `etsys`.`batch` (`del_status`, `batch_name`, `credit`, `bat_describe`) VALUES ('0', '3', '1', '车削');
--
-- INSERT INTO `etsys`.`s_group` (`s_group_id`, `num`, `batch_name`, `del_status`) VALUES ('1', '10', '1', '0');
-- INSERT INTO `etsys`.`s_group` (`s_group_id`, `num`, `batch_name`, `del_status`) VALUES ('2', '10', '1', '0');
-- INSERT INTO `etsys`.`s_group` (`s_group_id`, `num`, `batch_name`, `del_status`) VALUES ('3', '10', '2', '0');
-- INSERT INTO `etsys`.`s_group` (`s_group_id`, `num`, `batch_name`, `del_status`) VALUES ('4', '10', '3', '0');
--
-- INSERT INTO `etsys`.`student` (`sid`, `s_group_id`, `batch_name`, `sname`, `class`, `sdept`, `depart`, `del_status`) VALUES ('2', '1', '1', '护具你', '1', '信安', '信息院', '0');
-- INSERT INTO `etsys`.`student` (`sid`, `s_group_id`, `batch_name`, `sname`, `class`, `sdept`, `depart`, `del_status`) VALUES ('3', '1', '2', '堂已经', '2', '几点', '机电院', '0');
--
UPDATE `etsys`.`t_group` SET `t_group_id` = '车削' WHERE (`t_group_id` = '1');
UPDATE `etsys`.`t_group` SET `t_group_id` = '铣工' WHERE (`t_group_id` = '2');
INSERT INTO `etsys`.`t_group` (`t_group_id`, `t_num`, `del_status`) VALUES ('教师管理组', '10', '0');
