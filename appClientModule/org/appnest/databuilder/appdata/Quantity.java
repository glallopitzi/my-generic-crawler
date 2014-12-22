package org.appnest.databuilder.appdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="QUANTITY")
public class Quantity extends BaseObject{
	
	@Column(name="quantity")
	private String quantity;

	public Quantity() {
		super();
	}
	
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getQuantity() {
		return quantity;
	}
}
