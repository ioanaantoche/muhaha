package ro.pub.cti.listeners;

import ro.pub.cti.remotenao.R;
import ro.pub.cti.remotenao.R.id;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class TouchListener implements OnTouchListener {
	Activity activity;
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	PointF start = new PointF();
	PointF mid = new PointF();
	int i = 1;
	float x1, y1, x2, y2;
	private static final String TAG = "Touch";
	
	Bitmap bmp;
	public TouchListener(Activity activity, Bitmap bmp) {
		this.activity = activity;
		this.bmp = bmp;
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
	      case MotionEvent.ACTION_MOVE:
	         savedMatrix.set(matrix);
	         start.set(event.getX(), event.getY());
	         Log.d(TAG, "mode=DRAG");
	         Log.i(TAG, "("+String.valueOf((int)event.getX())+","+String.valueOf((int)event.getY())+")");
	         if (i==1){
	             x1 = event.getX();
	             y1 = event.getY();
	             i = 2;
	             Log.i(TAG, "coordinate x1 : "+String.valueOf(x1)+" y1 : "+String.valueOf(y1));
	         } else if (i==2){
	             x2 = event.getX();
	             y2 = event.getY();
	             i = 3;
	             Log.i(TAG, "coordinate x2 : "+String.valueOf(x2)+" y2 : "+String.valueOf(y2));
	             //draw();
	         } 
	        break;
	      }
	    return true;
	}
	
	public void draw(){
	    Bitmap tes = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);
	    tes = bmp;
	    Canvas cvs = new Canvas(tes);
	    Paint pnt = new Paint();
	    pnt.setColor(Color.RED);
	    Log.i(TAG, "draw line");
	    cvs.drawLine(x1, y1, x2, y2, pnt);
	    i=1;
	    //((ImageView) activity.findViewById(R.id.image)).setImageBitmap(tes);
	}

}
