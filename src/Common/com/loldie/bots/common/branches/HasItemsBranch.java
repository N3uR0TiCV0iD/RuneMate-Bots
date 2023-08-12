package com.loldie.bots.common.branches;
import com.loldie.bots.common.items.ItemAmount;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
public class HasItemsBranch extends LogicBranch
{
	private boolean checkAll;
	private ItemAmount[] itemAmounts;
	public HasItemsBranch(ItemAmount itemAmount, boolean checkAll, ILogicNode truePath, ILogicNode falsePath)
	{
		this(new ItemAmount[] { itemAmount }, checkAll, truePath, falsePath);
	}
	public HasItemsBranch(ItemAmount[] itemAmounts, boolean checkAll, ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
		this.checkAll = checkAll;
		this.itemAmounts = itemAmounts;
	}
	@Override
	protected boolean condition()
	{
		for (ItemAmount currItemAmount : itemAmounts)
		{
			int currItemQuantity = Inventory.getQuantity(currItemAmount.getItemID());
			if (checkAll)
			{
				if (currItemQuantity < currItemAmount.getAmount())
				{
					return false;
				}
			}
			else if (currItemQuantity >= currItemAmount.getAmount())
			{
				return true;
			}
		}
		return checkAll; //If we got here after checking all, it means that we have @ least >= the amount requested. If we were only checking ANY, it means we had no >= amount requested
	}
}
