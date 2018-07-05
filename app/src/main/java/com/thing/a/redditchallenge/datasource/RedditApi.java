package com.thing.a.redditchallenge.datasource;

import com.thing.a.redditchallenge.model.RedditHome;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface RedditApi {
    @GET(".json")
    Call<RedditHome> getHome();

    @GET("r/{subreddit}/.json")
    Call<RedditHome> getSubReddit(@Path("subreddit") String subReddit);
}
