package com.sty.menu.grid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sty.menu.grid.clickprotect.QuickClickProtection;
import com.sty.menu.grid.view.MenuPage;

/**
 * Created by shity on 2017/9/4/0004.
 */

public class SubActivity extends AppCompatActivity {

    protected QuickClickProtection quickClickProtection = QuickClickProtection.getInstance();
    private TextView tvTitle;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quickClickProtection.stop();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.sub_menu);

        initViews();
        setListeners();
    }

    private void initViews(){
        tvTitle = (TextView) findViewById(R.id.header_title);
        tvTitle.setText("子页面");
        ivBack = (ImageView) findViewById(R.id.header_back);

        LinearLayout llContainer = (LinearLayout) findViewById(R.id.ll_container);

        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        llContainer.addView(createMenuPage(), params);
    }

    private void setListeners(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private MenuPage createMenuPage(){
        MenuPage.Builder builder = new MenuPage.Builder(SubActivity.this, 6, 2)
                .addMenuItem("消费", R.drawable.app_sale, null)
                .addMenuItem("消费", R.drawable.app_sale, null)
                .addMenuItem("消费", R.drawable.app_sale, null)
                .addMenuItem("消费", R.drawable.app_sale, null)
                .addMenuItem("管理", R.drawable.app_manage, null)
                .addMenuItem("管理", R.drawable.app_manage, null);
        return builder.create();
    }

}
