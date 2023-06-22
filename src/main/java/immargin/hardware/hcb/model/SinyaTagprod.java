package immargin.hardware.hcb.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "sinyatag_prod" ,schema ="PUBLIC")
@IdClass(TagRelationshipId.class)
public class SinyaTagprod {
    
    @Id
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name="fk_prod_id")
    private Sinyamaintable fkProdId;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="fk_tag")
	@JsonBackReference
	SinyaTagcompare sinyaTagcompares;

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
