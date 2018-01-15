package io.felipepoliveira.senaikeep.ws.rest.v1.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.felipepoliveira.senaikeep.excp.EntityNotFoundException;
import io.felipepoliveira.senaikeep.excp.ForbiddenException;
import io.felipepoliveira.senaikeep.excp.ValidationException;
import io.felipepoliveira.senaikeep.models.NoteModel;
import io.felipepoliveira.senaikeep.models.TaskModel;
import io.felipepoliveira.senaikeep.services.NoteService;
import io.felipepoliveira.senaikeep.services.TaskService;
import io.felipepoliveira.senaikeep.ws.rest.AbstractRestController;

@RestController
@RequestMapping(value = "/rest/1.0/notes")
@SuppressWarnings("rawtypes")
public class NoteController extends AbstractRestController{
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping
	public ResponseEntity create(@RequestBody @Valid NoteModel note, BindingResult bindingResult) {
		try {
			noteService.create(note, bindingResult);
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(note);
		} catch (ValidationException e) {
			
			return ResponseEntity
					.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(this.bindingResultAsMap(bindingResult));
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping(value = "/{id}/tasks")
	public ResponseEntity createTask(	@PathVariable(name = "id") Long noteId,
										@RequestBody @Valid TaskModel task, 
										BindingResult bindingResult) {
		
		try {
			TaskModel createdTask = taskService.create(noteId, task, bindingResult);
			
			return ResponseEntity
					.created(this.uri("/rest/1.0/notes/" + noteId + "/tasks/" + createdTask.getId()))
					.build();
			
		} catch (ValidationException e) {
			return ResponseEntity
					.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(this.bindingResultAsMap(bindingResult));
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.header("X-Status-Reason", e.getMessage())
					.build();
			
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity delete(@PathVariable(name = "id") Long noteId) {
		try 
		{
			noteService.delete(noteId);
			
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
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	
	@GetMapping(value = "{id}") 
	public ResponseEntity get(@PathVariable(name = "id") Long id) {
		try {
			NoteModel queriedNote = noteService.getFromAuthUser(id);
			
			return ResponseEntity.ok(queriedNote);
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
			
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
	
	@GetMapping
	public ResponseEntity list() {
		try {
			List<NoteModel> notes = noteService.listByOwner();
			
			return ResponseEntity.ok(notes);
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping(value = "/{id}/tasks")
	public ResponseEntity listTasks(@PathVariable(name = "id") Long noteId) {
		try 
		{
			return ResponseEntity
					.ok(taskService.allFromNote(noteId));
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
		}
		catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}
}
