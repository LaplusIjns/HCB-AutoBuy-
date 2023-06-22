package immargin.hardware.hcb.autobuy;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import immargin.hardware.hcb.DTO.MaintableDTO;
import immargin.hardware.hcb.model.Maintable;

@Repository
public interface MaintableRepository extends JpaRepository<Maintable,String>,JpaSpecificationExecutor<Maintable> {



	@Query(value="SELECT m.prodname,m.prod_id from maintable m where m.prod_id = ?1" ,nativeQuery = true)
	Optional<MaintableDTO> findMaintableDTOByProdId(String prodId);
	
	@Query(value="SELECT * FROM `maintable` m WHERE m.inital_date = SUBDATE(CURRENT_DATE,?1)" ,nativeQuery = true)
	List<MaintableDTO> dailyNew(Integer index);
	
}
