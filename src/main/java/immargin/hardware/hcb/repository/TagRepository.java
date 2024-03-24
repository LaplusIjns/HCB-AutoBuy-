package immargin.hardware.hcb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import immargin.hardware.hcb.DTO.MaintableDTO;
import immargin.hardware.hcb.DTO.TagnameDTO;
import immargin.hardware.hcb.model.TagRelationshipId;
import immargin.hardware.hcb.model.TagRelationshipId2;
import immargin.hardware.hcb.model.Tagprod;



@Repository
public interface TagRepository extends JpaRepository<Tagprod,TagRelationshipId2> {
//  AutoBuy
    @Query(value="SELECT t.fk_tag,y.tag_zhtw from tag_prod t INNER JOIN tag_compare y ON y.tag_id = t.fk_tag WHERE t.fk_prod_id = ?1",nativeQuery = true)
    List<TagnameDTO> findByProdName(String name);
    
    @Query(value ="SELECT m.prod_id,m.prodname FROM `tag_prod` t INNER JOIN maintable m ON t.fk_prod_id = m.prod_id WHERE t.fk_tag = ?1",
            countQuery = "SELECT count(*) FROM `tag_prod` t INNER JOIN maintable m ON t.fk_prod_id = m.prod_id WHERE t.fk_tag = ?1",
            nativeQuery = true )
    Page<MaintableDTO> findByTagName(String name,Pageable pageable);
    
    @Query(value="SELECT * FROM tag_compare WHERE tag_id = ?1" ,nativeQuery = true)
    Optional<TagnameDTO> findTagnameByfk_tag(String tag_id);

//  Sinya
    @Query(value="SELECT t.fk_tag,y.tag_zhtw from sinyatag_prod t INNER JOIN sinyatag_compare y ON y.tag_id = t.fk_tag WHERE t.fk_prod_id = ?1",nativeQuery = true)
    List<TagnameDTO> SinyafindByProdName(String name);
    
    @Query(value ="SELECT m.prod_id,m.prodname FROM `sinyatag_prod` t INNER JOIN sinyamaintable m ON t.fk_prod_id = m.prod_id WHERE t.fk_tag = ?1",
            countQuery = "SELECT count(*) FROM `sinyatag_prod` t INNER JOIN sinyamaintable m ON t.fk_prod_id = m.prod_id WHERE t.fk_tag = ?1",
            nativeQuery = true )
    Page<MaintableDTO> SinyafindByTagName(String name,Pageable pageable);
    
    @Query(value="SELECT * FROM sinyatag_compare WHERE tag_id = ?1" ,nativeQuery = true)
    Optional<TagnameDTO> SinyafindTagnameByfk_tag(String tag_id);
}
