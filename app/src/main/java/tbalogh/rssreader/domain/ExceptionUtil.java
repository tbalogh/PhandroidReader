package tbalogh.rssreader.domain;

/**
 * Created by tbalogh on 29/07/16.
 */
public final class ExceptionUtil {
    private ExceptionUtil() {}

    public static String illegalArgumentExceptionMessage(String className, String message) {
        return "Illegal argument at " + className + ". Message: " + message;
    }
}
