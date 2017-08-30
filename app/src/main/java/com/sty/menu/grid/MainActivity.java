package com.sty.menu.grid;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sty.menu.grid.view.MenuPage;

public class MainActivity extends AppCompatActivity {

    private MenuPage menuPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    protected void initViews(){
        LinearLayout mLayout = (LinearLayout) findViewById(R.id.ll_gallery);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        menuPage = createMenu();
        mLayout.addView(menuPage, params);
    }

    private MenuPage createMenu(){
        Context context = MainActivity.this;
        MenuPage.Builder builder = new MenuPage.Builder(context, 6, 2)
                .addMenuItem("消费", R.drawable.app_sale, null)
                .addMenuItem("消费", R.drawable.app_sale, null)
                .addMenuItem("消费", R.drawable.app_sale, null)
                .addMenuItem("消费", R.drawable.app_sale, null)
                .addMenuItem("消费", R.drawable.app_sale, null)
                .addMenuItem("消费", R.drawable.app_sale, null)
                .addMenuItem("管理", R.drawable.app_manage, null)
                .addMenuItem("管理", R.drawable.app_manage, null)
                .addMenuItem("管理", R.drawable.app_manage, null)
                .addMenuItem("管理", R.drawable.app_manage, null)
                .addMenuItem("管理", R.drawable.app_manage, null);
        return builder.create();
    }
}
