package com.loldie.bots.common.logic;
import java.util.HashMap;
import com.loldie.bots.common.Utils;
import com.runemate.game.api.hybrid.region.Players;
public abstract class LogicTask implements ILogicNode
{
	private static HashMap<Long, LogicTask> lastTask;
	static
	{
		lastTask = new HashMap<Long, LogicTask>();
	}
	protected void notifyNewTask(String message)
	{
		long botID = Utils.getBotID();
		if (lastTask.get(botID) != this)
		{
			System.out.println("[BOT-NAME=" + Players.getLocal().getName() + "]: " + message);
			lastTask.put(botID, this);
		}
	}
}
