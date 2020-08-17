package com.system.monitor.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

/**
 * A class that models an alert. An instance of this class will be sent when a record 
 * has a reading with a value greater than a given threshold.
 * 
 * TODO
 * 
 */

@Data
@Entity
public class Alert {

	/**
	 * Alert Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Resource Record that triggered this alert
	 */
	@OneToOne
	private ResourceRecord record;
	
	/**
	 * Rule that created this alert
	 */
	@ManyToOne
	private Rule rule;
	
	/**
	 * Description of the alert.
	 */
	private String description;
	
	/*
	 * Constructor for Alert Class
	 * 
	 * @param record
	 * @param rule 
	 * @param priority
	 */
	public Alert(ResourceRecord record, Rule rule, String description) {
		this.record = record;
		this.rule = rule;
		this.description = description;
	}
}
 