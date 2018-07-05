package com.thing.a.redditchallenge.presenter;

public interface ListPostView {
    void setTitle(String title);

    void setSubreddit(String subreddit);

    void setThumbnail(String thumbnail);

    void hideThumbnail();
}
