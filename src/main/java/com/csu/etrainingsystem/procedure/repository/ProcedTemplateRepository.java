package com.csu.etrainingsystem.procedure.repository;

import com.csu.etrainingsystem.procedure.entity.Proced_template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcedTemplateRepository extends JpaRepository<Proced_template,String> {

    @Query(value = "select * from proced_template where template_name=?1 and del_status=0",nativeQuery = true)
    Iterable<Proced_template> findByTemplateName(String templateName);


    @Query(value = "select pro_name from proced_template where template_name=?1 and del_status=0",nativeQuery = true)
    List<String> findProByName(String name);

}
