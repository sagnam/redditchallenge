package com.thing.a.redditchallenge.presenter;

import com.thing.a.redditchallenge.datasource.RedditDataSource;
import com.thing.a.redditchallenge.model.RedditPost;

import java.util.ArrayList;
import java.util.List;

public class RedditPresenter {
    private final RedditDataSource redditDataSource;
    private List<RedditPost> posts = new ArrayList<>();
    private PostDataUpdateListener postDataUpdateListener;

    private final RedditDataSource.RedditCallback callback = new RedditDataSource.RedditCallback() {
        @Override
        public void onNewPosts(List<RedditPost> posts) {
            RedditPresenter.this.posts = posts;
            postDataUpdateListener.onDataUpdated();
        }

        @Override
        public void onFail(String error) {
        }
    };

    public RedditPresenter(RedditDataSource redditDataSource) {
        this.redditDataSource = redditDataSource;
    }

    public void init() {
        fetchHomePosts();
    }

    public void search(String query) {
        fetchSubreddit(query);
    }

    public void clearSearch() {
        fetchHomePosts();
    }

    private void fetchHomePosts() {
        redditDataSource.getHomePosts(callback);
    }

    private void fetchSubreddit(String subReddit) {
        redditDataSource.getSubRedditPosts(subReddit, callback);
    }

    public void setPostView(int position, ListPostView postView) {
        RedditPost post = posts.get(position);
        postView.setTitle(post.getTitle());
        postView.setSubreddit(post.getSubredditNamePrefixed());
        if (post.getThumbnail().isEmpty()) {
            postView.hideThumbnail();
        } else {
            postView.setThumbnail(post.getThumbnail());
        }
    }

    public int getNumberOfPosts() {
        return posts.size();
    }

    public String getPostUrl(int position) {
        RedditPost post = posts.get(position);
        return post.getPermalink();
    }

    public void setDataUpdateListener(PostDataUpdateListener postDataUpdateListener) {
        this.postDataUpdateListener = postDataUpdateListener;
    }
}
