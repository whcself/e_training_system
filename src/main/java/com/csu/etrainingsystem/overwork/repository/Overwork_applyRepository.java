package com.csu.etrainingsystem.overwork_apply.repository;

import com.csu.etrainingsystem.overwork.entity.Overwork_apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Overwork_applyRepository extends JpaRepository<Overwork_apply,Integer> {

    @Query(value="select * from overwork_apply where overwork_apply.apply_id=? and overwork_apply.del_status=0",nativeQuery = true)
    Optional<Overwork_apply>findOverwork_applyByApply_id(int apply_id);

    @Query(value="select * from overwork_apply where  overwork_apply.del_status=0",nativeQuery = true)
    Iterable<Overwork_apply> findAllOverwork_apply();
}
