package immargin.hardware.HCB.sinya;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import immargin.hardware.HCB.model.TagRelationshipId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "sinyatag_prod" ,schema ="PUBLIC")
@IdClass(TagRelationshipId.class)
public class SinyaTagprod {
//	@Id
//	@Column(name="fk_prod_id", columnDefinition="char")
//	@JoinColumn(name="fk_tag")
//	private String fkProdId;
    
    @Id
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name="fk_prod_id")
    private Sinyamaintable fkProdId;
    
//	@Id
//	@Column(name="fk_tag", columnDefinition="char")
//	private String fkTag;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="fk_tag")
	@JsonBackReference
	SinyaTagcompare sinyaTagcompares;
	
	
//    public String getFkProdId() {
//        return fkProdId;
//    }
//    public void setFkProdId(String fkProdId) {
//        this.fkProdId = fkProdId;
//    }
//    public String getFkTag() {
//        return fkTag;
//    }
//    public void setFkTag(String fkTag) {
//        this.fkTag = fkTag;
//    }
    public SinyaTagcompare getSinyaTagcompares() {
        return sinyaTagcompares;
    }
    public void setSinyaTagcompares(SinyaTagcompare sinyaTagcompares) {
        this.sinyaTagcompares = sinyaTagcompares;
    }
    public Sinyamaintable getFkProdId() {
        return fkProdId;
    }
    public void setFkProdId(Sinyamaintable fkProdId) {
        this.fkProdId = fkProdId;
    }
    
    

    
}
