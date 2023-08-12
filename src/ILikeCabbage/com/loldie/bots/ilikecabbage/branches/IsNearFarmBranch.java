package com.loldie.bots.ilikecabbage.branches;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.loldie.bots.common.tasks.JumpOverStileTask;
public class IsNearFarmBranch extends LogicAreaBranch
{
	private static final Coordinate CABBAGE_FARM_POS = new Coordinate(3063, 3277, 0);
	private static final Area CABBAGE_FARM_AREA = new Area.Circular(CABBAGE_FARM_POS, 10);
	public IsNearFarmBranch()
	{
		super(CABBAGE_FARM_AREA, new JumpOverStileTask(), "Cabbage farm");
	}
}
