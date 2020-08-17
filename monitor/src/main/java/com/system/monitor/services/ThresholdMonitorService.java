package com.system.monitor.services;

import java.util.Set;

import com.system.monitor.models.Alert;
import com.system.monitor.models.ResourceRecord;
import com.system.monitor.models.User;

public interface ThresholdMonitorService {
	public void verifyRecord(ResourceRecord record);	
	public void notifyAlertRecipients(Alert alert, Set<User> Recipients);
	
}
