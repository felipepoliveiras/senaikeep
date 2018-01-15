package io.felipepoliveira.senaikeep.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Note")
@Table(name = "note")
public class NoteModel {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(optional = false)
	@Valid
	@JsonIgnore
	private UserModel owner;

	@Column(length = 40, nullable = false, unique = false)
	@Size(min = 1, max = 40)
	@NotNull
	private String title;
	
	@Column(nullable = false, unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date();
	
	@Column(nullable = false, unique = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;
	
	@OneToMany(	mappedBy = "note", fetch = FetchType.EAGER, orphanRemoval = false, 
				cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@Valid
	@NotNull
	@Size(min = 1, message = "{Note.tasks.size}")
	private List<TaskModel> tasks;
	
	public void updateLastUpdateDate() {
		this.lastUpdateDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public List<TaskModel> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskModel> tasks) {
		this.tasks = tasks;
	}
	
	public UserModel getOwner() {
		return owner;
	}
	
	public void setOwner(UserModel owner) {
		this.owner = owner;
	}
}
