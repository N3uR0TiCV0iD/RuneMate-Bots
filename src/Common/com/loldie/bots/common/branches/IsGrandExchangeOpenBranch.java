package com.loldie.bots.common.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.net.GrandExchange;
public class IsGrandExchangeOpenBranch extends LogicBranch
{
	public IsGrandExchangeOpenBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
	}
	@Override
	protected boolean condition()
	{
		return GrandExchange.isOpen();
	}
}
