package dev.ramar.utils;


import java.util.List;
import java.util.ArrayList;

import java.util.Collection;

import java.util.Map;
import java.util.HashMap;

import java.util.Random;


public class Timer implements Comparable<Timer>
{

	public interface TimerListener
	{
		public void timerComplete(String name);
	}

	/* Static Implementation
	---------------------------
	*/

	private static long pauseDelay = 0;
	private static long startPause = 0;

	private static List<Timer> timerCache = new ArrayList<>();
	private static List<Timer> sortedTimerList = java.util.Collections.synchronizedList(new SortedSinglyDouble<Timer>());

	public static void pause()
	{
		if( startPause != 0 )
			throw new IllegalStateException("Already paused!");

		startPause = System.currentTimeMillis();
	}


	public static void unpause()
	{
		if( startPause == 0 )
			throw new IllegalStateException("Already unpaused!");

		pauseDelay += Math.abs(System.currentTimeMillis() - startPause);

		startPause = 0;
	}

	public static void wait(long delay, String name, TimerListener tl)
	{
		long time = System.currentTimeMillis();
		// System.out.println("'" + name + "' waiting until " + (time + delay) + " || (" + time + ")");
		waitUntil(time + delay, name, tl);
	}


	public static void waitUntil(long epochTime, String name, TimerListener tl)
	{
		Timer t = getTimer(epochTime);
		t.setTime(epochTime);
		t.addListenerTo(name, tl);
		sortedTimerList.add(t);
	}


	private static List<Timer> toRemove = new SinglyDouble<>();

	public static void update()
	{
		long time = System.currentTimeMillis();

		toRemove.clear();

		int index = 0;

		for( Timer t : sortedTimerList)
		{
			if( t != null )
			{
				// if this timer ends in the future,
				if( Long.compare(time + pauseDelay, t.getTime() + pauseDelay) <= 0 )
				{
					// System.out.println("current time: " + System.currentTimeMillis());
					// System.out.println("[Timer Update] time " + time  + " > " + t.getTime() + " | " + sortedTimerList);
					break;
				}
				// otherwise
				t.complete();
				t.wipe();

				toRemove.add(t);
				index++;

			}
		}


		int a = 0;
		for( Timer t : toRemove )
		{
			sortedTimerList.remove(t);
		}

	}


	/*
	Method: getTimer
	 - Accesses the first not in use timer, wipes it, and returns it
	*/
	private static Timer getTimer(long epochTime)
	{
		Timer exportTimer = null;

		// Test if we already have a Timer for this time

		for( Timer t : sortedTimerList )
		{
			if( t != null && t.getTime() == epochTime )
			{
				exportTimer = t;
			}
		}

		// if not,
		if( exportTimer == null )
		{
			// search the cache for a timer no-longer in use so we can re-use it
			int index = 0;
			for( Timer t : timerCache )
			{
				t.wipe();
				exportTimer = t;
				timerCache.remove(index);
				index++;
			}

			// if there was none
			if( exportTimer == null )
			{
				exportTimer = new Timer();
			}
		}

		exportTimer.setTime(epochTime);

		return exportTimer;
	}





	/* Object implementation
	----------------------------
	*/

	public long epochTime;

	public Map<String, List<TimerListener>> namedListeners = new HashMap<>();

	private Timer() {}


	private Timer(long epoch)
	{
		setTime(epoch);
	}

	/* Comparable implementation
	--------------------------------
	*/


	public Long getValue()
	{
		return epochTime;
	}


	public int compareTo(Timer t)
	{
		// consider null as 0
		if( t == null )
			return Long.compare(epochTime, 0);
		
		return Long.compare(epochTime, t.getTime());
	}

	/* Object implementation
	---------------------
	*/

	public String toString()
	{
		return "{" + epochTime + "}";
	}

	public long getTime()
	{
		return epochTime;
	}

	public boolean isInUse()
	{
		return ! namedListeners.isEmpty();
	}


	public void wipe()
	{
		namedListeners.clear();
		epochTime = -1;
	}


	public void setTime(long epoch)
	{
		this.epochTime = epoch;
	}


	public void addListenerTo(String name, TimerListener tl)
	{

		List<TimerListener> bucket = namedListeners.get(name);
		if( bucket == null )
		{
			bucket = new ArrayList<>(5);
			namedListeners.put(name, bucket);			
		}
		bucket.add(tl);
	}
	
	
	public void addAllListenersTo(String name, Collection<TimerListener> c)
	{
		for( TimerListener tl : c )
		{
			addListenerTo(name, tl);
		}
	}


	public void complete()
	{
		for( String thisName : namedListeners.keySet() )
		{
			for( TimerListener tl : namedListeners.get(thisName) )
			{
				tl.timerComplete(thisName);
			}
		}
	}


	/* TESTING MAIN
	--------------------
	*/

	private static int nComplete = 0;

	public static void main(String[] args)
	{
		System.out.println("Timer main " + System.currentTimeMillis());

		SortedSinglyDouble<Integer> list = new SortedSinglyDouble();

		for( int ii = 0; ii < 10; ii++ )
		{
			list.add(ii);
			System.out.println("head: " + list.getNode(0)  + " (" + list.size() + ")");
		}

		while(! list.isEmpty())
		{
			System.out.println("head: " + list.getNode(0) + " (" + list.size() + ")");
			list.remove(list.size() - 1);
		}

	}


	private static List<Integer> ints = new ArrayList<>();

	public static int getUniqueInt(int max)
	{
		Random rd = new Random();

		int expInt = rd.nextInt(max);
		while(ints.contains(expInt) )
		{
			expInt = rd.nextInt(max);
		}

		ints.add(expInt);
		return expInt;
	}
}