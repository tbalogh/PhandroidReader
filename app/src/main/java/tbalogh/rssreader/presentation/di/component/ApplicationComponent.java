package tbalogh.rssreader.presentation.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import tbalogh.rssreader.domain.repository.FeedRepository;
import tbalogh.rssreader.presentation.di.module.ApplicationModule;
import tbalogh.rssreader.presentation.di.module.NetworkModule;
import tbalogh.rssreader.presentation.view.util.LocalizedStringProvider;

/**
 * Created by tbalogh on 28/07/16.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    Context context();

    FeedRepository feedRepository();

    LocalizedStringProvider localizedStringProvider();
}
