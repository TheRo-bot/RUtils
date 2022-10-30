package dev.ramar.utils;

import java.util.Set;

import java.util.Map;
import java.util.HashMap;

public class HiddenMap<K, V>
{
	protected Map<K, V> map = null;

	public HiddenMap()
	{
		this.map = new HashMap<>();
	}

	public HiddenMap(Map<K, V> map)
	{
		this.map = map;
		if( this.map == null )
			this.map = new HashMap<>();
	}

	/* Accessors
	--===----------
	*/

	public boolean hasKey(K k)
	{
		return this.map.containsKey(k);
	}

	public boolean hasVal(V v)
	{
		return this.map.containsValue(v);
	}



	public int size()
	{
		return this.map.size();
	}

	public V get(K k)
	{
		return this.map.get(k);
	}



	public Set<K> keySet()
	{
		return this.map.keySet();
	}

	/* Mutators
	--===---------
	*/

	public V put(K k, V v)
	{
		return this.map.put(k, v);
	}

	public V remove(K k)
	{
		return this.map.remove(k);
	}
}