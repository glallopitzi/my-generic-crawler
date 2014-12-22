package org.appnest.databuilder.appdata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * L'entità di base estesa da tutti gli oggetti.
 * Determina concetti come id, stato (cancellazione logica), ultimo aggiornamento
 * @author lorenzo
 *
 */

@MappedSuperclass
public abstract class BaseObject {
	
	@Column(name="ID") 
	@Id 
	@GeneratedValue
	protected Long id;
	
	@Column(name="CREATED")
	protected Date created;
	
	@Column(name="LAST_UPDATED")
	protected Date lastUpdated;

	@Column(name="DELETED")
	protected Boolean deleted = Boolean.FALSE;
	
	@Column(name="DATE_DELETED")
	protected Date dateDeleted;
	
	@PrePersist
	public void prePersist() {
		created = new Date();
	}
	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getId() {
		return id;
	}

	@PreUpdate
	public void preUpdate() {
		lastUpdated = new Date();
	}
	
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getDateDeleted() {
		return dateDeleted;
	}

	public void setDateDeleted(Date dateDeleted) {
		this.dateDeleted = dateDeleted;
	}
	
}
