public interface ICache<K,V> {
	void put(K key, V value);
    void remove(K key);
    V get(K key);
    void clear();
    long size();
}
