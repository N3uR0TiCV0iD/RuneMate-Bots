package com.loldie.bots.common.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.rs3.local.hud.interfaces.Lodestone;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
public class GoToTask extends LogicTask
{
	private static final int MAX_REGIONPATH_DISTANCE = 50;
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
			if (Math.abs(localPlayerPos.getX() - locatablePos.getX()) <= MAX_REGIONPATH_DISTANCE &&
				Math.abs(localPlayerPos.getY() - locatablePos.getY()) <= MAX_REGIONPATH_DISTANCE)
			{
				try
				{
					RegionPath.buildTo(locatable).step();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			else
			{
				BresenhamPath.buildTo(locatable).step();
			}
		}
	}
	private void updateClosestLodestone()
	{
		double currDistance;
		double closestDistance = Double.MAX_VALUE;
		for (Lodestone currLodestone : Lodestone.values())
		{
			if (currLodestone.isActivated())
			{
				currDistance = currLodestone.distanceTo(locatable);
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
