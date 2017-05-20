package basicDataSet;


import java.io.Serializable;

import basicDataSet.basicDataSet.Charactor;
import basicDataSet.basicDataSet.MapPoint;

public class Packet  implements Serializable {
	private static final long serialVersionUID = 48763L;

	// Def of _packetID : https://goo.gl/6llUwp
	private static final int MaxPlayerNum  = 4;
	private static int _packetID           = 0;
	private static int _clientNum          = -1;
	private static int _integer            = 0;
	private static Charactor _charactor[]  = new Charactor[MaxPlayerNum];
	private static String _msg             = null;
	private static MapPoint _loc           = null;
	
	
	public Packet(MapPoint loc) {
		_packetID = 102;
		_loc = loc;
	}

	public Packet(int clientNum,Charactor charactor) {
		_packetID = 103;
		_charactor[clientNum] = charactor;
	}

	public Packet(int clientNum, String msg) {
		_packetID = 110;
		_integer = clientNum;
		_msg = msg;
	}

	public Packet(String msg) {
		_packetID = 111;
		_msg = msg;
	}

	public Packet(int clientNum,int functionID,int integer) {
		_packetID  = functionID;
		_clientNum = clientNum;
		_integer   = integer;
	}
	
	public Packet(int functionID,int integer) {
		_packetID  = functionID;
		_integer   = integer;
	}
	
	public int getPacketID(){
		return _packetID;
	}
	public int getClientNum(){
		return _clientNum;
	}
	public int getInteger(){
		return _integer;
	}
	public Charactor[] getAllCharactor(){
		return _charactor;
	}
	public Charactor getCharactor(int clientNum){
		return _charactor[clientNum];
	}
	public String getMsg(){
		return _msg;
	}
	public MapPoint getLoc(){
		return _loc;
	}
}
