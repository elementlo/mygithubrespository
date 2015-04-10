package net.wash8.customview.AsortList.sort;
/***
 * ����ӿڣ�����V value����K key
 *
 * @param <K>
 * @param <V>
 */
public interface KeySort<K, V> {
	public K getKey(V v);
}
