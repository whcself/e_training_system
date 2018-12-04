package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<Apply,Integer> {

    @Query(value="select * from material_apply where" +
            " material_apply.sid like ? and material_apply.sname like? and material_apply.clazz like?"
            +" and  material_apply.apply_time between ? and ? "
            ,nativeQuery = true)
    Iterable<Apply> findAplyBySidAndSnameAndClazzAndTime(String sid,String sname ,String clazz,String startTime,String endTime);



    @Query(value="  SELECT  MAX(material_apply.apply_time) FROM material_apply where material_apply.del_status=0",nativeQuery = true)
    List<Date> findMaxTime();
    @Query(value="   SELECT MIN(material_apply.apply_time) FROM material_apply where material_apply.del_status=0",nativeQuery = true)
    List<Date> findMinTime();

}
