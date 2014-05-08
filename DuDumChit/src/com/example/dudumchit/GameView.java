package com.example.dudumchit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

public class GameView extends View implements Runnable {
	private Drawable image;
	int viewWidth, viewHeight;
	int imageWidth, imageHeight;
	int x, y;

	public GameView(Context context) {
		super(context);
		image = getResources().getDrawable(R.drawable.icon);

		Thread thread = new Thread(this);
		thread.start();

		viewWidth = this.getWidth();
		viewHeight = this.getHeight();
		imageWidth = image.getIntrinsicWidth();
		imageHeight = image.getIntrinsicHeight();
		x = y = 0;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
				post(new Runnable() {
					@Override
					public void run() {
						y += 10;
						if (y > viewHeight) {
							y = 0;
						}
						postInvalidate();
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		image.setBounds(x, y, x + imageWidth, y + imageHeight);
		image.draw(canvas);
	}
}
