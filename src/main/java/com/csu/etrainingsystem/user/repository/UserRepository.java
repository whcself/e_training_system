package com.csu.etrainingsystem.user.repository;

import com.csu.etrainingsystem.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {



     @Query(value="select * from users where users.account=? and users.del_status=0",nativeQuery = true)
     User findUserByAccount(String account);
}
