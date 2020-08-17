package com.system.monitor.services.Impl;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.monitor.models.Alert;
import com.system.monitor.models.Reading;
import com.system.monitor.models.ResourceRecord;
import com.system.monitor.models.Rule;
import com.system.monitor.models.User;
import com.system.monitor.services.EmailService;
import com.system.monitor.services.RuleService;
import com.system.monitor.services.ThresholdMonitorService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ThresholdMonitorServiceImpl implements ThresholdMonitorService{
	
	@Autowired
	RuleService ruleService;
	
	@Autowired
	EmailService emailService;
	
	
	@Override
	public void verifyRecord(ResourceRecord record) {
		List<Rule> monitorRules = ruleService.getAll();
		if(monitorRules.isEmpty()) {
			return;
		}else {
			for(Rule currentRule : monitorRules) {
				if(isRecordAboveThreshold(record, currentRule)) {
					Alert newAlert = createNewAlert(record, currentRule);
					notifyAlertRecipients(newAlert,currentRule.getAlertRecipients());
				}
			}
		}
	}
	
	private Alert createNewAlert(ResourceRecord record, Rule currentRule) {
		StringBuilder stringBuilder = new StringBuilder("The resource recourd has shown a value greater than the threshold");
		stringBuilder.append("\nPriority: ");
		stringBuilder.append(currentRule.getPriority());
		stringBuilder.append("\nResource: ");
		stringBuilder.append(record.getResourceName());
		stringBuilder.append("TimeStamp: ");
		stringBuilder.append(record.getTimestamp());
		stringBuilder.append("\nRecord ID: ");
		stringBuilder.append(record.getId());
		stringBuilder.append("\nReadings: ");
		for (Entry<String, Reading> reading : record.getReadings().entrySet()) {
			ResourceRecord resourceThreshold = currentRule.getResourceThreshold();
			stringBuilder.append("\n - Reading: ");
			stringBuilder.append(reading.getKey() + ": " + reading.getValue());
			stringBuilder.append("\n - Treshold: ");
			stringBuilder.append(reading.getKey() + ": "); 
			stringBuilder.append(resourceThreshold.getReadings().get(reading.getKey()));
		}
		String description = stringBuilder.toString();
		if(log.isDebugEnabled()) log.debug(description);
		return new Alert(record, currentRule, description);
	}

	private boolean isRecordAboveThreshold(ResourceRecord record, Rule rule){
		ResourceRecord threshold = rule.getResourceThreshold();
		
		for (Entry<String, Reading> entry : record.getReadings().entrySet()) {
			
			/*If the value form the record is greater than the value from the threshold,
			* return true
			*/
			if(Double.compare(entry.getValue().getValue(), 
					threshold.getReadings().get(entry.getKey()).getValue()) > 0 ){
				return true;
			}
		}
		return false;
	}

	@Override
	public void notifyAlertRecipients(Alert alert, Set<User> recipients) {
		StringBuilder stringBuilder = new StringBuilder("System Monitor Alert - ");
		stringBuilder.append(alert.getRecord().getResourceName() + " - ");
		stringBuilder.append(alert.getRule().getPriority() + " - ");
		
		for(User recipient : recipients) {
			emailService.sendSimpleMessage(recipient.getEmail(), stringBuilder.toString() , alert.getDescription());
		}

		
	}
	
	
}
