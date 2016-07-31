package tbalogh.rssreader.domain.interactor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by tbalogh on 29/07/16.
 */
public abstract class Interactor {
    protected Subscription subscription = Subscriptions.empty();

    protected Interactor() {}

    protected abstract Observable buildInteraction();

    public void execute(Subscriber subscriber) {
        this.subscription = buildInteraction()
                .subscribeOn(Schedulers.io())
//				.delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
