package com.phpec.sokoban;



import java.util.ArrayList;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Sokoban extends Activity implements View.OnTouchListener{
	private ArrayList<TextView> tv = new ArrayList<TextView>();
	public static final int TVID_STEP 	= 0;
	public static final int TVID_LEVEL 	= 1;
	public static final int TVID_TIME 	= 2;
	public static final int TVID_TARGET = 3;
	//public static final int TVID_XY 	= 4;

	private final static int ITEM1 = 0;
	private final static int ITEM2 = 1;
	private final static int ITEM3 = 2;
	private final static int ITEM4 = 3;

	public final static int DIALOG_ABOUT = 0;


	public SokobanView mSokobanView;
	private GestureDetector mGestureDetector;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mSokobanView = (SokobanView) findViewById(R.id.sokoban);
		tv.add(TVID_STEP,	(TextView) findViewById(R.id.tv_step));	//显示步数
		tv.add(TVID_LEVEL, 	(TextView) findViewById(R.id.tv_level));//当前关卡
		tv.add(TVID_TIME,	(TextView) findViewById(R.id.tv_time)); //已过去的时间
		//tv.add(TVID_XY,		(TextView) findViewById(R.id.tv_xy));	 //主角所有位置
		tv.add(TVID_TARGET, (TextView) findViewById(R.id.tv_target));//目标完成情况
		mSokobanView.setTextView(tv);
		//dmSokobanView.setOnTouchListener(SokobanView);
		mGestureDetector = new GestureDetector(new gestureListener());
		mSokobanView.setOnTouchListener(this);
		mSokobanView.setFocusable(true);
		mSokobanView.setClickable(true);
		mSokobanView.setLongClickable(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0, ITEM1, ITEM1, R.string.menu_reset).setIcon(R.drawable.reset); //setIcon,setText
		menu.add(0, ITEM2, ITEM2, R.string.menu_prev).setIcon(R.drawable.prev);
		menu.add(0, ITEM3, ITEM3, R.string.menu_next).setIcon(R.drawable.next);
		menu.add(0, ITEM4, ITEM4, R.string.menu_about).setIcon(R.drawable.about);
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
	public boolean onOptionsItemSelected(MenuItem item){
		//int level = mSokobanView.backstage.getLevel();
		//int total = mSokobanView.backstage.getTotalLevels();
		switch(item.getItemId()){
			case ITEM1:
				mSokobanView.loadMap();
				break;
			case ITEM2:
				if(level > 1){
					level --;
					mSokobanView.loadMap(level);
				}
				break;
			case ITEM3:
				if(level < total){
					level ++;
					mSokobanView.loadMap(level);
				}
				break;
			case ITEM4: //关于
				showDialog(DIALOG_ABOUT);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
*/

	protected Dialog onCreateDialog(int id) //只在第一次创建时调用
	{
		if(id == DIALOG_ABOUT){
			return new AlertDialog.Builder(Sokoban.this)
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