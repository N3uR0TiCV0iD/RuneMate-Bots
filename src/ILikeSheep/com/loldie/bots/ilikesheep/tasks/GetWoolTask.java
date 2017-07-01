package com.loldie.bots.ilikesheep.tasks;
import java.util.Random;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.queries.results.LocatableEntityQueryResults;
public class GetWoolTask extends LogicTask
{
	private static final int BLACKSHEEP_NPC_ID = 8876;
	private static final int FAKESHEEP_NPC_ID = 3579;
	private boolean hasFakedIt;
	private Random generator;
	public GetWoolTask()
	{
		this.generator = new Random();
	}
	@Override
	public void runNode()
	{
		Npc sheep;
		notifyNewTask("Getting wool...");
		if (hasFakedIt || generator.nextInt(100) >= 5)
		{
			LocatableEntityQueryResults<Npc> sheeps = Npcs.newQuery().names("Sheep").actions("Shear").results();
			boolean invalidSheep;
			sheep = sheeps.nearest();
			invalidSheep = sheep != null;
			while (invalidSheep)
			{
				switch (sheep.getId())
				{
					case FAKESHEEP_NPC_ID:
					case BLACKSHEEP_NPC_ID:
						sheeps.remove(sheep);
						sheep = sheeps.nearest();
						if (sheep == null)
						{
							invalidSheep = false;
						}
					break;
					default:
						invalidSheep = false;
					break;
				}
			}
			hasFakedIt = false;
		}
		else
		{
			sheep = Npcs.newQuery().names("Sheep").actions("Talk-to").results().first();
			hasFakedIt = true;
		}
		if (sheep != null)
		{
			Utils.turnCameraOrInteract(sheep, "Shear");
		}
	}
}
