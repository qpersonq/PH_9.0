package basicDataSet;

public class basicDataSet{


	public class MapPoint{
		int _xAxis, _yAxis;
		public MapPoint(int inputX, int inputY){
			setPoint(inputX,inputY);
		}
		public MapPoint(MapPoint inputPoint){
			this(inputPoint.getX(),inputPoint.getY());
		}
		public int getX() {
			return _xAxis;
		}
		public int getY() {
			return _yAxis;
		}
		public void setX(int inputX) {
			_xAxis = inputX;
		}
		public void setY(int inputY) {
			_yAxis = inputY;
		}
		public void setPoint(int inputX, int inputY) {
			setX(inputX);
			setY(inputY);
		}
		public void setPoint(MapPoint inputPoint) {
			setPoint(inputPoint.getX(), inputPoint.getY());
		}
	}
	
	public class Charactor{
		private final static int iniMoney = 500000;
		private final int _moveX[] = { 0,-1, 0, 1};
		private final int _moveY[] = {-1, 0, 1, 0};
		
		private String _name;
		private int _number;
		private MapPoint _loc;
		private int _direc; // 0:up 1:left 2:down 3:right
		private int _money;
		
		public Charactor(String name,int number,MapPoint beginLoc,int direc,int money) {
			_name = name;
			_number = number;
			_loc = beginLoc;
			_direc = direc;
			_money = money;
		}
		public Charactor(String name,int number,MapPoint beginLoc){
			this(name,number,beginLoc,2,iniMoney);
		}
		public Charactor(){
			this("NOBITA",1,new MapPoint(0,0));
		}

		public int getX() {
			return _loc.getX();
		}
		public int getY() {
			return _loc.getY();
		}
		public MapPoint getLoc(){
			return _loc;
		}
		public int getDirec(){
			return _direc;
		}
		public int getMoney(){
			return _money;
		}
		public int getNumber(){
			return _number;
		}
		public String getName(){
			return _name;
		}
		public void moveChacrator(int moveCode) {
			_loc.setPoint(getX()+_moveX[moveCode],getY()+_moveY[moveCode]);
			_direc = moveCode;
		}
		public void setLoc(MapPoint newLoc) {
			_loc.setPoint(newLoc.getX(),newLoc.getY());
		}

		public MapPoint newLoc(int direcCode){
			return new MapPoint(_loc.getX()+_moveX[direcCode],_loc.getY()+_moveY[direcCode]);
		}

		public void setDirec(int direcCode){
			_direc = direcCode;
		}
		public void setMoney(int money){
			_money = money;
		}
		
		public void printCharactor(){
			System.out.println(_name+"  "+_number);
			System.out.println("("+_loc.getX()+","+_loc.getY()+")"+"  "+_direc);
			System.out.println(_money);
		}
		
		
		
	}

}
