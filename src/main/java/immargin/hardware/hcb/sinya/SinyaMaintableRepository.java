package immargin.hardware.hcb.sinya;


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
import immargin.hardware.hcb.model.Sinyamaintable;

@Repository
public interface SinyaMaintableRepository extends JpaRepository<Sinyamaintable,String>,JpaSpecificationExecutor<Sinyamaintable> {


    @Query(value="SELECT m.prodname,m.prod_id from sinyamaintable m where m.prod_id = ?1" ,nativeQuery = true)
    Optional<MaintableDTO> SinyafindMaintableDTOByProd_id(String Prod_id);
	
    @Query(value="SELECT * FROM `sinyamaintable` m WHERE m.inital_date = SUBDATE(CURRENT_DATE,?1)" ,nativeQuery = true)
    List<MaintableDTO> SinyaDailyNew(Integer index);
	
}
