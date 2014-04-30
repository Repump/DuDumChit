package com.example.dudumchit;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.TrackInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity {
	TextView scoreView;
	ImageView characterView;
	MediaPlayer player;
	String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);

		Intent i = getIntent();
		path = i.getExtras().get("path").toString();
		Log.i("PATH", path);
		initialize();

		player.start();
		characterView.setRotationX(180);

		TrackInfo[] info = player.getTrackInfo();

	}

	private void initialize() {
		scoreView = (TextView) findViewById(R.id.score);
		scoreView.setText("0");
		characterView = (ImageView) findViewById(R.id.character);

		player = new MediaPlayer();

		try {
			player.setDataSource(path);
			player.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}

		player.setLooping(false);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		player.stop(); //
		player.release();
	}
}
