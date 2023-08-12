package com.loldie.bots.common.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
public class UseItemOnItemTask extends LogicTask
{
	private String usingItemName;
	private String targetItemName;
	private String taskDescription;
	public UseItemOnItemTask(String usingItemName, String targetItemName)
	{
		this.usingItemName = usingItemName;
		this.targetItemName = targetItemName;
		this.taskDescription = "Using " + usingItemName + " on " + targetItemName + "...";
	}
	@Override
	public void runNode()
	{
		SpriteItem usingItem = Inventory.newQuery().names(usingItemName).results().first();
		notifyNewTask(taskDescription);
		if (usingItem != null)
		{
			SpriteItem targetItem = Inventory.newQuery().names(targetItemName).results().first();
			if (targetItem != null)
			{
				usingItem.click();
				targetItem.click();
			}
		}
	}
}
