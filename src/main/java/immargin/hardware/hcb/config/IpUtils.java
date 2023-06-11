package immargin.hardware.hcb.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;

public class IpUtils {
    
    private static final String  UNKOWN = "unknown";
    private static final String  LOCAL_IP = "127.0.0.1";
    private static final byte[] NULL_ARRAY = {} ;
    
    private IpUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return UNKOWN;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || UNKOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
 
        if (ip == null || ip.length() == 0 || UNKOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? LOCAL_IP : ip;
    }
 
    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || LOCAL_IP.equals(ip);
    }

    private static boolean internalIp(byte[] addr) {
        boolean result = true;
        if (isNull(addr) || addr.length < 2) {
            return result;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        if(  (b0==SECTION_2 && (b1 >= SECTION_3 && b1 <= SECTION_4))  || (b0==SECTION_5 && b1==SECTION_6) || (b0==SECTION_1) ) {
            return result;
        }else {
            return !result;
        }
    }
 
    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    public static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return NULL_ARRAY;
        }
        String[] elements = text.split("\\.", -1);
        try {
            if(elements.length==1) {
                return switchCondition(elements) ;
            }
            if(elements.length==2) {
                return switchCondition2(elements) ;
            }
            if(elements.length==3) {
                
                return switchCondition3(elements) ;
            }
            if(elements.length==4) {
                
                return  switchCondition4(elements) ;
            }
                return NULL_ARRAY;
            
        } catch (NumberFormatException e) {
            return NULL_ARRAY;
        }
    }

    private static byte[] switchCondition(String[] elements) {
        byte[] bytes = new byte[4];
        long l = Long.parseLong(elements[0]);
        if ((l < 0L) || (l > 4294967295L)) {
            return NULL_ARRAY;
        }
        bytes[0] = (byte) (int) (l >> 24 & 0xFF);
        bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
        bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
        bytes[3] = (byte) (int) (l & 0xFF);
        return bytes;
    }
    
    private static byte[] switchCondition2(String[] elements) {
        long l = Integer.parseInt(elements[0]);
        byte[] bytes = new byte[4];
        if ((l < 0L) || (l > 255L)) {
            return NULL_ARRAY;
        }
        bytes[0] = (byte) (int) (l & 0xFF);
        l = Integer.parseInt(elements[1]);
        if ((l < 0L) || (l > 16777215L)) {
            return NULL_ARRAY;
        }
        bytes[1] = (byte) (int) (l >> 16 & 0xFF);
        bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
        bytes[3] = (byte) (int) (l & 0xFF);
        return bytes;
    }
    
    private static byte[] switchCondition3(String[] elements) {
        long l ;
        byte[] bytes = new byte[4];
        for (int i = 0; i < 2; ++i) {
            l = Integer.parseInt(elements[i]);
            if ((l < 0L) || (l > 255L)) {
                return NULL_ARRAY;
            }
            bytes[i] = (byte) (int) (l & 0xFF);
        }
        l = Integer.parseInt(elements[2]);
        if ((l < 0L) || (l > 65535L)) {
            return NULL_ARRAY;
        }
        bytes[2] = (byte) (int) (l >> 8 & 0xFF);
        bytes[3] = (byte) (int) (l & 0xFF);
        return bytes;
    }
    
    private static byte[] switchCondition4(String[] elements) {
        long l ;
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; ++i) {
            l = Integer.parseInt(elements[i]);
            if ((l < 0L) || (l > 255L)) {
                return NULL_ARRAY;
            }
            bytes[i] = (byte) (int) (l & 0xFF);
        }
        return bytes;
    }
    
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return LOCAL_IP;
        }
    }
 
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (java.net.UnknownHostException e) {
            return "未知";
        }
    }
 
    public static boolean isNull(Object object) {
        return object == null;
    }
}
