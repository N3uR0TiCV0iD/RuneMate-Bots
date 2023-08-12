package com.loldie.bots.common;
public class NoAvailableWorldsException extends Exception
{
	private static final long serialVersionUID = -2833613029367109157L;
	public NoAvailableWorldsException()
	{
		super("The selected worlds are either currently occupied or have a cooldown");
	}
}
