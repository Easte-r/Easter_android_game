package com.example.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SettingActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting);
	}
}
