package ro.pub.cti.connection;

import ro.pub.cti.listeners.AutomaticUpdateListeners;
import ro.pub.cti.utils.Constants;

public class ConnectionRequest extends Request {
	public ConnectionRequest(AutomaticUpdateListeners listener) {
		this.listener = listener;
		type = "connectionStatus";
	}

	@Override
	public void responseReady(int responseCode) {
		System.out.println("Yupiii@@");
		//listener.update(responseCode == Constants.SUCCESS);
		System.out.println(".............Yupiii@@");
	}
}
