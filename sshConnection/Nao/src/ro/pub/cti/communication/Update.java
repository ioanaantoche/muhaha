package ro.pub.cti.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ro.pub.cti.connection.ConnectionRequest;
import ro.pub.cti.connection.Request;
import ro.pub.cti.listeners.AutomaticUpdateListeners;
import android.os.SystemClock;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Update extends Thread {
	AutomaticUpdateListeners listener;
	private static int nrExec = 0;
	
	public Update(AutomaticUpdateListeners listener) {
		System.out.println("Update created");
		this.listener = listener;
	}
	
	@Override
	public void run() {
		++nrExec;
		if (nrExec > 1) {
			System.out.println("here!!!!!!!!!!!!!");
			return;
		}
		ExecutorService svc = Executors.newFixedThreadPool(1);
		// nr de threaduri
		final List<Request> requests = new ArrayList<Request>();
		requests.add(new ConnectionRequest(listener));
		svc.submit( new Runnable() {

			@Override
			public void run() {
				System.out.println("Update.run a fost apelat");
				while (true) {
					for (Request request: requests) {
						request.request();
					}
					SystemClock.sleep(2000);
				}
			}
			
		});
		
	}
}
