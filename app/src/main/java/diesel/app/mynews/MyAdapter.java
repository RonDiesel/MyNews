package diesel.app.mynews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Rinat Galiev on 20.10.2016.
 */
public class MyAdapter extends ArrayAdapter<RssItem> {
    public MyAdapter(Context context, List<RssItem> res) {
        super(context, R.layout.item_main, res);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RssItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_main, null);
        }

        ((TextView) convertView.findViewById(R.id.title))
                .setText(item.getTitle());
        ((TextView) convertView.findViewById(R.id.text))
                .setText(item.getText());
        ((TextView) convertView.findViewById(R.id.pubDate))
                .setText(item.getPubDate());
        if (item.getImglink()!= null){
            ImgInfo imgInfo = new ImgInfo(item.getImglink(), convertView);
            GetImg task = new GetImg();
            task.execute(imgInfo);



        }
        return convertView;
    }
    private class ImgInfo{
        String url;
        View parent;
        public ImgInfo(String url, View parent){
            this.url = url;
            this.parent = parent;
        }
        public String getUrl(){
            return url;
        }
        public View getParent(){
            return parent;
        }
    }
    private class GetImg extends AsyncTask<ImgInfo, Void, Bitmap > {
        View savelink;
        @Override
        protected Bitmap doInBackground(ImgInfo... info) {
            // TODO Auto-generated method stub
            try{
                URL feedImage= new URL(info[0].getUrl());
                savelink = info[0].getParent();
                HttpURLConnection conn= (HttpURLConnection)feedImage.openConnection();
                InputStream is  = conn.getInputStream();
                Bitmap img = BitmapFactory.decodeStream(is);
                return img;

            }catch (Exception e) {
                // TODO: handle exception

            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            ((ImageView) savelink.findViewById(R.id.imageView1))
                    .setImageBitmap(result);
        }


    }
}