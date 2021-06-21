import java.util.ArrayList;
import java.util.List;
/**
 * @author anupa
 * This is a simple Driver class, Test Classes are provided as Test1.java and Test2.java
 * 
 *
 */
public class DriverClass {

	public static void main(String[] args) {

		List<ICacheEntriesWriter> writers=new ArrayList<>();
		
		writers.add(new ExpiredEntriesFileWriter("expired.txt"));
		Cache<Integer, Integer> cache=new Cache(TimePeriodUnit.MILISECOND,100,writers);

		Thread t=new Thread(()->{
			for(int i=0;i<200;i++) {
				cache.put(i, i);

			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}


		}) ;
		t.start();
		
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

