package vgd;

import java.util.ArrayList;

import ro.pub.cti.drawing.Point;
import ro.pub.cti.utils.Move;

public class ds {

	public static void main(String[] args) throws InterruptedException {
		ArrayList<Point> list = new ArrayList<Point>();
		list.add(new Point(0,0));
		//stanga 1 metru
		list.add(new Point(1,0));
		//se intoarce cu 90 grade si merge sus 1 metru
		list.add(new Point(1,1));
		// se intoarce cu 45 de grade si merge in sus 1 metru
		list.add(new Point(2,2));
		for (int i = 1; i < list.size(); ++i) {
			Move.getMove(list.get(i-1), list.get(i));
		}
	}

}
