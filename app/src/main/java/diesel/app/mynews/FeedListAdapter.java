package diesel.app.mynews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rinat Galiev on 21.10.2016.
 */
public class FeedListAdapter extends ArrayAdapter<String> {
    public FeedListAdapter(Context context, List<String> res) {
        super(context, R.layout.item_settings, res);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_settings, null);
        }

        ((TextView) convertView.findViewById(R.id.feedTextView))
                .setText(item);
    //    ((Button) convertView.findViewById(R.id.delleteButton)).set

        return convertView;
    }
}
