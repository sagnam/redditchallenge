package com.thing.a.redditchallenge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.thing.a.redditchallenge.R;

public class PostActivity extends AppCompatActivity {
    public static final String POST_URL = "com.thing.a.redditchallenge.activity.PostActivity.POST_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        WebView webView = findViewById(R.id.web_view);
        Intent startIntent = getIntent();
        if (startIntent != null && startIntent.hasExtra(POST_URL)) {
            String url = startIntent.getStringExtra(POST_URL);
            webView.loadUrl(url);
        }

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}
