package mw.offers.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Lightweight logging implementation.
 */
public class Log
{
    /**
     * Attribute to store logging state.
     */
    private final static boolean LOGGING = false;

    /**
     * Logs a debug message.
     * @param msg The message to log.
     */
    public static void debug(Object msg)
    {
        if (LOGGING)
        {
            System.out.println(getTimestamp() + " [DEBUG] : " + msg);
        }
    }

    /**
     * Logs a warning message.
     * @param msg The message to log.
     */
    public static void warning(Object msg)
    {
        if (LOGGING)
        {
            System.err.println(getTimestamp() + " [WARNING] : " + msg);
        }
    }

    /**
     * Logs a debug message.
     * @param msg The message to log.
     */
    public static void error(Object msg)
    {
        if (LOGGING)
        {
            System.err.println(getTimestamp() + " [ERROR] : " + msg);
        }
    }

    /**
     * Retrieves the current system time in the format dd.MM.yyyy HH:mm
     * @return The timestamp.
     */
    private static String getTimestamp()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }
}
