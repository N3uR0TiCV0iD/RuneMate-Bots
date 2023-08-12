package com.loldie.bots.common.tasks;
import com.loldie.bots.common.inherit.BaseTreeBot;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.script.framework.AbstractBot;
public class StopBotTask extends LogicTask
{
	private String reason;
	private AbstractBot botInstance;
	public StopBotTask(String reason, AbstractBot botInstance)
	{
		this.reason = reason;
		this.botInstance = botInstance;
	}
	@Override
	public void runNode()
	{
		notifyNewTask(BaseTreeBot.stopBot(botInstance, reason));
	}
}
