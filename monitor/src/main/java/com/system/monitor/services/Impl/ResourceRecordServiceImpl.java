package com.system.monitor.services.Impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.monitor.models.ResourceRecord;
import com.system.monitor.repositories.ResourceRecordRepository;
import com.system.monitor.services.ResourceRecordService;


/**
 * A Service class that provides a layer of abstraction to interact with the DB.
 * 
 * <p> Bugs: -
 * 
 * 
 */
@Service
@Transactional
public class ResourceRecordServiceImpl implements ResourceRecordService {
	
	/**
	 * Repository to interact with DB
	 */
	@Autowired
	private ResourceRecordRepository repository;
	
	/**
	 * Get all the Resource Records
	 * 
	 * @return List of Resource Records
	 */
	public List<ResourceRecord> getAll() {		
		return repository.findAll();
	}
	
	/**
	 * 
	 * Gets all the Resource Records for a specific resource.
	 * 
	 * @param resourceName is the name of the target resource
	 */
	@Override
	public List<ResourceRecord> getAllOfaKind(String resourceName) {		
		return repository.findByResourceName(resourceName);
	}
	
	/**
	 * Get the Resource Record for a specific ID
	 * @param id is the ID of the target record
	 * @return - A Resource Record corresponding to the given ID<p>
	 * -  There may not be a Record Resource.
	 * 
	 */
	@Override
	public Optional<ResourceRecord> findById(Long id) {
		return repository.findById(id);
	}

	/**
	 * Insert a record in the DB
	 * 
	 * @param resourceRecord is the record to be inserted in the DB
	 * @return The recently inserted record
	 */
	@Override
	public ResourceRecord insert(ResourceRecord resourceRecord) {
		return repository.save(resourceRecord);
	}

}

