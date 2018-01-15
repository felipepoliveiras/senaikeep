package io.felipepoliveira.senaikeep.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import io.felipepoliveira.senaikeep.dao.TaskDAO;
import io.felipepoliveira.senaikeep.models.TaskModel;

@Repository(value = "taskDAO")
@SuppressWarnings("unchecked")
public class TaskJPA extends AbstractJPA<TaskModel> implements TaskDAO {

	@Override
	public String getEntityName() {
		return "Task";
	}

	@Override
	public List<TaskModel> allFromNote(Long noteId) {
		return this.search("note.id", noteId);
	}

	@Override
	public TaskModel getFromNote(Long noteId, Long taskId) {
		String hql = "FROM Task t WHERE t.id = :taskId AND t.note.id = :noteId";
		Query query = session().createQuery(hql);
		
		query.setParameter("taskId", taskId);
		query.setParameter("noteId", noteId);
		
		List<TaskModel> result = query.list();
		
		if(result.size() > 0) {
			return result.get(0);
		}else {
			return null;
		}
	}

	@Override
	public TaskModel getFromOwner(Long ownerId, Long taskId) {
		String hql = "FROM Task t WHERE t.id = :taskId AND t.note.owner.id = :ownerId";
		Query query = session().createQuery(hql);
		
		query.setParameter("taskId", taskId);
		query.setParameter("ownerId", ownerId);
		
		List<TaskModel> result = query.list();
		
		if(result.size() > 0) {
			return result.get(0);
		}else {
			return null;
		}
	}

}
