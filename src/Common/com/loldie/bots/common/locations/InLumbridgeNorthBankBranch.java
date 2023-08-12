package com.loldie.bots.common.locations;
import com.loldie.bots.common.logic.ILogicNode;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.runemate.game.api.hybrid.location.Coordinate;
public class InLumbridgeNorthBankBranch extends LogicAreaBranch
{
	public static final Coordinate BANK_POS = new Coordinate(3215, 3260, 0);
	private static final Area BANK_AREA = new Area.Circular(BANK_POS, 10);
	public InLumbridgeNorthBankBranch(ILogicNode truePath)
	{
		super(BANK_AREA, truePath, "Lumbridge-North Bank");
	}
	public InLumbridgeNorthBankBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(BANK_AREA, truePath, falsePath);
	}
}
