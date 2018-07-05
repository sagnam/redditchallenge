package com.thing.a.redditchallenge.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thing.a.redditchallenge.R;
import com.thing.a.redditchallenge.presenter.ListPostView;
import com.thing.a.redditchallenge.presenter.PostDataUpdateListener;
import com.thing.a.redditchallenge.presenter.RedditPresenter;

public class RedditAdapter extends RecyclerView.Adapter<RedditAdapter.RedditViewHolder> implements PostDataUpdateListener {

    private final RedditPresenter presenter;
    private final PostClickListener postClickListener;

    public RedditAdapter(RedditPresenter presenter, PostClickListener postClickListener) {
        this.presenter = presenter;
        this.postClickListener = postClickListener;
        presenter.setDataUpdateListener(this);
    }

    @NonNull
    @Override
    public RedditViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View postView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_reddit_post, viewGroup, false);
        return new RedditViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull RedditViewHolder redditViewHolder, int position) {
        presenter.setPostView(position, redditViewHolder);
    }

    @Override
    public int getItemCount() {
        return presenter.getNumberOfPosts();
    }

    @Override
    public void onDataUpdated() {
        notifyDataSetChanged();
    }

    class RedditViewHolder extends RecyclerView.ViewHolder implements ListPostView, View.OnClickListener {

        private final TextView titleView;
        private final TextView subRedditView;
        private final ImageView thumbnailView;

        RedditViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            subRedditView = itemView.findViewById(R.id.sub_reddit);
            thumbnailView = itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setTitle(String title) {
            titleView.setText(title);
        }

        @Override
        public void setSubreddit(String subreddit) {
            subRedditView.setText(subreddit);
        }

        @Override
        public void setThumbnail(String thumbnail) {
            Picasso.get().load(thumbnail).into(thumbnailView);
            thumbnailView.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideThumbnail() {
            thumbnailView.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View view) {
            postClickListener.onPostClicked(getAdapterPosition());
        }
    }
}
