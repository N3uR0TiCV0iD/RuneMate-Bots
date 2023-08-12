package com.loldie.bots.common.locations;
import com.loldie.bots.common.logic.ILogicNode;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.runemate.game.api.hybrid.location.Coordinate;
public class InGrandExchangeBranch extends LogicAreaBranch
{
	public static final Coordinate BANK_POS = new Coordinate(3165, 3485, 0);
	private static final Area BANK_AREA = new Area.Circular(BANK_POS, 15);
	public InGrandExchangeBranch(ILogicNode truePath)
	{
		super(BANK_AREA, truePath, "The Grand Exchange");
	}
	public InGrandExchangeBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(BANK_AREA, truePath, falsePath);
	}
}
