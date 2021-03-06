package ro.pub.cti.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CommunicationThread extends Thread {		
	private final CommunicationInfo communicationInfo;
	private String path;
	
	public CommunicationThread(CommunicationInfo communicationInfo, String path) {
		this.communicationInfo = communicationInfo;
		this.path = path;
		
	}
	@Override
	public void run() {
		String url = communicationInfo.protocol + "://" +communicationInfo.ip + 
				":" + communicationInfo.port + path;
		System.err.println(url);
		URL obj;
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", communicationInfo.USER_AGENT);
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
