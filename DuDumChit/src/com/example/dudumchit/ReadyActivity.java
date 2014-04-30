package com.example.dudumchit;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ReadyActivity extends Activity {
	Uri uri;
	Button playButton;
	MediaPlayer player;
	MP3ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ready);

		final ListView MP3List = (ListView) findViewById(R.id.mp3_list);

		adapter = new MP3ListAdapter(getApplicationContext());

		MP3List.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				uri = Uri.withAppendedPath(
						MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ""
								+ adapter.getID(position));

				playButton.setEnabled(true);
			}
		});
		MP3List.setAdapter(adapter);

		//
		playButton = (Button) findViewById(R.id.play);
		playButton.setEnabled(false);
		playButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), GameStart.class);
				i.setData(uri);
				startActivity(i);
			}
		});

		//
		Button backButton = (Button) findViewById(R.id.back);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/*
	 * // Draw Bitmap - http://ensider.tistory.com/2 private static final
	 * BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
	 * private static final Uri sArtworkUri = Uri
	 * .parse("content://media/external/audio/albumart");
	 * 
	 * public static Bitmap getArtworkQuick(Context context, int album_id, int
	 * w, int h) { // NOTE: There is in fact a 1 pixel frame in the ImageView
	 * used to // display this drawable. Take it into account now, so we don't
	 * have to // scale later. w -= 2; h -= 2; ContentResolver res =
	 * context.getContentResolver(); Uri uri =
	 * ContentUris.withAppendedId(sArtworkUri, album_id); if (uri != null) {
	 * ParcelFileDescriptor fd = null; try { fd = res.openFileDescriptor(uri,
	 * "r"); int sampleSize = 1;
	 * 
	 * // Compute the closest power-of-two scale factor // and pass that to
	 * sBitmapOptionsCache.inSampleSize, which will // result in faster decoding
	 * and better quality sBitmapOptionsCache.inJustDecodeBounds = true;
	 * BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null,
	 * sBitmapOptionsCache); int nextWidth = sBitmapOptionsCache.outWidth >> 1;
	 * int nextHeight = sBitmapOptionsCache.outHeight >> 1; while (nextWidth > w
	 * && nextHeight > h) { sampleSize <<= 1; nextWidth >>= 1; nextHeight >>= 1;
	 * }
	 * 
	 * sBitmapOptionsCache.inSampleSize = sampleSize;
	 * sBitmapOptionsCache.inJustDecodeBounds = false; Bitmap b =
	 * BitmapFactory.decodeFileDescriptor( fd.getFileDescriptor(), null,
	 * sBitmapOptionsCache);
	 * 
	 * if (b != null) { // finally rescale to exactly the size we need if
	 * (sBitmapOptionsCache.outWidth != w || sBitmapOptionsCache.outHeight != h)
	 * { Bitmap tmp = Bitmap.createScaledBitmap(b, w, h, true); b.recycle(); b =
	 * tmp; } }
	 * 
	 * return b; } catch (FileNotFoundException e) { } finally { try { if (fd !=
	 * null) fd.close(); } catch (IOException e) { } } } return null; }
	 */
}
