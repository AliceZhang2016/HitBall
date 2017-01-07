package com.example.nina.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * Created by Nina on 17/1/1.
 */

public class sokoban extends AppCompatActivity implements View.OnTouchListener{

    private ArrayList<TextView> tv = new ArrayList<TextView>();
    public static final int TVID_STEP 	= 0;
    public static final int TVID_LEVEL 	= 1;
    public static final int TVID_TIME 	= 2;
    public static final int TVID_TARGET = 3;

    public SokobanView mSokobanView;
    private GestureDetector mGestureDetector;

    private final static int ITEM1 = 0;
    private final static int ITEM2 = 1;
    private final static int ITEM3 = 2;
    //private final static int ITEM4 = 3;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSokobanView = (SokobanView) findViewById(R.id.sokoban);
        Log.d("ss", "PPPPPPPPPPPPPP");
        tv.add(TVID_STEP,	(TextView) findViewById(R.id.tv_step));	//显示步数
        tv.add(TVID_LEVEL, 	(TextView) findViewById(R.id.tv_level));//当前关卡
        tv.add(TVID_TIME,	(TextView) findViewById(R.id.tv_time)); //已过去的时间
        tv.add(TVID_TARGET, (TextView) findViewById(R.id.tv_target));//目标完成情况
        Log.d("ss", "P222222222");
        mGestureDetector = new GestureDetector(new gestureListener());
        Log.d("ss","0000123456");
        mSokobanView.setOnTouchListener(this);
        Log.d("ss","0000123456");
        mSokobanView.setFocusable(true);
        Log.d("ss","0000123456");
        mSokobanView.setClickable(true);
        Log.d("ss","0000123456");
        mSokobanView.setLongClickable(true);
        Log.d("ss","123456");
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
                Toast.makeText(this,"你点击了下一关",Toast.LENGTH_SHORT).show();
                break;
            case R.id.reset_item:
                Toast.makeText(this,"你点击了重新开始",Toast.LENGTH_SHORT).show();
                mSokobanView.loadMap();
                break;
            case R.id.about_item:
                Toast.makeText(this,"你点击了关于",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSokobanView.stopHandler(); //停止线程重画
        Log.d("ss","es="+String.valueOf(SokobanView.es_time));
        Log.d("ss","onstop");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("ss","es="+String.valueOf(SokobanView.es_time));
        //mSokobanView.sokobanArtists.s_time = System.currentTimeMillis() - SokobanView.es_time * 1000;
        Log.d("ss","onresume");
    }
/*
    protected Dialog onCreateDialog(int id) //只在第一次创建时调用
    {
        if(id == DIALOG_ABOUT){
            return new AlertDialog.Builder(sokoban.this)
                    // .setIcon(R.drawable.alert_dialog_icon)
                    .setTitle(R.string.menu_about)
                    .setMessage(R.string.dia_about)
                    .setPositiveButton(R.string.menu_ok,
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog,int whichButton)
                                {
                                }
                            }
                    )
                    .create();
        }
        return null;
    }
*/
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private class gestureListener implements GestureDetector.OnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            Log.d("ss", "onDown!!!");
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float minMove = 120;        //最小滑动距离
            float minVelocity = 0;     //最小滑动速度
            float beginX = e1.getX();
            float endX = e2.getX();
            float beginY = e1.getY();
            float endY = e2.getY();
            int direct=0;

            if (beginX - endX > minMove && Math.abs(velocityX) > minVelocity) {  //左滑
                //Toast.makeText(this, velocityX + "左滑", Toast.LENGTH_SHORT).show();
                direct = BackStage.LEFT;
            }
            else if(endX - beginX > minMove && Math.abs(velocityX) > minVelocity) {  //右滑
                //Toast.makeText(this, velocityX + "右滑", Toast.LENGTH_SHORT).show();
                direct = BackStage.RIGHT;
            }
            else if(beginY - endY > minMove && Math.abs(velocityY) > minVelocity) {  //上滑
                //Toast.makeText(this, velocityX + "上滑", Toast.LENGTH_SHORT).show();
                direct = BackStage.UP;
            }
            else if(endY - beginY > minMove && Math.abs(velocityY) > minVelocity) {  //下滑
                //Toast.makeText(this, velocityX + "下滑", Toast.LENGTH_SHORT).show();
                direct = BackStage.DOWN;
            }
            if(mSokobanView.backstage.Move(mSokobanView.current_player,direct)==1) {  //赢了
                mSokobanView.declareWin();
            }
            Log.d("ss",String.format("direction:%d",direct));
            Log.d("ss", "hhhhhhhhhhhhhhhhhh");
            mSokobanView.invalidate();
            mSokobanView.current_player=4-mSokobanView.current_player;
            return false;
        }
    }
}
