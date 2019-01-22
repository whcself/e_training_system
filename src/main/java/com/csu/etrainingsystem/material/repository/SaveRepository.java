package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveRepository extends JpaRepository<Save,Integer> {

    @Query(value = "select sum(save_num) from save where purchase_id=?",nativeQuery = true)
    int getAllSaveNum(String pid);
}
