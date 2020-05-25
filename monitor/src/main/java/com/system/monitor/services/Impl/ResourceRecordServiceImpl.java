package com.system.monitor.services.Impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.monitor.models.ResourceRecord;
import com.system.monitor.repositories.ResourceRecordRepository;
import com.system.monitor.services.ResourceRecordService;

@Service
@Transactional
public class ResourceRecordServiceImpl implements ResourceRecordService {

	@Autowired
	private ResourceRecordRepository repository;
	
	@Override
	public List<ResourceRecord> getAll() {		
		return repository.findAll();
	}

	@Override
	public Optional<ResourceRecord> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public ResourceRecord insert(ResourceRecord resourceRecord) {
		return repository.save(resourceRecord);
	}

}

