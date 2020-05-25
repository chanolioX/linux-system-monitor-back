package com.system.monitor.websockets;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.system.monitor.services.Impl.ResourceCollector;


@Service
public class ResourceDispatcherWSHandler extends AbstractWebSocketHandler {
	
	@Autowired
	private ResourceCollector collector;
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
        while(session.isOpen()){
        	Thread.sleep(1000);
        	String test = collector.getTest();
        	if(test != null) {
        		TextMessage cpuUtilization = new TextMessage("CPU: " + test + "%");
        		session.sendMessage(cpuUtilization);
        	}
        }
	}

	
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InterruptedException {
    	
    
    }
        
}

