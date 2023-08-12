package com.loldie.bots.dotutorial.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
public class IsInRatPitBranch  extends LogicAreaBranch
{
	public static final Coordinate PIT_POS = new Coordinate(3103, 9518, 0);
	public static final Area PIT_AREA = new Area.Circular(PIT_POS, 7);
	public IsInRatPitBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(PIT_AREA, truePath, falsePath);
	}
}
