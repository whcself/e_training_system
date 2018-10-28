package com.csu.etrainingsystem.user.repository;

import com.csu.etrainingsystem.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

}
