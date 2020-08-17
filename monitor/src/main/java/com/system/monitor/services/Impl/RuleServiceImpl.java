package com.system.monitor.services.Impl;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.monitor.models.Rule;
import com.system.monitor.repositories.RuleRepository;
import com.system.monitor.services.RuleService;


/**
 * A Service class that provides a layer of abstraction to interact with the DB.
 * 
 * <p> Bugs: -
 * 
 * 
 */

@Service
@Transactional
public class RuleServiceImpl implements RuleService {

		/**
		 * Repository to interact with DB
		 */
		@Autowired
		private RuleRepository repository;
		
		/**
		 * Get all the Readings
		 * 
		 * @return List of Readings
		 */
		@Override
		public List<Rule> getAll() {		
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
		public Optional<Rule> findById(Long id) {
			return repository.findById(id);
		}

		/**
		 * Insert a record in the DB
		 * 
		 * @param rule is the rule to be inserted in the DB
		 * @return The recently inserted rule
		 */
		@Override
		public Rule insert(Rule rule) {
			return repository.save(rule);
		}

	}
