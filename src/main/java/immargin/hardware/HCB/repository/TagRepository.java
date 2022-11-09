package immargin.hardware.HCB.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import immargin.hardware.HCB.DTO.MaintableDTO;
import immargin.hardware.HCB.DTO.TagnameDTO;
import immargin.hardware.HCB.model.TagRelationshipId;
import immargin.hardware.HCB.model.Tagprod;



@Repository
public interface TagRepository extends JpaRepository<Tagprod,TagRelationshipId> {

    @Query(value="SELECT t.fk_tag,y.tag_zhtw from tag_prod t INNER JOIN tag_compare y ON y.tag_id = t.fk_tag WHERE t.fk_prod_id = ?1",nativeQuery = true)
    List<TagnameDTO> findByProdName(String name);
    
    @Query(value ="SELECT m.prod_id,m.prodname FROM `tag_prod` t INNER JOIN maintable m ON t.fk_prod_id = m.prod_id WHERE t.fk_tag = ?1",
            countQuery = "SELECT count(*) FROM `tag_prod` t INNER JOIN maintable m ON t.fk_prod_id = m.prod_id WHERE t.fk_tag = ?1",
            nativeQuery = true )
    Page<MaintableDTO> findByTagName(String name,Pageable pageable);
    
    @Query(value="SELECT * FROM tag_compare WHERE tag_id = ?1" ,nativeQuery = true)
    Optional<TagnameDTO> findTagnameByfk_tag(String tag_id);
}
