package diesel.app.mynews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


///класс слушатель клика по элементу ListView
public class ListListener implements OnItemClickListener {

    List<RssItem> listItems;
    Activity activity;

    public ListListener(List<RssItem> aListItems, Activity anActivity) {
        listItems = aListItems;
        activity  = anActivity;
    }
    //ќткрываем в браузере страницу выбранной новости
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(listItems.get(pos).getLink()));

        activity.startActivity(i);

    }

}