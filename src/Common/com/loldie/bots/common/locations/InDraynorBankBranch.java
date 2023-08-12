package com.loldie.bots.common.locations;
import com.loldie.bots.common.logic.ILogicNode;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.runemate.game.api.hybrid.location.Coordinate;
public class InDraynorBankBranch extends LogicAreaBranch
{
	public static final Coordinate BANK_POS = new Coordinate(3092, 3243, 0);
	public static final Area BANK_AREA = new Area.Circular(BANK_POS, 10);
	public InDraynorBankBranch(ILogicNode truePath)
	{
		super(BANK_AREA, truePath, "Draynor Bank");
	}
	public InDraynorBankBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(BANK_AREA, truePath, falsePath);
	}
}
