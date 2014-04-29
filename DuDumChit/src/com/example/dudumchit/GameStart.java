package com.example.dudumchit;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class GameStart extends Activity {
	MediaPlayer player;
	Uri path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);

		Intent i = getIntent();
		path = i.getData();
		//try {
		//	player.setDataSource(getApplicationContext(), path);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		Log.i("PATH", path.toString());
	}

}
