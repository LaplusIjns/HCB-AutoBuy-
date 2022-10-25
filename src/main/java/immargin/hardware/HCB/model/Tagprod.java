package immargin.hardware.HCB.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "tag_prod" ,schema ="PUBLIC")
@IdClass(TagRelationshipId.class)
public class Tagprod {
	@Id
	@Column(name="fk_prod_id", columnDefinition="char")
	private String fk_prod_id;
	@Id
	@Column(name="fk_tag", columnDefinition="char")
	private String fk_tag;
	
    public String getFk_prod_id() {
        return fk_prod_id;
    }
    public void setFk_prod_id(String fk_prod_id) {
        this.fk_prod_id = fk_prod_id;
    }
    public String getFk_tag() {
        return fk_tag;
    }
    public void setFk_tag(String fk_tag) {
        this.fk_tag = fk_tag;
    }
	

	
}
