package ro.pub.cti.listeners;

import android.view.View;

public abstract class Receivers implements View.OnClickListener {
	public abstract void update(String message);
}
