package com.loldie.bots.givemebeer.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.entities.Npc;
public class StartBeerOrderTask extends LogicTask
{
	@Override
	public void runNode()
	{
		Npc barTenderNPC = Npcs.newQuery().names("Emily", "Kaylee").results().nearest();
		notifyNewTask("Starting the beer order...");
		if (barTenderNPC != null)
		{
			Utils.turnCameraOrInteract(barTenderNPC, "Talk-to");
		}
	}
}
