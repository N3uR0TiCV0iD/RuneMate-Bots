package com.loldie.bots.common.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.entities.Npc;
public class StartFishingTask extends LogicTask
{
	@Override
	public void runNode()
	{
		Npc fishingSpot = Npcs.newQuery().names("Fishing spot").results().nearest();
		notifyNewTask("Fishing...");
		if (fishingSpot != null)
		{
			Utils.debugLog("Fishing spot @ " + fishingSpot.getPosition());
			Utils.turnCameraOrClick(fishingSpot);
		}
	}
}
