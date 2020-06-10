package com.system.monitor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.monitor.models.ResourceRecord;

/**
 * A class that models a Resource Record Repository.
 * 
 * <p> Bugs: -
 * <p> TODO: DB Queries. Unit tests.
 * 
 * 
 */
@Repository
public interface ResourceRecordRepository extends JpaRepository<ResourceRecord, Long>{
	
	/**
	 * Get the records for a given resource.
	 * 
	 * @param resourceName is the name of the target resource 
	 * 
	 */
	List<ResourceRecord> findByResourceName(String resourceName);
}
