package ro.pub.cti.connection;

import java.util.List;

import ro.pub.cti.communication.CommunicationThread;
import ro.pub.cti.communication.ResponseEvent;
import ro.pub.cti.listeners.AutomaticUpdateListeners;
import ro.pub.cti.utils.Pair;

public abstract class Request implements ResponseEvent {
	public String type = null;
	public List<Pair> arguments = null;
	protected AutomaticUpdateListeners listener = null;
	
	protected String getArguments() {
		StringBuffer buffer = new StringBuffer();
		if (arguments == null || arguments.isEmpty()) {
			return buffer.toString();
		}
		
		buffer.append("?");
		buffer.append(arguments.get(0).getParameter());
		buffer.append("=");
		buffer.append(arguments.get(0).getValue());
		for (int i = 1; i < arguments.size(); ++i) {
			buffer.append("&&");
			buffer.append(arguments.get(i).getParameter());
			buffer.append("=");
			buffer.append(arguments.get(i).getValue());
		}
		
		return buffer.toString();
	}
	
	protected String getPath() {
		return "/"+type+getArguments();
	}
	
	public void request() {
		CommunicationThread thread = 
				new CommunicationThread(this, getPath());
		thread.start();
		try {
			thread.join();
			System.out.println("Request thread terminated");
		} catch (InterruptedException e) {
			System.out.println("Request thread Error");
			e.printStackTrace();
		}
	}
	
}
