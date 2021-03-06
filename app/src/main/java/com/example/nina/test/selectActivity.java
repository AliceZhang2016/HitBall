package com.example.nina.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nina on 17/1/5.
 */

public class selectActivity extends Activity implements OnClickListener {


    private tab people1;
    private tab2 people2;
    private LinearLayout mTab1;
    private LinearLayout mTab2;
    private ImageView mHero;
    private LinearLayout next;
    private LinearLayout pre;
    private FragmentManager fragmentManager;
    int[] imageId ;
    private int cur;
    private Button heroConfirm;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        final Data app = (Data)getApplication();
        initViews();
        fragmentManager = getFragmentManager();
        imageId = new int[] { R.drawable.p01, R.drawable.p02,R.drawable.p03,R.drawable.p04,R.drawable.p05,R.drawable.p06 };
        cur = 0;
        mHero = (ImageView) findViewById(R.id.hero);
        heroConfirm = (Button) findViewById(R.id.btn_hero) ;
        next = (LinearLayout) findViewById(R.id.rightChange);
        pre = (LinearLayout) findViewById(R.id.leftChange);

        //默认为一
        resetBtn();
        ((ImageButton) mTab1.findViewById(R.id.btn_tab_bottom_tab1))
                .setImageResource(R.drawable.tab_find_frd_pressed);
        FragmentTransaction transactioni = fragmentManager.beginTransaction();
        hideFragments(transactioni);

        if (people1 == null){
        people1 = new tab();
        transactioni.add(R.id.id_content, people1);}
        else transactioni.show(people1);
        mHero.setImageResource(imageId[app.getHero1()]);
        transactioni.commit();

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (cur<5){
                cur++;
                mHero.setImageResource(imageId[cur]);}
                else{
                    mHero.setImageResource(R.drawable.p06);
                }
            }
        });
        pre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (cur>0){
                    cur--;
                    mHero.setImageResource(imageId[cur]);}
                else{
                    mHero.setImageResource(R.drawable.p01);
                }
            }
        });
        heroConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(people1.isVisible()){
                    app.setHero1(cur);
                }
                else if(people2.isVisible()){
                    app.setHero2(cur);
                }
            }
        });
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
        final Data app = (Data)getApplication();
        switch (index)
        {
            case 0:
                ((ImageButton) mTab1.findViewById(R.id.btn_tab_bottom_tab1))
                        .setImageResource(R.drawable.tab_find_frd_pressed);
                mHero.setImageResource(imageId[app.getHero1()]);
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
                mHero.setImageResource(imageId[app.getHero2()]);
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
