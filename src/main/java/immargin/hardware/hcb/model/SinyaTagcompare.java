package immargin.hardware.hcb.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity

@Table(name = "sinyatag_compare" ,schema ="PUBLIC")
public class SinyaTagcompare {
	@Id
	@Column(name="tag_id", columnDefinition = "char")
	private String TagID;
	
	public String getTagID() {
		return TagID;
	}

	public void setTagID(String tagID) {
		TagID = tagID;
	}

	public String getTagzhtw() {
		return Tagzhtw;
	}

	public void setTagzhtw(String tagzhtw) {
		Tagzhtw = tagzhtw;
	}

	public java.util.Date getInitaldate() {
		return Initaldate;
	}

	public void setInitaldate(java.util.Date initaldate) {
		Initaldate = initaldate;
	}

	@Column(name="tag_zhtw", columnDefinition = "char")
	private String Tagzhtw;
	
	@Column(name="create_date")
	private java.util.Date Initaldate;
	

}
