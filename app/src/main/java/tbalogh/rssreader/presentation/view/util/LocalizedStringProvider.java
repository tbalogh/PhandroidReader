package tbalogh.rssreader.presentation.view.util;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import tbalogh.rssreader.R;

/**
 * Created by tbalogh on 31/07/16.
 */
@Singleton
public class LocalizedStringProvider {

    Context context;

    @Inject
    public LocalizedStringProvider(Context context) {
        this.context = context;
    }


    public String noMatchingCategory() {
        return localizedString(R.string.no_matching_category);
    }

    public String internetConnectionError() {
        return localizedString(R.string.internet_connection_error);
    }

    public String somethingWentWrong() {
        return localizedString(R.string.something_went_wrong);
    }

    public String requestTimeout() {
        return localizedString(R.string.requestTimeout);
    }

    public String loading() {
        return localizedString(R.string.loading);
    }

    public String localizedString(int resId) {
        // enable to control resource access (eg.: Api Version dependent resource access)
        return String.valueOf(this.context.getString(resId));
    }
}
