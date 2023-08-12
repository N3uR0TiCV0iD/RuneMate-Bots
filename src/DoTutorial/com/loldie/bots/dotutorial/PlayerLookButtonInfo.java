package com.loldie.bots.dotutorial;
public class PlayerLookButtonInfo
{
	private int interfaceID;
	private int maleChoices;
	private int femaleChoices;
	public PlayerLookButtonInfo(int interfaceID, int totalChoices)
	{
		this(interfaceID, totalChoices, totalChoices);
	}
	public PlayerLookButtonInfo(int interfaceID, int maleChoices, int femaleChoices)
	{
		this.interfaceID = interfaceID;
		this.maleChoices = maleChoices;
		this.femaleChoices = femaleChoices;
	}
	public int getInterfaceID()
	{
		return interfaceID;
	}
	public int getMaleChoices()
	{
		return maleChoices;
	}
	public int getFemaleChoices()
	{
		return femaleChoices;
	}
}
