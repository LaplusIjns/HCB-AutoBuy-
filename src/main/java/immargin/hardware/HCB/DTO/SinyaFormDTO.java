package immargin.hardware.HCB.DTO;

import java.util.Date;
import java.util.List;

public record SinyaFormDTO(
        String prodname,
        String prodId,
        Integer lastprice,
        Date lastUpdateDate,
        List<TagDTO> tagnameDTO
        ) {

}
