package com.loldie.bots.common.branches;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.items.ItemAmount;
import com.loldie.bots.common.logic.ILogicNode;
public class HasGoldPerItemBranch extends HasGoldBranch
{
	private int goldPerAmount;
	private ItemAmount itemAmount;
	public HasGoldPerItemBranch(ItemAmount itemAmount, int goldPerAmount, ILogicNode truePath, ILogicNode falsePath)
	{
		super(0, truePath, falsePath);
		this.itemAmount = itemAmount;
		this.goldPerAmount = goldPerAmount;
	}
	@Override
	protected boolean condition()
	{
		super.amount = Utils.getNeededGold(itemAmount, goldPerAmount);
		return super.condition();
	}
}
