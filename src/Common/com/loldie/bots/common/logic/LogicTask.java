package com.loldie.bots.common.logic;
import java.util.HashMap;
import com.loldie.bots.common.Utils;
import com.runemate.game.api.hybrid.region.Players;
import com.loldie.bots.common.inherit.StandardTreeBot;
public abstract class LogicTask implements ILogicNode
{
	private static HashMap<Integer, LogicTask> lastTask = new HashMap<Integer, LogicTask>();
	protected void notifyNewTask(String taskDescription)
	{
		int botID = Utils.getBotID();
		if (lastTask.get(botID) != this)
		{
			Utils.debugLog("[BOT-NAME=" + Players.getLocal().getName() + "]: " + taskDescription);
			StandardTreeBot.setUIBotTaskDescription(botID, taskDescription);
			lastTask.put(botID, this);
		}
	}
}
