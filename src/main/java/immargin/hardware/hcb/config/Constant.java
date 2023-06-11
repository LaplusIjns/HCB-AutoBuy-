package immargin.hardware.hcb.config;


public class Constant {
    
    private Constant(){
        throw new IllegalStateException("Utility class");
    }
    
    public static final String DOT = ",";
    public static final int TIME_INTERVAL = 2;
    public static final int BAN_COUNT = 3;
    public static final String URL_METHOD_GET = "GET";
    public static final String ICON_PATH = "/".concat("favicon.ico");
    public static final String ERROR_PATH = "/".concat("error");
    public static final String PERCENT = "%";
}
