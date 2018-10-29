

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

    /****打分权限的增删改查****/

    @Transactional
    public void addMarking(Marking marking) {
        markingRepository.save(marking);
    }

    @Transactional
    public Marking getMarkingById(String id) {
        return markingRepository.getOne(id);
    }

    @Transactional
    public Iterable<Marking> getAllMarking() {
        return markingRepository.findAll();
    }

    @Transactional
    public void updateMarking(Marking marking) {
        markingRepository.saveAndFlush(marking);
    }

    @Transactional
    public void deleteMarking(String Authority) {
        Marking marking = markingRepository.getByAuthority(Authority);
        marking.setDel_status(true);
        markingRepository.saveAndFlush(marking);
    }
}
