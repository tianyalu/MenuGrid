package com.sty.menu.grid.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sty.menu.grid.MainActivity;
import com.sty.menu.grid.R;
import com.sty.menu.grid.clickprotect.MyApplication;
import com.sty.menu.grid.util.ActivityStack;
import com.sty.menu.grid.util.ScreenMetricUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shity on 2017/8/30/0030.
 */

public class GridViewAdapter extends BaseAdapter {

    private static final String TAG = GridViewAdapter.class.getSimpleName();
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

    public GridViewAdapter(Context context, List<GridItem> list, int pageIndex, int maxItemNumPerPage, int columns){
        this.context = context;
        this.pageIndex = pageIndex;
        this.maxItemNumPerPage = maxItemNumPerPage;
        this.columns = columns;
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
        Log.i("Tag", "maxItemNumPerPage:-->" + maxItemNumPerPage + "  columns:-->" + columns);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);



            int gridItemInnerHeight = getGridItemInnerLayoutHeight(convertView);
            int gridItemOrigHeight = getOrigGridItemLayoutHeight(convertView);
            int padding = gridItemOrigHeight - gridItemInnerHeight; //这是上下padding之和
            Log.i("Tag", "-----------------------------");
            Log.i("Tag", "grid item inner height:-->" + gridItemInnerHeight + "\norig grid item height:-->" + gridItemOrigHeight);

            int gridRow = maxItemNumPerPage % columns == 0 ? maxItemNumPerPage / columns : maxItemNumPerPage / columns + 1;
            int screenHeight = ScreenMetricUtils.getRealScreenPixel(ActivityStack.getInstance().top()).y;
            int statusBarHeight = ScreenMetricUtils.getStatusBarHeight(ActivityStack.getInstance().top());
            int customTitleBarHeight = getCustomTitleBarHeight();
            int gridItemHeight = (screenHeight - statusBarHeight - customTitleBarHeight) / gridRow;
            int newMargin = (gridItemHeight - padding - gridItemInnerHeight) / 2;
            Log.i("Tag", "graid raw:-->" + gridRow + "\nscreen height:-->" + screenHeight + "\nnewMargin:-->" + newMargin +
                    "\ncustomTitleBarHeight:-->" + customTitleBarHeight);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    gridItemHeight);
            lp.setMargins(0, newMargin, 0, newMargin);
            //注意：这里是根据父控件的布局类型来设置子控件的各种参数
            RelativeLayout rl =  convertView.findViewById(R.id.grid_item_inner_layout);
            rl.setLayoutParams(lp);

            CustomGridView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridItemHeight);
            convertView.setLayoutParams(params);

        }
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);

        if(position < itemList.size()){
            tv.setText(getViewText(position));
            iv.setImageResource(getViewIcon(position));
        }

        return convertView;
    }

    /**
     * 获得GridItem中的ImageView和TextView联合体的高度
     * @param v
     * @return
     */
    private int getGridItemInnerLayoutHeight(View v){
        RelativeLayout rl = v.findViewById(R.id.grid_item_inner_layout);
        rl.measure(0, 0);
        return rl.getMeasuredHeight();
    }

    /**
     * 获得GridItem的高度
     * @param v
     * @return
     */
    private int getOrigGridItemLayoutHeight(View v){
        RelativeLayout rl = v.findViewById(R.id.grid_item_layout);
        rl.measure(0, 0);
        return rl.getMeasuredHeight();
    }

    /**
     * 获得自定义标题栏的高度
     * @return
     */
    private int getCustomTitleBarHeight(){
        int customTitleBarHeight = 0;
        try {
            View v = LayoutInflater.from(context).inflate(R.layout.header_layout, null);
            RelativeLayout rl = v.findViewById(R.id.layout_header);
            rl.measure(0, 0);
            customTitleBarHeight = rl.getMeasuredHeight();
        } catch (Exception e) {
            Log.i(TAG, "", e);
        }
        return customTitleBarHeight;
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
