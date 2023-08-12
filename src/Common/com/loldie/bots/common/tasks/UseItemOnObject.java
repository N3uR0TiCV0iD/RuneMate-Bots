package com.loldie.bots.common.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.GameObjects;
public class UseItemOnObject extends LogicTask
{
	private String itemName;
	private String objectName;
	private String taskDescription;
	public UseItemOnObject(String itemName, String objectName)
	{
		this.itemName = itemName;
		this.objectName = objectName;
		this.taskDescription = "Using " + itemName + " on " + objectName + "...";
	}
	@Override
	public void runNode()
	{
		SpriteItem usingItem = Inventory.newQuery().names(itemName).results().first();
		notifyNewTask(taskDescription);
		if (usingItem != null)
		{
			GameObject targetObject = GameObjects.newQuery().names(objectName).results().nearest();
			if (targetObject != null)
			{
				usingItem.click();
				Utils.turnCameraOrInteract(targetObject, "Use");
			}
		}
	}
}
