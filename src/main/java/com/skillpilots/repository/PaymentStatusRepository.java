package com.skillpilots.repository;

import com.skillpilots.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus,Long> {


    public PaymentStatus  findByCollegeId(Long collegeId);
}
