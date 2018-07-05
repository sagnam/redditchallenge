package com.thing.a.redditchallenge.model;

public class RedditPost {
    String title;
    String subreddit;
    String subreddit_name_prefixed;
    String permalink;
    String thumbnail;

    public String getTitle() {
        return title;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getSubredditNamePrefixed() {
        return subreddit_name_prefixed;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
