package immargin.hardware.hcb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import immargin.hardware.hcb.DTO.DailyDTO;
import immargin.hardware.hcb.DTO.LastDTO;
import immargin.hardware.hcb.model.Last0;


@Repository
public interface LastRepository extends JpaRepository<Last0,String> {

//    AutoBuy
	@Query(value="SELECT * FROM last_?1 WHERE fk_prod_id=?2",nativeQuery = true)
	List<LastDTO> getProd(Integer index,String name);
	
	@Query(value="SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_0` f,`last_0` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	        + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_1` f,`last_1` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	        + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_2` f,`last_2` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	        + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_3` f,`last_3` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	        + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_4` f,`last_4` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	        + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_5` f,`last_5` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	        + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_6` f,`last_6` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	        + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_7` f,`last_7` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	        + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_8` f,`last_8` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	        + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_9` f,`last_9` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	        + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_0` f,`last_0` s )\r\n"
	        + "INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id\r\n"
	        + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	        + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price"
	        ,nativeQuery = true)
    List<DailyDTO> getDaily(Integer index,Integer index2);
	
	
//	Sinya
	@Query(value="SELECT * FROM slast_?1 WHERE fk_prod_id=?2",nativeQuery = true)
	List<LastDTO> getSinyaProd(Integer index,String name);
	
	   @Query(value="SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_0` f,`slast_0` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	            + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_1` f,`slast_1` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	            + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_2` f,`slast_2` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	            + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_3` f,`slast_3` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	            + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_4` f,`slast_4` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	            + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_5` f,`slast_5` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	            + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_6` f,`slast_6` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	            + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_7` f,`slast_7` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	            + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_8` f,`slast_8` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	            + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_9` f,`slast_9` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price UNION\r\n"
	            + "SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_0` f,`slast_0` s )\r\n"
	            + "INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id\r\n"
	            + "WHERE f.upload_date = SUBDATE(CURRENT_DATE, ?1) AND s.upload_date = SUBDATE(CURRENT_DATE, ?2) \r\n"
	            + "AND f.fk_prod_id = s.fk_prod_id AND f.price!=s.price"
	            ,nativeQuery = true)
	    List<DailyDTO> getSinyaDaily(Integer index,Integer index2);
	
}
