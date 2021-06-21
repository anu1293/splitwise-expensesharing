import java.util.ArrayList;
import java.util.List;

/**
 * @author anupa
 * Test  when eviction time is 100 ms , 1st 5 elements are put before 1st 100 ms 
 * and later  5 more elements are put after 110 ms, this should result in cache having only 5 elements 
 * 
 *
 */
public class Test1 {

	public static void main(String[] args) {
		List<ICacheEntriesWriter> writers=new ArrayList<>();
		writers.add(new ExpiredEntriesFileWriter("expired.txt"));
		
		Cache<Integer, Integer> cache=new Cache(TimePeriodUnit.MILISECOND,100,writers);

		Thread t=new Thread(()->{
			for(int i=0;i<5;i++) {
				cache.put(i, i);

			}
			try {
				Thread.sleep(120);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			for(int i=5;i<10;i++) {
				cache.put(i, i);
			}
			for(int i=5;i<10;i++) {
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
