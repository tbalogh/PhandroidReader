package tbalogh.rssreader.presentation.di.component;

import dagger.Component;
import tbalogh.rssreader.presentation.di.PerActivity;
import tbalogh.rssreader.presentation.di.module.FeedModule;
import tbalogh.rssreader.presentation.view.activity.FeedActivity;

/**
 * Created by tbalogh on 27/07/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FeedModule.class)
public interface FeedComponent {
    void inject(FeedActivity activity);
}
