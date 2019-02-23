package com.csu.etrainingsystem.user.repository;

import com.csu.etrainingsystem.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {



     @Query(value="select * from users where users.account=?",nativeQuery = true)
     User findUserByAccount(String account);

     @Query(value="update users set del_status=1 where account=?",nativeQuery = true)
     @Modifying
     void delById(String account);

     /**
      * 恢复被删除的用户
      * @param account
      */
     @Query(value="update users set del_status=0 where account=?",nativeQuery = true)
     @Modifying
     void reSetById(String account);
}
