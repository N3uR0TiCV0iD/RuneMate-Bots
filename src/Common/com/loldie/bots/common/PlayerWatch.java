package com.loldie.bots.common;
public class PlayerWatch
{
	private long lastSeen;
	private long seenSince;
	public PlayerWatch()
	{
		this.lastSeen = System.currentTimeMillis();
		this.seenSince = lastSeen;
	}
	public long getTimeSinceLastSeen()
	{
		return System.currentTimeMillis() - lastSeen;
	}
	public long getTimeSinceFirstSeen()
	{
		return System.currentTimeMillis() - seenSince;
	}
	public void updateLastSeen()
	{
		lastSeen = System.currentTimeMillis();
	}
}
