package tbalogh.rssreader.presentation;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by tbalogh on 30/07/16.
 */

public enum Preferences {
    INSTANCE;

    private static final String PREFERENCES_NAME = "rssreader-preferences";

    private SharedPreferences preferences;

    public void init(final Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public boolean isSeen(String url) {
        return this.preferences.getBoolean(url, false);
    }

    public void setAsSeen(String url) {
        Editor editor = this.preferences.edit();
        editor.putBoolean(url, true);
        editor.apply();
    }
}
