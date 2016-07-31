package tbalogh.rssreader.presentation.util;

import android.test.AndroidTestCase;

import tbalogh.rssreader.R;
import tbalogh.rssreader.presentation.view.util.LocalizedStringProvider;

/**
 * Created by tbalogh on 31/07/16.
 */

public class LocalizedStringProviderTest extends AndroidTestCase {

    LocalizedStringProvider localizedStringProvider;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        localizedStringProvider = new LocalizedStringProvider(getContext());
    }

    public void testInternetConnectionError() {
        String expectedMessage = getContext().getString(R.string.internet_connection_error);
        String actualMessage = localizedStringProvider.internetConnectionError();

        assertEquals(expectedMessage, actualMessage);
    }

    public void testRequestTimeout() {
        String expectedMessage = getContext().getString(R.string.requestTimeout);
        String actualMessage = localizedStringProvider.requestTimeout();

        assertEquals(expectedMessage, actualMessage);
    }

    public void testSomethingWentWrong() {
        String expectedMessage = getContext().getString(R.string.something_went_wrong);
        String actualMessage = localizedStringProvider.somethingWentWrong();

        assertEquals(expectedMessage, actualMessage);
    }

    public void testLoading() {
        String expectedMessage = getContext().getString(R.string.loading);
        String actualMessage = localizedStringProvider.loading();

        assertEquals(expectedMessage, actualMessage);
    }
}
