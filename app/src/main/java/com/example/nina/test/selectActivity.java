package com.example.nina.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nina on 17/1/5.
 */

public class selectActivity extends Activity implements OnClickListener {


    private tab people1;
    private tab2 people2;
    private LinearLayout mTab1;
    private LinearLayout mTab2;


    private FragmentManager fragmentManager;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        initViews();
        fragmentManager = getFragmentManager();
    }
    private void initViews()
    {

        mTab1 = (LinearLayout) findViewById(R.id.tab_bottom_tab1);
        mTab2 = (LinearLayout) findViewById(R.id.tab_bottom_tab2);

        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);

    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tab_bottom_tab1:
                setTabSelection(0);
                break;
            case R.id.tab_bottom_tab2:
                setTabSelection(1);
                break;

            default:
                break;
        }
    }

    @SuppressLint("NewApi")
    private void setTabSelection(int index) {
        // 重置按钮
        resetBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index)
        {
            case 0:
                ((ImageButton) mTab1.findViewById(R.id.btn_tab_bottom_tab1))
                        .setImageResource(R.drawable.tab_find_frd_pressed);

                if (people1 == null)
                {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    people1 = new tab();
                    transaction.add(R.id.id_content, people1);
                } else
                {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(people1);
                }
                break;

            case 1:
                ((ImageButton) mTab2.findViewById(R.id.btn_tab_bottom_tab2))
                        .setImageResource(R.drawable.tab_find_frd_pressed);

                if (people2 == null)
                {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    people2 = new tab2();
                    transaction.add(R.id.id_content, people2);
                } else
                {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(people2);
                }

                break;
        }

        transaction.commit();

    }

    private void resetBtn()
    {
        ((ImageButton) mTab1.findViewById(R.id.btn_tab_bottom_tab1))
                .setImageResource(R.drawable.tab_find_frd_normal);
        ((ImageButton) mTab2.findViewById(R.id.btn_tab_bottom_tab2))
                .setImageResource(R.drawable.tab_find_frd_normal);

    }

    @SuppressLint("NewApi")
    private void hideFragments(FragmentTransaction transaction)
    {
        if (people1 != null)
        {
            transaction.hide(people1);
        }
        if (people2 != null)
        {
            transaction.hide(people2);
        }


    }
}
