package com.loldie.bots.knightsword.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
public class InFaladorCastleBranch extends LogicAreaBranch
{
	private static final Coordinate CASTLE_POS = new Coordinate(2975, 3340, 0);
	private static final Area CASTLE_AREA = new Area.Circular(CASTLE_POS, 15);
	public InFaladorCastleBranch(ILogicNode truePath)
	{
		super(CASTLE_AREA, truePath, "Falador Castle");
	}
}
