package diesel.app.mynews;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {
    ProgressDialog progress;
    View feedLayout;//панель настройки фида
    Button okButton;
    EditText feedEdit;
    GetRSSDataTask task;//наш AsyncTask
    String feedUrl;
    boolean feedChanged = false;//индикатор изменения источника
    protected MainActivity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        feedLayout = findViewById(R.id.feedlayout);
        feedLayout.setVisibility(View.GONE);
        okButton = (Button) findViewById(R.id.button1);
        okButton.setOnClickListener(this);
        feedEdit = (EditText) findViewById(R.id.editFeed);

        //—читываем адрес фида из файла, если файла нет, то задаем яндекс новости
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("Feed.txt")));
            feedUrl = br.readLine();
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            feedUrl = "https://news.yandex.ru/world.rss";
        }
        feedEdit.setText(feedUrl);
        progress = new ProgressDialog(this);
        //запускаем AsyncTask, который собственно формирует нашу ленту
        task = new GetRSSDataTask();
        task.execute(feedUrl);

    }

    //Ётот метод обрабатывает нажати¤ на аппаратную кнопку меню
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            feedLayout.setVisibility((feedLayout.getVisibility())==View.VISIBLE? View.GONE: View.VISIBLE);
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    ///если будут вопросы по этому классу, с удовольствием отвечу
    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem> > {
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

    ///этот метод обрабатывает нажатия на кнопку ок
    @Override
    public void onClick(View v) {
        feedChanged = true;
        feedUrl = feedEdit.getText().toString();
        feedLayout.setVisibility(View.GONE);
        task = new GetRSSDataTask();
        task.execute(feedUrl);
    }




}