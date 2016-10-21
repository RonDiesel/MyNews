package diesel.app.mynews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Rinat Galiev on 21.10.2016.
 */
public class SettingsActivity extends AppCompatActivity {
    View feedLayout;
    Button okButton;
    EditText feedEdit;
    Button addFeed;
    ListView feedsListView;
    SharedPreferences mFeeds;
    int mSlot;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mFeeds = getSharedPreferences("Feeds", Context.MODE_PRIVATE);
        feedsListView = (ListView) findViewById(R.id.feedsListView);
        feedLayout = findViewById(R.id.editFeedLayout);
        addFeed = (Button) findViewById(R.id.addFeedButton);
        feedLayout.setVisibility(View.GONE);
        okButton = (Button) findViewById(R.id.addButton);
        feedEdit = (EditText) findViewById(R.id.editFeedEditText);
        ArrayList<String> feedList = new ArrayList<String>();
        loadList(feedList);
    }
private void loadList(ArrayList<String> feedList){
    for(int i=0; i<10; i++) {
        String feed = "feed"+i;
        if (mFeeds.contains(feed)) {
            feedList.add(mFeeds.getString(feed, ""));
        }
    }
    ArrayAdapter<String> adapter = new FeedListAdapter(this, feedList);

    feedsListView.setAdapter(adapter );
    mSlot = feedList.size();
}
    public void onAddClick(View v){
       String feedUrl = feedEdit.getText().toString();
        feedLayout.setVisibility(View.GONE);
        addFeed.setVisibility(View.VISIBLE);
        SharedPreferences.Editor editor = mFeeds.edit();
        String feed = "feed"+mSlot;
        editor.putString(feed, feedUrl);
        editor.apply();

    }
    public void onDelleteClick(View v){
       //v.get
    }
    public void onAddFeedClick(View v){
        feedLayout.setVisibility(View.VISIBLE);
        addFeed.setVisibility(View.GONE);
    }
}
