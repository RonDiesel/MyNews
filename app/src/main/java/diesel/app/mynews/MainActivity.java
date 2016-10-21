package diesel.app.mynews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by Rinat Galiev on 20.10.2016.
 */
public class MainActivity extends AppCompatActivity {
    ProgressDialog progress;


    GetRSSDataTask task;
    String feedUrl;
    boolean feedChanged = false;
    protected MainActivity context;
    SharedPreferences mFeeds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFeeds = getSharedPreferences("Feeds", Context.MODE_PRIVATE);
        context = this;

        if (mFeeds.contains("feed0")) {
            feedUrl = mFeeds.getString("feed0", "");
        }else{
            feedUrl = "https://news.yandex.ru/world.rss";
            SharedPreferences.Editor editor = mFeeds.edit();
            editor.putString("feed0", feedUrl);
            editor.apply();
        }


        progress = new ProgressDialog(this);
        task = new GetRSSDataTask();
        task.execute(feedUrl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {
        @Override
        protected List<RssItem> doInBackground(String... urls) {
            publishProgress(null);
            try {
                RssReader rssReader = new RssReader(urls[0]);

                return rssReader.getItems();
            } catch (Exception e) {

            }

            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            progress.show();
        }
        @Override
        protected void onPostExecute(List<RssItem> result) {
            progress.dismiss();
            if(result==null){
            }
            ListView itcItems = (ListView) findViewById(R.id.listMainView);
            ArrayAdapter<RssItem> adapter = new MyAdapter(context, result);
            itcItems.setAdapter(adapter);
            itcItems.setOnItemClickListener(new ListListener(result, context));
            if(feedChanged){
                BufferedWriter bw;
                try {
                    bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("Feed.txt", MODE_PRIVATE)));
                    bw.write(feedUrl);
                    bw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
            }
        }
    }





}