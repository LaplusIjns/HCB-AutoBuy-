package immargin.hardware.hcb.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Page;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.lang.NonNull;

import immargin.hardware.hcb.DTO.SinyaFormDTO;
import immargin.hardware.hcb.model.Maintable;

public class CommonUtils {
    
    private static final DateFormatter FORMATTER = new DateFormatter("yyyyMMdd");

    private CommonUtils() {
        
    }
    public static int getDayFromTwoDate(@NonNull Date date1,@NonNull Date date2) {
        long diff = date1.getTime() - date2.getTime();
        return Math.abs((int)TimeUnit.SECONDS.convert(diff, TimeUnit.SECONDS));
    }
    
    public static String formatDate(Date date) {
        return FORMATTER.print(date, null);
    }
    public static String nowDateString() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }
    
    public static String DateString(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, i);
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }
    
    public static Map<String, Object> parseListResult(Page<?> result, List<SinyaFormDTO> result3) {
        Map<String, Object> result2 = new HashMap<>();
        result2.put("produts", result3);
        result2.put("totalnumber", result.getTotalElements());
        result2.put("page", result.getNumber());
        result2.put("totalpage", result.getTotalPages());
        return result2;
    }
}
