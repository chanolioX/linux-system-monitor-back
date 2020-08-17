package com.system.monitor.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * A class that models a reading from the collector script.
 * 
 * <p> Bugs: -
 * <p> TODO: DB Queries. Unit tests.
 * 
 * 
 */
@Data
@Entity
@Table(name = "readings")
public class Reading {
	
	/**
	 * Reading Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * The value obtained by the collector script
	 */	
	private double value;
	
	/**
	 * Corresponding record
	 */	
	@ManyToOne
	@JsonIgnore
	private ResourceRecord record;
    
	/**
	 * Set the corresponding record.
	 * 
	 * @param record is the record corresponding to this reading
	 */ 
    public void setRecord(ResourceRecord record) {
    	this.record = record;
    }
}
