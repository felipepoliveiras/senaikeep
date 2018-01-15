package io.felipepoliveira.senaikeep.dao.jpa;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import io.felipepoliveira.senaikeep.dao.NoteDAO;
import io.felipepoliveira.senaikeep.models.NoteModel;

@Repository(value = "noteJPA")
@SuppressWarnings("unchecked")
public class NoteJPA extends AbstractJPA<NoteModel> implements NoteDAO{

	@Override
	public String getEntityName() {
		return "Note";
	}

	@Override
	public List<NoteModel> listByOwner(Long ownerId) {
		return this.search("owner.id", ownerId);
	}

	@Override
	public NoteModel searchByOwner(Long noteId, Long ownerId) {
		String hql = "FROM Note n WHERE n.id = :noteId AND n.owner.id = :ownerId";
		Query query = session().createQuery(hql);
		
		query.setParameter("noteId", noteId);
		query.setParameter("ownerId", ownerId);
		
		List<NoteModel> result = query.list();
		if(result.size() > 0) {
			return result.get(0);
		}else {
			return null;
		}
	}

}
