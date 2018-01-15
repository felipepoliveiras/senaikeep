package io.felipepoliveira.senaikeep.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import io.felipepoliveira.senaikeep.dao.NoteDAO;
import io.felipepoliveira.senaikeep.dao.TaskDAO;
import io.felipepoliveira.senaikeep.excp.EntityNotFoundException;
import io.felipepoliveira.senaikeep.excp.ForbiddenException;
import io.felipepoliveira.senaikeep.excp.ValidationException;
import io.felipepoliveira.senaikeep.models.NoteModel;
import io.felipepoliveira.senaikeep.models.TaskModel;
import io.felipepoliveira.senaikeep.ws.rest.v1.utils.AuthClientProvider;

@Service
public class TaskService extends AbstractService{
	
	@Autowired
	private AuthClientProvider authClientProvider;
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private NoteDAO noteDAO;
	
	@Autowired
	private TaskDAO taskDAO;
	
	public List<TaskModel> allFromNote(Long noteId) throws EntityNotFoundException, ForbiddenException{
		//Query the note
		NoteModel queriedNote = noteService.getFromAuthUser(noteId);
		
		//Return each task from the given note
		return taskDAO.allFromNote(queriedNote.getId());
	}
	
	public TaskModel create(Long noteId, TaskModel task, BindingResult bindingResult) throws ValidationException, EntityNotFoundException, ForbiddenException {
		this.validate(bindingResult);
		
		//Query the request note
		NoteModel queriedNote = noteService.getFromAuthUser(noteId);
		
		//Associate the task with the note and persist it
		task.setNote(queriedNote);
		taskDAO.persist(task);
		
		return task;
	}
	
	public void delete(Long taskId) throws EntityNotFoundException, ForbiddenException {
		//Query the task
		TaskModel deletedTask = this.getFromAuthUser(taskId);
		
		taskDAO.delete(deletedTask.getId());
	}
	
	public TaskModel getFromAuthUser(Long taskId) throws EntityNotFoundException, ForbiddenException {
		//Query the task
		TaskModel queriedTask = this.taskDAO.search(taskId);
		
		//Verify if the task exists
		if(queriedTask == null) {
			throw new EntityNotFoundException("The Task [id="+taskId+"] does not exists");
		}
		
		//Check if the owner of the task is the same as the authenticated client
		if(!authClientProvider.validateResource(queriedTask.getNote().getOwner().getId())) {
			throw new ForbiddenException("The client does not have permission to update the Task[id="+taskId+"]");
		}
		
		return queriedTask;
	}
	
	public TaskModel update(Long taskId, TaskModel task, BindingResult bindingResult) throws EntityNotFoundException, ForbiddenException, ValidationException {
		this.validate(bindingResult);
		
		//Get the task
		TaskModel updatedTask = getFromAuthUser(taskId);
		
		//Copy the properties from the source task to the updated task
		BeanUtils.copyProperties(task, updatedTask, "id", "note");
		taskDAO.update(updatedTask);
		
		return updatedTask;
	}
}
