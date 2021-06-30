import java.util.ArrayList;
import java.util.List;
/**
 * @author anupa
 * Test  to calculate average of integer sum in multithreading environment,
 * To make the opertion atomic, I have created a custom class which maintains sum of all the  values
 * of noon expired key in the cache and length of the map, the method used in updating the variables 
 * of the class are synchronized at object level, so any thread that access that Custom objects method 
 * method while calling put or remove method of the cache, it first updates the cache then calls this method, in this way threads are not blocked while accessing the map inside the cache and can update the sum and length 
 * value of the custom object in atomic manner.
 * 
 */
public class Test3 {
	public static void main(String[] args) {

		
		
		List<ICacheEntriesWriter> writers=new ArrayList<>();
		writers.add(new ExpiredEntriesFileWriter("expired.txt"));

		IntCache cache=new IntCache(TimePeriodUnit.MILISECOND,150,writers);

		Thread t=new Thread(()->{
			for(int i=1;i<=10;i++) {
				cache.put(i, i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}

		}) ;

		t.start();
		/*
		 This thread continuously checks average of all the values 
		  i.e. non expired keys present in the IntCache at time interval of 5ms. until the main thread stops
		  or cache gets empty; 
		    
		*/		Thread count=new Thread(()->{
			try {
				t.join();
				double val=0;
				while(true) {

					val= cache.getAverage();
					System.out.println("Average value of cache is :"+val);
					if(val==0)
					{
						Thread.currentThread().interrupt();
					}	
					Thread.currentThread().sleep(5);

				}
			} catch (InterruptedException e) {
				System.out.println("Current Thread Interrupted while"
						+ " fetrching the average value, "
						+ "possible reason being cache is empty");
			}

		});
		count.setDaemon(true);
		count.start();


		/*
		 * This is a Dameon thread which check if eviction thread is running or not, if
		 * not then it creates a new one so that entries can be evicted.
		 */
		Thread t1=new Thread(()->{
			while(true) {
				try {
					Thread.sleep(5000L);
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
