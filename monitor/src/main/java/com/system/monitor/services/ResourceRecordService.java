package com.system.monitor.services;

import java.util.List;
import java.util.Optional;

import com.system.monitor.models.ResourceRecord;

public interface ResourceRecordService {

	List<ResourceRecord> getAll();
	
	List<ResourceRecord> getAllOfaKind(String resourceName);
	
	ResourceRecord insert(ResourceRecord resourceRecord);

	Optional<ResourceRecord> findById(Long id);

}
