package com.loldie.bots.common.branches;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
public class HasGoldBranch extends LogicBranch
{
	protected int amount;
	public HasGoldBranch(int amount, ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
		this.amount = amount;
	}
	@Override
	protected boolean condition()
	{
		return Utils.getPlayerGold() >= amount;
	}
}
