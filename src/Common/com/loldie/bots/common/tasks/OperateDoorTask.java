package com.loldie.bots.common.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.location.Coordinate;
public class OperateDoorTask extends LogicTask
{
	private Coordinate doorPos;
	public OperateDoorTask(Coordinate doorPos)
	{
		this.doorPos = doorPos;
	}
	@Override
	public void runNode()
	{
		GameObject door = GameObjects.newQuery().names("Door").results().nearestTo(doorPos);
		notifyNewTask("Operating door...");
		if (door != null)
		{
			Utils.turnCameraOrClick(door);
		}
	}
}
