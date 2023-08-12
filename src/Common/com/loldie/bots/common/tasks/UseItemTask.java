package com.loldie.bots.common.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
public class UseItemTask extends LogicTask
{
	private String itemName;
	public UseItemTask(String itemName)
	{
		this.itemName = itemName;
	}
	@Override
	public void runNode()
	{
		SpriteItem usingItem = Inventory.newQuery().names(itemName).results().first();
		notifyNewTask("Clicking on " + itemName + "...");
		if (usingItem != null)
		{
			usingItem.click();
		}
	}
}
