package ro.pub.cti.utils;

import java.util.HashMap;
import java.util.Map;

import ro.pub.cti.remotenao.R;

public class Maps {
	public static final Map<String, String> bodyComponents;
	static {
		bodyComponents = new HashMap<String, String>();
		bodyComponents.put("Left Leg", "LLeg");
		bodyComponents.put("Right Leg", "RLeg");
		bodyComponents.put("Left Arm", "LArm");
		bodyComponents.put("Right Arm", "RArm");
	}
	
	public static final Map<Integer, String> moveCommand;
	static {
		moveCommand = new HashMap<Integer, String>();
		moveCommand.put(R.id.moveBackwardButton, "/moveBackward");
		moveCommand.put(R.id.moveForwardButton, "/moveForward");
		moveCommand.put(R.id.moveLeftButton, "/moveLeft");
		moveCommand.put(R.id.moveRightButton, "/moveRight");
		moveCommand.put(R.id.turnLeftButton, "/turnLeft");
		moveCommand.put(R.id.turnRightButton, "/turnRight");
	}
	
	public static final Map<Integer, String> postures;
	static {
		postures = new HashMap<Integer, String>();
		postures.put(R.id.standButton, "Stand");
		postures.put(R.id.standInitButton, "StandInit");
		postures.put(R.id.standZeroButton, "StandZero");
		postures.put(R.id.crouchButton, "Crouch");
		postures.put(R.id.sitButton, "Sit");
		postures.put(R.id.sitRelaxButton, "SitRelax");
		postures.put(R.id.lyingBellyButton, "LyingBelly");
		postures.put(R.id.lyingBackButton, "LyingBack");
	}
	
	
	public static final Map<Integer, String> personalities;
	static {
		personalities = new HashMap<Integer, String>();
		personalities.put(R.id.elimianBratuButton, "1");
		personalities.put(R.id.gheorgheDucaButton, "4");
		personalities.put(R.id.gheorgheLazarButton, "3");
		personalities.put(R.id.mateiDraghiceanuButton, "undefined");
		personalities.put(R.id.nicolaeVasilescuKarpenButton, "2");
		personalities.put(R.id.serbanSolacoluButton, "5");
		personalities.put(R.id.serbanTitieicaButton, "undefined");
		personalities.put(R.id.aurelNiresteanButton, "6");
		personalities.put(R.id.dineshBhugraButton, "7");
		personalities.put(R.id.eliotSorelButton, "8");
		personalities.put(R.id.hellenHerrmanButton, "9");
		personalities.put(R.id.masatoshiTakedaButton, "10");
	}
}
