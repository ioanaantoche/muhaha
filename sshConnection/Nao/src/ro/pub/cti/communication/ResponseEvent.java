package ro.pub.cti.communication;

import ro.pub.cti.connection.Request;

public interface ResponseEvent {
	public void responseReady(int responseCode);
}
