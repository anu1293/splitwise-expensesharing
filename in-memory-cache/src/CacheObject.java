import java.lang.ref.SoftReference;
import java.sql.Time;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

 public class CacheObject<K,V> implements Delayed {

	 private  K key;
	 private  SoftReference<V> reference;
	 private  long expiryTime;
	     
	 public CacheObject(K key, SoftReference<V> reference, long expiryTime) {
			super();
			this.key = key;
			this.reference = reference;
			this.expiryTime = expiryTime;
		}
	     
     
	 public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public SoftReference<V> getReference() {
		return reference;
	}

	public void setReference(SoftReference<V> reference) {
		this.reference = reference;
	}

	
	public long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

	@Override
     public long getDelay(TimeUnit unit) {
         return unit.convert(expiryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
     }

     @Override
     public int compareTo(Delayed o) {
         return Long.compare(expiryTime, ((CacheObject) o).expiryTime);
     }
     
     public String toString() {
		return "key="+this.key+", value="+this.reference.get()+", expired at:"+new Time(this.expiryTime);
    	 
     }
     
     
     public boolean equals(Object o) {
    	return this.key.equals(((CacheObject)o).key);
    
     }
     
     
     public int hashcode() {
    	 return this.key.hashCode();
     }
 }
