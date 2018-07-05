package com.thing.a.redditchallenge.datasource;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thing.a.redditchallenge.model.RedditHome;
import com.thing.a.redditchallenge.model.RedditListItem;
import com.thing.a.redditchallenge.model.RedditPost;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RedditDataSource {
    public static final String BASE_URL = "https://www.reddit.com/";
    private final RedditApi redditApi;

    public RedditDataSource() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        redditApi = retrofit.create(RedditApi.class);
    }

    public void getHomePosts(RedditCallback callback) {
        Call<RedditHome> redditHomeCall = redditApi.getHome();
        redditHomeCall.enqueue(new CallbackWrapper(callback));
    }

    public void getSubRedditPosts(String subReddit, RedditCallback callback) {
        Call<RedditHome> subRedditCall = redditApi.getSubReddit(subReddit);
        subRedditCall.enqueue(new CallbackWrapper(callback));
    }

    public interface RedditCallback {
        void onNewPosts(List<RedditPost> posts);

        void onFail(String error);
    }

    private static class CallbackWrapper implements Callback<RedditHome> {
        RedditCallback redditCallback;

        CallbackWrapper(RedditCallback redditCallback) {
            this.redditCallback = redditCallback;
        }

        public void onResponse(@NonNull Call<RedditHome> call, @NonNull Response<RedditHome> response) {
            if (response.isSuccessful()) {
                RedditHome redditHome = response.body();
                if (redditHome != null && redditHome.getData() != null && redditHome.getData().getChildren() != null) {
                    final List<RedditPost> posts = new ArrayList<>();
                    for (RedditListItem item : redditHome.getData().getChildren()) {
                        if (item != null) {
                            posts.add(item.getData());
                        }
                    }
                    redditCallback.onNewPosts(posts);
                }
            }

            redditCallback.onFail("Response: " + response.message());
        }

        @Override
        public void onFailure(Call<RedditHome> call, Throwable t) {
            redditCallback.onFail(t.getMessage());
        }
    }
}
