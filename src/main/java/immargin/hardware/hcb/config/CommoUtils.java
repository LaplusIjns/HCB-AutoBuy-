package immargin.hardware.hcb.config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CommoUtils {

    private CommoUtils() {
        
    }
    public static int getDayFromTwoDate(@NonNull Date date1,@NonNull Date date2) {
        long diff = date1.getTime() - date2.getTime();
        return Math.abs((int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }
}
