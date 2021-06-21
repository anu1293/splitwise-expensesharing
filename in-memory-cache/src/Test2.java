import java.util.ArrayList;
import java.util.List;
/**
 * @author anupa
 * Test  when eviction time is 100 ms , 1st all elements are put initially 
 * and are accessed after 100 ms 
 * 
 */
public class Test2 {
	public static void main(String[] args) {
		List<ICacheEntriesWriter> writers=new ArrayList<>();
		writers.add(new ExpiredEntriesFileWriter("expired.txt"));
		
		Cache<Integer, Integer> cache=new Cache(TimePeriodUnit.MILISECOND,50,writers);

		Thread t=new Thread(()->{
			for(int i=0;i<10;i++) {
				cache.put(i, i);

			}
			try {
				Thread.sleep(110);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			
			for(int i=0;i<10;i++) {
				System.out.println(cache.get(i));
			}
			

		}) ;
		t.start();
		
		
		/*
		 * This is a Dameon thread which check if eviction thread is running or not, if
		 * not then it creates a new one so that entries can be evicted.
		 */
		Thread t1=new Thread(()->{
			while(true) {
				try {
					Thread.sleep(1000L);
					cache.runCleaningThread();
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			}
		});
		t1.setDaemon(true);
		t1.start();

		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}


	}

}
