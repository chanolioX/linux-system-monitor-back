package com.system.monitor.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Rule {
	/**
	 * Alert Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Resource Record that triggered this alert
	 */
	private Resource resourceName;
	
	/**
	 * Priority of this alert 
	 */
	private AlertPriority priority;
	
	/**
	 * Thresholds to compare the records against
	 */
	private Set<Reading> thresholds;
	
	/**
	 * Set of users that will receive a notification if an alert is triggered
	 */
	private Set<User> alertRecipients;
	
	public Rule(Resource resourceName, AlertPriority priority, Set<Reading> thresholds, Set<User> alertRecipients) {
		this.resourceName = resourceName;
		this.priority = priority;
		this.thresholds = thresholds;
		this.alertRecipients = alertRecipients;
	}
}
