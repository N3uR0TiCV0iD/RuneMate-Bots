package com.loldie.bots.common.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.loldie.bots.common.locations.InWildernessBranch;
public class JumpOverWildernessTask extends LogicTask
{
	public static final JumpOverWildernessTask INSTANCE = new JumpOverWildernessTask();
	private JumpOverWildernessTask() { }
	@Override
	public void runNode()
	{
		Coordinate localPlayerPos = Players.getLocal().getPosition();
		notifyNewTask("Jumping over wilderness ditch...");
		if (Math.abs(localPlayerPos.getY() - InWildernessBranch.Y_POS_START) > 10)
		{
			Utils.moveTowards(localPlayerPos, new Coordinate(localPlayerPos.getX(), InWildernessBranch.Y_POS_START, 0));
		}
		else
		{
			GameObject wildernessDitch = GameObjects.newQuery().names("Wilderness Ditch").results().nearest();
			if (wildernessDitch != null)
			{
				Utils.turnCameraOrInteract(wildernessDitch, "Cross");
			}
		}
	}
}
