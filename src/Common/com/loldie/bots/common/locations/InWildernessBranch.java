package com.loldie.bots.common.locations;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.region.Players;
public class InWildernessBranch extends LogicBranch
{
	public static final int Y_POS_START = 3523;
	public InWildernessBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
	}
	@Override
	protected boolean condition()
	{
		return Players.getLocal().getPosition().getY() >= Y_POS_START;
	}
}
