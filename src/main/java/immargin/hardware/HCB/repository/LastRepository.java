package immargin.hardware.HCB.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import immargin.hardware.HCB.DTO.LastDTO;
import immargin.hardware.HCB.model.Last0;


@Repository
public interface LastRepository extends JpaRepository<Last0,String> {

	@Query(value="SELECT * FROM last_?1 WHERE fk_prod_id=?2",nativeQuery = true)
	List<LastDTO> getProd(Integer index,String name);
	
}
