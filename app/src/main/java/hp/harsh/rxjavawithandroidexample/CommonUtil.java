package hp.harsh.rxjavawithandroidexample;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Harsh Patel on 12/10/2016.
 */

public class CommonUtil {

    public static String getTime() {
        return new SimpleDateFormat("kk:mm:ss a", Locale.getDefault()).format(new Date());
    }
}
