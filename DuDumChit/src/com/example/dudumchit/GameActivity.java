package com.example.dudumchit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameActivity extends Activity implements OnCompletionListener {
	Context mContext;

	String path;
	MediaPlayer player;
	ProgressBar playBar;
	int duration;
	boolean playing;
	Handler handler;

	SoundPool effect;
	int effectID;

	int score;
	TextView scoreView;
	ImageView background;
	ImageButton leftButton, rightButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);

		mContext = this;

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

		// Set Effect
		effect = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		effectID = effect.load(getBaseContext(), R.raw.effect, 1);

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

		// Set Background
		background = (ImageView) findViewById(R.id.background);
		background.setImageResource(R.drawable.left);

		// Set Button
		leftButton = (ImageButton) findViewById(R.id.leftButton);
		leftButton.setSoundEffectsEnabled(false);
		leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				effect.play(effectID, 1.0f, 1.0f, 0, 0, 1.0f);
				background.setImageResource(R.drawable.left);
				score += 100;
				scoreView.setText("" + score);
			}
		});
		rightButton = (ImageButton) findViewById(R.id.rightButton);
		rightButton.setSoundEffectsEnabled(false);
		rightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				effect.play(effectID, 1.0f, 1.0f, 0, 0, 1.0f);
				background.setImageResource(R.drawable.right);
				score += 100;
				scoreView.setText("" + score);
			}
		});
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
		effect.release();
	}
}
