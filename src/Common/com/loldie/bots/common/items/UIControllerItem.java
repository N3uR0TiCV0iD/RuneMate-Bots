package com.loldie.bots.common.items;
import com.loldie.bots.common.ITimedTaskDoneHandler;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
public class UIControllerItem implements ITimedTaskDoneHandler
{
	private ItemPricePoller pricePoller;
	private String unknownAmountString;
	private String unknownProfitString;
	private ItemName itemName;
	private int itemsPerTask;
	private int bankedItems;
	private int totalItems;
	public UIControllerItem(ItemName itemName, ItemPricePoller pricePoller, int itemsPerTask)
	{
		this.unknownProfitString = "Profit: ? " + itemName.getPluralName() + "/min";
		this.unknownAmountString = "Total " + itemName.getPluralName() + ": ?";
		this.itemsPerTask = itemsPerTask;
		this.pricePoller = pricePoller;
		this.itemName = itemName;
		this.bankedItems = -1;
		this.totalItems = -1;
	}
	public void update()
	{
		if (bankedItems != -1)
		{
			totalItems = bankedItems + Inventory.getQuantity(pricePoller.getItemID());
		}
		pricePoller.update();
	}
	@Override
	public void onTimedTaskDone()
	{
		bankedItems = Bank.getQuantity(pricePoller.getItemID());
	}
	public int getItemPrice()
	{
		return pricePoller.getItemPrice();
	}
	public ItemName getItemName()
	{
		return itemName;
	}
	public int getTotalItems()
	{
		return totalItems;
	}
	public String getUnknownAmountString()
	{
		return unknownAmountString;
	}
	public int getItemsPerTask()
	{
		return itemsPerTask;
	}
	public String getUnknownProfitString()
	{
		return unknownProfitString;
	}
	public String getLastUpdateString()
	{
		return pricePoller.getLastUpdateString();
	}
}
