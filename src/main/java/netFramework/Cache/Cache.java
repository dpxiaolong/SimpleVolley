package netFramework.Cache;

/**
 * Created by luozhenlong.
 */

public interface Cache<K, V> {

    public V get(K key);

    public void put(K key, V value);

    public void remove(K key);

}
