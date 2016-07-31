package tbalogh.rssreader.data.repository;

import tbalogh.rssreader.data.entity.RssEntity;

/**
 * Created by tbalogh on 30/07/16.
 */

public interface DbDataSource {
    RssEntity getRssEntity();

    void saveRssEntity(RssEntity rssEntity);
}
