package com.system.monitor.services.Impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.monitor.models.ResourceRecord;
import com.system.monitor.services.ReadingService;
import com.system.monitor.services.ResourceRecordService;
import com.system.monitor.models.Reading;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * <p> Bugs: (a list of bugs and other problems)
 * <p> TODO: Autowired anotation for  
 * 
 *
 * @author (your name)
 */

@Slf4j
@Service
@Transactional
public class ResourceCollector {
	
	@Autowired
	private ResourceRecordService recordService;
	
	@Autowired
	private ReadingService readingService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	private @Getter ResourceRecord lastRecord = null;
	private @Getter String test= null;											//test
	
	private String resourceName = "cpuUtilization";
	private ProcessBuilder processBuilder = new ProcessBuilder();

	/**
	 * 
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	
	@Async
	@PostConstruct
	public void initCollection() {

		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {

				while(true) {

					try {
						String scriptPath = getScriptPath(resourceName); 
						processBuilder.command(scriptPath);
						// List of outputs
						List<String> outputs = new ArrayList<String>();

						// Run the data collector script
						Process process = processBuilder.start();

						// reader pointing to Standard Output of script
						BufferedReader reader = new BufferedReader(	
								new InputStreamReader(process.getInputStream()));

						// Read the outputs
						String line;
						while ((line = reader.readLine()) != null) {
							outputs.add(line);
						}

						// Wait for script to finish
						int exitVal = process.waitFor();

						// If execution is successful
						if (exitVal == 0) {

							// Get the record
							lastRecord = getRecord(outputs);

							// Insert record to DB
							lastRecord = recordService.insert(lastRecord);

							// Get the Object as JSONString
							String jsonString = getJsonString(lastRecord);	

							// log the record for debugging
							if(log.isDebugEnabled()) {log.debug(jsonString);}

						} else {

							// TODO corrupted reading
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}



		});
	}
	
	
	/**
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	
	private String getScriptPath(String resourceName) {
		// TODO Auto-generated method stub
		return "/path/to/cpuUtil.sh";
	}
	
	
	
	/**
	 *
	 *
	 * @param lastRecord is the record that it will be return for real time streaming.
	 * @return jsonString  
	 */
	
	private String getJsonString(ResourceRecord lastRecord) {
				
		//Creating the ObjectMapper object
	    ObjectMapper mapper = new ObjectMapper();
	    String jsonString = null;
	    try {
	    	 jsonString = mapper.writeValueAsString(lastRecord);
	    }catch(JsonProcessingException e) {
	    	log.error("The record cannot be retrived as Json String ID: " + lastRecord.getId());
	    }
		return jsonString;
	}
	
	
	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	private ResourceRecord getRecord(List<String> outputs) {
		Set<Reading> readings = new HashSet<Reading>();
		
		// getting all the readings
		for(String value: outputs) {
			Reading temp = new Reading(Double.parseDouble(value));
			temp = readingService.insert(temp);
			readings.add(temp);
			test = value;													//test
		}
					
		// Prepare time stamp
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					
		// Get new record and store it as last Record
		ResourceRecord record = new ResourceRecord(timestamp, resourceName, readings);
					
		return record;    
	}
}
		


		
	
	


