package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.Material;
import com.csu.etrainingsystem.overwork_apply.entity.Overwork_apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material,String> {
    @Query(value="select * from material where material.clazz=? and material.del_status=0",nativeQuery = true)
    Optional<Material> findMaterialByClazz(String clazz);
    @Query(value="select * from material where  material.del_status=0",nativeQuery = true)
    Iterable<Material> findAllMaterial();
}
