package com.tarena.customsurfaceview1603;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class CustomSurfaceView extends SurfaceView {
	int viewWidth, viewHeight;
	Thread thread;
	// 管理surfaceView
	SurfaceHolder holder;

	public CustomSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = getHolder();
		MyCallback myCallback = new MyCallback();
		holder.addCallback(myCallback);

	}

	class MyCallback implements Callback {
		// 控件创建时执行
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			thread = new Thread(new MyRunnable());
			thread.start();
		}

		// 控件的大小发生变化时执行，得到控件的寬膏
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			viewHeight = height;
			viewWidth = width;

		}

		//
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}
	}

	boolean isRuning = true;

	class MyRunnable implements Runnable {
		@Override
		public void run() {
			while (isRuning) {
				Canvas canvas = null;
				try {
					canvas = holder.lockCanvas();
					Paint paint = new Paint();
					paint.setColor(Color.RED);
					Rect rect = new Rect(0, 0, viewWidth, viewHeight);
					canvas.drawRect(rect, paint);

					Log.i("MyRunanle", "run" + System.currentTimeMillis());
					thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}

	}

}
