package immargin.hardware.HCB.autobuy;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Component
@Entity
@Data
@Table(name = "maintable" ,schema ="PUBLIC")
public class Maintable {
	
	@Id
	@Column(name="prod_id", columnDefinition = "char")
	private String prodId;
	
	@Column(name="inital_date")
	private java.util.Date initalDate;
	
	@Column(name="last_update_date")
	private java.util.Date lastUpdateDate;
	
	@Column(name="prodavailable",columnDefinition = "BIT(1)")
	private Integer prodavailable;

	@Column(name="prodname", columnDefinition = "char")
	private String prodname;
	
	@Column(name="lastprice", columnDefinition = "varchar")
    private Integer lastprice;
	
	@Transient
	private String page;

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	
    

    public java.util.Date getInitalDate() {
        return initalDate;
    }

    public void setInitalDate(java.util.Date initalDate) {
        this.initalDate = initalDate;
    }

    public java.util.Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(java.util.Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getLastprice() {
        return lastprice;
    }

    public void setLastprice(Integer lastprice) {
        this.lastprice = lastprice;
    }
    
}
