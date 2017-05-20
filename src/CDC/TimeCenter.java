package CDC;

public class TimeCenter implements Runnable{
	
	private int      _leftTime = 0;
	private boolean  _countDown     = false;
	
	public TimeCenter(int time){
		_leftTime = time;
	}
	
	
	public void run(){
		openCountDown();
		while(_countDown && _leftTime >= 0){
			try {
				
				Thread.sleep(1000);
				_leftTime --;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getleftTime(){
		return _leftTime;
	}
	
	public void openCountDown(){
		_countDown = true;
	}
	public void closeCountDown(){
		_countDown = false;
	}
	
	
}
