package ahmed.com.mvp_rx_viewmodel.utils;

import android.text.format.DateUtils;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

public class DateTimeUtils
{
    public static String formatRelativeTime(long time)
    {
        return DateUtils.getRelativeTimeSpanString(time * 1000, System.currentTimeMillis(),
                android.text.format.DateUtils.MINUTE_IN_MILLIS).toString();
    }
}
