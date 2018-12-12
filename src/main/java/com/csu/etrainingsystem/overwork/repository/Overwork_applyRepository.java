package com.csu.etrainingsystem.overwork.repository;

import com.csu.etrainingsystem.overwork.entity.Overwork_apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface Overwork_applyRepository extends JpaRepository<Overwork_apply,Integer> {

    @Query(value="select * from overwork_apply where overwork_apply.apply_id=? and overwork_apply.del_status=0",nativeQuery = true)
    Optional<Overwork_apply>findOverwork_applyByApply_id(int apply_id);

    @Query(value="select * from overwork_apply where  overwork_apply.del_status=0",nativeQuery = true)
    Iterable<Overwork_apply> findAllOverwork_apply();

    @Query(value = "select a.*,b.sname,b.clazz from " +
            "(select * from overwork_apply where apply_time>?1 and apply_time<?2 and pro_name like ?3 and del_status=0) as a " +
            "left join (select sid,sname,clazz from student)as b on a.sid=b.sid",nativeQuery = true)
    List<Map<String,String>> findBetweenBeginAndEndTime(String beginDate, String endDate, String proName);

    @Query(value = "select * from overwork_apply where sid=? and del_status=0",nativeQuery = true)
    List<Overwork_apply> findOverwork_applyBySId(String sId);

}
