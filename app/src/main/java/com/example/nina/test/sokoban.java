package com.example.nina.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * Created by Nina on 17/1/1.
 */

public class sokoban extends AppCompatActivity {

    private ArrayList<TextView> tv = new ArrayList<TextView>();
    public static final int TVID_STEP 	= 0;
    public static final int TVID_LEVEL 	= 1;
    public static final int TVID_TIME 	= 2;
    public static final int TVID_TARGET = 3;

    private final static int ITEM1 = 0;
    private final static int ITEM2 = 1;
    private final static int ITEM3 = 2;
    //private final static int ITEM4 = 3;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tv.add(TVID_STEP,	(TextView) findViewById(R.id.tv_step));	//显示步数
        tv.add(TVID_LEVEL, 	(TextView) findViewById(R.id.tv_level));//当前关卡
        tv.add(TVID_TIME,	(TextView) findViewById(R.id.tv_time)); //已过去的时间
        tv.add(TVID_TARGET, (TextView) findViewById(R.id.tv_target));//目标完成情况

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        super.onCreateOptionsMenu(menu);

        setIconEnable(menu,true);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    private void setIconEnable(Menu menu, boolean enable)
    {
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            //下面传入参数
            m.invoke(menu, enable);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.prev_item:
                Toast.makeText(this,"你点击了前一步",Toast.LENGTH_SHORT).show();
                break;
            case R.id.next_item:
                Toast.makeText(this,"你点击了下一步",Toast.LENGTH_SHORT).show();
                break;
            case R.id.reset_item:
                Toast.makeText(this,"你点击了重新开始",Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_item:
                Toast.makeText(this,"你点击了关于",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;

    }
}
