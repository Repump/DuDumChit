package com.example.dudumchit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MP3ListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> IDList;
	// private ArrayList<String> albumIDList;
	private ArrayList<String> titleList;
	private ArrayList<String> artistList;
	private ArrayList<String> durationList;

	// Constructor
	public MP3ListAdapter(Context context) {
		mContext = context;
		IDList = new ArrayList<String>();
		// albumIDList = new ArrayList<String>();
		titleList = new ArrayList<String>();
		artistList = new ArrayList<String>();
		durationList = new ArrayList<String>();
		getMP3List();
	}

	private void getMP3List() {
		Uri uri = MediaStore.Audio.Media.getContentUri("external");
		String[] projection = { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.DURATION };
		String selection = null;
		CursorLoader loader = new CursorLoader(mContext, uri, projection,
				selection, null, null);
		Cursor cursor = loader.loadInBackground();
		if (cursor != null && cursor.moveToFirst()) {
			String ID;
			// String albumID;
			String title;
			String artist;
			String duration;

			int IDColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
			// int albumIDColumn = cursor
			//		.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
			int titleColumn = cursor
					.getColumnIndex(MediaStore.Audio.Media.TITLE);
			int artistColumn = cursor
					.getColumnIndex(MediaStore.Audio.Media.ARTIST);
			int durationColumn = cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION);
			do {
				ID = cursor.getString(IDColumn);
				// albumID = cursor.getString(albumIDColumn);
				title = cursor.getString(titleColumn);
				artist = cursor.getString(artistColumn);
				// Convert Duration
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(cursor.getLong(durationColumn));
				SimpleDateFormat format = new SimpleDateFormat("mm:ss");
				duration = format.format(calendar.getTime());

				IDList.add(ID);
				// albumIDList.add(albumID);
				titleList.add(title);
				artistList.add(artist);
				durationList.add(duration);
			} while (cursor.moveToNext());
		}
		cursor.close();
	}

	@Override
	public int getCount() {
		return IDList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getID(int position) {
		return Integer.parseInt((IDList.get(position)));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listViewItem = convertView;
		if (listViewItem == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listViewItem = inflater.inflate(R.layout.list_item, null);
		}

		/*
		 * // Album Image ImageView album = (ImageView)
		 * listViewItem.findViewById(R.id.album); Bitmap albumImage =
		 * ReadyActivity.getArtworkQuick(mContext,
		 * Integer.parseInt((albumIDList.get(position))), 50, 50);
		 * album.setImageBitmap(albumImage);
		 */

		// Title
		TextView title = (TextView) listViewItem.findViewById(R.id.title);
		title.setText(titleList.get(position));

		// Artist
		TextView artist = (TextView) listViewItem.findViewById(R.id.artist);
		artist.setText(artistList.get(position));

		// Duration
		TextView duration = (TextView) listViewItem.findViewById(R.id.duration);
		duration.setText(durationList.get(position));

		return listViewItem;
	}
}
