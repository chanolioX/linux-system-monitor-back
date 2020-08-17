package com.system.monitor.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.system.monitor.models.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}