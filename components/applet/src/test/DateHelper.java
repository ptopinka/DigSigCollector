package test;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DateHelper {
    
    public static String label = null;
        
    public String getDate() {
        return label + " " + new SimpleDateFormat().format(new Date());
    }

}
