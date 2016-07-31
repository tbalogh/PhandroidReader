package tbalogh.rssreader.presentation.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by tbalogh on 27/07/16.
 */
public class FeedItemModel {
    public static final String INVALID_PHOTO_URL = "invalid_photo_url";

    private final String       title;
    private final String       description;
    private final String       url;
    private final String       photoUrl;
    private final String       dateTime;
    private final List<String> categories;
    private final boolean      isSeen;

    public FeedItemModel(String title, String description, String url, String photoUrl,
                         String dateTime, List<String> categories, boolean isSeen) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.photoUrl = photoUrl == null ? INVALID_PHOTO_URL : photoUrl;
        this.dateTime = dateTime;
        this.categories = categories == null ? Collections.emptyList() : categories;
        this.isSeen = isSeen;
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

    public boolean hasPhoto() {
        return !INVALID_PHOTO_URL.equals(photoUrl);
    }
}
