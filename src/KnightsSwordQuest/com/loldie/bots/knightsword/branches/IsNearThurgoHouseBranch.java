package com.loldie.bots.knightsword.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.runemate.game.api.hybrid.location.Coordinate;
public class IsNearThurgoHouseBranch extends LogicAreaBranch
{
	private static final Coordinate HOUSE_POS = new Coordinate(3028, 3379, 0);
	private static final Area HOUSE_AREA = new Area.Circular(HOUSE_POS, 10);
	public IsNearThurgoHouseBranch(ILogicNode truePath)
	{
		super(HOUSE_AREA, truePath, "Thurgo's House (The Imcando Dwarf)");
	}
}
