package immargin.hardware.HCB.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Component
@Entity
@Data
@Table(name = "maintable" ,schema ="PUBLIC")
public class Maintable {
	
	@Id
	@Column(name="prod_id", columnDefinition = "char")
	private String prod_id;
	

	@Column(name="inital_date")
	private java.util.Date inital_date;
	
	@Column(name="last_update_date")
	private java.util.Date last_update_date;
	
	@Column(name="prodavailable",columnDefinition = "BIT(1)")
	private Integer prodavailable;

	@Column(name="prodname", columnDefinition = "char")
	private String prodname;

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public java.util.Date getInital_date() {
		return inital_date;
	}

	public void setInital_date(java.util.Date inital_date) {
		this.inital_date = inital_date;
	}

	public java.util.Date getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(java.util.Date last_update_date) {
		this.last_update_date = last_update_date;
	}

	public Integer getProdavailable() {
		return prodavailable;
	}

	public void setProdavailable(Integer prodavailable) {
		this.prodavailable = prodavailable;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
}
