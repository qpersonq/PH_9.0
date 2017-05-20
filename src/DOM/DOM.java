package DOM;

import java.io.IOException;

import basicDataSet.Packet;
import basicDataSet.basicDataSet;



public class DOM extends basicDataSet{

	private int       _clientNum;
	private int       _time   = 0;
	private Charactor[] _player = null;
	
	
	public DOM() throws IOException {
		super();
	}

	public static void main(String[] args) {
	}
	
	public Charactor getCharactor(int clientNum){
		return _player[clientNum];
	}
	
	public Charactor[] getAllCharactor(){
		return _player;
	}
	
	public int getTime(){
		return _time;
	}
	
	// need fix
	public void receivePacket(Packet packet) throws ClassNotFoundException{

		switch(packet.getPacketID()){
		case 5:
			_clientNum = packet.getClientNum();
		break;
		case 8:
			_time = packet.getInteger();
		break;
		case 103:
			_player = packet.getAllCharactor();
		break;
		case 111:
			// receive msg
			String msg = packet.getMsg();
		break;
		}
	}
	
	//_packetID:101
	public Packet sendMoveCode(int MoveCode){
		return new Packet(_clientNum,101,MoveCode);
	}
	
	//_packetID:102
	public Packet sendPoint(){
		return new Packet(_player[_clientNum].getLoc());
	}
	
	//_packetID:110
	public Packet sendMsg(String msg){
		return new Packet(_clientNum,msg);
	}
	
	// need fix
	public Packet sendFunc(int functionID,int number){
		Packet packet = null;
		
		switch (functionID){
		case 3:			// login request
		case 112: 		// Fundraising party
		case 114:		// walk worker
		case 116:		// make four
		case 118:		// transe money
		case 120:		// muo black
			packet = new Packet(_clientNum,functionID,number);
		}
		
		return packet;
	}
}
