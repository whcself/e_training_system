package com.csu.etrainingsystem.teacher.service;

import com.csu.etrainingsystem.experiment.service.ExperimentService;
import com.csu.etrainingsystem.teacher.entity.Teacher;
import com.csu.etrainingsystem.teacher.entity.TeacherAndGroup;
import com.csu.etrainingsystem.teacher.entity.TeacherGroup;
import com.csu.etrainingsystem.teacher.entity.TeacherGroupId;
import com.csu.etrainingsystem.teacher.repository.GroupRepository;
import com.csu.etrainingsystem.teacher.repository.TeacherGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TeacherGroupService {
    private  final TeacherGroupRepository teacherGroupRepository;
    private  final MarkingService markingService;
    private final ExperimentService experimentService;
    private final GroupRepository groupRepository;
    @Autowired
    public TeacherGroupService(GroupRepository groupRepository,TeacherGroupRepository teacherGroupRepository, MarkingService markingService, ExperimentService experimentService) {
        this.groupRepository=groupRepository;
        this.teacherGroupRepository = teacherGroupRepository;
        this.markingService = markingService;
        this.experimentService = experimentService;
    }

    @Transactional
    public void addGroup(TeacherGroup teacherGroup){
        groupRepository.save(teacherGroup);
    }
    @Transactional
    public void updateGroup(TeacherGroup teacherGroup){
        groupRepository.saveAndFlush(teacherGroup);
    }
    @Transactional
    public void deleteGroup(String groupName){
        groupRepository.deleteGroup(groupName);
    }
    @Transactional
    public void addTeacherGroup(TeacherAndGroup TeacherAndGroup) {
        this.teacherGroupRepository.save(TeacherAndGroup);
    }

    /**
     * 复合主键,不能被避免(需要作为外键);在写控制层的时候应该在路径参数中传入两个值
     * @param id
     * @return
     */
    @Transactional
    public TeacherAndGroup getTeacherGroup(TeacherGroupId id) {
        return this.teacherGroupRepository.findTeacherGroupById(id.getTid(),id.getT_group_id());
    }
    @Transactional
    public Iterable<TeacherAndGroup> getAllTeacherGroup() {
        return this.teacherGroupRepository.findAllTeacherGroup();
    }

    @Transactional
    public void  updateTeacherGroup(TeacherAndGroup TeacherAndGroup) {
        this.teacherGroupRepository.saveAndFlush(TeacherAndGroup);
    }

    /**
     * 删除该老师在教师组表的记录,这个名字起的不太好
     * @param tid
     */
    @Transactional
    public void deleteTeacherGroupByTeacher(String tid ){
        this.teacherGroupRepository.DeleteTeacherGroupByTidSQL(tid);
    }

    /**
     * 有失误,应该是很多的,如果照t_group_id来的话,
     * @param id
     */
    @Transactional
    public void  deleteTeacherGroup(TeacherGroupId id) {
        TeacherAndGroup teacherAndGroup =getTeacherGroup(id);
         if (teacherAndGroup ==null){
             return ;
         }
         else {
             teacherAndGroup.setDel_status(true);
             updateTeacherGroup(teacherAndGroup);
             //消除打分权限
             this.markingService.deleteMarking(id.getT_group_id());
             //删除指导的实验
             this.experimentService.deleteExperimentByT_group(id.getT_group_id());
         }

        /*
       todo:消除删除一个教师组所带来的影响
       即:解散该组所有的老师,打分权限取消,物料,加班权限不变;在最新的变动中
       打分权限记录的取消,查询该分组的打分权限记录(Marking表),并消除
       需要一个markingService
        */
    }


}
