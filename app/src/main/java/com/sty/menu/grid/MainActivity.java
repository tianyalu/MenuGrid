package com.sty.menu.grid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sty.menu.grid.util.ActivityStack;
import com.sty.menu.grid.view.MenuPage;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private MenuPage menuPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        WeakReference<Activity> weakReference = new WeakReference<Activity>(this);
        ActivityStack.getInstance().push(weakReference.get());



        initViews();

        LinearLayout ll = (LinearLayout)findViewById(R.id.activity_main);
        ll.measure(0, 0);
        int height = ll.getMeasuredHeight();

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout_header);
        rl.measure(0, 0);
        int headerHeight = rl.getMeasuredHeight();

        Log.i("Tag", "total height:-->" + height + "\nheader height:-->" + headerHeight + "----------------");
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
        MenuPage.Builder builder = new MenuPage.Builder(context, 9, 3)
                .addMenuItem("消费", R.drawable.app_sale, SubActivity.class)
                .addMenuItem("消费", R.drawable.app_sale, SubActivity.class)
                .addMenuItem("消费", R.drawable.app_sale, SubActivity.class)
                .addMenuItem("消费", R.drawable.app_sale, SubActivity.class)
                .addMenuItem("消费", R.drawable.app_sale, SubActivity.class)
                .addMenuItem("管理", R.drawable.app_manage, SubActivity.class)
                .addMenuItem("管理", R.drawable.app_manage, SubActivity.class)
                .addMenuItem("管理", R.drawable.app_manage, SubActivity.class)
                .addMenuItem("管理", R.drawable.app_manage, SubActivity.class);
        return builder.create();
    }
}
