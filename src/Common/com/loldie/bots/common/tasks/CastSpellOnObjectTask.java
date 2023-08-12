package com.loldie.bots.common.tasks;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.osrs.local.hud.interfaces.Magic;
public class CastSpellOnObjectTask extends CastSpellTask
{
	private String objectName;
	public CastSpellOnObjectTask(Magic osrsMagic, com.runemate.game.api.rs3.local.hud.Powers.Magic rs3Magic, String objectName)
	{
		super(osrsMagic, rs3Magic);
		this.objectName = objectName;
	}
	@Override
	protected void afterSpellCast()
	{
		GameObjects.newQuery().names(objectName).results().nearest().click();
	}
}
