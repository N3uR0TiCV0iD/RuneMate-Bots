package com.loldie.bots.common.tasks;
import java.awt.Color;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.RockType;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.region.GameObjects;
public class MineRocksTask extends LogicTask
{
	private static final Color DISABLED_COLOR = new Color(48, 48, 48);
	private RockType rockType;
	private String taskDescription;
	public MineRocksTask(RockType rockType)
	{
		this.rockType = rockType;
		this.taskDescription = "Mining " + rockType.getName() + " rocks...";
	}
	@Override
	public void runNode()
	{
		GameObject rocks = GameObjects.newQuery().names("Rocks").colorSubstitutions(DISABLED_COLOR, rockType.getColor()).results().first();
		notifyNewTask(taskDescription);
		if (rocks != null)
		{
			Utils.turnCameraOrInteract(rocks, "Mine");
		}
	}
}
