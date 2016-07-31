package tbalogh.rssreader.data.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import io.realm.RealmObject;

/**
 * Created by tbalogh on 29/07/16.
 */
@Root(strict = false, name = "rss")
public class RssEntity extends RealmObject {

    @Element(name = "channel")
    FeedEntity feedEntity;

    public RssEntity() {}

    public FeedEntity getFeedEntity() {
        return feedEntity;
    }
}
