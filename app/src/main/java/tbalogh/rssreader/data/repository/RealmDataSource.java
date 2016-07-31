package tbalogh.rssreader.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import tbalogh.rssreader.data.entity.RssEntity;

/**
 * Created by tbalogh on 30/07/16.
 */
@Singleton
public class RealmDataSource implements DbDataSource {

    @Inject
    public RealmDataSource() {}

    @Override
    public RssEntity getRssEntity() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(RssEntity.class).findFirst();
    }

    @Override
    public void saveRssEntity(final RssEntity rssEntity) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(RssEntity.class);
        realm.copyToRealm(rssEntity);
        realm.commitTransaction();
    }
}
