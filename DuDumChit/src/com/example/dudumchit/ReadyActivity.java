package com.example.dudumchit;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ReadyActivity extends Activity {
	Uri uri;
	String path;
	Button playButton;
	MediaPlayer player;
	MP3ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ready);

		// MP3 List View
		final ListView MP3List = (ListView) findViewById(R.id.mp3_list);
		adapter = new MP3ListAdapter(getApplicationContext());
		MP3List.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				path = adapter.getPath(position);

				playButton.setEnabled(true);
			}
		});
		if (adapter == null) {
			TextView noFile = (TextView) findViewById(R.id.no_file);
			noFile.setText("MP3 파일이 없습니다.");
		} else
			MP3List.setAdapter(adapter);

		// Play Button
		playButton = (Button) findViewById(R.id.play);
		playButton.setEnabled(false);
		playButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						GameActivity.class);
				i.putExtra("path", path);
				startActivity(i);
			}
		});

		// Back Button
		Button backButton = (Button) findViewById(R.id.back);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
