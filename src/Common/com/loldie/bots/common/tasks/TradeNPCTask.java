package com.loldie.bots.common.tasks;
public class TradeNPCTask extends InteractWithNPCTask
{
	public TradeNPCTask(String npcName)
	{
		super("Trading with " + npcName + "...", "Trade", npcName);
	}
}
