package com.loldie.bots.common.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.rs3.local.hud.interfaces.MoneyPouch;
public class HasGoldBranch extends LogicBranch
{
	private int amount;
	public HasGoldBranch(int amount, ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
		this.amount = amount;
	}
	@Override
	protected boolean condition()
	{
		return MoneyPouch.getContents() >= amount;
	}
}
