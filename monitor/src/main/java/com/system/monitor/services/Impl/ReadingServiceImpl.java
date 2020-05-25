package com.system.monitor.services.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.monitor.models.Reading;
import com.system.monitor.repositories.ReadingRepository;
import com.system.monitor.services.ReadingService;


@Service
@Transactional
public class ReadingServiceImpl implements ReadingService {

	@Autowired
	private ReadingRepository repository;
	
	@Override
	public List<Reading> getAll() {		
		return repository.findAll();
	}

	@Override
	public Optional<Reading> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Reading insert(Reading reading) {
		return repository.save(reading);
	}

}
