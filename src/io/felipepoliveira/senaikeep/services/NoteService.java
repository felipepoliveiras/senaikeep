package io.felipepoliveira.senaikeep.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import io.felipepoliveira.senaikeep.dao.NoteDAO;
import io.felipepoliveira.senaikeep.excp.EntityNotFoundException;
import io.felipepoliveira.senaikeep.excp.ForbiddenException;
import io.felipepoliveira.senaikeep.excp.ValidationException;
import io.felipepoliveira.senaikeep.models.NoteModel;
import io.felipepoliveira.senaikeep.models.TaskModel;
import io.felipepoliveira.senaikeep.models.UserModel;
import io.felipepoliveira.senaikeep.ws.rest.v1.utils.AuthClientProvider;

@Service
public class NoteService extends AbstractService{
	
	@Autowired
	private NoteDAO noteDAO;
	
	@Autowired
	private AuthClientProvider authClientProvider;
	
	public NoteModel create(NoteModel note, BindingResult bindingResult) throws ValidationException {
		this.validate(bindingResult);
		
		//Put the auth user as the owner of the note
		UserModel owner = new UserModel();
		owner.setId(authClientProvider.getAuthClient().getId());
		note.setOwner(owner);
		
		//Associate each task with the note
		for (TaskModel task: note.getTasks()) {
			task.setNote(note);
		}
		
		//Persist and return the note
		note.updateLastUpdateDate();
		noteDAO.persist(note);
		return note;
	}
	
	public void delete(Long noteId) throws EntityNotFoundException, ForbiddenException {
		NoteModel deletedNote = getFromAuthUser(noteId);
		noteDAO.delete(deletedNote.getId());
	}
	
	public NoteModel getFromAuthUser(Long noteId) throws EntityNotFoundException, ForbiddenException {
		NoteModel queriedNote = noteDAO.search(noteId);
		
		//Check if the note was not found
		if(queriedNote == null) {
			throw new EntityNotFoundException("The note[id="+noteId+"] does not exists");
		}
		
		//Check if the user owns the note
		if(!authClientProvider.validateResource(queriedNote.getOwner().getId())) {
			throw new ForbiddenException("The authenticated user does not have permission to manipulate this Note[id="+noteId+"]");
		}
		
		return queriedNote;
	}
	
	public List<NoteModel> listByOwner(){
		return noteDAO.listByOwner(authClientProvider.getAuthClient().getId());
	}
}
