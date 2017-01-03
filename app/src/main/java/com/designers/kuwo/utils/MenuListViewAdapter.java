package com.designers.kuwo.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.designers.kuwo.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/30.
 */
public class MenuListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, ?>> listItems;
    private LayoutInflater inflater;

    public MenuListViewAdapter(Context context, List<Map<String, ?>> listItems) {
        this.context = context;
        this.listItems = listItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.menu_item_llayout, null);
            viewHolder.menu_item_txt = (TextView) convertView.findViewById(R.id.menu_item_txt);
            viewHolder.menu_item_imgview = (ImageView) convertView.findViewById(R.id.menu_item_imgview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.menu_item_txt.setText(listItems.get(position).get("menuText").toString());
        return convertView;
    }

    private class ViewHolder {
        private TextView menu_item_txt;
        private ImageView menu_item_imgview;
    }

}
