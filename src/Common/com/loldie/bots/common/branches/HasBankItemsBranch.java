package com.loldie.bots.common.branches;
import com.loldie.bots.common.items.ItemAmount;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
public class HasBankItemsBranch extends LogicBranch
{
	private ItemAmount[] itemAmounts;
	public HasBankItemsBranch(ItemAmount itemAmount, ILogicNode truePath, ILogicNode falsePath)
	{
		this(new ItemAmount[] { itemAmount }, truePath, falsePath);
	}
	public HasBankItemsBranch(ItemAmount[] itemAmounts, ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
		this.itemAmounts = itemAmounts;
	}
	@Override
	protected boolean condition()
	{
		for (ItemAmount currItemAmount : itemAmounts)
		{
			int currItemQuantitySum = 0;
			for (SpriteItem currResult : Bank.newQuery().ids(currItemAmount.getItemID()).results())
			{
				currItemQuantitySum += currResult.getQuantity();
			}
			if (currItemQuantitySum < currItemAmount.getAmount())
			{
				return false;
			}
		}
		return true;
	}
}
