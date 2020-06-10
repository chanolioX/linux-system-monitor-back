package com.system.monitor.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * A configuration class that provide the data structure for dynamic management of data collectors.
 * 
 * 
 */
@Data
@ConfigurationProperties(prefix = "monitor.collectors.resource")
@Configuration("CollectorProperties")
public class CollectorConfiguration {
	
	/**
	 * Collector scripts path map
	 */
	private Map<String,String> collectorConfigMapping;

}
