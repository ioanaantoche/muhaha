package ro.pub.cti.listeners;

import ro.pub.cti.communication.CommandsSender;
import ro.pub.cti.move.Move;
import ro.pub.cti.remotenao.R;
import ro.pub.cti.utils.Constants;
import ro.pub.cti.utils.Global;
import ro.pub.cti.utils.Maps;
import android.app.Activity;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class CommandsManagementButtonListeners implements View.OnClickListener {
	Activity activity;
	
	public CommandsManagementButtonListeners(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.executeMoveButton) {
			Move move= getMove();
			CommandsSender.sendCommand("/executeMove" + move.getParameters());
			Global.movesHistory.add(move); 
			return;
		}
		if (v.getId() == R.id.addMoveButton) {
			Global.queueMoves.add(getMove()); 
			return;
		}
		
		if (v.getId() == R.id.resetQueueMoveButton) {
			Global.queueMoves.clear(); 
			((ListView) activity.findViewById(R.id.commands_list)).setAdapter(
					new ArrayAdapter<Move>(activity, android.R.layout.simple_list_item_1, Global.queueMoves));
			return;
		}
		
		if (v.getId() == R.id.executeQueueMoveButton) {
			CommandsSender.executeList(Global.queueMoves); 
			return;
		}
		
	}
	
	
	private Move getMove() {
		Float xNr = 0f;
		Float yNr = 0f;
		Float tethaNr = 0f;
		Float speedNr = 0f;
		Integer nrSteps = 0;
		String component = Maps.bodyComponents.get(
				((Spinner)activity.findViewById(R.id.robot_body_component)).getSelectedItem().toString());
		try {
			xNr = Float.parseFloat(((EditText) activity.findViewById(R.id.x_input)).getText().toString().trim());
		} catch (Exception e) {
			xNr = Constants.DEFAULT_X;
		}
		try {
			yNr = Float.parseFloat(((EditText) activity.findViewById(R.id.y_input)).getText().toString().trim());
		} catch (Exception e) {
			yNr = Constants.DEFAULT_Y;
		}
		
		try {
			tethaNr = Float.parseFloat(((EditText) activity.findViewById(R.id.tetha_input)).getText().toString().trim());
		} catch (Exception e) {
			tethaNr = Constants.DEFAULT_TETHA;
		}
		try {
			speedNr = Float.parseFloat(((EditText) activity.findViewById(R.id.speed_input)).getText().toString().trim());
		} catch (Exception e) {
			speedNr = Constants.DEFAULT_SPEED;
		}
		try {
			nrSteps = Integer.parseInt(((EditText) activity.findViewById(R.id.nrsteps_input)).getText().toString().trim());
		} catch (Exception e) {
			nrSteps = Constants.DEFAULT_NR_STEPS;
		}
		Editable et = ((EditText)activity.findViewById(R.id.name_input)).getText();
		String name = et == null ? "DefaultName" : et.toString().trim();
		if (name.isEmpty()) {
			name = "DefaultName";
		}
		
		return new Move(name, xNr, yNr, tethaNr, speedNr, nrSteps, component);
	}
}
