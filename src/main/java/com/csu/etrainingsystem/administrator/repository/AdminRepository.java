package com.csu.etrainingsystem.administrator.repository;


import com.csu.etrainingsystem.administrator.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
//    public List<Admin> findAll();
}
