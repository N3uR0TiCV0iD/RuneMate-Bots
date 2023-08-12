package com.loldie.bots.ilikesheep.branches;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.loldie.bots.common.tasks.JumpOverStileTask;
import com.runemate.game.api.hybrid.location.Coordinate;
public class IsNearPenBranch extends LogicAreaBranch
{
	private static final Coordinate SHEEP_PEN_POS = new Coordinate(3200, 3285, 0);
	private static final Area SHEEP_PEN_AREA = new Area.Circular(SHEEP_PEN_POS, 10);
	public IsNearPenBranch()
	{
		super(SHEEP_PEN_AREA, new JumpOverStileTask(), "Sheep Pen");
	}
}
