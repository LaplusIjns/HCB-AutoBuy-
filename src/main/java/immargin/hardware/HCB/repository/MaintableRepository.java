package immargin.hardware.HCB.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import immargin.hardware.HCB.DTO.MaintableDTO;
import immargin.hardware.HCB.model.Maintable;

@Repository
public interface MaintableRepository extends JpaRepository<Maintable,String> {

	
	@Query(value="SELECT m.prodname,m.prod_id from maintable m where m.prodname LIKE CONCAT('%',?1,'%')",countQuery ="SELECT count(*) from maintable AS m where m.prodname LIKE CONCAT('%',?1,'%')" ,nativeQuery = true)
	Page<MaintableDTO> findByName(String name,Pageable pageable);
	
	@Query(value="SELECT prodname,prod_id from maintable WHERE prodname like concat('%',?1,'%') AND prodname like concat('%',?2,'%')",countQuery="SELECT count(*) from maintable WHERE prodname like concat('%',?1,'%') AND prodname like concat('%',?2,'%')",nativeQuery = true)
	Page<MaintableDTO> findByName2(String name,String name2, Pageable pageable);
	
	@Query(value="SELECT m.prodname,m.prod_id from maintable m WHERE m.prodname like concat('%',?1,'%') AND prodname like concat('%',?2,'%') AND prodname like concat('%',?3,'%')",countQuery="SELECT count(*) from maintable AS m WHERE m.prodname like concat('%',?1,'%') AND prodname like concat('%',?2,'%') AND prodname like concat('%',?3,'%')",nativeQuery = true)
	Page<MaintableDTO> findByName3(String name,String name2,String name3, Pageable pageable);
	
	@Query(value="SELECT m.prodname,m.prod_id from maintable AS m WHERE m.prodname like concat('%',?1,'%') AND m.prodname like concat('%',?2,'%') AND m.prodname like concat('%',?3,'%') AND m.prodname like concat('%',?4,'%')",countQuery="SELECT count(*) from maintable AS m WHERE m.prodname like concat('%',?1,'%') AND m.prodname like concat('%',?2,'%') AND m.prodname like concat('%',?3,'%') AND m.prodname like concat('%',?4,'%')",nativeQuery = true)
	Page<MaintableDTO> findByName4(String name,String name2,String name3,String name4, Pageable pageable);
	
	@Query(value="SELECT m.prodname,m.prod_id from maintable AS m WHERE m.prodname like concat('%',?1,'%') AND m.prodname like concat('%',?2,'%') AND m.prodname like concat('%',?3,'%') AND m.prodname like concat('%',?4,'%') AND m.prodname like concat('%',?5,'%')",countQuery="SELECT count(*) from maintable AS m WHERE m.prodname like concat('%',?1,'%') AND m.prodname like concat('%',?2,'%') AND m.prodname like concat('%',?3,'%') AND m.prodname like concat('%',?4,'%') AND m.prodname like concat('%',?5,'%')",nativeQuery = true)
	Page<MaintableDTO> findByName5(String name,String name2,String name3,String name4,String name5, Pageable pageable);
	
	@Query(value="SELECT m.prodname,m.prod_id from maintable AS m WHERE m.prodname like concat('%',?1,'%') AND m.prodname like concat('%',?2,'%') AND m.prodname like concat('%',?3,'%') AND m.prodname like concat('%',?4,'%') AND m.prodname like concat('%',?5,'%') AND m.prodname like concat('%',?6,'%')",countQuery="SELECT count(*) from maintable AS m WHERE m.prodname like concat('%',?1,'%') AND m.prodname like concat('%',?2,'%') AND m.prodname like concat('%',?3,'%') AND m.prodname like concat('%',?4,'%') AND m.prodname like concat('%',?5,'%') AND m.prodname like concat('%',?6,'%')",nativeQuery = true)
    Page<MaintableDTO> findByName6(String name,String name2,String name3,String name4,String name5,String name6, Pageable pageable);

	@Query(value="SELECT m.prodname,m.prod_id from maintable m where m.prod_id = ?1" ,nativeQuery = true)
	Optional<MaintableDTO> findMaintableDTOByProd_id(String Prod_id);

	
	
}
