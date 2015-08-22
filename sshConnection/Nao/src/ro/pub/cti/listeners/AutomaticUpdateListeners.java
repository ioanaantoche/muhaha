package ro.pub.cti.listeners;

import android.widget.CompoundButton;

public abstract class AutomaticUpdateListeners  implements CompoundButton.OnHoverListener {
	public abstract void update(boolean state);
}
