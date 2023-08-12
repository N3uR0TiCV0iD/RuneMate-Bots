package com.loldie.bots.common;

public enum MembershipPreference
{
	NONE(0),
	PREFERED(1),
	REQUIRED(2);
	
	private int id;
	private MembershipPreference(int id)
	{
		this.id = id;
	}
	public int getID()
	{
		return id;
	}
}
