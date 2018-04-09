package Step1;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

public class CalculateTimeDifference {

    public static long GetTimeElapsed(long startTime, long currentTime ){
        SimpleDateFormat dateFormat = new SimpleDateFormat("SSS");
        dateFormat.setTimeZone(SimpleTimeZone.getTimeZone("GMT"));
        long elapsed = currentTime - startTime;
        return elapsed/1000;
    }

}
