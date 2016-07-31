package tbalogh.rssreader.domain;

import java.util.Collections;
import java.util.List;

/**
 * Created by tbalogh on 29/07/16.
 */
public class FeedItem {
    private final String       title;
    private final String       description;
    private final String       url;
    private final String       photoUrl;
    private final String       dateTime;
    private final List<String> categories;
    private final boolean      isSeen;

    public FeedItem(String title, String description, String url, String photoUrl,
                    String dateTime, List<String> categories, boolean isSeen) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.photoUrl = photoUrl;
        this.dateTime = dateTime;
        this.categories = categories == null ? Collections.emptyList() : categories;
        this.isSeen = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public boolean matchCategory(String matchableCategory) {
        for (String category : categories) {
            if (category.toLowerCase().contains(matchableCategory.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
