package com.system.monitor.services;

import com.system.monitor.collectors.ResourceRecordCollector;

public interface CollectorFactoryService {
	
	ResourceRecordCollector getCollector(String resourceName);
	
}
