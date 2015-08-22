package ro.pub.cti.drawing;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawingView extends View implements OnTouchListener {

	//drawing path
	private Path drawPath;
	//drawing and canvas paint
	private Paint drawPaint, canvasPaint;
	//initial color
	private int paintColor = 0xFF660000;
	//canvas
	private Canvas drawCanvas;
	//canvas bitmap
	private Bitmap canvasBitmap;
	
	public static List<Point> pointList;

	public DrawingView(Context context) {
		super(context);
		setupDrawing();
		pointList = new ArrayList<Point>();
	}
	private void setupDrawing(){
		drawPath = new Path();
		drawPaint = new Paint();
		drawPaint.setColor(paintColor);
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(20);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);
		canvasPaint = new Paint(Paint.DITHER_FLAG);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Point currentPoint = new Point(event.getX(), event.getY());
		Point auxPoint =  new Point(-currentPoint.getY(), currentPoint.getX());
		Log.d("TAG", "point: " + currentPoint.getX() + "..." + currentPoint.getY());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		    drawPath.moveTo(currentPoint.getX(), currentPoint.getY());
		    pointList.add(auxPoint);
		    break;
		case MotionEvent.ACTION_MOVE:
		    drawPath.lineTo(currentPoint.getX(), currentPoint.getY());
		    pointList.add(auxPoint);
		    break;
		case MotionEvent.ACTION_UP:
		    drawCanvas.drawPath(drawPath, drawPaint);
		    pointList.add(auxPoint);
		    drawPath.reset();
		    break;
		default:
		    return false;
		}
		invalidate();
		return true;
	}
	
	
	public List<Point> getPointList() {
		return pointList;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}

