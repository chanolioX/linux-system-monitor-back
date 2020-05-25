package com.system.monitor.models;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class ResourceRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Timestamp timestamp;

	private String resourceName;
	
	@OneToMany
	private Set<Reading> readings;
	
	public ResourceRecord(Timestamp timestamp, String resourceName, Set<Reading> readings) {
		this.timestamp = timestamp;
		this.resourceName = resourceName;
		this.readings = readings;
	}
	
	
}
