package com.piaoxue.weixins;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Context context;
	private final static int REQUEST_CONNECT_DEVICE = 1; // 宏定义查询设备句柄
	private String SERVER_HOST_IP = "192.168.4.1";
	private int SERVER_HOST_PORT = 8080;

	private Socket socket;
	private PrintStream output;
	
	
	private Button startButton;	
	private EditText IPText;
	private Button sendButtonClient;
	
	private EditText editMsgText, editMsgTextCilent;
	
	private boolean isConnecting = false;
	private Thread mThreadClient = null;	
	private Socket mSocketClient = null;
	static BufferedReader mBufferedReaderClient	= null;
	static PrintWriter mPrintWriterClient = null;
	
	private TextView recvText;
	private  String recvMessageClient = "";
	private  String recvMessageServer = "";
	//颜色选择器
	private ColorPickerDialog dialog;
    private TextView tvTextR;
    private TextView tvTextG;
    private TextView tvTextB;
    public  int int_color;
    public  int int_color_A;
    public  int int_color_R;
    public  int int_color_G;
    public  int int_color_B;
    public String color_S_R;
    public String color_S_G;
    public String color_S_B;
    //所有LED  编码
    public String ceshiLEDON  = "L";
    public String ceshiLEDOFF = "l";
    public String LED1ON = "PFL11";
    public String LED1OFF = "PFL10";
    public String LED2ON = "PFL21";
    public String LED2OFF = "PFL20";
    public String LED3ON = "PFL31";
    public String LED3OFF = "PFL30";
    public String LED4ON = "PFL41";
    public String LED4OFF = "PFL40";
    public String LED5ON = "PFL51";
    public String LED5OFF = "PFL50";
    public String LED6ON = "PFL61";
    public String LED6OFF = "PFL60";
    //电视机切换频道
    public String TV_Switch_on = "PSTr0";
    public String TV_Switch_up = "PSTr0";
    public String TV_Switch_down = "PSTl0";
    
    public String Switch_up = "PSC1";
    public String Switch_down = "PSC0";
    
    public String Zigbee_chuanglian_kai="ZL0";
    public String Zigbee_chuanglian_guan="ZL1";
    public String Zigbee_chuanglian_zidong="ZL2";
    
    public String WIFI_fengshan_kai="WF0";
    public String WIFI_fengshan_guan="WF1";
    
    public String ceshiGRB = "GRB";//WDHGRB
    public String G_00 = "00";
    public String G_0 = "0";
    public String R_00 = "00";
    public String R_0 = "0";
    public String B_00 = "00";
    public String B_0 = "0";
    public String G_bai;
    public String R_bai;
    public String B_bai;
	//按钮动画
	ImageView rocketImage_chuanglian=null;
	AnimationDrawable rocketAnimation; 
	AnimationDrawable rocketAnimation_chuanglian; 
	AnimationDrawable rocketAnimation_signal;
	Animation anim;
	Animation anim2;
	private int flag_anim_fengshan=1;
	private int flag_anim_chuanglian=1;
	private int flag_chazuo=1;
	// **************************BlueTooth***********************************
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothSocket btSocket = null;
	private OutputStream outStream = null;
	private static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB"); // 这条是蓝牙串口通用的UUID，不要更改
	// <==要连接的蓝牙设备MAC地址机器人
	private static String address = "20:13:01:30:05:56";
	// *************************************************************
	private ViewPager mViewPager;
	private PagerAdapter mAdapter;
	private List<View> mViews = new ArrayList<View>();

	// private LinearLayout mTabmusic;
	// private LinearLayout mTabplace;
	// private LinearLayout mTabfrd;
	// private LinearLayout mTabmsg;

	private LinearLayout tab_blub1;
	private LinearLayout tab_blub2;
	private LinearLayout tab_blub3;
	private LinearLayout tab_blub4;

	private LinearLayout tab_blub5;
	private LinearLayout tab_blub6;
	private LinearLayout tab_blub7;
	private LinearLayout tab_blub8;

	// private ImageButton mMusic_img;
	// private ImageButton mPlace_img;
	// private ImageButton mFrd_img;
	// private ImageButton mMsg_img;

	private ImageButton bulb1_img;
	private ImageButton bulb2_img;
	private ImageButton bulb3_img;
	private ImageButton bulb4_img;

	private ImageButton bulb5_img;
	private ImageButton bulb6_img;
	private ImageButton bulb7_img;
	private ImageButton bulb8_img;
	private ImageButton chazuo_img;
	private ImageButton pindaojian_img;
	private ImageButton pindaojia_img;
	private TextView bulb1_txt;
	private int flag_bulb1 = 1;
	private int flag_bulb2 = 1;
	private int flag_bulb3 = 1;
	private int flag_bulb4 = 1;
	private int flag_bulb5 = 1;
	private int flag_bulb6 = 1;
	private int flag_bulb7 = 1;
	private int flag_bulb8 = 1;

	public void toastText(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	public void handleException(Exception e, String prefix) {
		e.printStackTrace();
		toastText(prefix + e.toString());
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 将标题栏去掉
		setContentView(R.layout.activity_main);
		
		
		//这段代码看不懂
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()        
	        .detectDiskReads()        
	        .detectDiskWrites()        
	        .detectNetwork()   // or .detectAll() for all detectable problems       
	        .penaltyLog()        
	        .build());        
	        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()        
	        .detectLeakedSqlLiteObjects()     
	        .penaltyLog()        
	        .penaltyDeath()        
	        .build()); 
	        
	        
		InitView();// 界面初始化
		InitEvents();

	}
	private void InitEvents() {

		// mTabmusic.setOnClickListener(this);
		// mTabplace.setOnClickListener(this);
		// mTabfrd.setOnClickListener(this);
		// mTabmsg.setOnClickListener(this);

		tab_blub1.setOnClickListener(this);
		tab_blub2.setOnClickListener(this);
		tab_blub3.setOnClickListener(this);
		tab_blub4.setOnClickListener(this);

		tab_blub5.setOnClickListener(this);
		tab_blub6.setOnClickListener(this);
		tab_blub7.setOnClickListener(this);
		tab_blub8.setOnClickListener(this);
		
		//客户端连接
		startButton.setOnClickListener(this);
		sendButtonClient.setOnClickListener(this);
		
		findViewById(R.id.id_chuanglian).setOnClickListener(this);
		findViewById(R.id.id_fengshan).setOnClickListener(this);
		findViewById(R.id.id_chazuo).setOnClickListener(this);
		findViewById(R.id.id_RGBLED).setOnClickListener(this);
		findViewById(R.id.id_pindaojia).setOnClickListener(this);
		findViewById(R.id.id_pindaojian).setOnClickListener(this);
	}

	private void InitView() {

		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		// mTabmusic = (LinearLayout) findViewById(R.id.id_tab_light);
		// mTabplace = (LinearLayout) findViewById(R.id.id_tab_place);
		// mTabfrd = (LinearLayout) findViewById(R.id.id_tab_frd);
		// mTabmsg = (LinearLayout) findViewById(R.id.id_tab_msg);
		tab_blub1 = (LinearLayout) findViewById(R.id.id_blub1);
		tab_blub2 = (LinearLayout) findViewById(R.id.id_blub2);
		tab_blub3 = (LinearLayout) findViewById(R.id.id_blub3);
		tab_blub4 = (LinearLayout) findViewById(R.id.id_blub4);

		tab_blub5 = (LinearLayout) findViewById(R.id.id_blub5);
		tab_blub6 = (LinearLayout) findViewById(R.id.id_blub6);
		tab_blub7 = (LinearLayout) findViewById(R.id.id_blub7);
		tab_blub8 = (LinearLayout) findViewById(R.id.id_blub8);

		// mMusic_img = (ImageButton) findViewById(R.id.id_tab_light_img);//底端按钮
		// mPlace_img = (ImageButton) findViewById(R.id.id_tab_place_img);
		// mFrd_img = (ImageButton) findViewById(R.id.id_tab_frd_img);
		// mMsg_img = (ImageButton) findViewById(R.id.id_tab_msg_img);

		bulb1_img = (ImageButton) findViewById(R.id.id_bulb1_img);
		bulb2_img = (ImageButton) findViewById(R.id.id_bulb2_img);
		bulb3_img = (ImageButton) findViewById(R.id.id_bulb3_img);
		bulb4_img = (ImageButton) findViewById(R.id.id_bulb4_img);

		bulb5_img = (ImageButton) findViewById(R.id.id_bulb5_img);
		bulb6_img = (ImageButton) findViewById(R.id.id_bulb6_img);
		bulb7_img = (ImageButton) findViewById(R.id.id_bulb7_img);
		bulb8_img = (ImageButton) findViewById(R.id.id_bulb8_img);
		chazuo_img =(ImageButton) findViewById(R.id.id_chazuo_img);
		pindaojian_img=(ImageButton) findViewById(R.id.id_pindaojian_img);
		pindaojia_img=(ImageButton) findViewById(R.id.id_pindaojia_img);

		bulb1_txt = (TextView) findViewById(R.id.id_bulb1_txt);
		
		tvTextR = (TextView) findViewById(R.id.tv_text_R);
		tvTextG = (TextView) findViewById(R.id.tv_text_G);
		tvTextB = (TextView) findViewById(R.id.tv_text_B);
		rocketImage_chuanglian=(ImageView)findViewById(R.id.id_chuanglian_img);
		LayoutInflater mInflater = LayoutInflater.from(this);
		anim = AnimationUtils.loadAnimation(this, R.anim.round_loading);
		anim2 = AnimationUtils.loadAnimation(this, R.anim.round_loading2_1);
		rocketImage_chuanglian.setBackgroundResource(R.anim.myanimchuanglian);
		rocketAnimation_chuanglian = (AnimationDrawable) rocketImage_chuanglian.getBackground();
		LinearInterpolator lin = new LinearInterpolator();//线性变化
		anim.setInterpolator(lin);
		anim2.setInterpolator(lin);
		findViewById(R.id.id_fengshan_img).startAnimation(anim);
		findViewById(R.id.id_RGBLED_img).startAnimation(anim);
		IPText= (EditText)findViewById(R.id.IPText);
		IPText.setText("192.168.4.1:8080");
		startButton= (Button)findViewById(R.id.StartConnect);
		
		editMsgTextCilent= (EditText)findViewById(R.id.clientMessageText);	   
        editMsgTextCilent.setText("Send Messages");
        
        sendButtonClient= (Button)findViewById(R.id.SendButtonClient);
        recvText= (TextView)findViewById(R.id.RecvText);       
        recvText.setMovementMethod(ScrollingMovementMethod.getInstance()); 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.StartConnect:
			if (isConnecting) 
			{				
				isConnecting = false;
				try {
					if(mSocketClient!=null)
					{
						mSocketClient.close();
						mSocketClient = null;
						
						mPrintWriterClient.close();
						mPrintWriterClient = null;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mThreadClient.interrupt();
				
				startButton.setText("开始连接");					
				IPText.setEnabled(true);		
				recvText.setText("信息:\n");
			}
			else
			{				
				isConnecting = true;
				startButton.setText("停止连接");						
				IPText.setEnabled(false);
				
				mThreadClient = new Thread(mRunnable);
				mThreadClient.start();				
			}
			break;
			
			
		case R.id.SendButtonClient:
			if ( isConnecting && mSocketClient!=null) 
			{
				String msgText =editMsgTextCilent.getText().toString();//取得编辑框中我们输入的内容
				if(msgText.length()<=0)
				{
					Toast.makeText(context, "发送内容不能为空！", Toast.LENGTH_SHORT).show();
				}
				else
				{
					try 
					{				    	
				    	mPrintWriterClient.print(msgText);//发送给服务器
				    	mPrintWriterClient.flush();
					}
					catch (Exception e) 
					{
						// TODO: handle exception
						Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
			}
			else
			{
				Toast.makeText(context, "没有连接", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.id_pindaojia:
			try 
			{				    	
		    	mPrintWriterClient.print(TV_Switch_up);//发送给服务器
		    	mPrintWriterClient.flush();
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.id_pindaojian:
			try 
			{				    	
		    	mPrintWriterClient.print(TV_Switch_down);//发送给服务器
		    	mPrintWriterClient.flush();
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.id_blub1:
			switch (flag_bulb1) {
			case 1:
				flag_bulb1 = 2;
				try 
				{				    	
			    	mPrintWriterClient.print(ceshiLEDON);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				try 
				{				    	
			    	mPrintWriterClient.print(ceshiLEDON);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				 bulb1_txt.setText("关闭");
				bulb1_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb1 = 1;
				try 
				{				    	
			    	mPrintWriterClient.print(ceshiLEDOFF);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				try 
				{				    	
			    	mPrintWriterClient.print(ceshiLEDOFF);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				 bulb1_txt.setText("打开");
				bulb1_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;
		case R.id.id_blub2:
			switch (flag_bulb2) {
			case 1:
				flag_bulb2 = 2;
				try 
				{				    	
			    	mPrintWriterClient.print(LED1ON);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb2_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb2 = 1;
				try 
				{				    	
			    	mPrintWriterClient.print(LED1OFF);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb2_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;
		case R.id.id_blub3:
			switch (flag_bulb3) {
			case 1:
				flag_bulb3 = 2;
				try 
				{				    	
			    	mPrintWriterClient.print(LED2ON);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb3_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb3 = 1;
				try 
				{				    	
			    	mPrintWriterClient.print(LED2OFF);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb3_img.setImageResource(R.drawable.light_bulb_off);

				break;
			}
			break;
		case R.id.id_blub4:
			switch (flag_bulb4) {
			case 1:
				flag_bulb4 = 2;
				try 
				{				    	
			    	mPrintWriterClient.print(LED3ON);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb4_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb4 = 1;
				try 
				{				    	
			    	mPrintWriterClient.print(LED3OFF);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb4_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;

		case R.id.id_blub5:
			switch (flag_bulb5) {
			case 1:
				flag_bulb5 = 2;
				try 
				{				    	
			    	mPrintWriterClient.print(LED4ON);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb5_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb5 = 1;
				try 
				{				    	
			    	mPrintWriterClient.print(LED4OFF);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb5_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;
		case R.id.id_blub6:
			switch (flag_bulb6) {
			case 1:
				flag_bulb6 = 2;
				try 
				{				    	
			    	mPrintWriterClient.print(LED5ON);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb6_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb6 = 1;
				try 
				{				    	
			    	mPrintWriterClient.print(LED5OFF);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb6_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;
		case R.id.id_blub7:
			switch (flag_bulb7) {
			case 1:
				flag_bulb7 = 2;
				try 
				{				    	
			    	mPrintWriterClient.print(LED6ON);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb7_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb7 = 1;
				try 
				{				    	
			    	mPrintWriterClient.print(LED6OFF);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				bulb7_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;
		case R.id.id_blub8:
			switch (flag_bulb8) {
			case 1:
				flag_bulb8 = 2;
				bulb8_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb8 = 1;
				bulb8_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;
	case R.id.id_fengshan:
		switch(flag_anim_fengshan)
		{
		case 1:
			flag_anim_fengshan=2;
			findViewById(R.id.id_fengshan_img).clearAnimation();
			break;
		case 2:
			flag_anim_fengshan=1;
			findViewById(R.id.id_fengshan_img).startAnimation(anim);
			break;
		}
		break;
	case R.id.id_chuanglian:
		switch(flag_anim_chuanglian)
		{
		case 1:
			flag_anim_chuanglian=2;
			try 
			{				    	
		    	mPrintWriterClient.print(Zigbee_chuanglian_kai);//发送给服务器
		    	mPrintWriterClient.flush();
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			rocketImage_chuanglian.setBackgroundResource(R.anim.myanimchuanglian);
			rocketAnimation_chuanglian = (AnimationDrawable) rocketImage_chuanglian.getBackground();
			
			rocketAnimation_chuanglian.setOneShot(true);
			rocketAnimation_chuanglian.start();
			break;
		case 2:
			flag_anim_chuanglian=1;
			try 
			{				    	
		    	mPrintWriterClient.print(Zigbee_chuanglian_guan);//发送给服务器
		    	mPrintWriterClient.flush();
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			rocketImage_chuanglian.setBackgroundResource(R.anim.myanimchuanglianclose);
			rocketAnimation_chuanglian = (AnimationDrawable) rocketImage_chuanglian.getBackground();
			rocketAnimation_chuanglian.setOneShot(true);
			rocketAnimation_chuanglian.start();
			break;
		}
		break;
	case R.id.id_chazuo:		
		switch(flag_chazuo)
		{
		case 1:
			flag_chazuo=2;
			try 
			{				    	
		    	mPrintWriterClient.print(Switch_up);//发送给服务器
		    	mPrintWriterClient.flush();
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			chazuo_img.setImageResource(R.drawable.device_calc_dock_open_big);
			break;
		case 2:
			try 
			{				    	
		    	mPrintWriterClient.print(Switch_down);//发送给服务器
		    	mPrintWriterClient.flush();
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			chazuo_img.setImageResource(R.drawable.device_calc_dock_close_big);
			
			flag_chazuo=1;
			
			break;
		}
		break;
	case R.id.id_RGBLED:
		dialog = new ColorPickerDialog(context, tvTextR.getTextColors().getDefaultColor(), 
				getResources().getString(R.string.btn_color_picker), 
				new ColorPickerDialog.OnColorChangedListener() {
			@Override
			public void colorChanged(int color) {
				int_color=color;
				int_color_R=((int_color&0x00FF0000)>>16);
				int_color_G=((int_color&0x0000FF00)>>8);
				int_color_B=(int_color&0x000000FF);
				tvTextR.setTextColor(int_color);
				tvTextG.setTextColor(int_color);
				tvTextB.setTextColor(int_color);
				//System.out.println(int_color);
				color_S_R=Integer.toString(int_color_R);
				color_S_G=Integer.toString(int_color_G);
				color_S_B=Integer.toString(int_color_B);
				tvTextR.setText(color_S_R);
				tvTextG.setText(color_S_G);
				tvTextB.setText(color_S_B);
				
				
				if((int_color_G>=0)&&(int_color_G<10))
				{
					G_bai=G_00.concat(color_S_G);
				}
				else if((int_color_G>=10)&&(int_color_G<100))
				{
					G_bai=G_0.concat(color_S_G);
				}
				else 
				{
					G_bai=color_S_G;
				} //Green
				
				
				if((int_color_R<10)&&(int_color_R>=0))
				{
					R_bai=R_00.concat(color_S_R);
				}
				else if((int_color_R>=10)&&(int_color_R<100))
				{
					R_bai=R_0.concat(color_S_R);
				}
				else 
				{
					R_bai=color_S_R;
				}//Red
				
				
				
				if((int_color_B<10)&&(int_color_B>=0))
				{
					B_bai=B_00.concat(color_S_B);
				}
				else if((int_color_B>=10)&&(int_color_B<100))
				{
					B_bai=B_0.concat(color_S_B);
				}
				else 
				{
					B_bai=color_S_B;
				}
				String send_G = ceshiGRB.concat(G_bai);
				String send_GR = send_G.concat(R_bai);
				String seng_GRB = send_GR.concat(B_bai);
				try 
				{				    	
			    	mPrintWriterClient.print(seng_GRB);//发送给服务器
			    	mPrintWriterClient.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(context, "发送异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			//	int_color=tvText.getTextColors();
				}
		});
		dialog.show();
		break;
		default:
			break;
		}
	}
	
	//线程:监听服务器发来的消息
	private Runnable	mRunnable	= new Runnable() 
	{
		public void run()
		{
			String msgText =IPText.getText().toString();
			if(msgText.length()<=0)
			{
				//Toast.makeText(mContext, "IP不能为空！", Toast.LENGTH_SHORT).show();
				recvMessageClient = "IP不能为空！\n";//消息换行
				Message msg = new Message();
                msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			int start = msgText.indexOf(":");
			if( (start == -1) ||(start+1 >= msgText.length()) )
			{
				recvMessageClient = "IP地址不合法\n";//消息换行
				Message msg = new Message();
                msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			String sIP = msgText.substring(0, start);
			String sPort = msgText.substring(start+1);
			int port = Integer.parseInt(sPort);				
			
			Log.d("gjz", "IP:"+ sIP + ":" + port);		

			try 
			{				
				//连接服务器
				mSocketClient = new Socket(sIP, port);	//portnum
				//取得输入、输出流
				mBufferedReaderClient = new BufferedReader(new InputStreamReader(mSocketClient.getInputStream()));
				
				mPrintWriterClient = new PrintWriter(mSocketClient.getOutputStream(), true);
				
				recvMessageClient = "已经连接server!\n";//消息换行
				Message msg = new Message();
                msg.what = 1;
				mHandler.sendMessage(msg);		
				//break;
			}
			catch (Exception e) 
			{
				recvMessageClient = "连接IP异常:" + e.toString() + e.getMessage() + "\n";//消息换行
				Message msg = new Message();
                msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}			

			char[] buffer = new char[256];
			int count = 0;
			while (isConnecting)
			{
				try
				{
					//if ( (recvMessageClient = mBufferedReaderClient.readLine()) != null )
					if((count = mBufferedReaderClient.read(buffer))>0)
					{						
						recvMessageClient = getInfoBuff(buffer, count) + "\n";//消息换行
						Message msg = new Message();
		                msg.what = 1;
						mHandler.sendMessage(msg);
					}
				}
				catch (Exception e)
				{
					recvMessageClient = "接收异常:" + e.getMessage() + "\n";//消息换行
					Message msg = new Message();
	                msg.what = 1;
					mHandler.sendMessage(msg);
				}
			}
		}
	};
	Handler mHandler = new Handler()
	{										
		  public void handleMessage(Message msg)										
		  {											
			  super.handleMessage(msg);			
			  if(msg.what == 0)
			  {
				  
				  recvText.append("Server: "+recvMessageServer);	// 刷新
			  }
			  else if(msg.what == 1)
			  {
				  recvText.append("Client: "+recvMessageClient);	// 刷新

			  }
		  }									
	 };
	 private String getInfoBuff(char[] buff, int count)
		{
			char[] temp = new char[count];
			for(int i=0; i<count; i++)
			{
				temp[i] = buff[i];
			}
			return new String(temp);
		}
	
	
	
	
	
//	public void closeSocket() {
//		try {
//			output.close();
//			socket.close();
//		} catch (IOException e) {
//			handleException(e, "close exception: ");
//		}
//	}
//	private void initClientSocket() {
//		try {
//			socket = new Socket(SERVER_HOST_IP, SERVER_HOST_PORT);
//			output = new PrintStream(socket.getOutputStream(), true, "utf-8");
//			//btnConnect.setEnabled(false);
//			//editSend.setEnabled(true);
//			//btnSend.setEnabled(true);
//		} catch (UnknownHostException e) {
//			handleException(e, "unknown host exception: " + e.toString());
//		} catch (IOException e) {
//			handleException(e, "io exception: " + e.toString());
//		}
//	}
//	private void sendMessage(String msg) {
//		output.print(msg);
//	}

}
