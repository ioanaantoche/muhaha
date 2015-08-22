package ro.pub.cti.utils;

import ro.pub.cti.utils.CommunicationInfo;
import ro.pub.cti.drawing.Point;

public class Move {
	public static enum Direction {
		STEP_FORWARD,
		STEP_BACKWARD,
		STEP_LEFT,
		STEP_RIGHT,
		STEP_TURN_LEFT,
		STEP_TURN_RIGHT,
		UNDEFINERD,
	};
	
	public String name;
	public Float x = 0.0f;
	public Float y = 0.0f;
	public Float tetha = 0.0f;
	public Float speed = 0.0f;
	public String component = null;
	public Integer nrSteps = 0;
	public Direction direction;
	public Move(String name, Float x, Float y, Float tetha, Float speed, Integer nrSteps, String component) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.tetha = tetha;
		this.speed = speed;
		this.nrSteps = nrSteps;
		this.component = component;
	}
	
	@Override
	public String toString() {
		return name + "\nx: " + x + "| y: " + y + "| T: " + tetha + "| S: " + speed + "| " + component;
	}
	
	public static Move getMove(Point prevPoint, Point currentPoint) throws InterruptedException {
		if (validLengthStepDirection(prevPoint.getX(), currentPoint.getX(), Constants.DEFAULT_X) &&
 		    validLengthStepDirection(prevPoint.getY(), currentPoint.getY(), Constants.DEFAULT_Y)) {
			System.out.println("invalid");
			return null;
		}
		
		double delta_x = currentPoint.getX() - prevPoint.getX();
		double delta_y = currentPoint.getY() - prevPoint.getY();
		
		if (delta_y == 0.0f) {
			System.out.println("miscare pe X " + delta_x);
			double theta = delta_x < 0 ? Constants.PI : 0;
			Thread t = new CommunicationThread(CommunicationInfo.getCommunicationInfo(), 
					"/turn?x=" + delta_x * Constants.SCALE + 
						 "&y=0" + 
						 "&tetha=" + theta);
			t.start();
			t.join();
			return null;
		} else if (delta_x == 0.0f) {
			System.out.println("miscare pe Y");
			float sign = delta_y > 0 ? 1.0f : -1.0f;
			Thread t = new CommunicationThread(CommunicationInfo.getCommunicationInfo(), 
					"/turn?x=" + delta_y*sign * Constants.SCALE + 
						 "&y=0" + 
						 "&tetha=" + Constants.PI/2 * sign);
			t.start();
			t.join();
			return null;
		}
		System.out.println("miscare sub unghi");
		double m = Math.atan(delta_y /delta_x); // radieni
		Double m_robot = m / Math.PI * Constants.PI;
		System.out.println("unghi = " + Math.toDegrees(m));
		double xx = delta_x * Math.sin(m) * Constants.SCALE;
		System.out.println("x = " + xx);
		Thread t =new CommunicationThread(CommunicationInfo.getCommunicationInfo(), 
				"/turn?x=" + xx + 
					 "&y=0" + 
					 "&tetha=" + m_robot);
		t.start();
		t.join();
		
		return null;
	}
	
	private static Direction getDirection(Point prevPoint, Point currentPoint) {
		RobotPosition robotPosition = RobotPosition.getRobotPositionInstance();
		Float tetha = 0f;
		if (prevPoint.getX() == currentPoint.getX()) {
			if (prevPoint.getY() < currentPoint.getY()) {
				switch (robotPosition.getOrientation()) {
				case NORTH :
				}
			}
		}
		return Direction.UNDEFINERD;
	}
	
	private static boolean validLengthStepDirection(Float x_1, Float x_2, Float delta) {
		if (x_1 < x_2) {
			Float aux = x_1;
			x_1 = x_2;
			x_2 = aux;
		}
		if (x_2 - x_1 < delta) {
			return false;
		}
		return true;
	}
}
