package com.system.monitor.websockets;


import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.system.monitor.collectors.ResourceRecordCollector;
import com.system.monitor.services.Impl.CollectorFactoryServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * A class that models a Web Socket Handler for the real time stream of the data collection.
 * 
 * <p> Bugs: -
 * <p> TODO afterConnectionEstablished needs refactoring
 * 
 * 
 */
@Slf4j
public class ResourceRecordWebSocketHandler extends AbstractWebSocketHandler {
	/**
	 * Data collector.
	 */	
	private ResourceRecordCollector collector;
	
	/**
	 * @param factory is the Collector Factory that will get the collector for the specific resource
	 * @param resourceName is the target resource 
	 */
	public ResourceRecordWebSocketHandler(String resourceName,CollectorFactoryServiceImpl factory) {
		this.collector = factory.getCollector(resourceName);
	}

	/**
	 * Start the streaming through the session recently established. Needs refactoring! 
	 * 
	 * @param session is the Web Socket session established in the ENDPOINT corresponding to this handler.
	 * 
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

			Thread.sleep(2000);

		while(session.isOpen()){
			String jsonString = collector.getLastRecordAsJsonString();
			if(log.isDebugEnabled()) {log.debug("Asking for record!");}
			TextMessage record= new TextMessage("CPU: " + jsonString);
			session.sendMessage(record);
			Thread.sleep(1000);
		}
	}



	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InterruptedException {


	}

}

