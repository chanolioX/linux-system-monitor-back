package com.system.monitor.services;

import java.util.List;
import java.util.Optional;
import com.system.monitor.models.Rule;

public interface RuleService {

	List<Rule> getAll();
	Rule insert(Rule rule);
	Optional<Rule> findById(Long id);
}
