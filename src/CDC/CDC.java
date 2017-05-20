package CDC;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


import basicDataSet.basicDataSet;
import basicDataSet.basicDataSet.MapPoint;
import basicDataSet.Packet;
import map.ReadMap;



public class CDC extends basicDataSet{
	
	private static String	CandicateFILE = "src/Res/CandicateInformationPreview.csv";
	private static final int MaxPlayerNum = 4;
	private static final int iniGameTime = 600;
	private static int PlayerNum;
	private static int LoginPlayerNum;
	private static ReadMap _map;
	
	
	private static String[] _candicateName  = new String[MaxPlayerNum]; 
	private static int[]    _candicateNum   = new int[MaxPlayerNum];
	private static int[]    _candicateMoney = new int[MaxPlayerNum];
	private static CDCCandicate _player[]  = new CDCCandicate[MaxPlayerNum];
	private static Votes _votes;
	
	private static TimeCenter _timeCenter = new TimeCenter(iniGameTime);
	
	public CDC() throws IOException {
		super();
		_map = new ReadMap();
		LoginPlayerNum = 0;
		_votes  = new Votes();
		ReadCandicate();
	}
	
	// main: for testing
	public static void main(String args[]) throws Exception {
		System.out.println("testing CDC");
		CDC cdc = new CDC();
	}
	
	private static class Votes{
		
		private static final String	VoteFILE = "src/Res/NumberofVote.csv";
		private static int[][] _vote;
		private static int[]   _cityCode;
		private static int _cityNum;
		private static int _candidateNum; // _candidateNum-1 : VoidBallot
		
		
		private Votes() throws IOException{
			BufferedReader	read = null;
			String			line;
			String 			csvSplitBy = ",";
			String[]		linecode;

			read        = new BufferedReader(new FileReader(VoteFILE));
			line        = read.readLine();
			linecode    = line.split(csvSplitBy);
			_candidateNum    = Integer.parseInt(linecode[0]);
			_cityNum         = Integer.parseInt(linecode[1]);
			_vote	         = new int[_candidateNum][_cityNum];
			_cityCode        = new int[_cityNum];
			
			line = read.readLine();
			linecode = line.split(csvSplitBy);
			for(int i = 0 ; i < _cityNum; i++){
				_cityCode[i] = Integer.parseInt(linecode[i]);
			}
			for (int i = 0; i < _candidateNum; i ++) {
				line = read.readLine();
				linecode = line.split(csvSplitBy);
				for (int j = 0; j < _cityNum; j ++) {
					_vote[i][j] = Integer.parseInt(linecode[j]);
				}
			}
			read.close();
		}
		
		private boolean transVote(int candidateNum, int victimNum , int cityCode, int voteNum ){
			int cityIndex = getCityIndex(cityCode);
			if( candidateNum < 0  || candidateNum >=  _candidateNum ||   cityIndex < 0 || cityIndex>= _cityNum || victimNum < 0 || victimNum >= _candidateNum){
				System.out.println("Invalid parameter for addfromOther().");
				return false;
			}
			if(_vote[victimNum][cityIndex] >=  voteNum ){
				_vote[victimNum][cityIndex] -= voteNum;
				_vote[candidateNum][cityIndex] += voteNum;
				return true;
			}
			return false;
		}
		
		private boolean transPercentVote(int candidateNum, int victimNum , int cityCode, int votePercent ){
			int cityIndex = getCityIndex(cityCode);
			if( candidateNum < 0  || candidateNum >=  _candidateNum ||   cityIndex < 0 || cityIndex>= _cityNum || victimNum < 0 || victimNum >= _candidateNum){
				System.out.println("Invalid parameter for addfromOther().");
				return false;
			}
			int trans = (int) (_vote[victimNum][cityIndex]*votePercent)/100;
			_vote[victimNum][cityIndex] -= trans;
			_vote[candidateNum][cityIndex] += trans;
			return true;

		}
		
		private int totalVotes(int candidateNum){
			int count = 0;
			for(int i = 0 ; i < _cityNum; i++){
				count += _vote[candidateNum][i];
			}
			return count;
		}
		
		private int getCityIndex(int CityCode){
			return Arrays.binarySearch(_cityCode,CityCode);
		}

	}
	
	public class CDCCandicate extends Charactor{
		private CDCCandicate(String name,int number,MapPoint beginLoc,int direc,int money){
			super(name,number,beginLoc,direc,money);
		}
		private CDCCandicate(String name,int number,MapPoint beginLoc){
			super(name,number,beginLoc);
		}
		private CDCCandicate(){
			super();
		}
	
		private void Move(int moveCode){
			if(canMove(moveCode)){
				moveChacrator(moveCode);
			}
		}
		private boolean canMove(int moveCode){
			MapPoint newLoc = this.newLoc(moveCode);
			if(newLoc.getX() < 0 || newLoc.getX() >= _map.Col() 
					|| newLoc.getY()<0 || newLoc.getY() >= _map.Rol()){
				System.out.println("Out of map");
				return false;
			}
			
			int mapCode = _map.Map()[newLoc.getX()][newLoc.getY()];
			return (mapCode!=8)&&(mapCode!=9);
		}
		
		private void addMoney(int lowerBound,int upperBound){
			int diff = upperBound-lowerBound;
			setMoney(getMoney()+(int)(Math.random()*diff+lowerBound));
		}
	}


	public void addPlayer(int clientNum){
		_player[clientNum] = new CDCCandicate(_candicateName[clientNum],_candicateNum[clientNum],
				new MapPoint(33,18),2,_candicateMoney[clientNum]);
	}
	public void addAllPlayer(){
		for(int i = 0 ; i < PlayerNum; i++){
			addPlayer(i);
		}
	}

	public static void ReadCandicate() throws IOException{
		BufferedReader	read = null;
		String			line;
		String 			csvSplitBy = ",";
		String[]		linecode;
		
		File file   = new File(CandicateFILE);
		read        = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		line        = read.readLine();
		PlayerNum = Integer.parseInt(line);
		for(int i = 0 ; i < PlayerNum; i++){
			line = read.readLine();
			linecode = line.split(csvSplitBy);
			
			_candicateName[i]  = linecode[0];
			_candicateNum[i]   = Integer.parseInt(linecode[1]);
			_candicateMoney[i] = Integer.parseInt(linecode[2]);
		}
		read.close();
	}

	public CDCCandicate getPlayer(int clientNum){
		return _player[clientNum];
	}
	
	public void printPlayer(int clientNum){
		_player[clientNum].printCharactor();
	}
	
	public void startCountDown(){
		_timeCenter.openCountDown();
	}

	
	
	// method below:  about Packet
	// need fixed
	public Packet receivePacket(Packet packet) throws ClassNotFoundException{
		
		int clientNum = packet.getClientNum();

		switch (packet.getPacketID()){
		
		case 3:
			// receive login request
			return ClientNum(clientNum);
		case 101:
			// receive moveCode
			return Move(clientNum,packet.getInteger());
		case 102:
			// receive loc (useless now)
		break;
		case 110:
			//receive msg
		break;
		case 112:
			// Fundraising party
			_player[clientNum].addMoney(30000, 50000);
		break;
		case 114:
			// walk worker
			//_votes.transVote(clientNum, MaxPlayerNum, cityCode, packet.getInteger());
		break;
		case 116:
			// make four
			for(int i = 0 ; i < MaxPlayerNum; i++){
			//	_votes.transPercentVote(clientNum, i, cityCode, 1);
			}
		break;
		case 118:
			// transe money
			
		break;
		case 120:
			// muo black
			//
		break;
		}
		return null;
	}
	
	//_packetID:5
	public Packet ClientNum(int clientNum){
		return new Packet(5,clientNum,clientNum);
	}
	
	//_packetID:8
	public Packet LeftTime(int time){
		return new Packet(8,time);
	}
	
	
	//_packetID:103
	public Packet Move(int clientNum,int moveCode){
		_player[clientNum].Move(moveCode);
		Packet packet = new Packet(clientNum,_player[clientNum]);
		try {
			return packet;
		} catch (Exception e) {
			System.out.println("Movefalse");
			e.printStackTrace();
			return null;
		}
	}
	
	//_packetID:111
	public Packet BroadCast(String msg){
		Packet packet = new Packet(msg);
		try {
			return packet;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//_packetID:122 need fixed
	public Packet GameResult(){
		Packet packet = null;
		return packet;
	}
}
