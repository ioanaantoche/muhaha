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
	static double prev_m = 0;
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
	
	public static boolean doMove(Point prevPoint, Point currentPoint) throws InterruptedException {
		System.out.println(prevPoint + " vs. " + currentPoint);
		if (invalidLengthStepDirection(prevPoint.getX(), currentPoint.getX(), Constants.DELTA_X) &&
 		    invalidLengthStepDirection(prevPoint.getY(), currentPoint.getY(), Constants.DELTA_Y)) {
			System.out.println("invalid");
			return false;
		}
		
		System.out.println("prev: " + prevPoint);
		System.out.println("current: " + currentPoint);
		
		double delta_x = currentPoint.getX() - prevPoint.getX();
		double delta_y = currentPoint.getY() - prevPoint.getY();
		
		if (delta_y == 0.0f) {
			System.out.println("miscare pe X " + delta_x);
			double theta = (delta_x < 0 ? Constants.PI_ROBOT : 0) - prev_m;
			System.out.println("miscare pe X " + delta_x);
			System.out.println("unghi nou = " + Math.toDegrees(theta));
			Thread t = new CommunicationThread(CommunicationInfo.getCommunicationInfo(), 
					"/turn?x=" + Math.abs(delta_x) * Constants.SCALE + 
						 "&y=0" + 
						 "&tetha=" + theta);
			t.start();
			t.join();
			prev_m = theta;
			return true;
		}
		
		if (invalidLengthStepDirection((float)prev_m, (float)(Math.abs(delta_y) / Math.abs(delta_y)), Constants.DELTA_TETHA)) {
			System.out.println("invalid1");
			return false;
		}
		
		if (delta_x == 0.0f) {
			double theta = (delta_y > 0 ? Constants.PI_ROBOT : -Constants.PI_ROBOT) - prev_m;
			System.out.println("miscare pe Y " + Math.abs(delta_y) * Constants.SCALE);
			System.out.println("unghi nou = " + Math.toDegrees(theta));
			Thread t = new CommunicationThread(CommunicationInfo.getCommunicationInfo(), 
					"/turn?x=" + Math.abs(delta_y) * Constants.SCALE + 
						 "&y=0" + 
						 "&tetha=" + theta);
			t.start();
			t.join();
			prev_m = theta;
			return true;
		}
		
		// calculez cadranul:
		if (delta_x > 0) {
			if (delta_y > 0) {
				//cadranul I:
				System.out.println("miscare sub unghi in cadranul I");
				double m = -Math.atan(delta_y /delta_x); // radieni
				sendCommand(m, delta_x);
				return true;
			}
			//cadranul IV:
			delta_y = Math.abs(delta_y);
			System.out.println("miscare sub unghi in cadranul IV");
			double m = -Math.atan(delta_y /delta_x); // radieni
			sendCommand(m, delta_x);
			return true;
		}
		delta_x = Math.abs(delta_x);
		if (delta_y > 0) {
			//cadranul II:
			System.out.println("miscare sub unghi in cadranul II");
			double m = Math.atan(delta_y /delta_x); // radieni
			sendCommand(m, delta_x);
			return true;
		}
		
		//cadranul III:
		delta_y = Math.abs(delta_y);
		System.out.println("miscare sub unghi in cadranul III");
		double m = Math.atan(delta_y /delta_x); // radieni
		sendCommand(m, delta_x);
		return true;
	}
	
	private static void sendCommand(double m, double delta_x) throws InterruptedException {
		m = m - prev_m;
		Double m_robot = m / Math.PI * Constants.PI;
		double xx = delta_x * Math.abs(Math.sin(m)) * Constants.SCALE;
		System.out.println("unghi nou = " + Math.toDegrees(m_robot));
		System.out.println("x = " + xx);
		Thread t =new CommunicationThread(CommunicationInfo.getCommunicationInfo(), 
				"/turn?x=" + xx + 
					 "&y=0" + 
					 "&tetha=" + m_robot);
		prev_m = m;
		t.start();
		t.join();
	}
	
	private static boolean invalidLengthStepDirection(Float x_1, Float x_2, Float delta) {
		if (x_1 > x_2) {
			Float aux = x_1;
			x_1 = x_2;
			x_2 = aux;
		}
		if (x_2 - x_1 < delta) {
			return true;
		}
		return false;
	}
}
