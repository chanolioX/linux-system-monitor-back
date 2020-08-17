package com.system.monitor.services.Impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.monitor.collectors.ResourceRecordCollector;
import com.system.monitor.config.CollectorConfiguration;
import com.system.monitor.services.CollectorFactoryService;
import com.system.monitor.services.ReadingService;
import com.system.monitor.services.ResourceRecordService;
import com.system.monitor.services.ThresholdMonitorService;

import lombok.extern.slf4j.Slf4j;


/**
 * A class that provide the collector construction and initialization for the resource provided.
 * 
 * <p> Bugs: -
 * <p> TODO: performance check (limitates the amount of collectors??), Unit tests.
 * 
 * 
 */
@Service
@Slf4j
public class CollectorFactoryServiceImpl implements CollectorFactoryService{

	
	/**
	 * The configuration properties for the collector scripts
	 */
	@Autowired
	private CollectorConfiguration CollectorProperties;

	
	/**
	 * The Resource Records Service to interact with the DB 
	 */
	@Autowired
	private ResourceRecordService recordService;

	
	/**
	 * The Readings Service to interact with the DB 
	 */
	@Autowired
	private ReadingService readingService;


	/**
	 * The Threshold Monitor interact with monitoring service
	 */
	@Autowired
	private ThresholdMonitorService thresholdMonitorService;
	
	
	/**
	 * Get the collector corresponding to the given resource.
	 * 
	 * 
	 * @param resourceName is the name of the Resource  
	 * @return ResourceRecordCollector collector. 
	 */	
	public ResourceRecordCollector getCollector(String resourceName) {
		String scriptPath = getCollectorScriptPath(resourceName);
		return new ResourceRecordCollector (
				resourceName,
				recordService,
				readingService,
				thresholdMonitorService,
				scriptPath);
	}


	/**
	 * Get the PATH to the collector script corresponding to the given resource
	 *
	 * 
	 * @param resourceName is the name of the Resource  
	 * @return String PATH to the collector script. 
	 */	
	private String getCollectorScriptPath(String resourceName) {
		
		// Absolute path to Collector Script
		String scriptPath = null;
		
		try {
			// Class Loader used to get the script as a resource
			ClassLoader classLoader = getClass().getClassLoader();
		
			// Script Path specified in applicatiion.properties
			String internalPath = CollectorProperties.getCollectorConfigMapping().get(resourceName);
		
			// The Collector Script File
			File file = new File(classLoader.getResource(internalPath).getFile());
		
		
			// Get absolute path
			scriptPath = file.getAbsolutePath();
			
			// Log resource and script path
			if(log.isDebugEnabled()) log.debug("Getting Configuration: Resource: " + resourceName + ". Script Path: " + scriptPath);

		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return scriptPath;
	}
}
