package com.csu.etrainingsystem.overwork.repository;

import com.csu.etrainingsystem.overwork.entity.Overwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OverworkRepository extends JpaRepository<Overwork,Integer> {
    @Query(value="select * from overwork where overwork.overwork_id=? and overwork.del_status=0",nativeQuery = true)
    Optional<Overwork> findOverworkByOverwork_id(int overwork_id);

    @Query(value="select * from overwork where overwork.del_status=0",nativeQuery = true)
    Iterable<Overwork> findAllOverwork();
}
