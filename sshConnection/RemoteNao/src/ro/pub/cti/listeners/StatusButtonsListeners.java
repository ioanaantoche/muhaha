package ro.pub.cti.listeners;

import ro.pub.cti.communication.CommandsSender;
import ro.pub.cti.remotenao.R;
import ro.pub.cti.utils.Global;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class StatusButtonsListeners extends Receivers {
	Activity activity;
	
	public StatusButtonsListeners(Activity activity) {
		this.activity = activity;
	}
	@Override
	public void onClick(View v) {
		Global.volume += 20;
		if (Global.volume > 100) {
			Global.volume = 0;
		}
		if (v.getId() == R.id.batteryStatus) {
			CommandsSender.sendCommand("/batteryStatus", this);
			CommandsSender.sendCommand("/getSpeakerVolume", this);
			CommandsSender.sendCommand("/setSpeakerVolume?volume=" + Global.volume, this);
			CommandsSender.sendCommand("/upload", this);
			return;
		}
	}
	
	public void update(String message) {
		((Button)activity.findViewById(R.id.batteryStatus)).setText(message);
	}

}