package com.loldie.bots.ilikesheep.branches;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.loldie.bots.ilikesheep.tasks.JumpOverStileTask;
public class IsNearPenBranch extends LogicAreaBranch
{
	private static final int SHEEP_PEN_RANGE = 10;
	private static final Coordinate SHEEP_PEN_POS = new Coordinate(3200, 3285, 0);
	private static final Area SHEEP_PEN_AREA = new Area.Circular(SHEEP_PEN_POS, SHEEP_PEN_RANGE);
	public IsNearPenBranch()
	{
		super(SHEEP_PEN_AREA, new JumpOverStileTask(), "Sheep Pen");
	}
}
