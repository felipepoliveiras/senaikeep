package io.felipepoliveira.senaikeep.ws.rest.v1.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.felipepoliveira.senaikeep.excp.EntityNotFoundException;
import io.felipepoliveira.senaikeep.excp.ForbiddenException;
import io.felipepoliveira.senaikeep.excp.ValidationException;
import io.felipepoliveira.senaikeep.models.TaskModel;
import io.felipepoliveira.senaikeep.services.TaskService;
import io.felipepoliveira.senaikeep.ws.rest.AbstractRestController;

@RestController
@RequestMapping(value = "/rest/1.0/tasks")
@SuppressWarnings("rawtypes")
public class TaskController extends AbstractRestController {
	
	@Autowired
	private TaskService taskService;
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity delete(@PathVariable(name = "id") Long taskId) {
		try {
			taskService.delete(taskId);
			
			return ResponseEntity
					.noContent()
					.build();
			
		} catch (EntityNotFoundException e) {
			
			return ResponseEntity
					.notFound()
					.header("X-Status-Reason", e.getMessage())
					.build();
			
		} catch (ForbiddenException e) {
			
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.header("X-Status-Reason", e.getMessage())
					.build();
			
		} catch(Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity update(	@PathVariable(name = "id") Long taskId,
									@RequestBody @Valid TaskModel task, BindingResult bindingResult) {
		
		try {
			taskService.update(taskId, task, bindingResult);
			
			return ResponseEntity
					.noContent()
					.build();
		} catch (EntityNotFoundException e) {
			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.header("X-Status-Reason", e.getMessage())
					.build();
			
			
		} catch (ForbiddenException e) {
			
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.header("X-Status-Reason", e.getMessage())
					.build();
			
		} catch (ValidationException e) {
			
			return ResponseEntity
					.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(this.bindingResultAsMap(bindingResult));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
}
