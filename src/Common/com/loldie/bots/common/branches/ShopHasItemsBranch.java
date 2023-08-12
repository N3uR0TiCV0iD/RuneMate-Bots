package com.loldie.bots.common.branches;
import com.loldie.bots.common.items.ItemAmount;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop;
public class ShopHasItemsBranch extends LogicBranch
{
	private ItemAmount itemAmount;
	public ShopHasItemsBranch(ItemAmount itemAmount, ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
		this.itemAmount = itemAmount;
	}
	@Override
	protected boolean condition()
	{
		return Shop.getQuantity(itemAmount.getItemID()) >= itemAmount.getAmount();
	}
}
