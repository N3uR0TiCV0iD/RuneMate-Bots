package com.loldie.bots.common.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.loldie.bots.common.tasks.OperateDoorTask;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.location.Coordinate;
public class IsDoorOpenBranch extends LogicBranch
{
	private Coordinate doorPos;
	public IsDoorOpenBranch(Coordinate doorPos, ILogicNode truePath)
	{
		this(doorPos, truePath, new OperateDoorTask(doorPos));
	}
	public IsDoorOpenBranch(Coordinate doorPos, ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
		this.doorPos = doorPos;
	}
	@Override
	protected boolean condition()
	{
		GameObject door = GameObjects.newQuery().names("Door").results().nearestTo(doorPos);
		return door != null && door.getDefinition().getActions().contains("Close");
	}
}
