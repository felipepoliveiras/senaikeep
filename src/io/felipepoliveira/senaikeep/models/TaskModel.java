package io.felipepoliveira.senaikeep.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Task")
@Table(name = "task")
public class TaskModel {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "note_id")
	@JsonIgnore
	private NoteModel note;
	
	@Lob
	@Column(nullable = false, unique = false)
	@NotNull
	@Size(min = 1)
	private String description;
	
	@Column(nullable = false)
	private Boolean completed = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNote(NoteModel note) {
		this.note = note;
	}
	
	public NoteModel getNote() {
		return note;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}	
}
