package ro.pub.cti.utils;

public class RobotPosition {
	public static enum Orientation {
		NORTH,
		SOUTH,
		EAST,
		WEST,
		ROTATE,
	}
	
	private Float x;
	private Float y;
	private Float tetha;
	private Orientation orientation;
	
	static RobotPosition robotPosition;
	
	private RobotPosition() {
		orientation = Orientation.SOUTH;
		x = 0f;
		y = 0f;
		tetha = 0f;
	}
	
	public static RobotPosition getRobotPositionInstance() {
		if (robotPosition == null) {
			robotPosition = new RobotPosition();
		}
		return robotPosition;
	}
	
	
	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public Float getTetha() {
		return tetha;
	}

	public void setTetha(Float tetha) {
		this.tetha = tetha;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public static RobotPosition getRobotPosition() {
		return robotPosition;
	}

	public static void setRobotPosition(RobotPosition robotPosition) {
		RobotPosition.robotPosition = robotPosition;
	}
}
