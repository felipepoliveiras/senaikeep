package io.felipepoliveira.senaikeep.dao;

import java.util.List;

import io.felipepoliveira.senaikeep.models.TaskModel;

public interface TaskDAO extends DAO<TaskModel> {
	public List<TaskModel> allFromNote(Long noteId);
	
	public TaskModel getFromNote(Long noteId, Long taskId);
	
	public TaskModel getFromOwner(Long ownerId, Long taskId);
}
