package com.system.monitor.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Reading {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double value;
		
//  @ManyToOne
//	private ResourceRecord record;
    
    
    public Reading(double value) {
    	this.value = value;
    }
}
