package com.tarena.customsurfaceview1603;

import com.tarena.customsurfaceview.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	// 管理surfaceView;
	SurfaceHolder holder;
	Bitmap bitmapFlower, bitmapPig;
	int imageX;
	final int left = 1, right = 2;
	int direction = left;
	int index;
	String[] array = { "导演：", "主演：" };
	int textY;

	public CustomSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = getHolder();
		MyCallBack myCallBack = new MyCallBack();
		holder.addCallback(myCallBack);

		// 加载图片
		bitmapPig = BitmapFactory.decodeResource(getResources(),
				R.drawable.f007);
		bitmapFlower = BitmapFactory.decodeResource(getResources(),
				R.drawable.f008);

	}

	class MyCallBack implements Callback {

		// 控件创建时
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			thread = new Thread(new MyRunnable());
			thread.start();
		}

		// 控件的大小发生变化时
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			viewHeight = height;
			viewWidth = width;
			imageX = width;
			textY = height;

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}

	}

	boolean isRunning = true;

	class MyRunnable implements Runnable {

		@Override
		public void run() {
			while (isRunning) {
				Canvas canvas = null;
				try {
					index++;
					canvas = holder.lockCanvas();
					if (canvas == null) {
						Log.i("MyRunnable", "null");
					}
					textY = textY - 20;
					if (textY < 0) {
						textY = viewHeight;
					}
					// 根据方向来改变坐标
					if (direction == left) {
						imageX = imageX - 20;
					} else {
						imageX = imageX + 20;
					}
					// 移到最左边
					if (imageX < 0) {
						direction = right;
					}
					if (imageX > viewWidth) {
						direction = left;
					}
					// 画
					Paint paint = new Paint();
					paint.setColor(Color.RED);
					Rect rect = new Rect(0, 0, viewWidth, viewHeight);
					// 画到内存中了，没有画到屏幕上
					canvas.drawRect(rect, paint);

					// 画图
					if (index % 2 == 0) {
						canvas.drawBitmap(bitmapPig, imageX, 100, paint);
					} else {
						canvas.drawBitmap(bitmapFlower, imageX, 100, paint);

					}
					// 画字符串数组
					paint.setColor(Color.WHITE);
					paint.setTextSize(50);
					for (int i = 0; i < array.length; i++) {
						int y = textY + i * 50;
						canvas.drawText(array[i], 0, y, paint);
					}
					Log.i("MyRunnable", "textY=" + textY);
					thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// 是把内存中的内容画出来
					holder.unlockCanvasAndPost(canvas);
				}

			}
		}
	}
}
