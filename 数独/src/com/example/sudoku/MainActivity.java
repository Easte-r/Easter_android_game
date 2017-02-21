package com.example.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;


public class MainActivity extends Activity {
	private ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
		new Handler().postDelayed(new Runnable(){ //�����൱��Handler�ĵ�һ������

            @Override
            public void run() { 
                Intent intent = new Intent(); 
                intent.setClass(MainActivity.this, StartActivity.class);  
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            } 
                
           }, 2000); //�����1000ָ1000���뼴1��
	}
	public void init(){
		imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setScaleType(ImageView.ScaleType. CENTER_CROP);
	}
	

}
