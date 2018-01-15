package io.felipepoliveira.senaikeep.dao;

import java.util.List;

import io.felipepoliveira.senaikeep.models.NoteModel;

public interface NoteDAO extends DAO<NoteModel> {

	public List<NoteModel> listByOwner(Long ownerId);
	
	public NoteModel searchByOwner(Long noteId, Long ownerId);
}
