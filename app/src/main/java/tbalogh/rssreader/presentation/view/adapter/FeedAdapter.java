package tbalogh.rssreader.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tbalogh.rssreader.R;
import tbalogh.rssreader.presentation.Preferences;
import tbalogh.rssreader.presentation.model.FeedItemModel;
import tbalogh.rssreader.presentation.view.OnFeedItemClickedListener;
import tbalogh.rssreader.presentation.view.util.DateUtil;

/**
 * Created by tbalogh on 27/07/16.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedItemViewHolder> {

    private final List<FeedItemModel> feedItemModels;
    private final Context             context;
    private final LayoutInflater      layoutInflater;

    private OnFeedItemClickedListener onFeedItemClickedListener;

    public FeedAdapter(Context context) {
        this.context = context;
        this.feedItemModels = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void update(List<FeedItemModel> feed) {
        this.feedItemModels.clear();
        this.feedItemModels.addAll(feed);
        notifyDataSetChanged();
    }

    public void setOnFeedItemClickedListener(
            final OnFeedItemClickedListener onFeedItemClickedListener) {
        this.onFeedItemClickedListener = onFeedItemClickedListener;
    }

    public void removeFeedItemClickedListener() {
        this.onFeedItemClickedListener = null;
    }

    @Override
    public FeedItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_feed, parent, false);
        return new FeedItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return this.feedItemModels == null ? 0 : this.feedItemModels.size();
    }

    @Override
    public void onBindViewHolder(FeedItemViewHolder holder, int position) {
        holder.update(this.feedItemModels.get(position));
    }

    class FeedItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_photo)     RoundedImageView photo;
        @BindView(R.id.tv_title)      TextView         title;
        @BindView(R.id.tv_extra_info) TextView         description;
        @BindView(R.id.tv_seen)       TextView         isSeen;

        private FeedItemModel feedItem;

        FeedItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void update(FeedItemModel feedItem) {
            this.feedItem = feedItem;
            this.title.setText(feedItem.getTitle());
            String formattedTime = DateUtil.INSTANCE.format(feedItem.getDateTime());
            this.description.setText(formattedTime);
            updateImage(feedItem);
            updateSeen(feedItem);
        }

        private void updateImage(FeedItemModel feedItem) {
            if (feedItem.hasPhoto()) {
                Picasso.with(FeedAdapter.this.context)
                       .load(feedItem.getPhotoUrl())
                       .into(this.photo);
            } else {
                photo.setVisibility(View.GONE);
            }
        }

        private void updateSeen(FeedItemModel feedItem) {
            if (Preferences.INSTANCE.isSeen(feedItem.getUrl())) {
                this.isSeen.setVisibility(View.VISIBLE);
            } else {
                this.isSeen.setVisibility(View.GONE);
            }
        }

        @OnClick(R.id.layout_item)
        void onItemClicked() {
            // Todo(tb) 31/07/16 Move the seen persistent storage into the data layer
            Preferences.INSTANCE.setAsSeen(this.feedItem.getUrl());
            onFeedItemClickedListener.onFeedItemClicked(this.feedItem);
        }
    }

}
