package com.loldie.bots.common.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.osrs.local.hud.interfaces.Magic;
public class CastSpellTask extends LogicTask
{
	private Magic osrsMagic;
	private com.runemate.game.api.rs3.local.hud.Powers.Magic rs3Magic;
	public CastSpellTask(Magic osrsMagic, com.runemate.game.api.rs3.local.hud.Powers.Magic rs3Magic)
	{
		this.rs3Magic = rs3Magic;
		this.osrsMagic = osrsMagic;
	}
	public CastSpellTask(Magic osrsMagic)
	{
		this.osrsMagic = osrsMagic;
	}
	public CastSpellTask(com.runemate.game.api.rs3.local.hud.Powers.Magic rs3Magic)
	{
		this.rs3Magic = rs3Magic;
	}
	@Override
	public void runNode()
	{
		if (Environment.isOSRS())
		{
			if (osrsMagic != null)
			{
				notifyNewTask("Using " + osrsMagic.name() + " spell...");
				osrsMagic.activate();
				afterSpellCast();
			}
			else
			{
				notifyNewTask("Using UNDEFINED spell...");
			}
		}
		else if (rs3Magic != null)
		{
			notifyNewTask("Using " + rs3Magic.name() + " spell...");
			rs3Magic.activate();
			afterSpellCast();
		}
		else
		{
			notifyNewTask("Using UNDEFINED spell...");
		}
	}
	protected void afterSpellCast() { } //Virtual method
}
