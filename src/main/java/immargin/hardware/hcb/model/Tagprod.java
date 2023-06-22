package immargin.hardware.hcb.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "tag_prod" ,schema ="PUBLIC")
@IdClass(TagRelationshipId2.class)
public class Tagprod {
    
    @Id
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name="fk_prod_id")
    private Maintable fkProdId;
    
    @Id
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="fk_tag")
    @JsonBackReference
    Tagcompare tagcompares;

    public Maintable getFkProdId() {
        return fkProdId;
    }

    public void setFkProdId(Maintable fkProdId) {
        this.fkProdId = fkProdId;
    }

    public Tagcompare getTagcompares() {
        return tagcompares;
    }

    public void setTagcompares(Tagcompare tagcompares) {
        this.tagcompares = tagcompares;
    }

	
}
