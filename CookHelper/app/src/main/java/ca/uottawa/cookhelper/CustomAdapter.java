package ca.uottawa.cookhelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Activity activity;
    String[] textArray;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Activity activity, String[] textArray) {
        this.activity = activity;
        this.textArray = textArray;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return textArray.length;
    }

    @Override
    public Object getItem(int index) {
        return textArray[index];
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.drawerlist_row, null);
        }

        //Customize the drawer item
        TextView drawerItem = (TextView) convertView.findViewById(R.id.textView1);
        drawerItem.setText(textArray[index]);
        drawerItem.setTextColor(Color.WHITE);
        if (drawerItem.getText() == "Browse") { drawerItem.setBackgroundResource(R.color.orange); }
        HomePage.font.setFontOf(drawerItem, 1);

        //adjust the height of each of the drawer items so that all elements are equally spaced out
        WindowManager w = activity.getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        drawerItem.setHeight((heightPixels-21-MainMenu.actionBarHeight)/textArray.length);


        return convertView;
    }



}