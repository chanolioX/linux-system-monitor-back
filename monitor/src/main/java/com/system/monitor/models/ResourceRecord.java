package com.system.monitor.models;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;


/**
 * A class that models a resource record.
 * 
 * <p> Bugs: -
 * <p> TODO: DB Queries. Unit tests.
 * 
 * 
 */
@Data
@Entity
public class ResourceRecord {

	/**
	 * Record Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Record time stamp
	 */
	private Timestamp timestamp;

	/**
	 * Resource name 
	 */
	private String resourceName;
	
	/**
	 * Map of readings corresponding to this record
	 */
	@OneToMany	
	private Map<String, Reading> readings;
	
	
	
}
