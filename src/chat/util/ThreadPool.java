package chat.util;

/**
 * ThreadPool class using ObjectPool pattern.
 * Used to keep a track of threads.
 *
 */
public class ThreadPool implements Pool{
	
	private Thread threadArray[];
	private int threadStatus[];
	private volatile static ThreadPool onlyInstance;
	
	/**
	 * Private constructor for ThreadPool class(Singleton)
	 * initializes thread array equal to input size and status = 0
	 * @param size
	 */
	private ThreadPool(int size){
		
		threadArray = new Thread[size];
		threadStatus = new int[size];
		for(int i=0;i<5;i++){
			threadStatus[i] = 0;
		}
	}
	
	/**
	 * static method to return the instance of ThreadPool
	 * @return ThreadPool instance
	 */
	public static ThreadPool getInstance(){	
		if(onlyInstance == null){
			synchronized(ThreadPool.class){
				if(onlyInstance == null)
					onlyInstance = new ThreadPool(5);
			}
		}
		return onlyInstance;
	}
	/**
	 * This method will return a available thread from the pool
	 * @return available Thread else null
	 */
	public Thread borrowThread(){
		Thread t = null;
		for(int i=0;i<5;i++){
			if(threadStatus[i] == 0){
				t = threadArray[i];
				threadStatus[i] = 1;
				break;
			}
		}
		return t;
	}
	public void returnThread(int i){
		threadStatus[i] = 0;
	}
}
