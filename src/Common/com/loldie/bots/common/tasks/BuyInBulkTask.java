package com.loldie.bots.common.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.loldie.bots.common.items.ItemAmount;
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
public class BuyInBulkTask extends LogicTask
{
	private ItemAmount[] itemAmounts;
	private boolean amountIsShopRemain;
	public BuyInBulkTask(ItemAmount itemAmount, boolean amountIsShopRemain)
	{
		this(new ItemAmount[] { itemAmount }, amountIsShopRemain);
	}
	public BuyInBulkTask(ItemAmount[] itemAmounts, boolean amountIsShopRemain)
	{
		this.itemAmounts = itemAmounts;
		this.amountIsShopRemain = amountIsShopRemain;
	}
	@Override
	public void runNode()
	{
		notifyNewTask("Buying item(s)...");
		if (amountIsShopRemain)
		{
			shopRemainBuy();
		}
		else
		{
			regularBuy();
		}
	}
	private void regularBuy()
	{
		for (ItemAmount currItemAmount : itemAmounts)
		{
			int currItemQuantity = Inventory.getQuantity(currItemAmount.getItemID());
			if (currItemQuantity < currItemAmount.getAmount())
			{
				Shop.buy(currItemAmount.getItemID(), currItemAmount.getAmount() - currItemQuantity);
			}
		}
	}
	private void shopRemainBuy()
	{
		for (ItemAmount currItemAmount : itemAmounts)
		{
			int currItemQuantity = Shop.getQuantity(currItemAmount.getItemID());
			if (currItemQuantity > currItemAmount.getAmount())
			{
				Shop.buy(currItemAmount.getItemID(), currItemQuantity - currItemAmount.getAmount());
			}
		}
	}
}
