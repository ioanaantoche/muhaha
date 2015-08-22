package ro.pub.cti.utils;

import java.util.ArrayList;
import java.util.List;

import ro.pub.cti.communication.CommunicationInfo;
import ro.pub.cti.move.Move;


public class Global {
	public static CommunicationInfo communicationInfo = null;
	public static int volume = 0;
	public static List<Move> movesHistory = new ArrayList<Move>();
	public static List<Move> queueMoves = new ArrayList<Move>();
}
