package immargin.hardware.hcb.model;



import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "last_3")
public class Last3 {
	@Id
	@Column(name = "fk_prod_id", columnDefinition = "char")
	private String FKProdID;
	
	@Column(name="price")
	private Integer price;
	
	@Column(name="upload_date")
	private java.util.Date Uploaddate;

}

//@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
//@Column(name = "member_id")
//private Integer member_id;