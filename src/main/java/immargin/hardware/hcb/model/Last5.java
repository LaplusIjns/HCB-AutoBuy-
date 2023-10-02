package immargin.hardware.hcb.model;



import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "last_5")
public class Last5 {
	@Id
	@Column(name = "fk_prod_id", columnDefinition = "char")
	private String FKProdID;
	
	@Column(name="price")
	private Integer price;
	
	@Column(name="upload_date",columnDefinition = "DATE")
	private java.util.Date Uploaddate;

}

//@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
//@Column(name = "member_id")
//private Integer member_id;