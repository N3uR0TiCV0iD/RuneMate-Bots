package com.loldie.bots.common.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.rs3.local.hud.interfaces.Lodestone;
public class GoToTask extends LogicTask
{
	private static final int LODESTONE_OFFSET_ADD = 5;
	private String taskMessage;
	private Locatable locatable;
	private double lodestoneDistance;
	private Lodestone closestLodestone;
	public GoToTask(String locationName, Locatable locatable)
	{
		this.taskMessage = "Going to " + locationName + "...";
		this.locatable = locatable;
	}
	@Override
	public void runNode()
	{
		Coordinate locatablePos;
		Coordinate localPlayerPos;
		notifyNewTask(taskMessage);
		if (closestLodestone == null)
		{
			updateClosestLodestone();
		}
		locatablePos = locatable.getPosition();
		localPlayerPos = Players.getLocal().getPosition();
		if (lodestoneDistance < localPlayerPos.distanceTo(locatable))
		{
			closestLodestone.teleport();
		}
		else
		{
			Utils.moveTowards(localPlayerPos, locatablePos, locatable);
		}
	}
	private void updateClosestLodestone()
	{
		double closestDistance = Double.MAX_VALUE;
		for (Lodestone currLodestone : Lodestone.values())
		{
			if (currLodestone.isActivated())
			{
				double currDistance = currLodestone.distanceTo(locatable);
				if (currDistance < closestDistance)
				{
					this.lodestoneDistance = currDistance + LODESTONE_OFFSET_ADD;
					this.closestLodestone = currLodestone;
					closestDistance = currDistance;
				}
			}
		}
	}
}
