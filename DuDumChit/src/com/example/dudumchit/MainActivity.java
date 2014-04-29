package com.example.dudumchit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 게임 시작 버튼
		Button startButton = (Button) findViewById(R.id.game_start);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						ReadyActivity.class);
				startActivity(i);
			}
		});

		// 환경 설정 버튼
		Button optionButton = (Button) findViewById(R.id.option);
		optionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						OptionActivity.class);
				startActivity(i);
			}
		});

		// 게임 종료 버튼
		Button exitButton = (Button) findViewById(R.id.exit);
		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
