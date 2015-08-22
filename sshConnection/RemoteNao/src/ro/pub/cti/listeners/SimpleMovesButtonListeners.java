package ro.pub.cti.listeners;

import ro.pub.cti.communication.CommandsSender;
import ro.pub.cti.move.Move;
import ro.pub.cti.remotenao.R;
import ro.pub.cti.utils.Constants;
import ro.pub.cti.utils.Global;
import ro.pub.cti.utils.Maps;
import android.app.Activity;
import android.view.View;

public class SimpleMovesButtonListeners implements View.OnClickListener {
	Activity activity;
	
	public SimpleMovesButtonListeners(Activity activity) {
		this.activity = activity;
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.motorsOffButton) {
			CommandsSender.sendCommand("/motorsOff");
			return;
		}
		
		if (v.getId() == R.id.motorsOnButton) {
			CommandsSender.sendCommand("/motorsOn");
			return;
		}
		CommandsSender.sendStandardCommand(Maps.moveCommand.get(v.getId()));
		Global.movesHistory.add( 
				new Move(Maps.moveCommand.get(v.getId()),
						Constants.DEFAULT_X,
						Constants.DEFAULT_Y, 
						Constants.DEFAULT_TETHA, 
						Constants.DEFAULT_SPEED, 
						Constants.DEFAULT_NR_STEPS, 
						null));		
	}

}
