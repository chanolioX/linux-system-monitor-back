package com.system.monitor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.monitor.models.Reading;
/**
 * A class that models Reading Repository.
 * 
 * <p> Bugs: -
 * <p> TODO: DB Queries. Unit tests.
 * 
 * 
 */
@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long>{
}
