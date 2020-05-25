package com.system.monitor.services;

import java.util.List;
import java.util.Optional;

import com.system.monitor.models.Reading;
public interface ReadingService{

	List<Reading> getAll();

	Reading insert(Reading reading);

	Optional<Reading> findById(Long id);

}
