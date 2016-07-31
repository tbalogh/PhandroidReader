package tbalogh.rssreader.presentation.view.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.Locale;

/**
 * Created by tbalogh on 30/07/16.
 */

public enum DateUtil {
    INSTANCE;

    private static final String UNKNOWN_DATE = "-";
    private static final String SEPARATOR    = ":";
    private static final String SEC_FORMAT   = " sec ago";
    private static final String MIN_FORMAT   = " min ago";
    private static final String HOUR_FORMAT  = " hour ago";
    private static final String DAY_FORMAT   = " day ago";
    private static final String WEEK_FORMAT  = " week ago";
    private static final String MONTH_FORMAT = " month ago";
    private static final String YEAR_FORMAT  = " year ago";

    private static final String PHANDROID_DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";

    private final PeriodFormatter   periodFormatter;
    private final DateTimeFormatter dateTimeFormatter;

    DateUtil() {
        this.periodFormatter = new PeriodFormatterBuilder()
                .appendSeconds().appendSuffix(SEC_FORMAT + SEPARATOR)
                .appendMinutes().appendSuffix(MIN_FORMAT + SEPARATOR)
                .appendHours().appendSuffix(HOUR_FORMAT + SEPARATOR)
                .appendDays().appendSuffix(DAY_FORMAT + SEPARATOR)
                .appendWeeks().appendSuffix(WEEK_FORMAT + SEPARATOR)
                .appendMonths().appendSuffix(MONTH_FORMAT + SEPARATOR)
                .appendYears().appendSuffix(YEAR_FORMAT + SEPARATOR)
                .printZeroNever()
                .toFormatter();


        this.dateTimeFormatter = DateTimeFormat.forPattern(PHANDROID_DATE_TIME_FORMAT).withLocale(
                Locale.ENGLISH);
        ;
    }

    public String format(String date) {
        try {
            DateTime dateTime = dateTimeFormatter.parseDateTime(date);
            Period period = new Period(dateTime, DateTime.now());
            String defaultFormat = periodFormatter.print(period);
            String[] parts = defaultFormat.split(SEPARATOR);
            return parts[parts.length - 1];
        } catch (Exception ex) {
            return UNKNOWN_DATE;
        }
    }

}
