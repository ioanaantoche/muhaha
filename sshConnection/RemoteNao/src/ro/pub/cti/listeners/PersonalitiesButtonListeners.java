package ro.pub.cti.listeners;

import ro.pub.cti.communication.CommandsSender;
import ro.pub.cti.remotenao.R;
import ro.pub.cti.utils.Maps;
import android.app.Activity;
import android.view.View;
import android.widget.ToggleButton;

public class PersonalitiesButtonListeners implements View.OnClickListener {
	Activity activity;
	
	public PersonalitiesButtonListeners(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.faceRecogButton) {
			ToggleButton button = (ToggleButton) activity.findViewById(R.id.faceRecogButton);
			if (button.isChecked()) {
				CommandsSender.sendCommand("/faceRecognition?action=start");
				button.setText("Stop Face Recognition");
			} else {
				CommandsSender.sendCommand("/faceRecognition?action=stop");
				button.setText("Start Face Recognition");
			}
			return;
		}
		CommandsSender.sendCommand("/personality?id=" + Maps.personalities.get(v.getId()));
	}

}
