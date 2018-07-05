package com.thing.a.redditchallenge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.thing.a.redditchallenge.R;
import com.thing.a.redditchallenge.adapter.PostClickListener;
import com.thing.a.redditchallenge.adapter.RedditAdapter;
import com.thing.a.redditchallenge.datasource.RedditDataSource;
import com.thing.a.redditchallenge.presenter.RedditPresenter;

public class MainActivity extends AppCompatActivity implements PostClickListener {
    private RedditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RedditDataSource redditDataSource = new RedditDataSource();
        presenter = new RedditPresenter(redditDataSource);

        RedditAdapter redditAdapter = new RedditAdapter(presenter, this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(redditAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);

        presenter.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.search(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String currentQuery) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                presenter.clearSearch();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.app_bar_search) {
            return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostClicked(int position) {
        String url = presenter.getPostUrl(position);
        String fullUrl = RedditDataSource.BASE_URL + url;
        Intent postIntent = new Intent(this, PostActivity.class);
        postIntent.putExtra(PostActivity.POST_URL, fullUrl);
        startActivity(postIntent);
    }
}
