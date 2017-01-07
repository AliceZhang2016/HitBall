package com.example.nina.test;;

/**
 * Created by Mengxue on 17/1/7.
 */


import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.res.Resources;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.view.GestureDetector;


public class SokobanView extends View{
	private int w,h;			//屏宽及高
	private static int mSize;		//每个格的大小
	private static int mXCount;		//x轴格数
	private static int mYCount;
	private static int mXOffset;    //水平起点
	private static int mYOffset;	//垂直起点

	private static final int hh = 30;	//头部高度
	private static final int fh = 0;	//脚部高度

	private long mDelay = 500;  //更新显示
	private long mLastUpdate ;

	public static int es_time = 0; //所用时间

	//private static int level = 1; //关卡
	//public SokobanArtists sokobanArtists = new SokobanArtists();
	BackStage backstage=new BackStage();
	public int current_player=0;


	Context context;

	private Bitmap[] bitmap = new Bitmap[14];

	ArrayList<TextView> tv = new ArrayList<TextView>();

	private RefreshHandler mRedrawHandler = new RefreshHandler();
	class RefreshHandler extends Handler {
		public void handleMessage(Message msg) {
			//SokobanView.this.updateTips();
			SokobanView.this.invalidate(0,0,30,w);
		}
		public void sleep(long delayMillis) {
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	};

	public SokobanView(Context context, AttributeSet arrts) {
		super(context,arrts);  //This is called when a view is being constructed from an XML file, supplying attributes that were specified in the XML file.
		this.context = context;
		initSokobanView();
	}

	public SokobanView(Context context,AttributeSet arrts,int defStyle) {
		super(context,arrts,defStyle);   //This constructor of View allows subclasses to use their own base style when they are inflating.
		this.context = context;
		initSokobanView();
	}

	private void initSokobanView(){
		Log.d("ss", "initSokobanView");
		setFocusable(true); //允许获得焦点，如果没有这句，按键不起作用
		setFocusableInTouchMode(true); //获取焦点时允许触控
		//准备绘图用的Bitmap对象
		Resources res=getResources();
		bitmap[BackStage.P1U] = BitmapFactory.decodeResource(res,R.drawable.hero_1_b);
		bitmap[BackStage.P1D] = BitmapFactory.decodeResource(res,R.drawable.hero_1_f);
		bitmap[BackStage.P1L] = BitmapFactory.decodeResource(res,R.drawable.hero_1_l);
		bitmap[BackStage.P1R] = BitmapFactory.decodeResource(res,R.drawable.hero_1_r);
		bitmap[BackStage.P2U] = BitmapFactory.decodeResource(res,R.drawable.hero_2_b);
		bitmap[BackStage.P2D] = BitmapFactory.decodeResource(res,R.drawable.hero_2_f);
		bitmap[BackStage.P2L] = BitmapFactory.decodeResource(res,R.drawable.hero_2_l);
		bitmap[BackStage.P2R] = BitmapFactory.decodeResource(res,R.drawable.hero_2_r);
		bitmap[BackStage.G] = BitmapFactory.decodeResource(res,R.drawable.grass);
		bitmap[BackStage.W] = BitmapFactory.decodeResource(res,R.drawable.wall);
		bitmap[BackStage.L] = BitmapFactory.decodeResource(res,R.drawable.lake);
		bitmap[BackStage.B] = BitmapFactory.decodeResource(res,R.drawable.ball);
		bitmap[BackStage.H] = BitmapFactory.decodeResource(res,R.drawable.hole);
		bitmap[BackStage.BH] = BitmapFactory.decodeResource(res,R.drawable.ball_in_hole);
	}
	public void stopHandler(){
		mRedrawHandler.removeMessages(0);
	}
	/**
	 * 设置显示的文本域
	 * @param tv
	 */
	public void setTextView(ArrayList<TextView> tv){
		Log.d("ss","setTextView");
		this.tv = tv;
	}
	/**
	 * 载入地图，并计算画图的一些参数
	 */
	public void loadMap(){
		Log.d("ss","loadMap");
		//sokobanArtists.setLevel(level);
		es_time = 0;
		backstage.MapGenerated();
		int[] rect = backstage.getMapSize();
		mXCount = rect[0];					//X的格数
		mYCount = rect[1];					//Y的格数
		//mXCount=15;
		//mYCount=10;

		int xSize = (int) Math.floor(this.w / mYCount); 	//X方向的每格大小
		int ySize = (int) Math.floor(this.h / mXCount); 	//Y方向的每格大小
		mSize = xSize > ySize ? ySize:xSize;				//使用小的尺寸来画
		mXOffset = ((this.w - (mSize * mYCount)) / 2);  	//X轴起点
		mYOffset = hh + ((this.h - (mSize * mXCount)) / 2);	//Y轴起点
		//updateTips();
		this.invalidate();
	}

	public Bundle saveState(){
		//直接用hashmap，可以保存对象
		Bundle mp=  new Bundle();
		//mp.putInt("level", level);
		//mp.putInt("es_time",es_time);
		//保存对象?
		return mp;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.w = w;
		this.h = h - hh - fh; //去掉头和脚
		loadMap();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("ss", String.valueOf(keyCode));
		int cleared = 0;
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			System.out.println("2");
			cleared = backstage.Move(current_player,BackStage.LEFT);
		}
		else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			System.out.println("1");
			cleared = backstage.Move(current_player,BackStage.DOWN);
		}
		else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			System.out.println("0");
			cleared = backstage.Move(current_player,BackStage.UP);
		}
		else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			System.out.println("3");
			cleared = backstage.Move(current_player,BackStage.RIGHT);
		}
		else if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){ //返回键
			Log.d("ss","back or home:"+String.valueOf(keyCode));
			//捕捉返回键及HOME键
			makeSure();
			return (true);
		}

		if(cleared==1){
			declareWin();
		}else if (cleared==-1){
			hit_wall();
		}
		current_player=4-current_player;
		this.invalidate();
		return super.onKeyDown(keyCode, event);
	}



	@Override
	protected void onDraw(Canvas canvas) {
		for(int i=0; i<backstage.m; i++)
		{
			for(int j=0; j<backstage.n; j++)
			{
				System.out.print(backstage.map[i][j]+" ");
			}
			System.out.print("\n");
		}
		System.out.print("\n\n");
		//updateTips();
		for(int i = 0;i < mXCount;i++){
			for(int j= 0; j < mYCount;j++){
				//int npcId = sokobanArtists.getArtistsByXY(i,j);
				int npcId= backstage.map[i][j];

				//if (npcId == 0) continue;  //空白位置
				Rect rt = new Rect();
				//int x = mXOffset + mSize * i; //
				//int y = mYOffset + mSize * j;
				int x=mXOffset+mSize*j;
				int y=mYOffset+mSize*i;


				//rt.set(x,y,x+mSize,y+mSize);
				rt.set(x,y,x+mSize,y+mSize);
				canvas.drawBitmap(bitmap[npcId], null, rt, null);
			}
		}
		super.onDraw(canvas);
	}


	public boolean declareWin(){

		//实时创建对话框并显示
		new AlertDialog.Builder(context)
				//.setIcon(R.drawable.alert_dialog_icon)
				.setTitle(R.string.dia_tips)
				.setMessage(R.string.dia_youwin)
				.setPositiveButton(R.string.menu_ok,	new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,int whichButton)
							{/*
								if(level < sokobanArtists.getTotalLevels()){
									Log.d("ee",String.format("level=%1$d,total=%2$d", level,sokobanArtists.getTotalLevels()));
									level++; //next
									loadMap(level);
								}
								*/
								loadMap();
							}
						}
				)
				.show();

		return true;
	}

	public boolean hit_wall(){

		//实时创建对话框并显示
		new AlertDialog.Builder(context)
				//.setIcon(R.drawable.alert_dialog_icon)
				.setTitle(R.string.dia_tips)
				.setMessage(R.string.dia_youwin)
				.setPositiveButton(R.string.menu_ok,	new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,int whichButton)
							{/*
								if(level < sokobanArtists.getTotalLevels()){
									Log.d("ee",String.format("level=%1$d,total=%2$d", level,sokobanArtists.getTotalLevels()));
									level++; //next
									loadMap(level);
								}
								*/
								loadMap();
							}
						}
				)
				.show();

		return true;
	}

	private boolean makeSure(){

		//实时创建对话框并显示
		new AlertDialog.Builder(context)
				//.setIcon(R.drawable.alert_dialog_icon)
				.setTitle(R.string.dia_tips)
				.setMessage(R.string.sure_tips)
				.setPositiveButton(R.string.menu_ok,	new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,int whichButton)
							{
								System.exit(0);
							}
						}
				)
				.setNegativeButton(R.string.menu_cancle, new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,int whichButton)
							{
								//取消
							}
						}
				)
				.show();

		return true;
	}

	/**
	 * 更新界面上的信息提示
	 */
	/*
	private void updateTips(){
		long now = System.currentTimeMillis();
		if (now - mLastUpdate > mDelay) {
			if(sokobanArtists.s_time != 0){
				es_time = (int) Math.floor((System.currentTimeMillis() - sokobanArtists.s_time) / 1000 );
			}
			tv.get(Sokoban.TVID_STEP).setText(String.valueOf(sokobanArtists.getPSteps()));
			tv.get(Sokoban.TVID_TIME).setText(String.valueOf(es_time));
			tv.get(Sokoban.TVID_LEVEL).setText(String.format("%1$d/%2$d", sokobanArtists.getLevel(),sokobanArtists.getTotalLevels()));

			//int pos[] = sokobanArtists.getPPosition();
			//tv.get(Sokoban.TVID_XY).setText(String.format("%1$d/%2$d",pos[0],pos[1]));
			tv.get(Sokoban.TVID_TARGET).setText(String.format("%1$d/%2$d",sokobanArtists.getTCleared(),sokobanArtists.getTTotal()));
			mLastUpdate = now;
		}
		mRedrawHandler.sleep(mDelay);
	}
	*/
}