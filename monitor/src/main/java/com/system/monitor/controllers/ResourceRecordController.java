package com.system.monitor.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.monitor.models.ResourceRecord;
import com.system.monitor.services.ResourceRecordService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/records")
@Slf4j
public class ResourceRecordController {
public class UsuarioController {

	@Autowired
	private ResourceRecordService service;
	
	@GetMapping("/{id}")
	public ResourceRecord metodoGet(@PathVariable(name = "id") Long id) {
		log.info("Ingresando a metodoGet con id {}", id);
		return service.findById(id).orElseThrow(() -> { throw new RuntimeException("Usuario no existe"); });
	}
	
	@GetMapping
	public List<ResourceRecord> getAll() {
		return service.getAll();
	}
	
	
}
}
