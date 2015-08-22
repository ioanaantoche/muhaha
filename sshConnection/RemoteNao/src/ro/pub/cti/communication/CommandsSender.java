package ro.pub.cti.communication;

import java.util.List;

import ro.pub.cti.listeners.Receivers;
import ro.pub.cti.move.Move;
import ro.pub.cti.utils.Global;


public class CommandsSender {
	public static void sendCommand(String parameters, Receivers recv) {
		Thread t = new CommunicationThread(
				CommunicationInfo.getCommunicationInfo(),
				parameters, recv);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendCommand(String parameters) {
		Thread t = new CommunicationThread(
				CommunicationInfo.getCommunicationInfo(),
				parameters);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendStandardCommand(String commandName) {
		sendCommand(commandName+"?nrSteps=1");
	}
	
	public static void executeList(List<Move> list) {
		for (Move move : list) {
			sendCommand("/executeMove" + move.getParameters());
			Global.movesHistory.add(move);
		}
	}
}
