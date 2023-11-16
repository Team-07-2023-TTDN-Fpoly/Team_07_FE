package com.team.team_07_fe.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.team_07_fe.R;

public class GridItemAdapter extends BaseAdapter {
    private Context context;
    private String[] tvItem;
    private int[] imgItem;

    public GridItemAdapter(Context context, String[] tvItem, int[] imgItem) {
        this.context = context;
        this.tvItem = tvItem;
        this.imgItem = imgItem;
    }

    @Override
    public int getCount() {
        return tvItem.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.gridview_item, null);
        TextView textView = (TextView) convertView.findViewById(R.id.tvItem);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgItem);

        textView.setText(tvItem[position]);
        imageView.setImageResource(imgItem[position]);
        return convertView;
    }
}
