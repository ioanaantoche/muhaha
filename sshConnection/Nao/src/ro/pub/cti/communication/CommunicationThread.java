package ro.pub.cti.communication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CommunicationThread extends Thread {		
	private final CommunicationInfo communicationInfo;
	private String path;
	private int responseCode;
	private ResponseEvent responseEvent = null;

	public CommunicationThread(ResponseEvent responseEvent, CommunicationInfo communicationInfo, String path) {
		this.communicationInfo = communicationInfo;
		this.path = path;
		this.responseEvent = responseEvent;
		
	}
	
	public CommunicationThread(ResponseEvent responseEvent, String path) {
		this.communicationInfo = CommunicationInfo.getCommunicationInfo();
		this.path = path;
		this.responseEvent = responseEvent;
	}
	
	@Override
	public void run() {
		try {
			String url = communicationInfo.protocol + "://" +communicationInfo.ip + 
					":" + communicationInfo.port + path;
			System.err.println(url);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", communicationInfo.USER_AGENT);
			responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			responseEvent.responseReady(responseCode);
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException");
			e.printStackTrace();
			responseEvent.responseReady(responseCode);
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
			responseEvent.responseReady(responseCode);
		}
	}
	
	public synchronized int getResponseCode() {
		return responseCode;
	}

}
