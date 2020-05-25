package com.system.monitor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.monitor.models.ResourceRecord;

@Repository
public interface ResourceRecordRepository extends JpaRepository<ResourceRecord, Long>{

}
