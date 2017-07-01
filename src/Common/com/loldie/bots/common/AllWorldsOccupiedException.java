package com.loldie.bots.common;
public class AllWorldsOccupiedException extends Exception
{
	private static final long serialVersionUID = -2833613029367109157L;
	public AllWorldsOccupiedException()
	{
		super("All known worlds are occupied!");
	}
}
