package com.system.monitor.config;


import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.system.monitor.services.Impl.CollectorFactoryServiceImpl;
import com.system.monitor.websockets.ResourceRecordWebSocketHandler;


/**
 * A configuration class that manage the Web Socket configuration: endpoints, hanlders, etc.
 * 
 * 
 */
@Configuration
@EnableWebSocket
public class WSConfiguration implements WebSocketConfigurer{
	
	/**
	 * Service that creates new collectors as needed
	 */
	@Autowired
	private CollectorFactoryServiceImpl collectorFactory;
	
	/**
	 * Dynamic configuration corresponding to file application.properties
	 */
	@Autowired
	private CollectorConfiguration CollectorProperties;
	
	/**
	 * @return container is a Servlet Server Container Bean
	 */
	@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        return container;
    }
	/**
	 * This method will add the corresponding web socket handlers according to the configuration
	 * 
	 * @param registry is the Web Socket Registry 
	 * 
	 */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	for (Entry<String, String> resource :CollectorProperties.getCollectorConfigMapping().entrySet()) {
    		String name = resource.getKey();
    		String endpoint = "/" + name;
        registry.addHandler(new ResourceRecordWebSocketHandler(name, collectorFactory), endpoint).setAllowedOrigins("*");
    	}
        

    }
}
