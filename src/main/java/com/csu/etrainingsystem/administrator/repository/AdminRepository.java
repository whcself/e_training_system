package com.csu.etrainingsystem.administrator.repository;


import com.csu.etrainingsystem.administrator.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    @Query(value="select * from tb_admin where tb_admin.aid=? and tb_admin.del_status=0",nativeQuery = true)
    Optional<Admin> findAdminByAid(String aid);
    @Query(value="select * from tb_admin where tb_admin.del_status=0",nativeQuery = true)
    Iterable<Admin> findAllAdmin();
}
