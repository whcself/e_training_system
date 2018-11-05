

/**
 * AdminService 中注入MarkingRepository ，对Marking的CRUD作为Admin的Service
 */
package com.csu.etrainingsystem.teacher.service;

import com.csu.etrainingsystem.teacher.entity.Marking;
import com.csu.etrainingsystem.teacher.repository.MarkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class MarkingService {
    private final MarkingRepository markingRepository;

    @Autowired
    public MarkingService(MarkingRepository markingRepository) {
        this.markingRepository = markingRepository;
    }

    @Transactional
    public void addMarking(Marking marking){
        markingRepository.save(marking);
    }

    /**
     * 由于marking表的id没有实际意义，所以我们需要用打分教室表的t-group-id来实现查询
     * 这张表应该都要基于该思想?
     * @param t_group_id
     * @return
     */
    @Transactional
    public Iterable<Marking> getMarkingByT_group_id(String t_group_id){
        return markingRepository.findMarkingByT_group_id(t_group_id);}
    @Transactional
    public Iterable<Marking> getMarkingByAuthority(String authority){
        return markingRepository.findByAuthority(authority);}

    @Transactional
    public Iterable<Marking>getAllMarking(){
        return markingRepository.findByAllMarking();}

    /**
     * 在打分的时候:根据前端选择的分组和工种,选择出实验,然后查看提交老师是否在该实验的教师组中
     * 如果在就拥有该组的打分权限;
     * @param marking
     */
    @Transactional
    public void updateMarking(Marking marking){markingRepository.saveAndFlush(marking);}

    /**
     * 教师组删除后,其打分权限也被删除
     * @param t_group_id
     */
    @Transactional
    public void deleteMarking(String t_group_id){

        this.markingRepository.deleteMarkingByT_group_id(t_group_id);
    }
    @Transactional
    public void deleteMarkingById(int marking_id){
        this.markingRepository.deleteMarkingByMark_id(marking_id);
    }
}
