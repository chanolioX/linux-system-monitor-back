package com.system.monitor.services.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.monitor.models.Reading;
import com.system.monitor.repositories.ReadingRepository;
import com.system.monitor.services.ReadingService;

/**
 * A Service class that provides a layer of abstraction to interact with the DB.
 * 
 * <p> Bugs: -
 * 
 * 
 */
@Service
@Transactional
public class ReadingServiceImpl implements ReadingService {

	/**
	 * Repository to interact with DB
	 */
	@Autowired
	private ReadingRepository repository;
	
	/**
	 * Get all the Readings
	 * 
	 * @return List of Readings
	 */
	@Override
	public List<Reading> getAll() {		
		return repository.findAll();
	}

	/**
	 * Get the Reading for a specific ID
	 * @param id is the ID of the target reading
	 * @return - A Reading corresponding to the given ID<p>
	 * -  There may not be a Reading.
	 * 
	 */
	@Override
	public Optional<Reading> findById(Long id) {
		return repository.findById(id);
	}

	/**
	 * Insert a record in the DB
	 * 
	 * @param resourceRecord is the record to be inserted in the DB
	 * @return The recently inserted record
	 */
	@Override
	public Reading insert(Reading reading) {
		return repository.save(reading);
	}

}
