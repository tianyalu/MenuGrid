package com.sty.menu.grid.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sty.menu.grid.R;
import com.sty.menu.grid.clickprotect.QuickClickProtection;
import com.sty.menu.grid.view.GridViewAdapter.GridItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shity on 2017/8/30/0030.
 */

public class MenuPage extends LinearLayout {
    private Context context;

    /**
     * 菜单列表项
     */
    private List<GridItem> itemList;

    private WrapContentHeightViewPager mViewPager;

    /**
     * 页面指示器/容器
     */
    private LinearLayout pageIndicatorLayout;

    /**
     * 页面指示器
     */
    private ImageView[] pageIndicator;

    /**
     * 总页面数
     */
    private int numPages;

    /**
     * 当前页面索引
     */
    private int currentPageIndex;

    /**
     * 每页最大显示item数目
     */
    private int maxItemNumPerPage = 9;

    /**
     * 列数
     */
    private int columns = 3;

    private QuickClickProtection quickClickProtection = QuickClickProtection.getInstance();

    public MenuPage(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        this.itemList = new ArrayList<>();
        initView();
    }

    public MenuPage(Context context, int maxItemNumPerPage, int columns, List<GridItem> list){
        super(context);
        this.context = context;
        this.columns = columns;
        this.maxItemNumPerPage = maxItemNumPerPage;
        this.itemList = list;
        initView();
        initPageIndicator();
        initOptionsMenu();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_menu, null);
        mViewPager = (WrapContentHeightViewPager) view.findViewById(R.id.view_pager);
        pageIndicatorLayout = (LinearLayout) view.findViewById(R.id.ll_dots);
        addView(view);
    }

    /**
     * 设置当前页面指示器
     * @param position
     */
    private void setCurrentIndicator(int position){
        if(position < 0 || position > numPages - 1 || currentPageIndex == position){
            return;
        }
        for(ImageView i : pageIndicator){
            i.setImageResource(R.drawable.guide_dot_normal);
        }
        pageIndicator[position].setImageResource(R.drawable.guide_dot_select);
        currentPageIndex = position;
    }

    /**
     * 获取每个页面的gridView
     * @param pageIndex
     * @return
     */
    private View getViewPagerItem(int pageIndex){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.viewpage_gridview, null);
        CustomGridView gridView = (CustomGridView) layout.findViewById(R.id.vp_gv);
        gridView.setNumColumns(columns);

        GridViewAdapter adapter = new GridViewAdapter(context, itemList, pageIndex, maxItemNumPerPage);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //连续多次点击未处理
                if(quickClickProtection.isStarted()){
                    return;
                }
                quickClickProtection.start();
                if((position + currentPageIndex * maxItemNumPerPage) < itemList.size()){
                    GridItem item = itemList.get(position + currentPageIndex * maxItemNumPerPage);
                    process(item);
                }
            }
        });
        gridView.setLongClickable(false);
        return gridView;
    }

    /**
     * 点击菜单项目处理
     * @param item
     */
    private void process(GridItem item){
        Class<?> clazz = item.getActivity();
        if(clazz != null){
            Intent intent = new Intent(context, clazz);
            Bundle bundle = new Bundle();
            bundle.putString("NAV_TITLE", item.getName());
            bundle.putBoolean("HAS_NAV_BACK", true);
            intent.putExtras(bundle);
            context.startActivity(intent);

            return;
        }
        
        // TODO: 2017/8/30/0030
    }

    /**
     * 初始选项菜单
     */
    public void initOptionsMenu(){
        List<View> gridViewList = new ArrayList<>();
        for(int i = 0; i < numPages; i++){
            gridViewList.add(getViewPagerItem(i));
        }
        mViewPager.setAdapter(new ViewPagerAdapter(gridViewList));
    }

    /**
     * 设置ViewPager显示position指定的页面
     * @param position
     */
    public void setCurrentPager(int position){
        mViewPager.setCurrentItem(position);
    }

    /**
     * 初始化指示点
     */
    public void initPageIndicator(){
        if (itemList.size() % maxItemNumPerPage == 0) {
            numPages = itemList.size() / maxItemNumPerPage;
        } else {
            numPages = itemList.size() / maxItemNumPerPage + 1;
        }
        if (0 < numPages) {
            pageIndicatorLayout.removeAllViews();
            if (1 == numPages) {
                pageIndicatorLayout.setVisibility(View.GONE);
            } else if (1 < numPages) {
                pageIndicatorLayout.setVisibility(View.VISIBLE);
                for (int j = 0; j < numPages; j++) {
                    ImageView image = new ImageView(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20); // dot的宽高
                    params.setMargins(10, 0, 10, 0);
                    image.setImageResource(R.drawable.guide_dot_normal);
                    pageIndicatorLayout.addView(image, params);
                }
            }
        }
        if (numPages != 1) {
            pageIndicator = new ImageView[numPages];
            for (int i = 0; i < numPages; i++) {
                pageIndicator[i] = (ImageView) pageIndicatorLayout.getChildAt(i);
            }
            currentPageIndex = 0;
            pageIndicator[currentPageIndex].setImageResource(R.drawable.guide_dot_select);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int index) {
                    setCurrentIndicator(index);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    //do nothing
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                    //do nothing
                }
            });
        }
    }

    public static class Builder{
        private Context context;
        private int maxItemNumPerPage;
        private int columns;
        private List<GridItem> itemList;

        public Builder(Context context, int maxItemNumPerPage, int columns){
            this.context = context;
            this.maxItemNumPerPage = maxItemNumPerPage;
            this.columns = columns;
        }

        /**
         * 负责Activity的跳转
         * @param title 菜单项的名称
         * @param icon 菜单项的图片ID
         * @param act 相关联的Activity
         * @return
         */
        public Builder addMenuItem(String title, int icon, Class<?> act){
            if(itemList == null){
                itemList = new ArrayList<>();
            }
            itemList.add(new GridItem(title, icon, act));
            return this;
        }

        /**
         * 创建并返回MenuPage视图
         * @return
         */
        public MenuPage create(){
            return new MenuPage(context, maxItemNumPerPage, columns, itemList);
        }
    }


}
