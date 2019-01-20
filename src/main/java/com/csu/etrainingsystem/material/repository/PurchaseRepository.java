package com.csu.etrainingsystem.material.repository;

import com.csu.etrainingsystem.material.entity.ApplyForPurchase;
import com.csu.etrainingsystem.material.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,String> {



}
