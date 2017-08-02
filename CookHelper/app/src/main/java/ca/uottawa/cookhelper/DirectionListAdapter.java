package ca.uottawa.cookhelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AshishK on 2016-11-28.
 */
public class DirectionListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList directions;

    private static LayoutInflater inflater = null;

    public DirectionListAdapter(Activity activity, ArrayList directions) {
        this.activity = activity;
        this.directions = directions;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return directions.size();
    }

    @Override
    public Object getItem(int index) {
        return directions.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.direction, null);
        }

        //Customize the direction item
        TextView direction = (TextView) convertView.findViewById(R.id.directionItem);
        direction.setText(directions.get(index).toString());
        direction.setTextColor(Color.WHITE);
        HomePage.font.setFontOf(direction, 2);

        return convertView;
    }

    //The following method was obtained from Murali Ganesan
    /* http://stackoverflow.com/questions/19117195/android-set-listview-height-dynamically */
    public void setListViewHeightBasedOnChildren(ListView listView) {

        int totalHeight = 0;
        for (int i = 0; i < getCount(); i++) {
            View listItem = getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
