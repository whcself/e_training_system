package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveRepository extends JpaRepository<Save,Integer> {

    @Query(value = "select sum(save_num) from save where purchase_id=?",nativeQuery = true)
    Integer getAllSaveNum(String pid);
    @Query(value = "select * from save where save_time between ?1 and ?2 and clazz like ?3 \n" +
            "and save_tname like ?4 and purchase_id like ?5 and del_status=0 order by save_time desc ;",nativeQuery = true)
    List<Save>findBy5(String begin,
                      String end,
                      String clazz,
                      String tname,
                      String pid);

//    @Query(value = "",nativeQuery = true)
//    void addSave(String pid,
//                 String num,
//                 String tname,
//                 String remark);

    @Query(value = "select sum(save_num) from save where purchase_id=?1 and del_status=0 group by purchase_id",nativeQuery = true)
    Integer remainSave(String pid);

    @Modifying
    @Query(value = "update material set num=num+?1 where clazz=?2 and del_status=0;",nativeQuery = true)
    void saveToStorage(int num,String clazz);

    @Query(value = "select num from material where clazz=?1 and del_status=0;",nativeQuery = true)
    Integer getNumofMaterial(String clazz);
}
