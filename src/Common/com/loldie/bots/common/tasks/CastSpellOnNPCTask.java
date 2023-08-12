package com.loldie.bots.common.tasks;
import com.loldie.bots.common.Utils;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.osrs.local.hud.interfaces.Magic;
public class CastSpellOnNPCTask extends CastSpellTask
{
	private String npcName;
	public CastSpellOnNPCTask(Magic osrsMagic, com.runemate.game.api.rs3.local.hud.Powers.Magic rs3Magic, String npcName)
	{
		super(osrsMagic, rs3Magic);
		this.npcName = npcName;
	}
	@Override
	protected void afterSpellCast()
	{
		Npc targetNpc = Npcs.newQuery().names(npcName).results().nearest();
		if (targetNpc != null)
		{
			Utils.turnCameraOrInteract(targetNpc, "Cast");
		}
	}
}
