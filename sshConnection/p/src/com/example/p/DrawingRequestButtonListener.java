package com.example.p;

import java.util.ArrayList;
import java.util.List;

import ro.pub.cti.drawing.DrawingView;
import ro.pub.cti.drawing.Point;
import ro.pub.cti.utils.CommunicationInfo;
import ro.pub.cti.utils.CommunicationThread;
import ro.pub.cti.utils.Move;
import android.app.Activity;
import android.view.View;

public class DrawingRequestButtonListener implements View.OnClickListener {
	Activity activity;
	DrawingView drawing;
	List<Move> moveList;
	public DrawingRequestButtonListener(
			Activity activity, DrawingView drawing) {
		this.activity = activity;
		this.drawing = drawing;
		moveList = new ArrayList<Move>();
	}
	
	@Override
	public void onClick(View v) {
		(new CommunicationThread(CommunicationInfo.getCommunicationInfo(), 
				"/motorsOn")).start();
		
		List<Point> pointsList = DrawingView.pointList;
		System.out.println("pointsList.size() = " + pointsList.size());
		if (pointsList.size() < 2) {
			return;
		}
		
		int prevIndex = 0;
		
		for (int i = 1; i < pointsList.size() ; ++i) {
			try {
				if (!Move.doMove(pointsList.get(prevIndex), pointsList.get(i))) {
					continue;
				}
				prevIndex = i;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
