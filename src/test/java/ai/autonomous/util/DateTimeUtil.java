package ai.autonomous.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Son Vu
 */

public class DateTimeUtil {

    public static Integer DAY = 3;

    public static String getPickuptime() {
        return getDate(DAY) + "T09:00:00+0800";
    }

    public static String getCurrentDate() {
        return getDate(0);
    }

    public static String getDate(int index) {
        Calendar c = Calendar.getInstance();
        long millis = System.currentTimeMillis() + 24 * index * 3600 * 1000;
        c.setTime(new Date(millis));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY) {
            millis += 24 * 3600 * 1000;
        }
        Date d = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String formattedDate = sdf.format(d);
        return formattedDate;
    }

    public static String printCurrentTime() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static int getUnixTimeStamp(){
        return (int) (System.currentTimeMillis() / 1000L);
    }

}
