package tbalogh.rssreader.presentation;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import tbalogh.rssreader.presentation.di.component.ApplicationComponent;
import tbalogh.rssreader.presentation.di.component.DaggerApplicationComponent;
import tbalogh.rssreader.presentation.di.module.ApplicationModule;
import tbalogh.rssreader.presentation.di.module.NetworkModule;

/**
 * Created by tbalogh on 28/07/16.
 */
public class RssReaderApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
        initInjector();
		initPreferences();
	}

    @Override
    public void onTerminate() {
        super.onTerminate();
        Realm.getDefaultInstance().close();
    }

    private void initInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule("http://feeds2.feedburner.com"))
                .build();
    }

    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
    }

	private void initPreferences() {
		Preferences.INSTANCE.init(this);
	}

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
