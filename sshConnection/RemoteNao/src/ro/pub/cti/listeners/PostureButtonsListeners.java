package ro.pub.cti.listeners;

import ro.pub.cti.communication.CommandsSender;
import ro.pub.cti.utils.Maps;
import android.app.Activity;
import android.view.View;

public class PostureButtonsListeners implements View.OnClickListener {
	Activity activity;
	
	public PostureButtonsListeners(Activity activity) {
		this.activity = activity;
	}
	@Override
	public void onClick(View v) {
		CommandsSender.sendCommand("/posture?posture=" + Maps.postures.get(v.getId()));
		
	}

}
