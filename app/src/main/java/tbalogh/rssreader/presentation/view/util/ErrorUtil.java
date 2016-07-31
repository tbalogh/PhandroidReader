package tbalogh.rssreader.presentation.view.util;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by tbalogh on 31/07/16.
 */

public final class ErrorUtil {
    public static final String NO_MATCHING_CATEGORY           = "No matching filter";
    public static final String INTERNET_PROBLEM_ERROR_MESSAGE = "There was a problem with the internet connection.";
    public static final String SOMETHING_WENT_WRONG           = "Something went wrong";

    public static final String EXCEPTION_HTTP;
//    public static final String EXCEPTION_IO      = IOException.class.getName();
//    public static final String EXCEPTION_TIMEOUT = TimeoutException.class.getName();

    static {
        EXCEPTION_HTTP = HttpException.class.getName();
    }

    private ErrorUtil() {}
}