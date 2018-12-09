package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material,String> {
    @Query(value="select * from material where material.clazz=? and material.del_status=0",nativeQuery = true)
    Material findMaterialByClazz(String clazz);
    @Query(value="select * from material where  material.del_status=0",nativeQuery = true)
    Iterable<Material> findAllMaterial();
    @Query(value="select * from material where material.num>0 and material.del_status=0",nativeQuery = true)
    Iterable<Material> findMaterialNotEmpty();
    @Query(value = "update material SET material.del_status=1 WHERE material.clazz=?",nativeQuery = true)
    @Modifying
    void deleteMaterialByClazz(String clazz);

}
