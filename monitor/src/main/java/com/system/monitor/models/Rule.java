package com.system.monitor.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	@OneToOne
	private ResourceRecord resourceThreshold;
	
	/**
	 * Priority of this alert 
	 */
	private AlertPriority priority;
	
	/**
	 * Set of users that will receive a notification if an alert is triggered
	 */
	@OneToMany
	private Set<User> alertRecipients;
	
	/**
	 * Set of users that will receive a notification if an alert is triggered
	 */
	@OneToMany
	private Set<Alert> alertsTrigerred;
	
	
	
	public Rule(ResourceRecord resourceThreshold, AlertPriority priority, Set<User> alertRecipients) {
		this.resourceThreshold = resourceThreshold;
		this.priority = priority;
		this.alertRecipients = alertRecipients;
	}
	
	public void newAlertTrigerred(Alert newAlert) {
		alertsTrigerred.add(newAlert);
	}
}
