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
	// ����surfaceView;
	SurfaceHolder holder;
	Bitmap bitmapFlower, bitmapPig;
	int imageX;
	final int left = 1, right = 2;
	int direction = left;
	int index;
	String[] array = { "���ݣ�", "���ݣ�" };
	int textY;

	public CustomSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = getHolder();
		MyCallBack myCallBack = new MyCallBack();
		holder.addCallback(myCallBack);

		// ����ͼƬ
		bitmapPig = BitmapFactory.decodeResource(getResources(),
				R.drawable.f007);
		bitmapFlower = BitmapFactory.decodeResource(getResources(),
				R.drawable.f008);

	}

	class MyCallBack implements Callback {

		// �ؼ�����ʱ
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			thread = new Thread(new MyRunnable());
			thread.start();
		}

		// �ؼ��Ĵ�С�����仯ʱ
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
					// ���ݷ������ı�����
					if (direction == left) {
						imageX = imageX - 20;
					} else {
						imageX = imageX + 20;
					}
					// �Ƶ������
					if (imageX < 0) {
						direction = right;
					}
					if (imageX > viewWidth) {
						direction = left;
					}
					// ��
					Paint paint = new Paint();
					paint.setColor(Color.RED);
					Rect rect = new Rect(0, 0, viewWidth, viewHeight);
					// �����ڴ����ˣ�û�л�����Ļ��
					canvas.drawRect(rect, paint);

					// ��ͼ
					if (index % 2 == 0) {
						canvas.drawBitmap(bitmapPig, imageX, 100, paint);
					} else {
						canvas.drawBitmap(bitmapFlower, imageX, 100, paint);

					}
					// ���ַ�������
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
					// �ǰ��ڴ��е����ݻ�����
					holder.unlockCanvasAndPost(canvas);
				}

			}
		}
	}
}
