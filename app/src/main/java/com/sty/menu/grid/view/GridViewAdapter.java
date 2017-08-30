package com.sty.menu.grid.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sty.menu.grid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shity on 2017/8/30/0030.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<GridItem> itemList;
    private Context context;
    private int pageIndex;
    private int maxItemNumPerPage;
    private int columns = 3;

    public GridViewAdapter(Context context, List<GridItem> list, int pageIndex, int maxItemNumPerPage){
        this.context = context;
        this.pageIndex = pageIndex;
        this.maxItemNumPerPage = maxItemNumPerPage;
        itemList = new ArrayList<>();
        int listIndex = pageIndex * maxItemNumPerPage;
        for(int i = listIndex; i < list.size(); i++){
            itemList.add(list.get(i));
        }
    }

    public GridViewAdapter(Context context, List<GridItem> list){
        this.context = context;
        this.pageIndex = 0;
        this.maxItemNumPerPage = list.size();
        itemList = new ArrayList<>();
        int listIndex = pageIndex * maxItemNumPerPage;
        for(int i = listIndex; i < list.size(); i++){
            itemList.add(list.get(i));
        }
    }

    public void setColumns(int columns){
        this.columns = columns;
    }

    @Override
    public int getCount() {
        if(maxItemNumPerPage % columns == 0){
            return maxItemNumPerPage;
        }else{ //处理每页最大个数不能被列数整除的情况：需要加上余数
            return maxItemNumPerPage + (columns - maxItemNumPerPage % columns);
        }
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);

        if(position < itemList.size()){
            tv.setText(getViewText(position));
            iv.setImageResource(getViewIcon(position));
        }

        return convertView;
    }

    private Integer getViewIcon(int position){
        GridItem holder = itemList.get(position);
        return holder.getIcon();
    }

    private String getViewText(int position){
        GridItem holder = itemList.get(position);
        return holder.getName();
    }

    public static class GridItem{
        private String name;
        private int icon;
        //private ATransaction trans;
        private Class<?> activity;
        //private AAction action;
        private Intent intent;

        public GridItem(String name, int icon, Class<?> act){
            this.name = name;
            this.icon = icon;
            this.activity = act;
        }

        public GridItem(String name, int icon, Intent intent){
            this.name = name;
            this.icon = icon;
            this.intent = intent;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public Class<?> getActivity() {
            return activity;
        }

        public void setActivity(Class<?> activity) {
            this.activity = activity;
        }

        public Intent getIntent() {
            return intent;
        }

        public void setIntent(Intent intent) {
            this.intent = intent;
        }
    }
}
