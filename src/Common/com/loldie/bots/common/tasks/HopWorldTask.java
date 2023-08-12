package com.loldie.bots.common.tasks;
import com.loldie.bots.common.NoAvailableWorldsException;
import com.loldie.bots.common.inherit.BaseTreeBot;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop;
public class HopWorldTask extends LogicTask
{
	private BaseTreeBot botInstance;
	public HopWorldTask(BaseTreeBot botInstance)
	{
		this.botInstance = botInstance;
	}
	@Override
	public void runNode()
	{
		try
		{
			Shop.close();
			botInstance.joinNewWorld();
		}
		catch (NoAvailableWorldsException ex)
		{
			ex.printStackTrace();
			botInstance.stop(ex.getMessage());
		}
	}
}
