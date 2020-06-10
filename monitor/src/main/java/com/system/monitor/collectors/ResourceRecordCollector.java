package com.system.monitor.collectors;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.springframework.transaction.annotation.Transactional;

import com.system.monitor.models.ResourceRecord;
import com.system.monitor.services.ReadingService;
import com.system.monitor.services.ResourceRecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.system.monitor.models.Reading;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * A class that models a resource data collector. It initialize a thread that collects the data form the OS.
 * 
 * <p> Bugs: -
 * <p> Initial MOCK. TODO Unit tests, DB Queries.
 * 
 * 
 */

@Transactional
@Slf4j
public class ResourceRecordCollector {

	/**
	 * Service to interact with Resource Records repository
	 */
	private ResourceRecordService recordService;

	/**
	 * Service to interact with Readings repository
	 */
	private ReadingService readingService;

	/**
	 * Last Record of the collection
	 */
	private @Getter ResourceRecord lastRecord = null;
	
	
	/**
	 * The name of the resource of this collector
	 */
	private String resourceName = null;
	
	
	/**
	 * 
	 * Constructor that create the collector and initialize the collection thread
	 * 
	 * @param resourceName is the name of the resource of this collector
	 * @param recordService is the service to interact with Resource Records repository
	 * @param readingService is the service to interact with Readings repository
	 * @param scriptPath is the path to the collector script.
	 */
	public ResourceRecordCollector(String resourceName,
						ResourceRecordService recordService,
						ReadingService readingService,
						String scriptPath) {
		
		
		this.resourceName = resourceName;
		this.recordService = recordService;
		this.readingService = readingService;
		
		Thread collectionThread = new Thread(new CollectionThread(scriptPath), "Collection Thread");
		collectionThread.start();
	}
	
	
	/**
	 * A method that the last record as a JSON String
	 * 
	 * 
	 * @return JsonString: last record as a JSON String
	 */
	public String getLastRecordAsJsonString() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonString = null;
		try {
			 jsonString = ow.writeValueAsString(lastRecord);	
			return jsonString;
		}catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}







	/**
	 * A runnable class that runs the collector script in an infinite loop, inserting data to the DB and updating the last record for real time streaming through web socket
	 * 
	 * 
	 */
		class CollectionThread implements Runnable {
			/**
			 * The process builder that will create the collection proccess in each run
			 */
			ProcessBuilder processBuilder = new ProcessBuilder();
			/**
			 * scriptPath is the absolute path to the collector script
			 */
			String scriptPath = null;
			
			
			/**
			 * @param scriptPath is the absolute path to the collector script
			 */
			CollectionThread (String scriptPath){
				this.scriptPath = scriptPath;
			}

			/**
			 * Key method for the data collection. An infinite loop executes the collector script,
			 * add the corresponding time stamp and persist the data in the Data Base.
			 * 
			 * @param ScriptPath is the path to the corresponding Collector Script
			 * @return void
			 */	
			@Override
			public void run() {
				processBuilder.command(scriptPath);

				while(true) {

					try {

						// List of outputs
						StringBuilder output = new StringBuilder();

						// Run the data collector script
						Process process = processBuilder.start();

						// reader pointing to Standard Output of script
						BufferedReader reader = new BufferedReader(	
								new InputStreamReader(process.getInputStream()));

						// Read the outputs
						String line;
						while ((line = reader.readLine()) != null) {
							output.append(line);
						}

						// Wait for script to finish
						int exitVal = process.waitFor();

						// If execution is successful
						if (exitVal == 0) {

							// Get the record
							lastRecord = makeNewRecord(output.toString());

							// Insert record to DB
							lastRecord = recordService.insert(lastRecord);
							
							setParentRecord(lastRecord);
							log.debug(getLastRecordAsJsonString());
							

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
			
			/**
			 * Get the readings from the outputs and get the full 
			 * record to be persisted in DB
			 *
			 * @param outputs 
			 * @return record 
			 */	
			@SuppressWarnings("deprecation")
			private ResourceRecord makeNewRecord(String outputs) {
				Map<String, Reading> readings = new HashMap<String, Reading>();
				if(outputs != null) {
					try {
						// get the readings from the output of the script
						Set<Map.Entry<String, JsonElement>> readingsEntrySet = 
								new JsonParser().parse(outputs).getAsJsonObject().entrySet();
						// iterate the readings
						for (Map.Entry<String, JsonElement> readingEntry: readingsEntrySet) {
							// get the reading value and its name 
							String key = readingEntry.getKey();
							String value = readingEntry.getValue().getAsString();
							
							// Initialize a new reading with the value collected by the
							// collector script
							Reading temp = new Reading();
							temp.setValue(Double.parseDouble(value));
							
							// Insert reading into DB
							temp = readingService.insert(temp);
							
							// Add reading to the readings map of the new record
							readings.put(key, temp);
						}
						
					}catch(NullPointerException e) {
						e.printStackTrace();
					}
				}else {
					return null;
				}

				// Prepare time stamp
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				// Get new record and store it as last Record
				ResourceRecord record = new ResourceRecord();
				record.setTimestamp(timestamp);
				record.setResourceName(resourceName);
				record.setReadings(readings);
				return record;
			}
			
			/**
			 *Update the record value of the readings from the last record. This is necesary as it is
			 *a bidirectional OneToMany relation.
			 *
			 * @param lastRecord is the last resource data registered by the collector.
			 * @return void
			 */
			public void setParentRecord(ResourceRecord lastRecord) {
				ResourceRecord record = lastRecord;
				for (Map.Entry<String, Reading> readingEntry : record.getReadings().entrySet()) {
					Reading temp = readingEntry.getValue();
					temp.setRecord(record);
					readingService.insert(temp);
					
				
				}
			}




		}



}
