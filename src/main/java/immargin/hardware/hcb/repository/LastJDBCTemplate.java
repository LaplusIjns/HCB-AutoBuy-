package immargin.hardware.hcb.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import immargin.hardware.hcb.DTO.DailyDTO2;
import immargin.hardware.hcb.DTO.LastDTO2;

@Component
public class LastJDBCTemplate {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private static final String GET_PROD= "SELECT * FROM last_0 WHERE fk_prod_id= :bbb ORDER BY upload_date ASC";
    private static final String GET_DAILY= """
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_0` f,`last_0` s ) INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_1` f,`last_1` s ) INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_2` f,`last_2` s ) INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_3` f,`last_3` s ) INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_4` f,`last_4` s ) INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_5` f,`last_5` s ) INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_6` f,`last_6` s ) INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_7` f,`last_7` s ) INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_8` f,`last_8` s ) INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`last_9` f,`last_9` s ) INNER JOIN `maintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id 
            """;
    private static final String GET_SINYA_DAILY= """
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_0` f,`slast_0` s ) INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_1` f,`slast_1` s ) INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_2` f,`slast_2` s ) INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_3` f,`slast_3` s ) INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_4` f,`slast_4` s ) INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_5` f,`slast_5` s ) INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_6` f,`slast_6` s ) INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_7` f,`slast_7` s ) INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_8` f,`slast_8` s ) INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id  UNION
            SELECT m.prodname, f.fk_prod_id,f.upload_date,f.price,f.price-s.price AS diff FROM (`slast_9` f,`slast_9` s ) INNER JOIN `sinyamaintable` m ON m.prod_id = f.fk_prod_id
            WHERE f.upload_date = SUBDATE(CURRENT_DATE, :?1) AND s.upload_date = SUBDATE(CURRENT_DATE, :?2) AND f.fk_prod_id = s.fk_prod_id 
            """;
    private static final String GET_SINYA_PROD= "SELECT * FROM slast_?1 WHERE fk_prod_id= :?2 ORDER BY upload_date ASC";
    
    public List<LastDTO2> getProd(String index,String name){
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("bbb", name);
        String sql = GET_PROD.replace("last_0", index);
        List<LastDTO2> x = namedParameterJdbcTemplate.query(sql, sqlParameterSource,new BeanPropertyRowMapper<>(LastDTO2.class));
        return x;
    }
    
    public List<LastDTO2> getSinyaProd(String index,String name){
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("?2", name);
        String sql = GET_SINYA_PROD.replace("slast_?1", index);
        List<LastDTO2> x = namedParameterJdbcTemplate.query(sql, sqlParameterSource,new BeanPropertyRowMapper<>(LastDTO2.class));
        return x;
    }
    
    public List<DailyDTO2> getDaily(Integer index,Integer index2){
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("?1", index).addValue("?2", index2);
        List<DailyDTO2> x = namedParameterJdbcTemplate.query(GET_DAILY, sqlParameterSource,new BeanPropertyRowMapper<>(DailyDTO2.class)).parallelStream().filter(t -> !t.getDiff().equals(0) ).toList();
        return x;
    }
    
    public List<DailyDTO2> getSinyaDaily(Integer index,Integer index2){
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("?1", index).addValue("?2", index2);
        List<DailyDTO2> x = namedParameterJdbcTemplate.query(GET_SINYA_DAILY, sqlParameterSource,new BeanPropertyRowMapper<>(DailyDTO2.class)).parallelStream().filter(t -> !t.getDiff().equals(0) ).toList();
        return x;
    }
}
