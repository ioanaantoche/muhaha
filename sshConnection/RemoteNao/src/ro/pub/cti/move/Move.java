package ro.pub.cti.move;

import ro.pub.cti.utils.Constants;
import ro.pub.cti.utils.Task;

public class Move implements Task {
	public String name;
	public Float x = 0.0f;
	public Float y = 0.0f;
	public Float tetha = 0.0f;
	public Float speed = 0.0f;
	public String component = null;
	public Integer nrSteps = 0;
	public Move(String name, Float x, Float y, Float tetha, Float speed, Integer nrSteps, String component) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.tetha = tetha;
		this.speed = speed;
		this.nrSteps = nrSteps;
		this.component = component;
	}
	
	public Move(String name, String component) {
		this.name = name;
		this.x = Constants.DEFAULT_X;
		this.y = Constants.DEFAULT_Y;
		this.tetha = Constants.DEFAULT_TETHA;
		this.speed = Constants.DEFAULT_SPEED;
		this.nrSteps = Constants.DEFAULT_NR_STEPS;
		this.component = component;
	}
	
	@Override
	public String toString() {
		return name + "\nx: " + x + "| y: " + y + "| T: " + tetha + "| S: " + speed + "| " + component;
	}
	
	public String getParameters() {
		return "?x=" + x + 
				"&y=" + y + 
				"&tetha=" + tetha + 
				"&speed=" + speed +
				"&nrSteps=" + nrSteps +
				"&component=" + component;
	}

}
