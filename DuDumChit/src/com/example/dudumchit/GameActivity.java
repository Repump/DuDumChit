package com.example.dudumchit;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameActivity extends Activity implements OnCompletionListener {
	String path;
	MediaPlayer player;
	ProgressBar playBar;
	int duration;
	boolean playing;
	Handler handler;

	int score;
	TextView scoreView;
	ImageView characterView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);

		Intent i = getIntent();
		path = i.getExtras().get("path").toString();
		Log.i("PATH", path);
		initialize();
		musicStart();
	}

	private void initialize() {
		// Set MediaPlayer
		player = new MediaPlayer();
		try {
			player.setDataSource(path);
			player.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
		player.setLooping(false);
		player.setOnCompletionListener(this);

		// Set Play Bar
		playBar = (ProgressBar) findViewById(R.id.play_time);
		duration = player.getDuration();
		playBar.setMax(duration);
		playBar.setProgress(0);
		playing = true;
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (playing) {
					playBar.setProgress(player.getCurrentPosition());
					sendEmptyMessageDelayed(0, 500);
				}
			}
		};

		// Set Score
		score = 0;
		scoreView = (TextView) findViewById(R.id.score);
		scoreView.setText("0");

		// Set Character
		characterView = (ImageView) findViewById(R.id.character);
		characterView.setImageResource(R.drawable.icon);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		musicStop();
	}

	private void musicStart() {
		player.start();
		handler.sendEmptyMessageDelayed(0, 500);
	}

	private void musicStop() {
		playing = false;
		handler.sendEmptyMessageAtTime(0, 0);
		player.stop();
		player.release();

		// Send Score to ResultActivity
		Intent i = new Intent(getApplicationContext(), ResultActivity.class);
		i.putExtra("score", score);
		startActivity(i);
		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (playing)
			musicStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler = null;
	}
}
