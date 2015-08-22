package ro.pub.cti.communication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import ro.pub.cti.listeners.Receivers;

public class CommunicationThread extends Thread {
	private final CommunicationInfo communicationInfo;
	private String path;
	private Receivers recv;

	public CommunicationThread(CommunicationInfo communicationInfo, String path) {
		this.communicationInfo = communicationInfo;
		this.path = path;
		this.recv = null;
	}

	public CommunicationThread(CommunicationInfo communicationInfo,
			String path, Receivers recv) {
		this.communicationInfo = communicationInfo;
		this.path = path;
		this.recv = recv;
	}

	@Override
	public void run() {
		if (communicationInfo.url != null) {
			try {
				String url = communicationInfo.protocol + "://" + communicationInfo.url + path;
				System.err.println(url);
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				if (path.indexOf("/upload") != -1) {
					// upload a file on a server
					// http://developer.android.com/reference/java/net/HttpURLConnection.html
					try {
						con.setDoOutput(true);
						con.setChunkedStreamingMode(0);

						OutputStream out = new BufferedOutputStream(con.getOutputStream());
						// writeStream(out);
						out.write("ana are mere".getBytes());

						InputStream in = new BufferedInputStream(con.getInputStream());
						// readStream(in);
					} catch (Exception e) {
						System.out.println("POST EXCEPTION :(");
					} finally {
						con.disconnect();
					}
					return;
				}
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent",
						communicationInfo.USER_AGENT);
				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'GET' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);
				Map<String, List<String>> map = con.getHeaderFields();
				for (Map.Entry<String, List<String>> entry : map.entrySet()) {
					System.out.println(entry);
				}
				InputStream inputStream = null;
				if (responseCode == HttpURLConnection.HTTP_OK) {
					inputStream = con.getInputStream();
					System.out.println("DA");
				} else {
					inputStream = con.getErrorStream();
					System.out.println("NU");
				}
				int data = inputStream.read();
				StringBuffer sb = new StringBuffer();
				while (data != -1) {
					sb.append((char) data);
					data = inputStream.read();
				}
				System.out.println(".........." + sb.toString());

			} catch (MalformedURLException e) {
				System.out.println("MalformedURLException");
				e.printStackTrace();
				// responseEvent.responseReady(responseCode);
			} catch (IOException e) {
				System.out.println("IOException");
				e.printStackTrace();
				// responseEvent.responseReady(responseCode);
			}
			return;
		}
		try {
			String url = communicationInfo.protocol + "://"
					+ communicationInfo.ip + ":" + communicationInfo.port
					+ path;
			System.err.println(url);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", communicationInfo.USER_AGENT);
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
