package immargin.hardware.hcb.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "tag_prod" ,schema ="PUBLIC")
@IdClass(TagRelationshipId2.class)
public class Tagprod {
	@Id
	@Column(name="fk_prod_id", columnDefinition="char")
	private String fkProdId;
	@Id
	@Column(name="fk_tag", columnDefinition="char")
	
	private String fkTag;
    public String getFkProdId() {
        return fkProdId;
    }
    public void setFkProdId(String fkProdId) {
        this.fkProdId = fkProdId;
    }
    public String getFkTag() {
        return fkTag;
    }
    public void setFkTag(String fkTag) {
        this.fkTag = fkTag;
    }
	
    
	

	
}
