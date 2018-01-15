package io.felipepoliveira.senaikeep.ws.rest.v1.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.felipepoliveira.senaikeep.ws.rest.AbstractRestController;

@RestController
@RequestMapping(value = "/rest/1.0/task")
@SuppressWarnings("rawtypes")
public class TaskController extends AbstractRestController {
	
	
	@PostMapping
	public ResponseEntity create() {
		return ResponseEntity.ok(null);
	}

}
