package com.piaoxue.weixins;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class placeactivity extends Activity implements OnClickListener {
	
	private LinearLayout tab_blub5;
	private LinearLayout tab_blub6;
	private LinearLayout tab_blub7;
	private LinearLayout tab_blub8;
	
	private ImageButton bulb5_img;
	private ImageButton bulb6_img;
	private ImageButton bulb7_img;
	private ImageButton bulb8_img;
	private int flag_bulb5=1;
	private int flag_bulb6=1;
	private int flag_bulb7=1;
	private int flag_bulb8=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);// 将标题栏去掉
	setContentView(R.layout.tab02);
	
	
	tab_blub5 = (LinearLayout) findViewById(R.id.id_blub5);
	tab_blub6 = (LinearLayout) findViewById(R.id.id_blub6);
	tab_blub7 = (LinearLayout) findViewById(R.id.id_blub7);
	tab_blub8 = (LinearLayout) findViewById(R.id.id_blub8);

	bulb5_img = (ImageButton) findViewById(R.id.id_bulb5_img);
	bulb6_img = (ImageButton) findViewById(R.id.id_bulb6_img);
	bulb7_img = (ImageButton) findViewById(R.id.id_bulb7_img);
	bulb8_img = (ImageButton) findViewById(R.id.id_bulb8_img);
	
	tab_blub5.setOnClickListener(this);
	tab_blub6.setOnClickListener(this);
	tab_blub7.setOnClickListener(this);
	tab_blub8.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.id_blub5:
			switch(flag_bulb5)
			{
			case 1:
				flag_bulb5=2;
				bulb5_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb5=1;
				bulb5_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;
		case R.id.id_blub6:
			switch(flag_bulb6)
			{
			case 1:
				flag_bulb6=2;
				bulb6_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb6=1;
				bulb6_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;
		case R.id.id_blub7:
			switch(flag_bulb7)
			{
			case 1:
				flag_bulb7=2;
				bulb7_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb7=1;
				bulb7_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;
		case R.id.id_blub8:
			switch(flag_bulb8)
			{
			case 1:
				flag_bulb8=2;
				bulb8_img.setImageResource(R.drawable.light_bulb_on);
				break;
			case 2:
				flag_bulb8=1;
				bulb8_img.setImageResource(R.drawable.light_bulb_off);
				break;
			}
			break;
		default:
			break;
		}
	}
}
