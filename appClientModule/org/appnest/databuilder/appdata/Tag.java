package org.appnest.databuilder.appdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="TAG")
public class Tag extends BaseObject {
	
	@Column(name="TAG")
	private String tag;
	
	public Tag(){
		super();
	}
	
	
	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}
}
